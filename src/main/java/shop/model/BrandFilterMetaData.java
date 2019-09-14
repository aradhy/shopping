package shop.model;

public class BrandFilterMetaData implements Comparable<BrandFilterMetaData>{

	private String brandName;
	private boolean flag;
	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public int compareTo(BrandFilterMetaData brandFilterMetaData) {
	      
	       return this.getBrandName().compareTo(brandFilterMetaData.getBrandName());

	    }
	
	@Override
	 public int hashCode() 
	{
		return this.brandName.hashCode();
		
	}
	
	@Override
	public boolean  equals(Object obj)
	{
		
		BrandFilterMetaData brandFilter=(BrandFilterMetaData) obj;
		if(this.brandName==null || brandFilter.brandName==null)
		{
			return false;
		}
		
		return brandFilter.brandName.equals(this.brandName);
		
	}
	
}
