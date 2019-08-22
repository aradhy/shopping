package shop.model;

public enum OrderStatus {

	OrderStatus1(1,"Pending Payment"), OrderStatus2(2,"Payment Complete"), OrderStatus3("Processing"), OrderStatus4(
			"Cancelled"), OrderStatus5("Completed");
	private Integer id;
	private String value;
	
	
	private OrderStatus(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	private OrderStatus(String value) {
		this.value = value;
	}

}
