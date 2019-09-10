package shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FilterDataModel {
	@JsonIgnore
	private Integer weight;
	@JsonIgnore
	private String weightUnit;
	private Product product;
	@JsonIgnore
	private Double price;
	private String brand;
	
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
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	public FilterDataModel(String brand,Integer weight, String weightUnit,Double price ) {
		super();
		this.weight = weight;
		this.price=price;
		this.weightUnit = weightUnit;
		this.brand = brand;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * @return the weightUnit
	 */
	public String getWeightUnit() {
		return weightUnit;
	}

	/**
	 * @param weightUnit the weightUnit to set
	 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	/**
	 * @return the productList
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(Product productList) {
		this.product = productList;
	}

}
