package shop.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping("/product/{productCode}/avId/{prodAvailId}")
	public ProductDTO getProduct(@PathVariable("productCode") String productCode,@PathVariable("prodAvailId") String prodAvailId ,HttpServletResponse response) throws ParseException {
		ProductDTO productDto = fetchProductService.findByProductAvail(productCode,prodAvailId);
		
		
		
		return productDto;
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
	public List<Product> getProductName(@RequestParam ("productName") String productName) throws InterruptedException {
		Thread.sleep(500);
		List<Product> productList = fetchProductService.getProductByName(replaceWithPattern(productName, " "));
	
		
		return productList;
	}
	
	
	@RequestMapping("/product-subcategory")
	public List<Product> getProductBySubCategory(@RequestParam ("subCategoryName") String subCategoryName)  {
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
	public List<ProductDTO> getBucketProducts(@RequestParam List<String> productIdList)
	{
		List<ProductDTO> productDtoList = fetchProductService.getProduct(productIdList);
		
		
		
		return productDtoList;
	}
	

/*
	@RequestMapping(value="/productFilters/cat/{catId}/subId/{subId}",method = RequestMethod.GET)
	public List<Product> getProductByGreaterThanWeightAndSubId(@PathVariable String catId,@PathVariable String subId,@RequestParam Integer[] filterValues,@RequestParam String[] filterCriteria      )
	{

        System.out.println(filterValues[0]);
        System.out.println(filterValues[1]);
        System.out.println(filterCriteria[0]);
        System.out.println(filterCriteria[1]);
        System.out.println(filterCriteria[2]);
		return null;
	}
	*/
	
	@RequestMapping(value="/productFilter/cat/{catId}/subId/{subId}",method = RequestMethod.GET)
	public List<Product> getProductByGreaterThanWeightAndSubId(@PathVariable String catId,@PathVariable String subId,@RequestParam Map<String,String> filterMap )
	{

        System.out.println(Integer.parseInt(filterMap.get("start")));
        System.out.println(Integer.parseInt(filterMap.get("end")));
        System.out.println(filterMap.get("criteria"));
        System.out.println(filterMap.get("startUnit"));
        System.out.println(filterMap.get("endUnit"));
		return null;
	}
	
	/*@RequestMapping(value="/productFilter/cat/{catId}/subId/{subId}",method = RequestMethod.GET)
	public List<Product> getProductByGreaterThanWeightAndSubId(@PathVariable String catId,@PathVariable String subId,@RequestParam String value,@RequestParam String cr )
	{
		String [] filterValue=value.split("-");
        System.out.println(Integer.parseInt(filterValue[0]));
        System.out.println(filterValue[1]);
        System.out.println(Integer.parseInt(filterValue[2]));
        System.out.println(filterValue[3]);
        System.out.println(cr);
		return null;
	}*/
	
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
	
	public String replaceWithPattern(String str,String replace){
	    
	    Pattern ptn = Pattern.compile("\\s+");
	    Matcher mtch = ptn.matcher(str);
	    return mtch.replaceAll(replace);
	}
}


