package shop.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
@Table(name="Product")
public class Product{
	@Id
	private String code;
	private String name;
	private String subId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss", locale = "en")
    protected LocalDateTime prodAddedDate=LocalDateTime.now();
	private String brand;
	@Column(columnDefinition = "TEXT")
    private String imageId;
	@OneToMany(fetch=FetchType.LAZY, targetEntity=ProductAvail.class, cascade=CascadeType.ALL,mappedBy="productId")
	@Basic(fetch=FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.TRUE)
	private List<ProductAvail> productAvailList;
	public String getBrand() {
		return brand;
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
	
	
}
