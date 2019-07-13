package shop.dto;

public class UserDetailsDeliveryDTO {
	
	private Integer houseNo;
	private String areaDetails;
	private String city; 
	private String country;
	private Long pinCode;
	private String landmark;
	private Long phoneNumber;

	/**
	 * @return the houseNo
	 */
	public Integer getHouseNo() {
		return houseNo;
	}
	/**
	 * @param houseNo the houseNo to set
	 */
	public void setHouseNo(Integer houseNo) {
		this.houseNo = houseNo;
	}
	
	/**
	 * @return the areaDetails
	 */
	public String getAreaDetails() {
		return areaDetails;
	}
	/**
	 * @param areaDetails the areaDetails to set
	 */
	public void setAreaDetails(String areaDetails) {
		this.areaDetails = areaDetails;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the pinCode
	 */
	public Long getPinCode() {
		return pinCode;
	}
	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}
	/**
	 * @return the landmark
	 */
	public String getLandmark() {
		return landmark;
	}
	/**
	 * @param landmark the landmark to set
	 */
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

}
