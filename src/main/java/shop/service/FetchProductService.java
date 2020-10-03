package shop.service;

import static shop.constant.Constants.AND;
import static shop.constant.Constants.BETWEEN;
import static shop.constant.Constants.BRAND;
import static shop.constant.Constants.BW;
import static shop.constant.Constants.DOT;
import static shop.constant.Constants.EQ;
import static shop.constant.Constants.EQUAL;
import static shop.constant.Constants.GREATER_THAN;
import static shop.constant.Constants.GT;
import static shop.constant.Constants.LEFT_PARANTHESIS;
import static shop.constant.Constants.LESS_THAN;
import static shop.constant.Constants.LT;
import static shop.constant.Constants.OR;
import static shop.constant.Constants.PRICE;
import static shop.constant.Constants.PROD;
import static shop.constant.Constants.PROD_AVAIL;
import static shop.constant.Constants.RIGHT_PARANTHESIS;
import static shop.constant.Constants.SINGLE_QUOTE;
import static shop.constant.Constants.WEIGHT;
import static shop.constant.Constants.WEIGHT_UNIT;

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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
import shop.model.ProductFilter;
import shop.model.SearchProduct;
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

	public Product findByProductCode(String productCode) throws ParseException {
		Optional<Product> prod = daoProductService.findById(productCode);
		return prod.get();

	}

	public Product createProduct(Product prod) throws ParseException {
		return daoProductService.save(prod);
	}

	public List<Product> getProductBySubCategory(String categoryId) throws ParseException {
		List<Product> prodList = daoProductService.findBySubId(categoryId);

		return prodList;

	}

	public List<ProductFilter> getProductByName(String productName) {
		List<ProductFilter> prodList = daoProductService.findByProductName(productName);
		
		return prodList;

	}
	
	public List<SearchProduct> getProductBasedOnFilter(String productName, FilterMetaData filterMetaData) {
		String queryString = "select new shop.model.SearchProduct(cat.id,prod) from Product prod join SubCategory sub  on prod.subId=sub.id join Category cat on sub.categoryId=cat.id join ProductAvail prodAvail on prod.code=prodAvail.productId where (((soundex(prod.brand)=soundex(:productName) and  prod.brand like concat('%', SUBSTRING(:productName,1,2), '%') ) or  prod.brand like  CONCAT('%', :productName,'%')) or ((soundex(prod.name)=soundex(:productName) and  prod.name like concat('%', SUBSTRING(:productName,1,2), '%') ) or prod.name like  CONCAT('%', :productName,'%')) or ((soundex(sub.name)=soundex( :productName) and sub.name like concat('%', SUBSTRING(:productName,1,2), '%')) \r\n"
				+ "or sub.name like  CONCAT('%', :productName,'%')) or ((soundex(cat.name)=soundex(:productName) and  cat.name like concat('%', SUBSTRING(:productName,1,2), '%')) or cat.name like  CONCAT('%',  :productName,'%') ) ) ";
		String weightFilterMetadataString = "";
		int weightCount = 0;
		for (WeightFilterMetaData weightFilterMetadata : filterMetaData.getWeightFilters()) {
			weightFilterMetadataString = weightFilterMetadataString + LEFT_PARANTHESIS
					+ handleSearchWeightFilter(weightFilterMetadata) + RIGHT_PARANTHESIS;
			weightCount++;
			if (weightCount < filterMetaData.getWeightFilters().size()) {
				weightFilterMetadataString = weightFilterMetadataString + OR;
			}
		}
		if (weightFilterMetadataString.length() > 0)
			queryString = queryString + AND + LEFT_PARANTHESIS + weightFilterMetadataString + RIGHT_PARANTHESIS;

		String brandFilterMetadataString = "";
		int countBrand = 0;
		for (BrandFilterMetaData brandFilterMetadata : filterMetaData.getBrandFilters()) {
			brandFilterMetadataString = brandFilterMetadataString + LEFT_PARANTHESIS + handleBrand(brandFilterMetadata)
					+ RIGHT_PARANTHESIS;
			countBrand++;
			if (countBrand < filterMetaData.getBrandFilters().size()) {
				brandFilterMetadataString = brandFilterMetadataString + OR;
			}
		}

		if (brandFilterMetadataString.length() > 0)
			queryString = queryString + OR + LEFT_PARANTHESIS + brandFilterMetadataString + RIGHT_PARANTHESIS;

		int countPrice = 0;
		String priceFilterMetadataString = "";
		for (PriceFilterMetaData priceFilterMetadata : filterMetaData.getPriceFilters()) {
			priceFilterMetadataString = priceFilterMetadataString + LEFT_PARANTHESIS
					+ handleSearchPriceFilter(priceFilterMetadata) + RIGHT_PARANTHESIS;
			countPrice++;
			if (countPrice < filterMetaData.getPriceFilters().size()) {
				priceFilterMetadataString = priceFilterMetadataString + OR;
			}
		}

		if (priceFilterMetadataString.length() > 0)
			queryString = queryString + AND + LEFT_PARANTHESIS + priceFilterMetadataString + RIGHT_PARANTHESIS;
		TypedQuery<SearchProduct> query = em.createQuery(queryString,SearchProduct.class);
		query.setParameter("productName", productName);
		List<SearchProduct> prodList = query.getResultList();
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
		List<ProductDTO> prodList = new ArrayList<>();
		if (productAvailList.size() > 0)
			prodList = daoProductService.findAllByProductAvail(productAvailList);
		return prodList;

	}

	public List<ProductDTO> getProductByCategory(String categoryId) {
		return daoProductService.findProductByCategory(categoryId);
	}

	public List<SearchProduct> getProductBasedOnFilter(String catId, String subId, FilterMetaData filterMetaData) {

		CriteriaBuilderModel cbmodel = getQueryBuilder();
		CriteriaBuilder cb = cbmodel.getCb();
		CriteriaQuery<SearchProduct> query = cbmodel.getQuery();
		Join<SubCategory, Product> prodSubJoin = cbmodel.getProdSubJoin();
		Join<ProductAvail, Product> prodAvail = cbmodel.getProdAvail();
		query.multiselect(cbmodel.getSubRoot().get("categoryId"),prodSubJoin);
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

		Order order1 = cb.desc(prodAvail.get(PRICE));
		Order order2 = cb.desc(prodSubJoin.get("name"));
		Order order3 = cb.desc(prodSubJoin.get(BRAND));
		query = query.orderBy(order1, order2, order3);
		query.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));

		List<SearchProduct> listPod = em.createQuery(query).getResultList();
		
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

		Predicate predicate = cb.equal(prodAvail.get(PRICE), value);
		return predicate;
	}

	private Predicate greaterThanPriceFilter(Integer value, Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {
		Predicate predicate = cb.greaterThan(prodAvail.get(PRICE), value);
		return predicate;
	}

	private Predicate lessThanPriceFilter(Integer value, Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {
		Predicate predicate = cb.lessThan(prodAvail.get(PRICE), value);
		return predicate;
	}

	private Predicate betweenCasePriceFilter(Integer v1, Integer v2, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate predicate = cb.between(prodAvail.get(PRICE), v1, v2);
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
		Predicate predicateStartWeightUnit = cb.equal(prodAvail.get(WEIGHT_UNIT), weightFilterMetaData.getU1());
		Predicate predicateStartWeight = cb.equal(prodAvail.get(WEIGHT), weightFilterMetaData.getV1());
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
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get(WEIGHT_UNIT), weightFilterMetaData.getU1());
			Predicate predicateStartWeight = cb.between(prodAvail.get(WEIGHT),
					Integer.valueOf(weightFilterMetaData.getV1()), Integer.valueOf(weightFilterMetaData.getV2()));
			finalWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);

		} else {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get(WEIGHT_UNIT), u1);
			Predicate predicateStartWeight = cb.greaterThanOrEqualTo(prodAvail.get(WEIGHT), v1);
			Predicate predStartWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
			Predicate predicateEndWeightUnit = cb.equal(prodAvail.get(WEIGHT_UNIT), u2);
			Predicate predicateEndWeight = cb.lessThanOrEqualTo(prodAvail.get(WEIGHT), v2);
			Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);
			finalWeightCriteria = cb.or(predStartWeightCriteria, predEndWeightCriteria);

		}
		return finalWeightCriteria;

	}

	Predicate lessThanWeightFilter(Integer v1, String u1, Join<ProductAvail, Product> prodAvail, CriteriaBuilder cb) {

		Predicate predicateEndWeightUnit = cb.equal(prodAvail.get(WEIGHT_UNIT), u1);
		Predicate predicateEndWeight = cb.lessThan(prodAvail.get(WEIGHT), v1);
		Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);

		String smallerWeightUnit = checkWeight(u1, "sm");
		if (isNotNullOrEmpty(smallerWeightUnit)) {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get(WEIGHT_UNIT), smallerWeightUnit);
			Predicate predicateStartWeight = cb.lessThan(prodAvail.get(WEIGHT), 1000);

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
		Predicate predicateEndWeightUnit = cb.equal(prodAvail.get(WEIGHT_UNIT), u1);
		Predicate predicateEndWeight = cb.greaterThan(prodAvail.get(WEIGHT), v1);
		Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);
		String greaterWeightUnit = checkWeight(u1, "gt");
		if (isNotNullOrEmpty(greaterWeightUnit)) {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get(WEIGHT_UNIT), greaterWeightUnit);
			Predicate predicateStartWeight = cb.greaterThanOrEqualTo(prodAvail.get(WEIGHT), 1);
			Predicate predStartWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
			Predicate finalWeightCriteria = cb.or(predStartWeightCriteria, predEndWeightCriteria);
			return finalWeightCriteria;
		}

		return predEndWeightCriteria;
	}

	Predicate handleBrandFilter(String brand, Join<SubCategory, Product> prodSubJoin, CriteriaBuilder cb) {
		Predicate predicate = cb.equal(prodSubJoin.get(BRAND), brand);
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
		Root<Category> catRoot = query.from(Category.class);
		Join<Category, SubCategory> catSubJoin = catRoot.join("subCategory");
		Join<SubCategory, Product> prodSubJoin = catSubJoin.join("productList");
		Join<ProductAvail, Product> prodAvail = prodSubJoin.join("productAvailList");

		CriteriaFilterBuilderModel criterFilterBuilderModel = new CriteriaFilterBuilderModel(cb, query, prodSubJoin,
				prodAvail, catSubJoin);
		criterFilterBuilderModel.setCatRoot(catRoot);

		return criterFilterBuilderModel;
	}

	public CriteriaBuilderModel getQueryBuilder() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SearchProduct> query = cb.createQuery(SearchProduct.class);
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
	//	return em.createQuery(criterBuilderModel.getQuery()).getResultList();
