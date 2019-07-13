package shop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Category")
public class Category {
	@Id

	@Column(name = "id")

	protected String id;
	protected String name;
	protected String description;
	@Column(name = "imageId")
	protected String imageId;
	@Transient
	@JsonIgnore
	protected String imageLink;

	public Category(String id, String name, String imageId) {
		super();
		this.id = id;
		this.name = name;
		this.imageId = imageId;
	}

	public Category() {
		super();

	}

	@OneToMany(fetch = FetchType.EAGER, targetEntity = SubCategory.class, cascade = CascadeType.ALL, mappedBy = "categoryId")
	private Set<SubCategory> subCategory;

	public String getId() {
		return id;
	}

	public void setCategoryId(String id) {
		this.id = id;
	}

	public Set<SubCategory> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<SubCategory> subCategory) {
		this.subCategory = subCategory;
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

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

}
