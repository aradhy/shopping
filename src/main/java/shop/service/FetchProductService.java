package shop.service;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoService;
import shop.model.Category;
import shop.model.Product;
import shop.model.SubCategory;
@Service
public class FetchProductService {
	
	
	@Autowired
	DaoService daoProductService;
	
	public Product getProduct(Long id) throws ParseException
	{
		Optional<Product> prodOptional=daoProductService.findByCode(id);
		if(prodOptional.isPresent())
		{
			Product prod=prodOptional.get();
			 return prod;
		}
		return null;
		
	}

	public Product createProduct(Product prod) throws ParseException {
		return daoProductService.save(prod);
	}
	

	public List<Product> getProductByCategory(Long categoryId) throws ParseException
	{
		List<Product> prodList=daoProductService.findById(categoryId);
		return prodList;
		
	}
	
	public List<Product> getProductByName(String productName) throws ParseException
	{
		List<Product> prodList=daoProductService.findByProductName(productName);
		return prodList;
		
	}
	
	public Set<Product> getProductBySubCategoryName(String subCategoryName)throws ParseException
	{
		SubCategory subCategory= daoProductService.findSubCategoryDetails(subCategoryName);
		Set<Product> productList=null;
		if(subCategory!=null)
		{
			productList= subCategory.getProductList();
		}
		return productList;
		
	}
	
	public Set<Product> getProductByCategoryName(String categoryName)throws ParseException
	{
		Category category= daoProductService.findCategoryDetails(categoryName);
		Set<Product> productList=new HashSet<Product>();
		if(category!=null)
		{
			
			category.getSubCategory().forEach(s->productList.addAll(s.getProductList()));
		}
		return productList;
		
	}
	
	public List<Product> getProductByBrand(String brandName)
	{
		List<Product> productList= daoProductService.findProductByBrand(brandName);
		return productList;
		
	}

}
