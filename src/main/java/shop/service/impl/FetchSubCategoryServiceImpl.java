package shop.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoSubCategoryService;
import shop.model.SubCategory;
import shop.service.FetchSubCategoryService;
import shop.util.Utility;

@Service
public class FetchSubCategoryServiceImpl implements FetchSubCategoryService {
	
	
	@Autowired
	private DaoSubCategoryService daoSubCategoryService;
	
	
	
	@Autowired
	private Utility util;
	

	public List<SubCategory>  findSubCategory(String categoryId)
	{
		List<SubCategory> subCategoryList = (List<SubCategory>)daoSubCategoryService.findSubCategory(categoryId);
		List<String> imageIds = null;
		if(!subCategoryList.isEmpty())
		{
			
			imageIds=subCategoryList.stream().map(subCategory->subCategory.getImageId().toString()).collect(Collectors.toList());
		
		
			Map<String, String> map =util.getImageLinks(imageIds);
		   setImageLinkSubCategory(new HashSet<SubCategory>(subCategoryList),map);
		}
		return subCategoryList;
		
	}
	
	
	private void setImageLinkSubCategory(Set<SubCategory> subCategoryList,Map<String, String> map)
	{
		subCategoryList.forEach(sub->sub.setImageLink("images//"+map.get(sub.getImageId())));
	}
	
	


	@Override
	public List<SubCategory> findAllSubCategories() {
		return daoSubCategoryService.findAll();
	}


}
