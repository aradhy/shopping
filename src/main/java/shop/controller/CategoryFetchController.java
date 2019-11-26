package shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



import shop.dto.CategoryDTO;
import shop.model.Category;
import shop.model.SubCategory;
import shop.model.TokenDTO;
import shop.service.FetchCategoryService;
import shop.service.FetchSubCategoryService;

@RestController
public class CategoryFetchController {

	@Autowired
	private FetchCategoryService fetchCategoryService;

	@Autowired
	private FetchSubCategoryService fetchSubCategoryService;

	@RequestMapping("/subcategory/{categoryId}")
	public List<SubCategory> getSubCategory(@PathVariable String categoryId) {
		List<SubCategory> subCategoryList = (List<SubCategory>) fetchSubCategoryService.findSubCategory(categoryId);
		return subCategoryList;
	}

	@RequestMapping("/category")
	public List<Category> getCategory() {
		List<Category> categoryList = (List<Category>) fetchCategoryService.findCategorySelectedFields();

		return categoryList;
	}

	@RequestMapping("/subcategory")
	public List<SubCategory> getSubCategory() {
		List<SubCategory> subCategoryList = (List<SubCategory>) fetchSubCategoryService.findAllSubCategories();

		return subCategoryList;
	}

	@RequestMapping("/category-all")
	public List<CategoryDTO> getCategories() {

		List<CategoryDTO> categoryList = fetchCategoryService.findAllCategory();
		return categoryList;
	}

	@RequestMapping("/category-search")
	public List<CategoryDTO> getCategorySubCategoryForSearchFilter(@RequestParam Map<String, String> map) {

		return fetchCategoryService.getCategorySubCategory(map);
	}

	
	@RequestMapping(value = "/googleUserInfo", method = RequestMethod.GET)
	public ResponseEntity<GoogleUserInfo> getToken(@RequestParam String code)
	{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("scope", "https://www.googleapis.com/auth/userinfo.email");
		map.add("grant_type", "authorization_code");
		map.add("redirect_uri", "https://localhost:4200/");
		map.add("code", code);
		map.add("client_id", "517977997834-kevh4fjm6um2roe04umom1h7mki74rtv.apps.googleusercontent.com");
		map.add("client_secret", "qb-rbrFVeqxKeHz3tq3WwC_E");
	
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
	
		ResponseEntity<TokenDTO> response = restTemplate.postForEntity(
		 "https://www.googleapis.com/oauth2/v4/token", request , shop.model.TokenDTO.class);
	
	
		ResponseEntity<GoogleUserInfo> tokenDTO
		 =restTemplate.getForEntity("https://www.googleapis.com/oauth2/v1/userinfo"+"?access_token="+response.getBody().getAccess_token(), GoogleUserInfo.class);
	
	
		return tokenDTO;
	}

}
