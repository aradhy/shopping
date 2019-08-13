package shop.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
	
	public CategoryDTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	private String id;
	public CategoryDTO() {
		super();
		
	}

	private String name;



	private List<SubCategoryDTO> subCategory=new ArrayList<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the subCategory
	 */
	public List<SubCategoryDTO> getSubCategory() {
		return subCategory;
	}

	/**
	 * @param subCategory the subCategory to set
	 */
	public void setSubCategory(List<SubCategoryDTO> subCategory) {
		this.subCategory = subCategory;
	}


}
