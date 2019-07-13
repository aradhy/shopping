package shop.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.dto.CategoryDTO;
import shop.model.Category;
import shop.model.SubCategory;
import shop.service.FetchCategoryService;
import shop.service.FetchSubCategoryService;

@RestController
public class CategoryFetchController {

	
	@Autowired
	private FetchCategoryService fetchCategoryService;
	
	@Autowired
	private FetchSubCategoryService fetchSubCategoryService;

	@RequestMapping("/subcategory/{categoryId}")
	public List<SubCategory> getSubCategory(@PathVariable String categoryId)  {
		List<SubCategory> subCategoryList = (List<SubCategory>)fetchSubCategoryService.findSubCategory(categoryId);
		return subCategoryList;
	}
	

	@RequestMapping("/category")
	public List<Category> getCategory()  {
		List<Category> categoryList = (List<Category>)fetchCategoryService.findCategorySelectedFields();
		
		return categoryList;
	}
	
	
	@RequestMapping("/subcategory")
	public List<SubCategory> getSubCategory()  {
		List<SubCategory> subCategoryList = (List<SubCategory>)fetchSubCategoryService.findAllSubCategories();
		
		return subCategoryList;
	}

	@RequestMapping("/category-all")
	public List<CategoryDTO> getCategories()  {
		
		List<CategoryDTO> categoryList=fetchCategoryService.findAllCategory();
		return categoryList;
	}
	

	
}
