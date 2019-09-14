package shop.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoProductService;
import shop.dto.ProductDTO;
import shop.model.BrandFilterMetaData;
import shop.model.Category;
import shop.model.CriteriaBuilderModel;
import shop.model.CriteriaFilterBuilderModel;
import shop.model.FilterDataModel;
import shop.model.FilterMetaData;
import shop.model.PriceFilterMetaData;
import shop.model.Product;
import shop.model.ProductAvail;
import shop.model.SubCategory;
import shop.model.WeightFilterMetaData;
import shop.util.Range;

@Service
public class FetchProductService {

	@Autowired
	DaoProductService daoProductService;

	@PersistenceContext
	private EntityManager em;

	SessionFactory sessionQuery;

	public ProductDTO findByProductAvail(String productCode, String prodAvailId) throws ParseException {
		ProductDTO prodDto = daoProductService.findByProductCodeAndAvail(productCode, prodAvailId);
		return prodDto;

	}

	public Product createProduct(Product prod) throws ParseException {
		return daoProductService.save(prod);
	}

	public List<Product> getProductBySubCategory(String categoryId) throws ParseException {
		List<Product> prodList = daoProductService.findBySubId(categoryId);

		return prodList;

	}

	public List<Product> getProductByName(String productName) {
		List<Product> prodList = daoProductService.findByProductName(productName);
		return prodList;

	}

	public List<Product> getProductBySubCategoryName(String subCategoryName) {
		SubCategory subCategory = daoProductService.findSubCategoryDetails(subCategoryName);
		List<Product> productList = null;
		if (subCategory != null) {
			productList = subCategory.getProductList();
		}
		return productList;

	}

	public Set<Product> getProductByCategoryName(String categoryName) throws ParseException {
		Category category = daoProductService.findCategoryDetails(categoryName);
		Set<Product> productList = new HashSet<Product>();
		if (category != null) {

			category.getSubCategory().forEach(s -> productList.addAll(s.getProductList()));
		}
		return productList;

	}

	public List<Product> getProductByBrand(String brandName) {
		List<Product> productList = daoProductService.findProductByBrand(brandName);
		return productList;

	}

	public List<ProductDTO> getProduct(List<String> productAvailList) {
		List<ProductDTO> prodList = daoProductService.findAllByProductAvail(productAvailList);
		return prodList;

	}

	public List<ProductDTO> getProductByCategory(String categoryId) {
		return daoProductService.findProductByCategory(categoryId);
	}

