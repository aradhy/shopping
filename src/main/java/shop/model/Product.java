package shop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "Product")
@JsonInclude(value=Include.NON_NULL)
public class Product {
	@Id
	private String code;
	private String name;
	private String subId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss", locale = "en")
	protected LocalDateTime prodAddedDate = LocalDateTime.now();
	private String brand;
	@Column(columnDefinition = "TEXT")
	private String imageId;
	@OneToMany(fetch = FetchType.LAZY, targetEntity = ProductAvail.class, cascade = CascadeType.ALL, mappedBy = "productId")
	@Basic(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<ProductAvail> productAvailList=new ArrayList<>();
	@Transient
	@JsonIgnore
	private Integer weight;
	@Transient
	@JsonIgnore
	private String weightUnit;
	
	/**
	 * @return the maxWeight
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the minPrice
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param minPrice the minPrice to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	@Transient
	@JsonIgnore
	private Double price;
	@Transient
	@JsonIgnore
	private Long count;

	
	public Product()
	{
		
	}
	
	public String getBrand() {
		return brand;
	}





	/**
	 * @return the weightUnit
	 */
	@JsonIgnore
	public String getWeightUnit() {
		return weightUnit;
	}

	/**
	 * @param weightUnit the weightUnit to set
	 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
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

	public List<ProductAvail> getProductAvailList() {
		return productAvailList;
	}

	public void setProductAvailList(List<ProductAvail> productAvailList) {
		this.productAvailList = productAvailList;
	}

	public Product(Integer weight,String weightUnit,Double price) {
		
		this.weight = weight;
		this.weightUnit = weightUnit;
		this.price=price;
	}


}
