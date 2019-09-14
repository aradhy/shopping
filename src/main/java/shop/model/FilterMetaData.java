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
