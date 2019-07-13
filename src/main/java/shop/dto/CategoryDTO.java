package shop.dto;

import java.util.List;

public class CategoryDTO {
	
	private String name;
	private String id;
	private String imageId;


	private List<SubCategoryDTO> subCategoryList;
	
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
	public List<SubCategoryDTO> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<SubCategoryDTO> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	/**
	 * @return the imageId
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
