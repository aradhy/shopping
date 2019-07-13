package shop.service;

import java.util.List;

import shop.dto.CategoryDTO;
import shop.model.Category;

public interface FetchCategoryService {
	

	public List<Category> findCategorySelectedFields();
	
	public List<CategoryDTO>  findAllCategory();
}
