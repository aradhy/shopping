package shop.service;

import java.util.List;

import shop.model.SubCategory;

public interface FetchSubCategoryService {
	
	

	List<SubCategory>  findSubCategory(String categoryId);
	List<SubCategory> findAllSubCategories();
}
