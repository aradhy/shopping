package shop.dto;

public class ProductDTO {

	private String code;
	private String name;
	private String brand;
	private String imageId;
	private Double price;
	private Integer weight;
	private String weightUnit;
	
	public ProductDTO(String code, String name,String brand, String imageId,Double price, Integer weight, String weightUnit) {
		super();
		this.code = code;
		this.name = name;
		this.brand=brand;
		this.price=price;
		this.imageId = imageId;
		this.weight = weight;
		this.weightUnit = weightUnit;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
