package shop.model;

public class FilterPriceIntervals {
	
	private Integer minPrice;
	private Integer maxPrice;
	
	
	public FilterPriceIntervals(double minPrice,Double maxPrice)
	{
		this.minPrice=(int) minPrice;
		
		String doubleMaxString=maxPrice.toString();
		if(!doubleMaxString.substring(doubleMaxString.indexOf(".")+1,doubleMaxString.indexOf(".")+2).equals("0"))
		{
			this.maxPrice=Integer.valueOf(maxPrice.intValue())+1;
		}
		else
		{
			this.maxPrice=Integer.valueOf(maxPrice.intValue());
		}
		
	}
	/**
	 * @return the minPrice
	 */
	public Integer getMinPrice() {
		return minPrice;
	}
	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	/**
	 * @return the maxPrice
	 */
	public Integer getMaxPrice() {
		return maxPrice;
	}
	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

}
