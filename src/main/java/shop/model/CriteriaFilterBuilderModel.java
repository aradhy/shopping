package shop.model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class CriteriaFilterBuilderModel {

	Root<SubCategory> subRoot;
	Root<Category> catRoot;
	/**
	 * @return the catRoot
	 */
	public Root<Category> getCatRoot() {
		return catRoot;
	}
	/**
	 * @param catRoot the catRoot to set
	 */
	public void setCatRoot(Root<Category> catRoot) {
		this.catRoot = catRoot;
	}
	Join<SubCategory, Product> prodSubJoin;
	Join<ProductAvail, Product> prodAvail;
	Join<Category, SubCategory> catSubJoin;
	CriteriaBuilder cb;

	CriteriaQuery<FilterDataModel> query;
	public CriteriaFilterBuilderModel(CriteriaBuilder cb, CriteriaQuery<FilterDataModel> query,
			Join<SubCategory, Product> prodSubJoin, Join<ProductAvail, Product> prodAvail,Join<Category, SubCategory> catSubJoin) {
		this.cb=cb;
		this.query=query;
		this.prodSubJoin=prodSubJoin;
		this.prodAvail=prodAvail;
		this.catSubJoin=catSubJoin;
	}
	/**
	 * @return the catSubJoin
	 */
	public Join<Category, SubCategory> getCatSubJoin() {
		return catSubJoin;
	}
	/**
	 * @param catSubJoin the catSubJoin to set
	 */
	public void setCatSubJoin(Join<Category, SubCategory> catSubJoin) {
		this.catSubJoin = catSubJoin;
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
	public CriteriaQuery<FilterDataModel> getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(CriteriaQuery<FilterDataModel> query) {
		this.query = query;
	}
	
}
