package shop.service;

import java.util.List;
import java.util.Map;

import shop.dto.CategoryDTO;
import shop.model.Category;

public interface FetchCategoryService {
	

	public List<Category> findCategorySelectedFields();
	
	public List<CategoryDTO>  findAllCategory();
	
	List<CategoryDTO> getCategorySubCategory(Map<String,String> map);
	
	
	
	
}
