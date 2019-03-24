package shop.service;

import java.util.List;

import shop.model.Category;

public interface FetchCategoryService {
	

	public List<Category> findCategorySelectedFields();
	
	public List<Category>  findAllCategory();
}
