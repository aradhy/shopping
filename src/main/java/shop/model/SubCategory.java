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
@Entity
@Table(name="Sub_Category")
@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)
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
		return image_id;
	}
	public void setImageId(String imageId) {
		this.image_id = imageId;
	}
	protected String name;
	protected String description;
	@Column(name="image_id")
	protected String image_id;
	private String imageLink;
	
	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public SubCategory(String id,String name,String image_id)
	{
		//super(id,name,image_id);
		this.id=id;
		this.name=name;
		this.image_id=image_id;
	}
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=Product.class, cascade=CascadeType.ALL,mappedBy="subId")
	private List<Product> productList;
	
	private String category_id;

	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
