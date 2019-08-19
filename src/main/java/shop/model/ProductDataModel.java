package shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductDataModel {
	@JsonIgnore
	private Integer weight;
	@JsonIgnore
	private String weightUnit;
	private Product product;
	@JsonIgnore
	private Double price;
	
	public ProductDataModel(Product product,Integer weight, String weightUnit,Double price ) {
		super();
		this.weight = weight;
		this.price=price;
		this.weightUnit = weightUnit;
		this.product = product;
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
