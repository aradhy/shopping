package shop.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.LongStream;

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
import shop.model.Category;
import shop.model.CriteriaBuilderModel;
import shop.model.FilterMetaData;
import shop.model.PriceFilterMetaData;
import shop.model.Product;
import shop.model.ProductAvail;
import shop.model.SubCategory;
import shop.model.WeightFilterMetaData;

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

	public List<Product> getProductBasedOnFilter(String catId, String subId, Map<String, String> mapFilter) {

		CriteriaBuilderModel cbmodel = getQueryBuilder();
		CriteriaBuilder cb = cbmodel.getCb();
		CriteriaQuery<Product> query = cbmodel.getQuery();
		Join<SubCategory, Product> prodSubJoin = cbmodel.getProdSubJoin();
		Join<ProductAvail, Product> prodAvail = cbmodel.getProdAvail();
		query.select(prodSubJoin);
		List<Predicate> criteria = new ArrayList<Predicate>();
		criteria.add(cb.equal(cbmodel.getSubRoot().get("categoryId"), catId));
		criteria.add(cb.equal(prodSubJoin.get("subId"), subId));
		switch (mapFilter.get("filterType")) {
		case "weight":
			criteria.add(handleWeightFilter(mapFilter, prodAvail, cb));
			break;
		case "price":
			criteria.add(handlePriceFilter(mapFilter, prodAvail, cb));
			break;
		case "brand":criteria.add(cb.equal(prodSubJoin.get("brand"), mapFilter.get("value")));

		}

		Order order1 = cb.desc(prodAvail.get("price"));
		Order order2 = cb.desc(prodSubJoin.get("name"));
		Order order3 = cb.desc(prodSubJoin.get("brand"));
		query = query.orderBy(order1, order2, order3);
		query.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));

		List<Product> listPod = em.createQuery(query).getResultList();
		return listPod;
	}

	private Predicate handlePriceFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		switch (mapFilter.get("filterCriteria")) {
		case "bw":
			return betweenCasePriceFilter(mapFilter, prodAvail, cb);

		case "lt":
			return lessThanPriceFilter(mapFilter, prodAvail, cb);

		case "gt":
			return greaterThanPriceFilter(mapFilter, prodAvail, cb);

		case "eq":
			return equalToPriceFilter(mapFilter, prodAvail, cb);

		default:
			return null;
		}
	}

	private Predicate equalToPriceFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {

		Predicate predicate = cb.equal(prodAvail.get("price"), Integer.valueOf(mapFilter.get("value")));
		return predicate;
	}

	private Predicate greaterThanPriceFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate predicate = cb.greaterThan(prodAvail.get("price"), Integer.valueOf(mapFilter.get("value")));
		return predicate;
	}

	private Predicate lessThanPriceFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate predicate = cb.lessThan(prodAvail.get("price"), Integer.valueOf(mapFilter.get("value")));
		return predicate;
	}

	private Predicate betweenCasePriceFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate predicate = cb.between(prodAvail.get("price"), Integer.valueOf(mapFilter.get("startValue")),
				Integer.valueOf(mapFilter.get("endValue")));
		return predicate;
	}

	private Predicate handleWeightFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {

		switch (mapFilter.get("filterCriteria")) {
		case "bw":
			return betweenCaseWeightFilter(mapFilter, prodAvail, cb);

		case "lt":
			return lessThanWeightFilter(mapFilter, prodAvail, cb);

		case "gt":
			return greaterThanWeightFilter(mapFilter, prodAvail, cb);

		case "eq":
			return equalToWeightFilter(mapFilter, prodAvail, cb);

		default:
			return null;
		}

	}

	private Predicate equalToWeightFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"), mapFilter.get("weightUnit"));
		Predicate predicateStartWeight = cb.equal(prodAvail.get("weight"), mapFilter.get("value"));
		Predicate finalWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
		return finalWeightCriteria;
	}

	public static boolean isNotNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return true;
		return false;
	}

	Predicate betweenCaseWeightFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate finalWeightCriteria;
		if (mapFilter.get("startWeightUnit").equals("endWeightUnit")) {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"),
					mapFilter.get("startWeightUnit"));
			Predicate predicateStartWeight = cb.between(prodAvail.get("weight"),
					Integer.valueOf(mapFilter.get("startValue")), Integer.valueOf(mapFilter.get("endValue")));
			finalWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);

		} else {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"),
					mapFilter.get("startWeightUnit"));
			Predicate predicateStartWeight = cb.greaterThanOrEqualTo(prodAvail.get("weight"),
					mapFilter.get("startValue"));
			Predicate predStartWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
			Predicate predicateEndWeightUnit = cb.equal(prodAvail.get("weightUnit"), mapFilter.get("endWeightUnit"));
			Predicate predicateEndWeight = cb.lessThanOrEqualTo(prodAvail.get("weight"), mapFilter.get("endValue"));
			Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);
			finalWeightCriteria = cb.or(predStartWeightCriteria, predEndWeightCriteria);

		}
		return finalWeightCriteria;

	}

	Predicate lessThanWeightFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {

		Predicate predicateEndWeightUnit = cb.equal(prodAvail.get("weightUnit"), mapFilter.get("weightUnit"));
		Predicate predicateEndWeight = cb.lessThan(prodAvail.get("weight"), mapFilter.get("value"));
		Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);

		String smallerWeightUnit = checkWeight(mapFilter.get("weightUnit"), "sm");
		if (isNotNullOrEmpty(smallerWeightUnit)) {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"), smallerWeightUnit);
			Predicate predicateStartWeight = cb.lessThan(prodAvail.get("weight"), 1000);

			Predicate predStartWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
			Predicate finalWeightCriteria = cb.or(predStartWeightCriteria, predEndWeightCriteria);
			return finalWeightCriteria;
		}

		return predEndWeightCriteria;
	}

	Predicate greaterThanWeightFilter(Map<String, String> mapFilter, Join<ProductAvail, Product> prodAvail,
			CriteriaBuilder cb) {
		Predicate predicateEndWeightUnit = cb.equal(prodAvail.get("weightUnit"), mapFilter.get("weightUnit"));
		Predicate predicateEndWeight = cb.greaterThan(prodAvail.get("weight"), mapFilter.get("value"));
		Predicate predEndWeightCriteria = cb.and(predicateEndWeight, predicateEndWeightUnit);
		String greaterWeightUnit = checkWeight(mapFilter.get("weightUnit"), "gt");
		if (isNotNullOrEmpty(greaterWeightUnit)) {
			Predicate predicateStartWeightUnit = cb.equal(prodAvail.get("weightUnit"), greaterWeightUnit);
			Predicate predicateStartWeight = cb.greaterThanOrEqualTo(prodAvail.get("weight"), 1);
			Predicate predStartWeightCriteria = cb.and(predicateStartWeightUnit, predicateStartWeight);
			Predicate finalWeightCriteria = cb.or(predStartWeightCriteria, predEndWeightCriteria);
			return finalWeightCriteria;
		}

		return predEndWeightCriteria;
	}

	String checkWeight(String weightUnit, String crit) {
		CheckWeightUnitService checkWeightService = new CheckWeightUnitService();
		if (crit.equals("gt"))
			return checkWeightService.checkForGreaterWeightUnit(weightUnit);
		else
			return checkWeightService.checkForSmallerWeightUnit(weightUnit);

	}

	public CriteriaBuilderModel getQueryBuilder() {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Product> query = cb.createQuery(Product.class);
		Root<SubCategory> subRoot = query.from(SubCategory.class);
		Join<SubCategory, Product> prodSubJoin = subRoot.join("productList");
		Join<ProductAvail, Product> prodAvail = prodSubJoin.join("productAvailList");
		query.select(prodSubJoin);
		CriteriaBuilderModel criterBuilderModel = new CriteriaBuilderModel(cb, query, prodSubJoin, prodAvail);
		criterBuilderModel.setSubRoot(subRoot);
		
		return criterBuilderModel;
	}

	
	public List<Product> getCategoryById(String catId)
	{
		CriteriaBuilderModel criterBuilderModel=getQueryBuilder();
		List<Predicate> criteria = new ArrayList<Predicate>();
		Root<SubCategory> subRoot=criterBuilderModel.getSubRoot();
		CriteriaBuilder cb=criterBuilderModel.getCb();
		criteria.add(cb.equal(subRoot.get("categoryId"), catId));
		criterBuilderModel.getQuery().where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		return em.createQuery(criterBuilderModel.getQuery()).getResultList();
	}
	
	
	public FilterMetaData getFiltersBasedOnCategoryAndSubCategoryId(String categoryId,String subId)
	{
		CriteriaBuilderModel cbmodel = getQueryBuilder();
		CriteriaBuilder cb = cbmodel.getCb();
		CriteriaQuery<Product> query = cbmodel.getQuery();
		Join<SubCategory, Product> prodSubJoin = cbmodel.getProdSubJoin();
		Join<ProductAvail, Product> prodAvail = cbmodel.getProdAvail();
		query.multiselect(cb.max(prodAvail.get("weight")),cb.min(prodAvail.get("weight")),cb.min(prodAvail.get("weightUnit")),cb.max(prodAvail.get("price")),cb.min(prodAvail.get("price")));
		query.groupBy(prodAvail.get("weightUnit"));
		List<Predicate> criteria = new ArrayList<Predicate>();
		
		if(isNotNullOrEmpty(categoryId))
		criteria.add(cb.equal(cbmodel.getSubRoot().get("categoryId"),categoryId));
		if(isNotNullOrEmpty(subId))
		criteria.add(cb.equal(prodSubJoin.get("subId"),subId));
		
		cbmodel.getQuery().where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		List<Product> prodList=  em.createQuery(cbmodel.getQuery()).getResultList();
		List<WeightFilterMetaData> filterWeightIntervalList = new ArrayList<>();
		List<PriceFilterMetaData> filterPriceIntervalList=new ArrayList<>();
		
		prodList.forEach(prod->filterWeightIntervalList.addAll(fetchWeightIntervals(prod)));
		if(!prodList.isEmpty())
		filterPriceIntervalList= processPriceIntervals(prodList.get(0).getMinPrice(),prodList.get(0).getMaxPrice());	
		FilterMetaData filterMetaData=new FilterMetaData();
		filterMetaData.setFilterCriteria("bw");
		filterMetaData.setPriceFilters(filterPriceIntervalList);
		filterMetaData.setWeightFilters(filterWeightIntervalList);
		
		
		return filterMetaData;
	}
	

	
	List<PriceFilterMetaData> fetchPriceIntervals(int min, int max, int diff) {
		
		
		
		List<PriceFilterMetaData> list = new ArrayList<>();
		int maxd = 0;

		maxd = (min + diff);
		while (min < max) {
			PriceFilterMetaData priceFilterMetaData=new PriceFilterMetaData();
			if (maxd > max) {
				priceFilterMetaData.setMax(max);
				priceFilterMetaData.setMin(min);
				list.add(priceFilterMetaData);
				return list;
			}
			
			min = maxd;
			maxd = maxd + diff;
			priceFilterMetaData.setMax(max);
			priceFilterMetaData.setMin(min);
			list.add(priceFilterMetaData);
			

		}

		return list;
	}
	
	
	public List<PriceFilterMetaData> processPriceIntervals(Double minPrice,Double maxPrice)
	{
		int maxPriceRoundOff;
		
		String doubleMaxString=maxPrice.toString();
		if(!doubleMaxString.substring(doubleMaxString.indexOf(".")+1,doubleMaxString.indexOf(".")+2).equals("0"))
		{
			maxPriceRoundOff=Integer.valueOf(maxPrice.intValue())+1;
		}
		else
		{
			maxPriceRoundOff=Integer.valueOf(maxPrice.intValue());
		}
		return fetchPriceIntervals(minPrice.intValue(),maxPriceRoundOff, 50);
	}
	
	
	List<WeightFilterMetaData> fetchWeightIntervals(Product prod)
	{
		
		if(prod.getWeightUnit().equals("kg") || prod.getWeightUnit().equals("litre"))
		{
			return fetchGreaterWeightUnitsIntervals(prod.getMinWeight(),prod.getMaxWeight(),prod.getWeightUnit());
		}
		else
		{
			return fetchSmallerWeightUnitsIntervals(prod.getMinWeight(),prod.getMaxWeight(),prod.getWeightUnit());
		}
		
		
	}
	
	 List<WeightFilterMetaData> fetchGreaterWeightUnitsIntervals(int minWeight, int maxWeight, String weightUnit) {
		 WeightFilterMetaData weightFilterMetaData=new WeightFilterMetaData();
			List<WeightFilterMetaData> listWeight = new ArrayList<>();
			if(minWeight==maxWeight)
			{
				weightFilterMetaData.setStartWeight(minWeight);
				weightFilterMetaData.setStartWeightUnit(weightUnit);
				listWeight.add(weightFilterMetaData);
				return listWeight;
			}
			for (int i = 1; i <= 5; i++) {
				OptionalLong containsValue = LongStream.rangeClosed(i, 5 * i).filter(p -> p == maxWeight).findAny();
				weightFilterMetaData.setStartWeight(minWeight);
				weightFilterMetaData.setStartWeightUnit(weightUnit);
				weightFilterMetaData.setEndWeight(5 * i);
				weightFilterMetaData.setEndWeightUnit(weightUnit);
				listWeight.add(weightFilterMetaData);
				minWeight = 5 * i;
				if (containsValue.isPresent()) {
					break;

				}
			}

			return listWeight;
		}

	 List<WeightFilterMetaData> fetchSmallerWeightUnitsIntervals(int minWeight, int maxWeight, String weightUnit) {
			CheckWeightUnitService checkUnitService = new CheckWeightUnitService();
			int weightInGramArray[] = {50, 100, 150, 200, 250, 500, 1000 };
			List<WeightFilterMetaData> listWeight = new ArrayList<>();
			int temp = minWeight;
			int counter = 0;
			
			for (int i = 1; i < weightInGramArray.length; i++) {
				OptionalLong containsValue = LongStream.rangeClosed(weightInGramArray[i - 1], weightInGramArray[i])
						.filter(p -> p == minWeight).findAny();
				if ((containsValue.isPresent() && weightInGramArray[i] > minWeight)
						|| (minWeight < weightInGramArray[i] && minWeight <= weightInGramArray[i - 1])) {
					containsValue = LongStream.rangeClosed(weightInGramArray[i - 1], weightInGramArray[i])
							.filter(p -> p == maxWeight).findAny();
					WeightFilterMetaData weightFilterMetaData=new WeightFilterMetaData();
					Integer  wMax ;
					if (counter != 0)
						temp = weightInGramArray[i - 1];
					weightFilterMetaData.setStartWeight(temp);
					weightFilterMetaData.setStartWeightUnit(weightUnit);
					if (weightInGramArray[i] == 1000)
					{
						wMax = (weightInGramArray[i] / 1000) ;
						weightFilterMetaData.setEndWeight(wMax);
						weightFilterMetaData.setEndWeightUnit(checkUnitService.checkForGreaterWeightUnit(weightUnit));
						
					}
					else
						wMax = (weightInGramArray[i]) ;
					    weightFilterMetaData.setEndWeight(wMax);
						weightFilterMetaData.setEndWeightUnit(weightUnit);

					listWeight.add(weightFilterMetaData);

					if (containsValue.isPresent()) {
						break;

					}

					counter++;
				}

			}
			

			return listWeight;
		}
	
	
}
