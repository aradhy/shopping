package shop.service.impl;

import java.util.List;
import java.util.Map;
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
			for(CategoryDTO category:categorytDTOList)
			{
				
			Map<String, String> mapSubCat =Utility.getImageLinks(getImageIdListSubCategory(category.getSubCategoryList()));
			setImageLinkSubCategory(category.getSubCategoryList(),mapSubCat);
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
		subCategoryDTO.setImageLink(subCategory.getImageLink());
		return subCategoryDTO;
		
	}
	
	
	
	
	private void setImageLinkSubCategory(List<SubCategoryDTO> subCategoryList,Map<String, String> map)
	{
		subCategoryList.forEach(sub->sub.setImageLink("images//"+map.get(sub.getImageId())));
	}
	
	
	private List<String>  getImageIdListSubCategory(List<SubCategoryDTO> subCategoryList)
	{
		List<String> imageIds=subCategoryList.parallelStream().map(subCategory->subCategory.getImageId().toString()).collect(Collectors.toList());
		
		return imageIds;
	}



	@Override
	public List<Category> findCategorySelectedFields() {
		
		return daoCategoryService.findCategorySelectedFields();
	}
	
}
