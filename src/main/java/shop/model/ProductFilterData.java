package shop.model;

import java.util.ArrayList;
import java.util.List;

public class ProductFilterData {
	private List<ProductDataModel> product=new ArrayList<ProductDataModel>();
	private FilterMetaData filterMetaData=new FilterMetaData();
	/**
	 * @return the product
	 */
	public List<ProductDataModel> getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(List<ProductDataModel> product) {
		this.product = product;
	}
	/**
	 * @return the filterMetaData
	 */
	public FilterMetaData getFilterMetaData() {
		return filterMetaData;
	}
	/**
	 * @param filterMetaData the filterMetaData to set
	 */
	public void setFilterMetaData(FilterMetaData filterMetaData) {
		this.filterMetaData = filterMetaData;
	}

}
