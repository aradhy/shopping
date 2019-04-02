package shop.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shop.model.Product;
import shop.model.ProductAvail;
import shop.model.SubCategory;
import shop.service.FetchProductService;
import shop.service.FetchSubCategoryService;

@RestController
public class ProductFetchController {

	@Autowired
	FetchProductService fetchProductService;

	
	@Autowired
	FetchSubCategoryService fetchSubCategoryService;
	@RequestMapping("/product/{productId}")
	public Product getProduct(@PathVariable("productId") String productId) throws ParseException {
		Product product = fetchProductService.getProduct(productId);
		
		return product;
	}
	
	
	@PostMapping("/product")
	public ResponseEntity<String> createProduct(@RequestBody Product prod ) throws ParseException {
		Product product =fetchProductService.createProduct(prod);
		
		
		return new ResponseEntity<String>("Product Id  "+ product.getCode()+" created successfully", 
			      HttpStatus.CREATED);
	}
	@RequestMapping("/product-subCategoryId/{subCategoryId}")
	public List<Product> getProductSubCategoryId(@PathVariable("subCategoryId") String subCategoryId) throws ParseException {
		List<Product> productList = fetchProductService.getProductBySubCategory(subCategoryId);
		
		return productList;
	}
	
	@RequestMapping("/product-name")
	public List<Product> getProductName(@RequestParam ("productName") String productName) throws ParseException {
		List<Product> productList = fetchProductService.getProductByName(productName);
		
		return productList;
	}
	
	
	@RequestMapping("/product-subcategory")
	public List<Product> getProductBySubCategory(@RequestParam ("subCategoryName") String subCategoryName) throws ParseException {
		List<Product> productList = fetchProductService.getProductBySubCategoryName(subCategoryName);
		
		return productList;
	}
	
	@RequestMapping("/product-category")
	public Set<Product> getProductByCategory(@RequestParam ("categoryName") String categoryName) throws ParseException {
		Set<Product> productList = fetchProductService.getProductByCategoryName(categoryName);
		
		return productList;
	}
	
	
	@RequestMapping("/product-brand")
	public List<Product> getProductByBrandName(@RequestParam ("brandName") String brandName) {
		List<Product> productList = fetchProductService.getProductByBrand(brandName);
		
		return productList;
	}
	
	@RequestMapping("/product-category/{categoryId}")
	public List<Product> getProductCategoryId(@PathVariable("categoryId") String categoryId) throws ParseException {
		List<SubCategory> subCategoryList =fetchSubCategoryService.findSubCategory(categoryId);
		List<Product> productList=new ArrayList<Product>();
		subCategoryList.forEach(subCat->{
			try {
				productList.addAll(fetchProductService.getProductBySubCategory(subCat.getId()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		
		return productList;
	}
	
	@RequestMapping("/product-avail/{productId}")
	public List<ProductAvail> getProductAvail(@PathVariable("productId") String productId) throws ParseException {
		Product product = fetchProductService.getProduct(productId);
		List<ProductAvail> productAvail=null;
		if(product!=null)
		productAvail=product.getProductAvailList();
		
		return productAvail;
	}

	
	
}
