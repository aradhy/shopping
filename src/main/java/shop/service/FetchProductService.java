package shop.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoProductService;
import shop.dto.ProductDTO;
import shop.model.Category;
import shop.model.FilterCriteria;
import shop.model.Product;
import shop.model.ProductAvail;
import shop.model.SubCategory;


@Service
public class FetchProductService {

	@Autowired
	DaoProductService daoProductService;
	
	@PersistenceContext
    private EntityManager em;

	
	SessionFactory sessionQuery;
	public ProductDTO findByProductAvail(String productCode,String prodAvailId) throws ParseException {
		ProductDTO prodDto = daoProductService.findByProductCodeAndAvail(productCode,prodAvailId);
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

	public List<Product> getProductBySubCategoryName(String subCategoryName)  {
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
	
	public List<ProductDTO> getProduct(List<String> productAvailList)  {
		List<ProductDTO> prodList = daoProductService.findAllByProductAvail(productAvailList);
		return prodList;

	}

	public List<ProductDTO> getProductByCategory(String categoryId)
	{
		return daoProductService.findProductByCategory(categoryId);
	}
	
	public List<Product> getProductBasedOnFilter(String catId,String subId,String filterType,String[] filterValue)
	{
		//return daoProductService.findByProductAvailListWeightUnitAndProductAvailListWeightBetween(startWeight,endWeight,weightUnit);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> query = cb.createQuery(Product.class);
		Root<Product> productRoot = query.from(Product.class);
		Root<ProductAvail> productAvailRoot = query.from(ProductAvail.class);
		Root<Category> categoryRoot = query.from(Category.class);
		Root<SubCategory> subCategoryRoot = query.from(SubCategory.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
	
		if(isNotNullOrEmpty(subId))
		 predicates.add(
	                cb.equal(productRoot.get("subId"), subId));
		 
		if(isNotNullOrEmpty(catId))
		{
		 predicates.add(
	                cb.equal(categoryRoot.get("id"), subCategoryRoot.get("categoryId")));
		 predicates.add(
	                cb.equal(categoryRoot.get("id"),catId));
		}
		 predicates.add(
	                cb.equal(productRoot.get("code"), productAvailRoot.get("productId")));
		/* predicates.add(
		                cb.equal(productAvailRoot.get("weightUnit"), "gram"));
		 predicates.add(
	                cb.between(productAvailRoot.get("weight"),30,50));*/
		 
		   
	
		query.select(productRoot)
        .where(cb.and(predicates.toArray(new Predicate[]{})));
		return em.createQuery(query).getResultList();
	}
	
	public static boolean isNotNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return true;
        return false;
    }
}
