package shop.model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class CriteriaBuilderModel {

	Root<SubCategory> subRoot;
	Join<SubCategory, Product> prodSubJoin;
	Join<ProductAvail, Product> prodAvail;
	CriteriaBuilder cb;

	CriteriaQuery<ProductDataModel> query;
	public CriteriaBuilderModel(CriteriaBuilder cb, CriteriaQuery<ProductDataModel> query,
			Join<SubCategory, Product> prodSubJoin, Join<ProductAvail, Product> prodAvail) {
		this.cb=cb;
		this.query=query;
		this.prodSubJoin=prodSubJoin;
		this.prodAvail=prodAvail;
		
	}
	/**
	 * @return the cb
	 */
	public CriteriaBuilder getCb() {
		return cb;
	}
	/**
	 * @param cb the cb to set
	 */
	public void setCb(CriteriaBuilder cb) {
		this.cb = cb;
	}
	/**
	 * @return the subRoot
	 */
	public Root<SubCategory> getSubRoot() {
		return subRoot;
	}
	/**
	 * @param subRoot the subRoot to set
	 */
	public void setSubRoot(Root<SubCategory> subRoot) {
		this.subRoot = subRoot;
	}
	/**
	 * @return the prodSubJoin
	 */
	public Join<SubCategory, Product> getProdSubJoin() {
		return prodSubJoin;
	}
	/**
	 * @param prodSubJoin the prodSubJoin to set
	 */
	public void setProdSubJoin(Join<SubCategory, Product> prodSubJoin) {
		this.prodSubJoin = prodSubJoin;
	}
	/**
	 * @return the prodAvail
	 */
	public Join<ProductAvail, Product> getProdAvail() {
		return prodAvail;
	}
	/**
	 * @param prodAvail the prodAvail to set
	 */
	public void setProdAvail(Join<ProductAvail, Product> prodAvail) {
		this.prodAvail = prodAvail;
	}
	
	/**
	 * @return the query
	 */
	public CriteriaQuery<ProductDataModel> getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(CriteriaQuery<ProductDataModel> query) {
		this.query = query;
	}
	
}