return null;
	}

	public FilterMetaData getFiltersBasedOnCategoryAndSubCategoryId(String categoryId, String subId, String search,
			FilterMetaData filterMetaData) {
		CriteriaFilterBuilderModel cbmodel = getFilterQueryBuilder();
		CriteriaBuilder cb = cbmodel.getCb();
		CriteriaQuery<FilterDataModel> query = cbmodel.getQuery();
		Join<SubCategory, Product> prodSubJoin = cbmodel.getProdSubJoin();
		Join<ProductAvail, Product> prodAvail = cbmodel.getProdAvail();
		Join<Category, SubCategory> catSubJoin = cbmodel.getCatSubJoin();
		query.multiselect(prodSubJoin.get(BRAND), prodAvail.get(WEIGHT), prodAvail.get(WEIGHT_UNIT),
				prodAvail.get(PRICE)).distinct(true);

		List<Predicate> criteria = new ArrayList<Predicate>();
		if (categoryId != null)
			criteria.add(cb.equal(cbmodel.getCatRoot().get("id"), categoryId));
		if (subId!=null )
			criteria.add(cb.equal(prodSubJoin.get("subId"), subId));
		if (search != null)
			criteria.add(filterMetaDataOnSearch(search, catSubJoin, prodSubJoin, cb, cbmodel));

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

		Order order1 = cb.asc(prodAvail.get(WEIGHT));
		query = query.orderBy(order1);
		query.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));

		List<FilterDataModel> prodList = em.createQuery(query).getResultList();
		if (prodList == null || prodList.isEmpty()) {
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
					WeightFilterMetaData lastAdded = filterWeightIntervalList.get(filterWeightIntervalList.size() - 1);
					OptionalInt containsValue = IntStream.rangeClosed(lastAdded.getV1(), lastAdded.getV2())
							.filter(p -> p == w).findAny();
					if (!containsValue.isPresent()) {

						createWeightFilterMetaData(weightFilterMetaData, entry, w, filterMetaData);
						filterWeightIntervalList.add(weightFilterMetaData);
					} else if (containsValue.isPresent() && !(lastAdded.getU1().equals(entry.getKey()))) {

						createWeightFilterMetaData(weightFilterMetaData, entry, w, filterMetaData);
						filterWeightIntervalList.add(weightFilterMetaData);
					}
				} else {

					createWeightFilterMetaData(weightFilterMetaData, entry, w, filterMetaData);
					filterWeightIntervalList.add(weightFilterMetaData);
				}

			}

			);

		}
		filterMetaDataResponse.setWeightFilters(filterWeightIntervalList);

		Set<PriceFilterMetaData> filterPriceIntervalList = null;
		filterPriceIntervalList = prodList.stream().map(prod -> fetchPriceIntervals(prod.getPrice(), filterMetaData))
				.sorted().collect(Collectors.toSet());
		Set<BrandFilterMetaData> brandFilterIntervalList = null;
		brandFilterIntervalList = prodList.stream()
				.map(prod -> createBranFilterMetaData(prod.getBrand(), filterMetaData)).sorted()
				.collect(Collectors.toSet());

		filterMetaDataResponse.setPriceFilters(filterPriceIntervalList);
		filterMetaDataResponse.setBrandFilters(brandFilterIntervalList);
		
		filterMetaDataResponse.setBrandFlag(filterMetaData.getBrandFlag());
		filterMetaDataResponse.setPriceFlag(filterMetaData.getPriceFlag());
		filterMetaDataResponse.setWeightFlag(filterMetaData.getWeightFlag());
		return filterMetaDataResponse;
	}

	BrandFilterMetaData createBranFilterMetaData(String brand, FilterMetaData filterMetaData) {
		BrandFilterMetaData brandFilterMetaData = new BrandFilterMetaData();
		brandFilterMetaData.setBrandName(brand);
		if (filterMetaData.getBrandFilters().contains(brandFilterMetaData)) {
			brandFilterMetaData.setFlag(true);
		}
		return brandFilterMetaData;
	}

	public PriceFilterMetaData fetchPriceIntervals(Double price, FilterMetaData filterMetaData) {
		PriceFilterMetaData priceFilterMetaData = new PriceFilterMetaData();
		Range range = getSmallerEndRange(price.intValue());
		priceFilterMetaData.setV1(range.getMin());
		priceFilterMetaData.setV2(range.getMax());
		priceFilterMetaData.setFilterCriteria("-");
		if (filterMetaData.getPriceFilters().contains(priceFilterMetaData)) {
			priceFilterMetaData.setFlag(true);
		}
		return priceFilterMetaData;

	}

	void createWeightFilterMetaData(WeightFilterMetaData weightFilterMetaData, Map.Entry<String, Set<Integer>> entry,
			Integer w, FilterMetaData filterMetaData) {

		Set<WeightFilterMetaData> setWeightFilter = new HashSet<WeightFilterMetaData>(
				filterMetaData.getWeightFilters());
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
		if (setWeightFilter.contains(weightFilterMetaData)) {
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

	Predicate filterMetaDataOnSearch(String search, Join<Category, SubCategory> catSubJoin,
			Join<SubCategory, Product> prodSubJoin, CriteriaBuilder cb, CriteriaFilterBuilderModel cbmodel) {

		Predicate brandPred = cb.like(cbmodel.getProdSubJoin().get(BRAND), "%" + search + "%");
		Predicate prodNamePred = cb.like(cbmodel.getProdSubJoin().get("name"), "%" + search + "%");
		Predicate catSubPred = cb.like(cbmodel.getCatSubJoin().get("name"), "%" + search + "%");
		Predicate catNamePred = cb.like(cbmodel.getCatRoot().get("name"), "%" + search + "%");
		Predicate orProdSearch = cb.or(brandPred, prodNamePred, catSubPred, catNamePred);
		return orProdSearch;

	}

	public FilterMetaData getFiltersBasedOnSearch(String search, FilterMetaData filterMetaData) {
		

		String queryString = "with findCat as(\r\n" + 
				"				select cat.id as mainId from Product prod join SubCategory sub  on prod.subId=sub.id join Category cat on sub.categoryId=cat.id  where ((soundex(prod.brand)=soundex(:productName) and  prod.brand like concat('%', SUBSTRING(:productName,1,2), '%') ) or  prod.brand like  CONCAT('%',:productName,'%'))\r\n" + 
				"				or ((soundex(prod.name)=soundex(:productName) and  prod.name like concat('%', SUBSTRING(:productName,1,2), '%') ) or prod.name like  CONCAT('%',:productName,'%'))\r\n" + 
				"				\r\n" + 
				"				 or ((soundex(sub.name)=soundex( :productName) and sub.name like concat('%', SUBSTRING('',1,2), '%')) \r\n" + 
				"							 or sub.name like  CONCAT('%', :productName,'%')) or ((soundex(cat.name)=soundex(:productName) and  cat.name like concat('%', SUBSTRING(:productName,1,2), '%')) or cat.name like  CONCAT('%',  :productName,'%') )\r\n" + 
				"				           \r\n" + 
				"				 )\r\n" + 
				"				 \r\n" + 
				"				 select prod.brand,prodAvail.weight,prodAvail.weightUnit,prodAvail.price from Product prod join SubCategory sub on prod.subId=sub.id join  findCat on findCat.mainId=sub.categoryId\r\n" + 
				"				join Product_Avail prodAvail on prod.code=prodAvail.productId"  
				;
				
		String weightFilterMetadataString = "";
		int weightCount = 0;
		for (WeightFilterMetaData weightFilterMetadata : filterMetaData.getWeightFilters()) {
			weightFilterMetadataString = weightFilterMetadataString + LEFT_PARANTHESIS
					+ handleSearchWeightFilter(weightFilterMetadata) + RIGHT_PARANTHESIS;
			weightCount++;
			if (weightCount < filterMetaData.getWeightFilters().size()) {
				weightFilterMetadataString = weightFilterMetadataString + OR;
			}
		}
		if (weightFilterMetadataString.length() > 0)
			queryString = queryString + AND + LEFT_PARANTHESIS + weightFilterMetadataString + RIGHT_PARANTHESIS;

		String brandFilterMetadataString = "";
		int countBrand = 0;
		for (BrandFilterMetaData brandFilterMetadata : filterMetaData.getBrandFilters()) {
			brandFilterMetadataString = brandFilterMetadataString + LEFT_PARANTHESIS + handleBrand(brandFilterMetadata)
					+ RIGHT_PARANTHESIS;
			countBrand++;
			if (countBrand < filterMetaData.getBrandFilters().size()) {
				brandFilterMetadataString = brandFilterMetadataString + OR;
			}
		}

		if (brandFilterMetadataString.length() > 0)
			queryString = queryString + AND + LEFT_PARANTHESIS + brandFilterMetadataString + RIGHT_PARANTHESIS;

		int countPrice = 0;
		String priceFilterMetadataString = "";
		for (PriceFilterMetaData priceFilterMetadata : filterMetaData.getPriceFilters()) {
			priceFilterMetadataString = priceFilterMetadataString + LEFT_PARANTHESIS
					+ handleSearchPriceFilter(priceFilterMetadata) + RIGHT_PARANTHESIS;
			countPrice++;
			if (countPrice < filterMetaData.getPriceFilters().size()) {
				priceFilterMetadataString = priceFilterMetadataString + OR;
			}
		}

		if (priceFilterMetadataString.length() > 0)
			queryString = queryString + AND + LEFT_PARANTHESIS + priceFilterMetadataString + RIGHT_PARANTHESIS;
		Query query = em.createNativeQuery(queryString);
		query.setParameter("productName", search);
		List<Object[]> queryList =(List<Object[]>) query.getResultList();
		List<FilterDataModel> prodList=parseQueryList(queryList);

		if (prodList == null || prodList.isEmpty()) {
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
					WeightFilterMetaData lastAdded = filterWeightIntervalList.get(filterWeightIntervalList.size() - 1);
					OptionalInt containsValue = IntStream.rangeClosed(lastAdded.getV1(), lastAdded.getV2())
							.filter(p -> p == w).findAny();
					if (!containsValue.isPresent()) {

						createWeightFilterMetaData(weightFilterMetaData, entry, w, filterMetaData);
						filterWeightIntervalList.add(weightFilterMetaData);
					} else if (containsValue.isPresent() && !(lastAdded.getU1().equals(entry.getKey()))) {

						createWeightFilterMetaData(weightFilterMetaData, entry, w, filterMetaData);
						filterWeightIntervalList.add(weightFilterMetaData);
					}
				} else {

					createWeightFilterMetaData(weightFilterMetaData, entry, w, filterMetaData);
					filterWeightIntervalList.add(weightFilterMetaData);
				}

			}

			);

		}
		filterMetaDataResponse.setWeightFilters(filterWeightIntervalList);

		Set<PriceFilterMetaData> filterPriceIntervalList = null;
		filterPriceIntervalList = prodList.stream().map(prod -> fetchPriceIntervals(prod.getPrice(), filterMetaData))
				.sorted().collect(Collectors.toSet());
		Set<BrandFilterMetaData> brandFilterIntervalList = null;
		brandFilterIntervalList = prodList.stream()
				.map(prod -> createBranFilterMetaData(prod.getBrand(), filterMetaData)).sorted()
				.collect(Collectors.toSet());

		filterMetaDataResponse.setPriceFilters(filterPriceIntervalList);
		filterMetaDataResponse.setBrandFilters(brandFilterIntervalList);
		filterMetaDataResponse.setBrandFlag(filterMetaData.getBrandFlag());
		filterMetaDataResponse.setPriceFlag(filterMetaData.getPriceFlag());
		filterMetaDataResponse.setWeightFlag(filterMetaData.getWeightFlag());
		return filterMetaDataResponse;
	}

	private List<FilterDataModel> parseQueryList(List<Object[]> queryList) {
		if(queryList.size()>0)
		{
		List<FilterDataModel> list=new ArrayList<FilterDataModel>();
		for(Object[] obj:queryList)
		{
			String brand=(String)obj[0];
			Integer weight		=(Integer) obj[1]; 
			String weightUnit=		(String) obj[2]; 
			Double price=		(Double) obj[3];
			FilterDataModel filterModel=new FilterDataModel(brand,weight, weightUnit, price);
			list.add(filterModel);
		}
		 
		return list;
		}
		else
		
		return null;
	}

	private String handleBrand(BrandFilterMetaData brandFilterMetaData) {

		return PROD + DOT + BRAND + EQUAL + SINGLE_QUOTE + brandFilterMetaData.getBrandName() + SINGLE_QUOTE;

	}

	private String handleSearchWeightFilter(WeightFilterMetaData filterMetaData) {
		switch (filterMetaData.getFilterCriteria()) {
		case BW:
			return weightBetweenQuery(filterMetaData);

		case LT:
			return weightLessThanQuery(filterMetaData);

		case GT:
			return weightGreaterThanQuery(filterMetaData);

		case EQ:
			return weightEqualQuery(filterMetaData);

		default:
			return null;
		}

	}

	private String handleSearchPriceFilter(PriceFilterMetaData filterMetaData) {
		switch (filterMetaData.getFilterCriteria()) {
		case BW:
			return priceBetweenQuery(filterMetaData);

		case LT:
			return priceLessThanQuery(filterMetaData);

		case GT:
			return priceGreaterThanQuery(filterMetaData);

		case EQ:
			return priceEqualQuery(filterMetaData);

		default:
			return null;
		}

	}

	private String priceEqualQuery(PriceFilterMetaData filterMetaData) {

		return PROD_AVAIL + DOT + PRICE + EQUAL + filterMetaData.getV1();
	}

	private String priceGreaterThanQuery(PriceFilterMetaData filterMetaData) {

		return PROD_AVAIL + DOT + PRICE + GREATER_THAN + filterMetaData.getV1();
	}

	private String priceLessThanQuery(PriceFilterMetaData filterMetaData) {

		return PROD_AVAIL + DOT + PRICE + LESS_THAN + filterMetaData.getV1();
	}

	private String priceBetweenQuery(PriceFilterMetaData filterMetaData) {

		return PROD_AVAIL + DOT + PRICE + BETWEEN + filterMetaData.getV1() + AND + filterMetaData.getV2();
	}

	private String weightBetweenQuery(WeightFilterMetaData filterMetaData) {

		if (filterMetaData.getU1().equals(filterMetaData.getU2())) {
			return PROD_AVAIL + DOT + WEIGHT + BETWEEN + filterMetaData.getV1() + AND + filterMetaData.getV2() + AND
					+ PROD_AVAIL + DOT + WEIGHT_UNIT + EQUAL + SINGLE_QUOTE + filterMetaData.getU1() + SINGLE_QUOTE;
		} else {
			String subQueryString = LEFT_PARANTHESIS + PROD_AVAIL + DOT + WEIGHT + BETWEEN + filterMetaData.getV1()
					+ AND + 1000 + AND + PROD_AVAIL + DOT + WEIGHT_UNIT + EQUAL + SINGLE_QUOTE + filterMetaData.getU1()
					+ SINGLE_QUOTE + RIGHT_PARANTHESIS;
			subQueryString = subQueryString + OR + LEFT_PARANTHESIS + PROD_AVAIL + DOT + WEIGHT + BETWEEN + 1 + AND
					+ filterMetaData.getV2() + AND + PROD_AVAIL + DOT + WEIGHT_UNIT + EQUAL + SINGLE_QUOTE
					+ filterMetaData.getU2() + SINGLE_QUOTE + RIGHT_PARANTHESIS;
			return subQueryString;
		}

	}

	private String weightLessThanQuery(WeightFilterMetaData filterMetaData) {

		return PROD_AVAIL + DOT + WEIGHT + LESS_THAN + filterMetaData.getV1() + AND + PROD_AVAIL + DOT + WEIGHT_UNIT
				+ EQUAL + SINGLE_QUOTE + filterMetaData.getU1() + SINGLE_QUOTE;

	}

	private String weightGreaterThanQuery(WeightFilterMetaData filterMetaData) {

		return PROD_AVAIL + DOT + WEIGHT + GREATER_THAN + filterMetaData.getV1() + AND + PROD_AVAIL + DOT + WEIGHT_UNIT
				+ EQUAL + SINGLE_QUOTE + filterMetaData.getU1() + SINGLE_QUOTE;

	}

	private String weightEqualQuery(WeightFilterMetaData filterMetaData) {

		return PROD_AVAIL + DOT + WEIGHT + EQUAL + filterMetaData.getV1() + AND + PROD_AVAIL + DOT + WEIGHT_UNIT + EQUAL
				+ SINGLE_QUOTE + filterMetaData.getU1() + SINGLE_QUOTE;

	}

	
	
}
