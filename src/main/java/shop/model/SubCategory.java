package shop.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)
@Table(name = "subcategory")
public class SubCategory{
	
	@Id
	@Column(name="id")
	protected String id;

	public SubCategory() {
		super();
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	protected String name;
	protected String description;
	@Column(columnDefinition = "TEXT")
	protected String imageId;
	@Transient
	private String imageLink;
	
	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public SubCategory(String id,String name,String imageId)
	{
		//super(id,name,image_id);
		this.id=id;
		this.name=name;
		this.imageId=imageId;
	}
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=Product.class, cascade=CascadeType.ALL,mappedBy="subId")
	private List<Product> productList;
	
	private String categoryId;


	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
