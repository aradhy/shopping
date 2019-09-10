package shop.dto;

public class CategoryOnProduct {
	private String categoryId;
	public CategoryOnProduct(String categoryId, String name, String subCategoryId, String subName) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.subCategoryId = subCategoryId;
		this.subName = subName;
	}
	private String name;
	private String subCategoryId;
	private String subName;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

}
