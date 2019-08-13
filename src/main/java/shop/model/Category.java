package shop.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Category")
public class Category {
	@Id

	@Column(name = "id")

	protected String id;
	protected String name;
	protected String description;
	@Transient
	private String subId;
	@Transient
	private String subName;

	public Category(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Category(String id, String name,String subId,String subName) {
		super();
		this.id = id;
		this.name = name;
		SubCategory subCa=new SubCategory();
		subCa.setName(subName);
		subCa.setId(subId);
		this.subCategory.add(subCa);
	}

	public Category() {
		super();

	}

	@OneToMany(fetch = FetchType.EAGER, targetEntity = SubCategory.class, cascade = CascadeType.ALL, mappedBy = "categoryId")
	private Set<SubCategory> subCategory=new HashSet<>();

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


}
