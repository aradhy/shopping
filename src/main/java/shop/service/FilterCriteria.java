package shop.service;

public class FilterCriteria {

	private Integer startWeight;
	private Integer endWeight;
	private String startWeightUnit;
	private String endWeightUnit;
	private String  catId;
	private String subId;
	
	/**
	 * @return the startWeight
	 */
	public Integer getStartWeight() {
		return startWeight;
	}
	/**
	 * @param startWeight the startWeight to set
	 */
	public void setStartWeight(Integer startWeight) {
		this.startWeight = startWeight;
	}
	/**
	 * @return the endWeight
	 */
	public Integer getEndWeight() {
		return endWeight;
	}
	/**
	 * @param endWeight the endWeight to set
	 */
	public void setEndWeight(Integer endWeight) {
		this.endWeight = endWeight;
	}
	/**
	 * @return the startWeightUnit
	 */
	public String getStartWeightUnit() {
		return startWeightUnit;
	}
	/**
	 * @param startWeightUnit the startWeightUnit to set
	 */
	public void setStartWeightUnit(String startWeightUnit) {
		this.startWeightUnit = startWeightUnit;
	}
	/**
	 * @return the endWeightUnit
	 */
	public String getEndWeightUnit() {
		return endWeightUnit;
	}
	/**
	 * @param endWeightUnit the endWeightUnit to set
	 */
	public void setEndWeightUnit(String endWeightUnit) {
		this.endWeightUnit = endWeightUnit;
	}
	/**
	 * @return the catId
	 */
	public String getCatId() {
		return catId;
	}
	/**
	 * @param catId the catId to set
	 */
	public void setCatId(String catId) {
		this.catId = catId;
	}
	/**
	 * @return the subId
	 */
	public String getSubId() {
		return subId;
	}
	/**
	 * @param subId the subId to set
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}
	
	
}
