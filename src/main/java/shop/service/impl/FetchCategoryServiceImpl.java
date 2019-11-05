package shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import shop.daoservice.DaoCategoryService;
import shop.dto.CategoryDTO;
import shop.dto.CategoryOnProduct;
import shop.dto.SubCategoryDTO;
import shop.model.Category;
import shop.model.Product;
import shop.model.SubCategory;
import shop.service.FetchCategoryService;
import shop.util.Utility;

@Service
public class FetchCategoryServiceImpl implements FetchCategoryService {

	@Autowired
	private DaoCategoryService daoCategoryService;

	@PersistenceContext
	private EntityManager em;

	public List<CategoryDTO> findAllCategory() {
		List<Category> categoryList = null;
		List<CategoryDTO> categorytDTOList = null;
		ResponseEntity<Object> responseEntity = null;
		try {
			responseEntity = Utility.getRequest("http://localhost:8080/category-all/");
		} catch (Exception ex) {
			System.out.println("Cache Not working...Might Be Connection Issue");
		}

		if (responseEntity != null && responseEntity.getBody() != null) {
			categorytDTOList = (List<CategoryDTO>) responseEntity.getBody();
		}

		if (categorytDTOList == null || categorytDTOList.isEmpty()) {
			categoryList = (List<Category>) daoCategoryService.findAll();
			categorytDTOList = categoryList.parallelStream().map(category -> convertToDTOCategory(category, true))
					.collect(Collectors.toList());
			if (!categoryList.isEmpty()) {
				for (CategoryDTO category : categorytDTOList) {

					Map<String, String> mapSubCat = Utility
							.getImageLinks(getImageIdListSubCategory(category.getSubCategory()));
					setImageLinkSubCategory(category.getSubCategory(), mapSubCat);
				}

			}
		}
		return categorytDTOList;

	}

	CategoryDTO convertToDTOCategory(Category category, boolean flag) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		List<SubCategoryDTO> subCategoryList = category.getSubCategory().parallelStream()
				.map(sub -> convertToDTOSubCategory(sub, flag)).collect(Collectors.toList());
		categoryDTO.setSubCategory(subCategoryList);

		return categoryDTO;
	}

	SubCategoryDTO convertToDTOSubCategory(SubCategory subCategory, boolean flag) {
		SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
		subCategoryDTO.setId(subCategory.getId());
		subCategoryDTO.setName(subCategory.getName());
		if (flag) {
			subCategoryDTO.setImageId(subCategory.getImageId());
			subCategoryDTO.setImageLink(subCategory.getImageLink());
		}
		return subCategoryDTO;

	}

	private void setImageLinkSubCategory(List<SubCategoryDTO> subCategoryList, Map<String, String> map) {
		subCategoryList.forEach(sub -> sub.setImageLink("images//" + map.get(sub.getImageId())));
	}

	private List<String> getImageIdListSubCategory(List<SubCategoryDTO> subCategoryList) {
		List<String> imageIds = subCategoryList.parallelStream().map(subCategory -> subCategory.getImageId().toString())
				.collect(Collectors.toList());

		return imageIds;
	}

	@Override
	public List<Category> findCategorySelectedFields() {

		return daoCategoryService.findCategorySelectedFields();
	}


	

	public List<CategoryOnProduct> getCategorySubCategory(String categoryId,String subId) {
		
	
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<CategoryOnProduct> query = cb.createQuery(CategoryOnProduct.class);
		Root<Category> catRoot = query.from(Category.class);
		Join<SubCategory, Product> catSubJoin = catRoot.join("subCategory");
		
		List<Predicate> criteria = new ArrayList<Predicate>();
		if (categoryId != null)
			criteria.add(cb.equal(catRoot.get("id"), categoryId));
		if (subId != null)
			criteria.add(cb.equal(catSubJoin.get("id"), subId));
		
		query.multiselect(catRoot.get("id"), catRoot.get("name"), catSubJoin.get("id"),
				catSubJoin.get("name")).distinct(true);
		
		query.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));

		List<CategoryOnProduct> categoryDTOList = em.createQuery(query).getResultList();
		return categoryDTOList;

		
	}

	public static boolean isNotNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return true;
		return false;
	}

	@Override
	public List<CategoryDTO> getCategorySubCategory(Map<String,String> map) {
		List<CategoryOnProduct> listCategory =null;
		String productName=map.get("search");
		if(productName!=null)
		{
			 listCategory = daoCategoryService.findCategorySubCategoryByProductName(productName);
		}
		else
		{
			String categoryId=map.get("catId");
			String subId=map.get("subId");
			listCategory = getCategorySubCategory(categoryId,subId);
		}
			
		Map<String, CategoryDTO> mapCategoryDTO = new HashMap<String, CategoryDTO>();
		for (CategoryOnProduct categoryOnProduct : listCategory) {
			CategoryDTO categoryDTO=null;
			if (mapCategoryDTO.get(categoryOnProduct.getCategoryId()) == null) {
				 categoryDTO = new CategoryDTO(categoryOnProduct.getCategoryId(),
						categoryOnProduct.getName());
				SubCategoryDTO subCategory = new SubCategoryDTO(categoryOnProduct.getSubCategoryId(),
						categoryOnProduct.getSubName());
				categoryDTO.getSubCategory().add(subCategory);
				mapCategoryDTO.put(categoryOnProduct.getCategoryId(), categoryDTO);

			} else {
				categoryDTO = mapCategoryDTO.get(categoryOnProduct.getCategoryId());
				SubCategoryDTO subCategory = new SubCategoryDTO(categoryOnProduct.getSubCategoryId(),
						categoryOnProduct.getSubName());
				categoryDTO.getSubCategory().add(subCategory);
			
			}
			
		}
		
		
		return new ArrayList<>(mapCategoryDTO.values());
	}


	
	

}
