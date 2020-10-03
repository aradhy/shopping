package shop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class FilterMetaData {
	
	private Set<PriceFilterMetaData> priceFilters = new HashSet<PriceFilterMetaData>();
	private List<WeightFilterMetaData> weightFilters = new ArrayList<WeightFilterMetaData>();
	private Set<BrandFilterMetaData> brandFilters = new HashSet<BrandFilterMetaData>();
	private Boolean priceFlag=false;
	private Boolean weightFlag=false;
	private Boolean brandFlag=false;




	/**
	 * @return the priceFlag
	 */
	public Boolean getPriceFlag() {
		return priceFlag;
	}





	/**
	 * @param priceFlag the priceFlag to set
	 */
	public void setPriceFlag(Boolean priceFlag) {
		this.priceFlag = priceFlag;
	}





	/**
	 * @return the weightFlag
	 */
	public Boolean getWeightFlag() {
		return weightFlag;
	}





	/**
	 * @param weightFlag the weightFlag to set
	 */
	public void setWeightFlag(Boolean weightFlag) {
		this.weightFlag = weightFlag;
	}





	/**
	 * @return the brandFlag
	 */
	public Boolean getBrandFlag() {
		return brandFlag;
	}





	/**
	 * @param brandFlag the brandFlag to set
	 */
	public void setBrandFlag(Boolean brandFlag) {
		this.brandFlag = brandFlag;
	}





	/**
	 * @return the priceFilters
	 */
	public Set<PriceFilterMetaData> getPriceFilters() {
		return priceFilters;
	}


	


	/**
	 * @return the brandFilters
	 */
	public Set<BrandFilterMetaData> getBrandFilters() {
		return brandFilters;
	}


	/**
	 * @param brandFilters2 the brandFilters to set
	 */
	public void setBrandFilters(Set<BrandFilterMetaData> brandFilters2) {
		this.brandFilters = brandFilters2;
	}


	/**
	 * @param priceFilters the priceFilters to set
	 */
	public void setPriceFilters(Set<PriceFilterMetaData> priceFilters) {
		this.priceFilters = priceFilters;
	}

	/**
	 * @return the weightFilters
	 */
	public List<WeightFilterMetaData> getWeightFilters() {
		return weightFilters;
	}

	/**
	 * @param weightFilters the weightFilters to set
	 */
	public void setWeightFilters(List<WeightFilterMetaData> weightFilters) {
		this.weightFilters = weightFilters;
	}

}
