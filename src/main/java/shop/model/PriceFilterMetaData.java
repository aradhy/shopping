package shop.model;

import shop.util.Range;

public class PriceFilterMetaData {
	
	private Integer v1;
	private Integer v2;
	boolean priceFlag=false;
	private String filterCriteria;
	
	/**
	 * @return the filterCriteria
	 */
	public String getFilterCriteria() {
		return filterCriteria;
	}
	/**
	 * @param filterCriteria the filterCriteria to set
	 */
	public void setFilterCriteria(String filterCriteria) {
		this.filterCriteria = filterCriteria;
	}
	/**
	 * @return the priceFlag
	 */
	public boolean isPriceFlag() {
		return priceFlag;
	}
	/**
	 * @param priceFlag the priceFlag to set
	 */
	public void setPriceFlag(boolean priceFlag) {
		this.priceFlag = priceFlag;
	}
	/**
	 * @return the v1
	 */
	public Integer getV1() {
		return v1;
	}
	/**
	 * @param v1 the v1 to set
	 */
	public void setV1(Integer v1) {
		this.v1 = v1;
	}
	/**
	 * @return the v2
	 */
	public Integer getV2() {
		return v2;
	}
	/**
	 * @param v2 the v2 to set
	 */
	public void setV2(Integer v2) {
		this.v2 = v2;
	}
	

	public boolean  equals(Object obj)
	{
		
		PriceFilterMetaData price=(PriceFilterMetaData) obj;
		if(this.v2==null)
		{
			return false;
		}
		
		return price.v1==this.v1 && price.v2==this.v2 ;
		
	}
	@Override
	public int  hashCode()
	{
		return this.v1.hashCode()+this.v2.hashCode();
		
	}
}
