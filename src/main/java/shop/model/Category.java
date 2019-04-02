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
	@Column(name = "image_id")
	protected String image_id;
	@Transient
	@JsonIgnore
	protected String imageLink;

	public Category(String id, String name, String image_id) {
		super();
		this.id = id;
		this.name = name;
		this.image_id = image_id;
	}

	public Category() {
		super();

	}

	@OneToMany(fetch = FetchType.LAZY, targetEntity = SubCategory.class, cascade = CascadeType.ALL, mappedBy = "category_id")
	private Set<SubCategory> subCategory;

	public String getId() {
		return id;
	}

	public void setCategory_id(String id) {
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
		return image_id;
	}

	public void setImageId(String image_id) {
		this.image_id = image_id;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

}
