package shop.model;

public class WeightFilterMetaData {

	private Integer v1;
	private Integer v2;
	private String u1;
	private String u2;
	private String filterCriteria;
	private boolean flag ;

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

	/**
	 * @return the u1
	 */
	public String getU1() {
		return u1;
	}

	/**
	 * @param u1 the u1 to set
	 */
	public void setU1(String u1) {
		this.u1 = u1;
	}

	/**
	 * @return the u2
	 */
	public String getU2() {
		return u2;
	}

	/**
	 * @param u2 the u2 to set
	 */
	public void setU2(String u2) {
		this.u2 = u2;
	}


	@Override
	 public int hashCode() 
	{
		return this.v1.hashCode()+this.v2.hashCode();
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		WeightFilterMetaData weightFilterMetaData=(WeightFilterMetaData)obj;
		if(this.v1==weightFilterMetaData.v1 && weightFilterMetaData.v2==this.v2)
		{
			if(this.u1.equals(weightFilterMetaData.u1) && this.u2.equals(weightFilterMetaData.u2))
			{
				return true;
			}
		}
		
		return flag;

	}

}
