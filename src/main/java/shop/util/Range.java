package shop.util;

public class Range {

	private  Integer max;
	private  Integer min;
	

	public Range(Integer min, Integer max) {
		this.min = min;
		this.max = max;
	}

	
	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}


	/**
	 * @param min the min to set
	 */
	public void setMin(Integer min) {
		this.min = min;
	}


	/**
	 * @return the min
	 */
	public Integer getMin() {
		return min;
	}

	public boolean  equals(Object obj)
	{
		
		Range range=(Range) obj;
		if(this.max==null)
		{
			return false;
		}
		
		return range.min==this.min && range.max==this.max ;
		
	}
	@Override
	public int  hashCode()
	{
		return this.min.hashCode()+this.max.hashCode();
		
	}
}
