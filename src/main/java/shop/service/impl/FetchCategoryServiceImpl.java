package shop.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoCategoryService;
import shop.dto.CategoryDTO;
import shop.dto.SubCategoryDTO;
import shop.model.Category;
import shop.model.SubCategory;
import shop.service.FetchCategoryService;
import shop.util.Utility;

@Service
public class FetchCategoryServiceImpl implements FetchCategoryService {
	
	
	@Autowired
	private DaoCategoryService daoCategoryService;
	

	
	public List<CategoryDTO>  findAllCategory()
	{
		List<Category> categoryList=null;
		List<CategoryDTO> categorytDTOList = null;
		ResponseEntity<Object> responseEntity=null;
		try
		{
		responseEntity=Utility.getRequest("http://localhost:8080/category-all/");
		}
		catch(Exception ex)
		{
			System.out.println("Cache Not working...Might Be Connection Issue");
		}
	
		if(responseEntity !=null && responseEntity.getBody()!=null )
		{
			categorytDTOList=(List<CategoryDTO>)responseEntity.getBody();
		}
		
		if(categorytDTOList==null || categorytDTOList.isEmpty())
		{
		categoryList = (List<Category>)daoCategoryService.findAll();
		categorytDTOList= categoryList.parallelStream().map(category->convertToDTOCategory(category)).collect(Collectors.toList());
		if(!categoryList.isEmpty())
		{
			Map<String, String> map =Utility.getImageLinks(getImageIdListCategory(categorytDTOList));
			setImageLinkCategory(new HashSet<Category>(categoryList),map);
			for(Category category:categoryList)
			{
				
			Map<String, String> mapSubCat =Utility.getImageLinks(getImageIdListSubCategory(category.getSubCategory()));
			setImageLinkSubCategory(category.getSubCategory(),mapSubCat);
			}
			
		}
		}
			return categorytDTOList;
		
	}
	
	CategoryDTO convertToDTOCategory(Category category)
	{
		CategoryDTO categoryDTO=new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		categoryDTO.setImageId(category.getImageId());
	    List<SubCategoryDTO> subCategoryList=	category.getSubCategory().parallelStream().map(sub->convertToDTOSubCategory(sub)).collect(Collectors.toList());
	    categoryDTO.setSubCategoryList(subCategoryList);
	    
	    return categoryDTO;
	}
	
	SubCategoryDTO convertToDTOSubCategory(SubCategory subCategory)
	{
		SubCategoryDTO subCategoryDTO=new SubCategoryDTO();
		subCategoryDTO.setId(subCategory.getId());
		subCategoryDTO.setName(subCategory.getName());
		subCategoryDTO.setImageId(subCategory.getImageId());
		return subCategoryDTO;
		
	}
	
	
	private void setImageLinkCategory(Set<Category> categoryList,Map<String, String> map)
	{
		categoryList.forEach(cat->cat.setImageLink("images//"+map.get(cat.getImageId())));
	}
	
	private List<String>  getImageIdListCategory(List<CategoryDTO> categoryList)
	{
		List<String> imageIds=categoryList.parallelStream().map(category->category.getImageId().toString()).collect(Collectors.toList());
		
		return imageIds;
	}
	
	private void setImageLinkSubCategory(Set<SubCategory> subCategoryList,Map<String, String> map)
	{
		subCategoryList.forEach(sub->sub.setImageLink("images//"+map.get(sub.getImageId())));
	}
	
	
	private List<String>  getImageIdListSubCategory(Set<SubCategory> subCategoryList)
	{
		List<String> imageIds=subCategoryList.parallelStream().map(subCategory->subCategory.getImageId().toString()).collect(Collectors.toList());
		
		return imageIds;
	}



	@Override
	public List<Category> findCategorySelectedFields() {
		
		return daoCategoryService.findCategorySelectedFields();
	}
	
}
