package shop.service;

import java.util.List;
import java.util.Optional;

import shop.dto.CategoryDTO;
import shop.model.Category;

public interface FetchCategoryService {
	

	public List<Category> findCategorySelectedFields();
	
	public List<CategoryDTO>  findAllCategory();
	
	CategoryDTO  findCategorySubCategoryNames(String catId);

	List<Category> getSubCategoryByCategoryId(String categoryId, String subCategoryId);
	
	
	
	
}
