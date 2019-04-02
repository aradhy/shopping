package shop.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
@Table(name="Product")
public class Product{
	@Id
	private String code;
	private String name;
	private String subId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss", locale = "en")
    protected LocalDateTime prod_added_date=LocalDateTime.now();
	private String brand;
    private String imageId;
    private String imageLink;
	@OneToMany(fetch=FetchType.LAZY, targetEntity=ProductAvail.class, cascade=CascadeType.ALL,mappedBy="productId")
	private List<ProductAvail> productAvailList;
    public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
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
	public LocalDateTime getProd_added_date() {
		return prod_added_date;
	}
	public void setProd_added_date(LocalDateTime prod_added_date) {
		this.prod_added_date = prod_added_date;
	}
	public List<ProductAvail> getProductAvailList() {
		return productAvailList;
	}
	public void setProductAvailList(List<ProductAvail> productAvailList) {
		this.productAvailList = productAvailList;
	}
	
	
}
