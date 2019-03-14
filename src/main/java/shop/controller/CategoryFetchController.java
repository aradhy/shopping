package shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shop.daoservice.FetchCategoryService;
import shop.daoservice.FetchSubCategoryService;
import shop.model.Category;
import shop.model.SubCategory;

@RestController
public class CategoryFetchController {

	@Autowired
	FetchSubCategoryService fetchSubCategoryService;
	
	@Autowired
	FetchCategoryService fetchCategoryService;
	

	@RequestMapping("/subcategory")
	public List<SubCategory> getSubCategoryt(@RequestParam Long categoryId)  {
		List<SubCategory> subCategoryList = (List<SubCategory>)fetchSubCategoryService.findSubCategory(categoryId);
		
		return subCategoryList;
	}
	

	@RequestMapping("/category")
	public List<Category> getCategory()  {
		List<Category> categoryList = (List<Category>)fetchCategoryService.findAllCategory();
		
		return categoryList;
	}
	

}
