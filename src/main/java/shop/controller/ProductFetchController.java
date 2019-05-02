package shop.controller;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shop.dto.ProductDTO;
import shop.model.Product;
import shop.model.ProductAvail;
import shop.service.FetchProductService;
import shop.service.FetchSubCategoryService;

@RestController
public class ProductFetchController {

	@Autowired
	FetchProductService fetchProductService;

	
	@Autowired
	FetchSubCategoryService fetchSubCategoryService;
	@RequestMapping("/product/{productId}")
	public List<Product> getProduct(@PathVariable("productId") String productId,HttpServletResponse response) throws ParseException {
		List<ProductDTO> productDtoList = fetchProductService.findByProductCode(productId);
		Map<String,Product> mapProduct=new HashMap<String,Product>();
		for(ProductDTO prodDto:productDtoList)
		{
			if(mapProduct.containsKey(prodDto.getProdCode()))
			{
				mapProduct.put(prodDto.getProdCode(), convertProductDTOToProduct(mapProduct.get(prodDto.getProdCode()),prodDto));
			}
			else
			{
				
				mapProduct.put(prodDto.getProdCode(), convertProductDTOToProduct(null,prodDto));
				
			}
			
			
		}
		
		
		return new ArrayList<Product>(mapProduct.values());
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
		List<ProductDTO> productDtoList =fetchProductService.getProductByCategory(categoryId);
		
		Map<String,Product> mapProduct=new HashMap<String,Product>();
		for(ProductDTO prodDto:productDtoList)
		{
			if(mapProduct.containsKey(prodDto.getProdCode()))
			{
				mapProduct.put(prodDto.getProdCode(), convertProductDTOToProduct(mapProduct.get(prodDto.getProdCode()),prodDto));
			}
			else
			{
				
				mapProduct.put(prodDto.getProdCode(), convertProductDTOToProduct(null,prodDto));
				
			}
			
			
		}
		
		
		return new ArrayList<Product>(mapProduct.values());
	}
	
	

	@RequestMapping("/bucketproducts")
	public List<Product> getBucketProducts(@RequestParam List<String> productIdList)
	{
		List<ProductDTO> productDtoList = fetchProductService.getProduct(productIdList);
		Map<String,Product> mapProduct=new HashMap<String,Product>();
		for(ProductDTO prodDto:productDtoList)
		{
			if(mapProduct.containsKey(prodDto.getProdCode()))
			{
				mapProduct.put(prodDto.getProdCode(), convertProductDTOToProduct(mapProduct.get(prodDto.getProdCode()),prodDto));
			}
			else
			{
				
				mapProduct.put(prodDto.getProdCode(), convertProductDTOToProduct(null,prodDto));
				
			}
			
			
		}
		
		
		return new ArrayList<Product>(mapProduct.values());
	}
	
	
	Product convertProductDTOToProduct(Product product,ProductDTO productDTO)
	{
		
		
		
		if(product==null)
		{
		product=new Product();
		product.setCode(productDTO.getProdCode());
		product.setBrand(productDTO.getBrand());
		product.setName(productDTO.getName());
		product.setImageId(productDTO.getImageId());
		ProductAvail productAvail=new ProductAvail();
		productAvail.setId(productDTO.getProdAvailId());
		productAvail.setProductId(productDTO.getProdCode());
		productAvail.setPrice(productDTO.getPrice());
		productAvail.setWeight(productDTO.getWeight());
		productAvail.setWeightUnit(productDTO.getWeightUnit());
		
		List<ProductAvail> productAvailList=new ArrayList<ProductAvail>();
		productAvailList.add(productAvail);
		product.setProductAvailList(productAvailList);
		}
		else
		{
			ProductAvail productAvail=new ProductAvail();
			productAvail.setProductId(productDTO.getProdCode());
			productAvail.setPrice(productDTO.getPrice());
			productAvail.setWeight(productDTO.getWeight());
			productAvail.setWeightUnit(productDTO.getWeightUnit());
			productAvail.setId(productDTO.getProdAvailId());
			List<ProductAvail> productAvailList=product.getProductAvailList();
			productAvailList.add(productAvail);
			product.setProductAvailList(productAvailList);
		}
		return product;
		
	}
}
