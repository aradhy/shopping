package shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ProductAvail")
public class ProductAvail {
	
   	@Id
	@Column(name="id")
	protected String id;
	private String productId;
	private Integer totalAvailUnits;
	private Integer weight;
	private String weightUnit;
	private Double price;


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
	
	

}