	public List<Product> getProductBasedOnFilter(String catId, String subId, FilterMetaData filterMetaData) {

		CriteriaBuilderModel cbmodel = getQueryBuilder();
		CriteriaBuilder cb = cbmodel.getCb();
		CriteriaQuery<Product> query = cbmodel.getQuery();
		Join<SubCategory, Product> prodSubJoin = cbmodel.getProdSubJoin();
		Join<ProductAvail, Product> prodAvail = cbmodel.getProdAvail();
		query.select(prodSubJoin);
		List<Predicate> criteria = new ArrayList<Predicate>();
		if (catId != null)
			criteria.add(cb.equal(cbmodel.getSubRoot().get("categoryId"), catId));
		if (subId != null)
			criteria.add(cb.equal(prodSubJoin.get("subId"), subId));

		Optional<Predicate> optPredicateWeight = filterMetaData.getWeightFilters().parallelStream()
				.map(weightFilter -> handleWeightFilter(weightFilter, prodAvail, cb)).reduce((p1, p2) -> cb.or(p1, p2));

		Optional<Predicate> optPredicatePrice = filterMetaData.getPriceFilters().parallelStream()
				.map(priceFilter -> handlePriceFilter(priceFilter, prodAvail, cb)).reduce((p1, p2) -> cb.or(p1, p2));

		Optional<Predicate> optPredicateBrand = filterMetaData.getBrandFilters().parallelStream()
				.map(brandFilter -> handleBrandFilter(brandFilter.getBrandName(), prodSubJoin, cb))
				.reduce((p1, p2) -> cb.or(p1, p2));

		if (optPredicateWeight.isPresent())
			criteria.add(optPredicateWeight.get());
		if (optPredicatePrice.isPresent())
			criteria.add(optPredicatePrice.get());
		if (optPredicateBrand.isPresent())
			criteria.add(optPredicateBrand.get());

		Order order1 = cb.desc(prodAvail.get("price"));
		Order order2 = cb.desc(prodSubJoin.get("name"));
		Order order3 = cb.desc(prodSubJoin.get("brand"));
		query = query.orderBy(order1, order2, order3);
		query.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));

		List<Product> listPod = em.createQuery(query).getResultList();
		return listPod;
	}

	private Predicate handlePriceFilter(PriceFilterMetaData priceFilterMetaData, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		switch (priceFilterMetaData.getFilterCriteria()) {
		case "bw":
			return betweenCasePriceFilter(priceFilterMetaData.getV1(), priceFilterMetaData.getV2(), prodAvail, cb);

		case "lt":
			return lessThanPriceFilter(priceFilterMetaData.getV1(), prodAvail, cb);

		case "gt":
			return greaterThanPriceFilter(priceFilterMetaData.getV1(), prodAvail, cb);

		case "eq":
			return equalToPriceFilter(priceFilterMetaData.getV1(), prodAvail, cb);

		default:
			return null;
		}
	}

	private Predicate equalToPriceFilter(Integer value, Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {

		Predicate predicate = cb.equal(prodAvail.get("price"), value);
		return predicate;
	}

	private Predicate greaterThanPriceFilter(Integer value, Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {
		Predicate predicate = cb.greaterThan(prodAvail.get("price"), value);
		return predicate;
	}

	private Predicate lessThanPriceFilter(Integer value, Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {
		Predicate predicate = cb.lessThan(prodAvail.get("price"), value);
		return predicate;
	}

	private Predicate betweenCasePriceFilter(Integer v1, Integer v2, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate predicate = cb.between(prodAvail.get("price"), v1, v2);
		return predicate;
	}

	private Predicate handleWeightFilter(WeightFilterMetaData weightFilterMetaData,
			Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {

		switch (weightFilterMetaData.getFilterCriteria()) {
		case "bw":
			return betweenCaseWeightFilter(weightFilterMetaData, prodAvail, cb);

		case "lt":
			return lessThanWeightFilter(weightFilterMetaData.getV1(), weightFilterMetaData.getU1(), prodAvail, cb);

		case "gt":
			return greaterThanWeightFilter(weightFilterMetaData, prodAvail, cb);

		case "eq":
			return equalToWeightFilter(weightFilterMetaData, prodAvail, cb);

		default:
			return null;
		}

	}

	private Predicate equalToWeightFilter(WeightFilterMetaData weightFilterMetaData,
			Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {
		Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"), weightFilterMetaData.getU1());
		Predicate predicateStartWeight = cb.equal(prodAvail.get("weight"), weightFilterMetaData.getV1());
		Predicate finalWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
		return finalWeightCriteria;
	}

	public static boolean isNotNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return true;
		return false;
	}

	Predicate betweenCaseWeightFilter(WeightFilterMetaData weightFilterMetaData, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate finalWeightCriteria;
		Integer v1 = weightFilterMetaData.getV1();
		Integer v2 = weightFilterMetaData.getV2();
		String u1 = weightFilterMetaData.getU1();
		String u2 = weightFilterMetaData.getU2();
		if (weightFilterMetaData.getU1().equals(weightFilterMetaData.getU2())) {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"), weightFilterMetaData.getU1());
			Predicate predicateStartWeight = cb.between(prodAvail.get("weight"),
					Integer.valueOf(weightFilterMetaData.getV1()), Integer.valueOf(weightFilterMetaData.getV2()));
			finalWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);

		} else {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"), u1);
			Predicate predicateStartWeight = cb.greaterThanOrEqualTo(prodAvail.get("weight"), v1);
			Predicate predStartWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
			Predicate predicateEndWeightUnit = cb.equal(prodAvail.get("weightUnit"), u2);
			Predicate predicateEndWeight = cb.lessThanOrEqualTo(prodAvail.get("weight"), v2);
			Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);
			finalWeightCriteria = cb.or(predStartWeightCriteria, predEndWeightCriteria);

		}
		return finalWeightCriteria;

	}

	Predicate lessThanWeightFilter(Integer v1, String u1, Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {

		Predicate predicateEndWeightUnit = cb.equal(prodAvail.get("weightUnit"), u1);
		Predicate predicateEndWeight = cb.lessThan(prodAvail.get("weight"), v1);
		Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);

		String smallerWeightUnit = checkWeight(u1, "sm");
		if (isNotNullOrEmpty(smallerWeightUnit)) {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"), smallerWeightUnit);
			Predicate predicateStartWeight = cb.lessThan(prodAvail.get("weight"), 1000);

			Predicate predStartWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
			Predicate finalWeightCriteria = cb.or(predStartWeightCriteria, predEndWeightCriteria);
			return finalWeightCriteria;
		}

		return predEndWeightCriteria;
	}

	Predicate greaterThanWeightFilter(WeightFilterMetaData weightFilterMetaData, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Integer v1 = weightFilterMetaData.getV1();
		String u1 = weightFilterMetaData.getU1();
		Predicate predicateEndWeightUnit = cb.equal(prodAvail.get("weightUnit"), u1);
		Predicate predicateEndWeight = cb.greaterThan(prodAvail.get("weight"), v1);
		Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);
		String greaterWeightUnit = checkWeight(u1, "gt");
		if (isNotNullOrEmpty(greaterWeightUnit)) {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"), greaterWeightUnit);
			Predicate predicateStartWeight = cb.greaterThanOrEqualTo(prodAvail.get("weight"), 1);
			Predicate predStartWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
			Predicate finalWeightCriteria = cb.or(predStartWeightCriteria, predEndWeightCriteria);
			return finalWeightCriteria;
		}

		return predEndWeightCriteria;
	}

	Predicate handleBrandFilter(String brand, Join<SubCategory, Product> prodSubJoin, CriteriaBuilder cb) {
		Predicate predicate = cb.equal(prodSubJoin.get("brand"), brand);
		return predicate;
	}

	String checkWeight(String weightUnit, String crit) {
		CheckWeightUnitService checkWeightService = new CheckWeightUnitService();
		if (crit.equals("gt"))
			return checkWeightService.checkForGreaterWeightUnit(weightUnit);
		else
			return checkWeightService.checkForSmallerWeightUnit(weightUnit);

	}

	public CriteriaFilterBuilderModel getFilterQueryBuilder() {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<FilterDataModel> query = cb.createQuery(FilterDataModel.class);
		Root<SubCategory> subRoot = query.from(SubCategory.class);
		Join<SubCategory, Product> prodSubJoin = subRoot.join("productList");
		Join<ProductAvail, Product> prodAvail = prodSubJoin.join("productAvailList");

		CriteriaFilterBuilderModel criterFilterBuilderModel = new CriteriaFilterBuilderModel(cb, query, prodSubJoin,
				prodAvail);
		criterFilterBuilderModel.setSubRoot(subRoot);

		return criterFilterBuilderModel;
	}

	public CriteriaBuilderModel getQueryBuilder() {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Product> query = cb.createQuery(Product.class);
		Root<SubCategory> subRoot = query.from(SubCategory.class);
		Join<SubCategory, Product> prodSubJoin = subRoot.join("productList");
		Join<ProductAvail, Product> prodAvail = prodSubJoin.join("productAvailList");

		CriteriaBuilderModel criterBuilderModel = new CriteriaBuilderModel(cb, query, prodSubJoin, prodAvail);
		criterBuilderModel.setSubRoot(subRoot);

		return criterBuilderModel;
	}

	public List<Product> getCategoryById(String catId) {
		CriteriaBuilderModel criterBuilderModel = getQueryBuilder();
		List<Predicate> criteria = new ArrayList<Predicate>();
		Root<SubCategory> subRoot = criterBuilderModel.getSubRoot();
		CriteriaBuilder cb = criterBuilderModel.getCb();
		criteria.add(cb.equal(subRoot.get("categoryId"), catId));
		criterBuilderModel.getQuery().where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		return em.createQuery(criterBuilderModel.getQuery()).getResultList();

	}

	public FilterMetaData getFiltersBasedOnCategoryAndSubCategoryId(String categoryId, String subId,
			FilterMetaData filterMetaData) {
		CriteriaFilterBuilderModel cbmodel = getFilterQueryBuilder();
		CriteriaBuilder cb = cbmodel.getCb();
		CriteriaQuery<FilterDataModel> query = cbmodel.getQuery();
		Join<SubCategory, Product> prodSubJoin = cbmodel.getProdSubJoin();
		Join<ProductAvail, Product> prodAvail = cbmodel.getProdAvail();
		query.multiselect(prodSubJoin.get("brand"), prodAvail.get("weight"), prodAvail.get("weightUnit"),
				prodAvail.get("price"));

		List<Predicate> criteria = new ArrayList<Predicate>();
		if (categoryId != null)
			criteria.add(cb.equal(cbmodel.getSubRoot().get("categoryId"), categoryId));
		if (subId != null)
			criteria.add(cb.equal(prodSubJoin.get("subId"), subId));

		Optional<Predicate> optPredicateWeight = filterMetaData.getWeightFilters().parallelStream()
				.map(weightFilter -> handleWeightFilter(weightFilter, prodAvail, cb)).reduce((p1, p2) -> cb.or(p1, p2));

		Optional<Predicate> optPredicatePrice = filterMetaData.getPriceFilters().parallelStream()
				.map(priceFilter -> handlePriceFilter(priceFilter, prodAvail, cb)).reduce((p1, p2) -> cb.or(p1, p2));

		Optional<Predicate> optPredicateBrand = filterMetaData.getBrandFilters().parallelStream()
				.map(brandFilter -> handleBrandFilter(brandFilter.getBrandName(), prodSubJoin, cb))
				.reduce((p1, p2) -> cb.or(p1, p2));

		if (optPredicateWeight.isPresent())
			criteria.add(optPredicateWeight.get());
		if (optPredicatePrice.isPresent())
			criteria.add(optPredicatePrice.get());
		if (optPredicateBrand.isPresent())
			criteria.add(optPredicateBrand.get());

		Order order1 = cb.asc(prodAvail.get("weight"));
		query = query.orderBy(order1);
		query.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));

		List<FilterDataModel> prodList = em.createQuery(query).getResultList();
		if(prodList==null || prodList.isEmpty())
		{
			return filterMetaData;
		}
		FilterMetaData filterMetaDataResponse = new FilterMetaData();
		Map<String, Set<Integer>> map = new HashMap<>();
		
			prodList.forEach(prod -> {
				if (map.get(prod.getWeightUnit()) == null) {
					Set<Integer> productList = new TreeSet<Integer>();
					productList.add(prod.getWeight());
					map.put(prod.getWeightUnit(), productList);
				} else {
					map.get(prod.getWeightUnit()).add(prod.getWeight());
				}

			});
			List<WeightFilterMetaData> filterWeightIntervalList = new ArrayList<WeightFilterMetaData>();

			for (Map.Entry<String, Set<Integer>> entry : map.entrySet()) {
				Set<Integer> set = entry.getValue();

				set.forEach(w -> {
					WeightFilterMetaData weightFilterMetaData = new WeightFilterMetaData();
					if (filterWeightIntervalList.size() > 0) {
						WeightFilterMetaData lastAdded = filterWeightIntervalList
								.get(filterWeightIntervalList.size() - 1);
						OptionalInt containsValue = IntStream.rangeClosed(lastAdded.getV1(), lastAdded.getV2())
								.filter(p -> p == w).findAny();
						if (!containsValue.isPresent()) {
							
							createWeightFilterMetaData(weightFilterMetaData, entry, w,filterMetaData);
							filterWeightIntervalList.add(weightFilterMetaData);
						} else if (containsValue.isPresent() && !(lastAdded.getU1().equals(entry.getKey()))) {
							
							createWeightFilterMetaData(weightFilterMetaData, entry, w,filterMetaData);
							filterWeightIntervalList.add(weightFilterMetaData);
						}
					} else {
						
						createWeightFilterMetaData(weightFilterMetaData, entry, w,filterMetaData);
						filterWeightIntervalList.add(weightFilterMetaData);
					}

				}

				);

			}
			filterMetaDataResponse.setWeightFilters(filterWeightIntervalList);
		
		Set<PriceFilterMetaData> filterPriceIntervalList = null;
			filterPriceIntervalList = prodList.stream().map(prod -> fetchPriceIntervals(prod.getPrice(),filterMetaData)).sorted()
					.collect(Collectors.toSet());
		Set<BrandFilterMetaData> brandFilterIntervalList = null;
			brandFilterIntervalList = prodList.stream().map(prod -> createBranFilterMetaData(prod.getBrand(),filterMetaData)).sorted()
					.collect(Collectors.toSet());

		filterMetaDataResponse.setPriceFilters(filterPriceIntervalList);
		filterMetaDataResponse.setBrandFilters(brandFilterIntervalList);
		return filterMetaDataResponse;
	}

	BrandFilterMetaData createBranFilterMetaData(String brand,FilterMetaData filterMetaData) {
		BrandFilterMetaData brandFilterMetaData = new BrandFilterMetaData();
		brandFilterMetaData.setBrandName(brand);
		if(filterMetaData.getBrandFilters().contains(brandFilterMetaData))
		{
			brandFilterMetaData.setFlag(true);
		}
		return brandFilterMetaData;
	}

	public PriceFilterMetaData fetchPriceIntervals(Double price,FilterMetaData filterMetaData) {
		PriceFilterMetaData priceFilterMetaData = new PriceFilterMetaData();
		Range range = getSmallerEndRange(price.intValue());
		priceFilterMetaData.setV1(range.getMin());
		priceFilterMetaData.setV2(range.getMax());
		priceFilterMetaData.setFilterCriteria("-");
		if(filterMetaData.getPriceFilters().contains(priceFilterMetaData))
		{
			priceFilterMetaData.setFlag(true);
		}
		return priceFilterMetaData;

	}

	void createWeightFilterMetaData(WeightFilterMetaData weightFilterMetaData, Map.Entry<String, Set<Integer>> entry,
			Integer w,FilterMetaData filterMetaData) {

		Set<WeightFilterMetaData> setWeightFilter=new HashSet<WeightFilterMetaData>(filterMetaData.getWeightFilters());
		weightFilterMetaData.setV1(w);
		weightFilterMetaData.setU1(entry.getKey());
		if (entry.getKey().equals("kg") || entry.getKey().equals("litre")) {
			Range range = getGreaterWeightEndRange(w);
			weightFilterMetaData.setV1(range.getMin());
			weightFilterMetaData.setV2(range.getMax());
		} else {
			Range range = getSmallerEndRange(w);
			weightFilterMetaData.setV1(range.getMin());
			weightFilterMetaData.setV2(range.getMax());

		}
		weightFilterMetaData.setU2(entry.getKey());
		weightFilterMetaData.setFilterCriteria("-");
		if(setWeightFilter.contains(weightFilterMetaData))
		{
			weightFilterMetaData.setFlag(true);
		}
		
	}

	Range getSmallerEndRange(int weight) {
		int w = 50;

		if (weight <= 50) {
			Range range = new Range(21, 50);
			return range;
		}
		for (int i = 0; i < 6; i++) {

			if (i >= 4) {
				OptionalInt containsValue = IntStream.rangeClosed(w + 1, w * 2).filter(p -> p == weight).findAny();
				if (containsValue.isPresent()) {
					Range range = new Range(w + 1, w * 2);
					return range;
				}
				w = w * 2;
			} else {

				OptionalInt containsValue = IntStream.rangeClosed(w + 1, w + 50).filter(p -> p == weight).findAny();
				if (containsValue.isPresent()) {
					Range range = new Range(w + 1, w + 50);
					return range;
				}
				w = w + 50;
			}

		}
		return new Range(w, w + 50);
	}

	Range getGreaterWeightEndRange(int weight) {

		if (weight <= 5) {
			Range range = new Range(1, 5);
			return range;
		} else
			return new Range(weight, weight + 5);
	}

}
