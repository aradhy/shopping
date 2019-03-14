package shop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="Category")
public class Category
{
	@Id
	@Column(name="category_Id")
	private Long category_id;
	private String name;
	private String description;
	public Category(Long id,String name)
	{
		this.category_id=id;
		this.name=name;
	}
	
	public Category() {
		super();
		
	}
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=SubCategory.class, cascade=CascadeType.ALL,mappedBy="category_id")
	private Set<SubCategory> subCategory;

	
	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
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
	
}
