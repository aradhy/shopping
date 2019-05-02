package shop.dto;

public class ProductDTO {

	private String prodAvailId;
	private String prodCode;
	private String name;
	private String brand;
	private String imageId;
	private Double price;
	private Integer weight;
	private String weightUnit;
	
	
	public ProductDTO(String prodAvailId,String prodCode, String name,String brand, String imageId,Double price, Integer weight, String weightUnit) {
		super();
		this.setProdAvailId(prodAvailId);
		this.setProdCode(prodCode);
		this.name = name;
		this.brand=brand;
		this.price=price;
		this.imageId = imageId;
		this.weight = weight;
		this.weightUnit = weightUnit;
		
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

	/**
	 * @return the prodCode
	 */
	public String getProdCode() {
		return prodCode;
	}

	/**
	 * @param prodCode the prodCode to set
	 */
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}


	/**
	 * @return the prodAvailId
	 */
	public String getProdAvailId() {
		return prodAvailId;
	}


	/**
	 * @param prodAvailId the prodAvailId to set
	 */
	public void setProdAvailId(String prodAvailId) {
		this.prodAvailId = prodAvailId;
	}
	
	
}
