package shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class SubCategoryDTO {
	
	
	private String id;
	private String name;
	private String imageId;
	private String imageLink;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	/**
	 * @return the imageLink
	 */
	public String getImageLink() {
		return imageLink;
	}
	/**
	 * @param imageLink the imageLink to set
	 */
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	@Override
	public int hashCode()
	{
		return this.id.hashCode()+this.name.hashCode();
		
	}
	@Override
	public boolean equals(Object obj)
	{
		SubCategoryDTO sub=(SubCategoryDTO)obj;
		if(this.id==null || sub.id==null)
		{
			return true;
		}
		if(this.id.equals(sub.id))
		{
			return true;
		}
		return false;
		
	}
	
}
