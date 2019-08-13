package shop.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class FilterMetaData {
	
    private String filterCriteria;
	private List<PriceFilterMetaData> priceFilters = new ArrayList<PriceFilterMetaData>();
	private List<WeightFilterMetaData> weightFilters = new ArrayList<WeightFilterMetaData>();

	/**
	 * @return the priceFilters
	 */
	public List<PriceFilterMetaData> getPriceFilters() {
		return priceFilters;
	}

	/**
	 * @param priceFilters the priceFilters to set
	 */
	public void setPriceFilters(List<PriceFilterMetaData> priceFilters) {
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
}
