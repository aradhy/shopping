package shop.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import shop.daoservice.DaoProductService;
import shop.dto.ProductDTO;
import shop.model.FilterMetaData;
import shop.model.Product;
import shop.service.FetchProductService;
import shop.service.FetchSubCategoryService;
import shop.util.FilterParamParser;

@RestController
public class ProductFetchController {

	@Autowired
	FetchProductService fetchProductService;

	@Autowired
	DaoProductService daoProductService;
	@Autowired
	FetchSubCategoryService fetchSubCategoryService;

	@RequestMapping("/product/{productCode}/avId/{prodAvailId}")
	public ProductDTO getProduct(@PathVariable("productCode") String productCode,
			@PathVariable("prodAvailId") String prodAvailId, HttpServletResponse response) throws ParseException {
		ProductDTO productDto = fetchProductService.findByProductAvail(productCode, prodAvailId);

		return productDto;
	}

	@PostMapping("/product")
	public ResponseEntity<String> createProduct(@RequestBody Product prod) throws ParseException {
		Product product = fetchProductService.createProduct(prod);

		return new ResponseEntity<String>("Product Id  " + product.getCode() + " created successfully",
				HttpStatus.CREATED);
	}

	@RequestMapping("/product-subCategoryId/{subCategoryId}")
	public List<Product> getProductSubCategoryId(@PathVariable("subCategoryId") String subCategoryId)
			throws ParseException {
		List<Product> productList = fetchProductService.getProductBySubCategory(subCategoryId);
		return productList;
	}

	@RequestMapping("/product-name")
	public List<Product> getProductName(@RequestParam("productName") String productName) throws InterruptedException {
	
		List<Product> productList = fetchProductService.getProductByName(replaceWithPattern(productName, " "));

		return productList;
	}

	@RequestMapping("/product-subcategory")
	public List<Product> getProductBySubCategory(@RequestParam("subCategoryName") String subCategoryName) {
		List<Product> productList = fetchProductService.getProductBySubCategoryName(subCategoryName);

		return productList;
	}

	@RequestMapping("/product-category")
	public Set<Product> getProductByCategory(@RequestParam("categoryName") String categoryName) throws ParseException {
		Set<Product> productList = fetchProductService.getProductByCategoryName(categoryName);

		return productList;
	}

	@RequestMapping("/product-brand")
	public List<Product> getProductByBrandName(@RequestParam("brandName") String brandName) {
		List<Product> productList = fetchProductService.getProductByBrand(brandName);

		return productList;
	}

	@RequestMapping("/product-category/{categoryId}")
	public List<Product> getProductCategoryId(@PathVariable("categoryId") String categoryId) throws ParseException {
		return fetchProductService.getCategoryById(categoryId);
	}

	@RequestMapping("/bucketproducts")
	public List<ProductDTO> getBucketProducts(@RequestParam List<String> productIdList) {
		List<ProductDTO> productDtoList = fetchProductService.getProduct(productIdList);

		return productDtoList;
	}

	@RequestMapping(value = "/productFilter/cat/{catId}", method = RequestMethod.GET)
	public List<Product> productFilters(@PathVariable String catId,
			@RequestParam(value = "subId", required = false) String subId,
			@RequestParam(value = "weightFilters", required = false) List<String> weightFilters,
			@RequestParam(value = "priceFilters", required = false) List<String> priceFilters,
			@RequestParam(value = "brandFilters", required = false) Set<String> brandFilters) {

		FilterMetaData filterMetaData = FilterParamParser.parse(weightFilters, priceFilters, brandFilters);
		return fetchProductService.getProductBasedOnFilter(catId, subId, filterMetaData);

	}

	@RequestMapping(value = "/filterIntervals/cat/{catId}", method = RequestMethod.GET)
	public FilterMetaData WeightBasedOnFilters(@PathVariable String catId,
			@RequestParam(value = "subId", required = false) String subId,
			@RequestParam(value = "weightFilters", required = false) List<String> weightFilters,
			@RequestParam(value = "priceFilters", required = false) List<String> priceFilters,
			@RequestParam(value = "brandFilters", required = false) Set<String> brandFilters) {

		FilterMetaData filterMetaData = FilterParamParser.parse(weightFilters, priceFilters, brandFilters);
		return fetchProductService.getFiltersBasedOnCategoryAndSubCategoryId(catId,
				subId,filterMetaData);
	}

	public String replaceWithPattern(String str, String replace) {

		Pattern ptn = Pattern.compile("\\s+");
		Matcher mtch = ptn.matcher(str);
		return mtch.replaceAll(replace);
	}
	
	@RequestMapping("/product/{productCode}")
	public Product getProduct(@PathVariable("productCode") String productCode) throws ParseException {
		Product product = fetchProductService.findByProductCode(productCode);

		return product;
	}
}
