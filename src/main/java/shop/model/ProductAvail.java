package shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="product_avail")
@JsonInclude(value=Include.NON_NULL)
public class ProductAvail {
	
   	@Id
	@Column(name="id")
	protected String id;
	private String productId;
	private Integer totalAvailUnits;
	private Integer weight;
	private String weightUnit;
	private Double price;
	@Transient
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
	@Transient
	private Integer maxWeight;

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getTotalAvailUnits() {
		return totalAvailUnits;
	}
	public void setTotalAvailUnits(Integer totalAvailUnits) {
		this.totalAvailUnits = totalAvailUnits;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	
	/**
	 * @return the maxWeight
	 */
	public Integer getMaxWeight() {
		return maxWeight;
	}
	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setMaxWeight(Integer maxWeight) {
		this.maxWeight = maxWeight;
	}

}
