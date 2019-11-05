package shop.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductFilter {

	@JsonIgnore
	private Product product;
	private String code;
	private String name;
	private String subId;
	private String brand;
	private String imageId;
	private List<ProductAvail> productAvailList;

	public ProductFilter(Product product, List<ProductAvail> prodAvail) {
		super();
		this.product = product;
		convertProductToDomain(product);
		this.productAvailList = prodAvail;
	}

	private void convertProductToDomain(Product product) {
		this.code = product.getCode();
		this.name = product.getName();
		this.brand = product.getBrand();
		this.imageId = product.getImageId();
		this.subId = product.getSubId();
	}

	/**
	 * 
	 * 
	 * @return the productCode
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the prodAvail
	 */
	public List<ProductAvail> getProdAvail() {
		return productAvailList;
	}

	/**
	 * @param prodAvail the prodAvail to set
	 */
	public void setProdAvail(List<ProductAvail> prodAvail) {
		this.productAvailList = (List<ProductAvail>) prodAvail;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the subId
	 */
	public String getSubId() {
		return subId;
	}

	/**
	 * @param subId the subId to set
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
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
