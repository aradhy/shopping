package shop.model;

public class SearchProduct {
	
	

	private String catId;
	private Product prod=new Product();
	
	public SearchProduct(String catId, Product prod) {
		super();
		this.catId = catId;
		this.prod=prod;
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
	 * @return the prod
	 */
	public Product getProd() {
		return prod;
	}

	/**
	 * @param prod the prod to set
	 */
	public void setProd(Product prod) {
		this.prod = prod;
	}
	
}
