package shop.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.model.Product;
import shop.service.FetchProductService;

@RestController
public class ProductFetchController {

	@Autowired
	FetchProductService fetchProductService;

	@RequestMapping("/product/{productId}")
	public Product getProduct(@PathVariable("productId") Integer productId) throws ParseException {
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
	public List<Product> getProductCategory(@PathVariable("categoryId") Integer categoryId) throws ParseException {
		List<Product> productList = fetchProductService.getProductByCategory(categoryId);
		
		return productList;
	}
	

}
