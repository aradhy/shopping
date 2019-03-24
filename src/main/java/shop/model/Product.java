package shop.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
@Table(name="Product")
public class Product{
	@Id
	private Long code;
	private String name;
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss", locale = "en")
    protected LocalDateTime prod_added_date=LocalDateTime.now();
	
	
	private String brand;
    private Long imageId;
    private String imageLink;
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
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	
   	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the code
	 */
	public Long getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Long code) {
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
	
	
}
