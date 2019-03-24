package shop.controller;

import java.text.ParseException;
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
import shop.service.FetchProductService;

@RestController
public class ProductFetchController {

	@Autowired
	FetchProductService fetchProductService;

	@RequestMapping("/product/{productId}")
	public Product getProduct(@PathVariable("productId") Long productId) throws ParseException {
		Product product = fetchProductService.getProduct(productId);
		
		return product;
	}
	
	
	@PostMapping("/product")
	public ResponseEntity<String> createProduct(@RequestBody Product prod ) throws ParseException {
		Product product =fetchProductService.createProduct(prod);
		
		
		return new ResponseEntity<String>("Product Id  "+ product.getCode()+" created successfully", 
			      HttpStatus.CREATED);
	}
	@RequestMapping("/product-category/{categoryId}")
	public List<Product> getProductCategory(@PathVariable("categoryId") Long categoryId) throws ParseException {
		List<Product> productList = fetchProductService.getProductByCategory(categoryId);
		
		return productList;
	}
	
	@RequestMapping("/product-name")
	public List<Product> getProductName(@RequestParam ("productName") String productName) throws ParseException {
		List<Product> productList = fetchProductService.getProductByName(productName);
		
		return productList;
	}
	
	
	@RequestMapping("/product-subcategory")
	public Set<Product> getProductBySubCategory(@RequestParam ("subCategoryName") String subCategoryName) throws ParseException {
		Set<Product> productList = fetchProductService.getProductBySubCategoryName(subCategoryName);
		
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

}
