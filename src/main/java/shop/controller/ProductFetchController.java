package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.model.Product;
import shop.service.FetchProductService;

import java.text.ParseException;

@RestController("/")
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

}
