package shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
			categorytDTOList = categoryList.parallelStream().map(category -> convertToDTOCategory(category,true))
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

	CategoryDTO convertToDTOCategory(Category category,boolean flag) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		List<SubCategoryDTO> subCategoryList = category.getSubCategory().parallelStream()
				.map(sub -> convertToDTOSubCategory(sub,flag)).collect(Collectors.toList());
		categoryDTO.setSubCategory(subCategoryList);

		return categoryDTO;
	}

	SubCategoryDTO convertToDTOSubCategory(SubCategory subCategory,boolean flag) {
		SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
		subCategoryDTO.setId(subCategory.getId());
		subCategoryDTO.setName(subCategory.getName());
		if(flag)
		{
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

	@Override
	public List<Category> getSubCategoryByCategoryId(String categoryId,String subCategoryId) {
		return getSubCategory(categoryId,subCategoryId);
	}

	List<Category> getSubCategory(String categoryId,String subCategoryId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Category> catQuery=null;
		CriteriaQuery<SubCategory> subCatQuery=null;
		Root<Category> catRoot=null;
		if(isNotNullOrEmpty(categoryId))
		{
			catQuery = cb.createQuery(Category.class);
			 catRoot = catQuery.from(Category.class);	
			
		}
		if(isNotNullOrEmpty(subCategoryId))
			subCatQuery = cb.createQuery(SubCategory.class);
		List<Predicate> criteria = new ArrayList<Predicate>();
		if(isNotNullOrEmpty(subCategoryId))
		criteria.add(cb.equal(catRoot.get("id"),categoryId));
		if(isNotNullOrEmpty(categoryId))
		criteria.add(cb.equal(catRoot.get("id"),categoryId));
		catQuery.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		
		List<Category> catList=  em.createQuery(catQuery).getResultList();
		return catList;

	}


	public static boolean isNotNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return true;
		return false;
	}

	@Override
	public CategoryDTO findCategorySubCategoryNames(String catId) {
		// daoCategoryService.findById(catId);
		 Optional<Category> categoryOpt = daoCategoryService.findById(catId);
			Category category=	categoryOpt.get();
			return convertToDTOCategory(category,false);
		
	}

}
