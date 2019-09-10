package shop.model;

import java.util.List;

import shop.dto.SubCategoryNames;

public class CategoryCompDTO {
	
	private String categoryId;
	String catName;
	String subCategoryId;
	String subName;
	
	public CategoryCompDTO(String categoryId,String catName,SubCategory subCategory)
	{
		this.categoryId=categoryId;
		this.catName=catName;
	}
	List<Product> product;
	List<SubCategoryNames> subCategoryNames;
	
	/**
	 * @return the subCategoryNames
	 */
	public List<SubCategoryNames> getSubCategoryNames() {
		return subCategoryNames;
	}
	/**
	 * @param subCategoryNames the subCategoryNames to set
	 */
	public void setSubCategoryNames(List<SubCategoryNames> subCategoryNames) {
		this.subCategoryNames = subCategoryNames;
	}
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the catName
	 */
	public String getCatName() {
		return catName;
	}
	/**
	 * @param catName the catName to set
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}
	/**
	 * @return the subCategoryId
	 */
	public String getSubCategoryId() {
		return subCategoryId;
	}
	/**
	 * @param subCategoryId the subCategoryId to set
	 */
	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	/**
	 * @return the subName
	 */
	public String getSubName() {
		return subName;
	}
	/**
	 * @param subName the subName to set
	 */
	public void setSubName(String subName) {
		this.subName = subName;
	}
	/**
	 * @return the product
	 */
	public List<Product> getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(List<Product> product) {
		this.product = product;
	}

}
