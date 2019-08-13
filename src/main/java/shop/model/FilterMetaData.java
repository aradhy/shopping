package shop.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class FilterMetaData {
	private List<String> priceFilters = new ArrayList<String>();
	private List<String> weightFilters = new ArrayList<String>();

	/**
	 * @return the priceFilters
	 */
	public List<String> getPriceFilters() {
		return priceFilters;
	}

	/**
	 * @param priceFilters the priceFilters to set
	 */
	public void setPriceFilters(List<String> priceFilters) {
		this.priceFilters = priceFilters;
	}

	/**
	 * @return the weightFilters
	 */
	public List<String> getWeightFilters() {
		return weightFilters;
	}

	/**
	 * @param weightFilters the weightFilters to set
	 */
	public void setWeightFilters(List<String> weightFilters) {
		this.weightFilters = weightFilters;
	}

}
