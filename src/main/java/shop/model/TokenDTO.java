package shop.model;

public class TokenDTO {
	
	private String userId;
	private String name;
	private String jwtToken;
	private String csrfToken;
	private String id_token;
	private String access_token;
	private Long expires_in;
	 
	
	
	
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
	public String getUserId() {
		return userId;
	}
	public String getAccess_token() {
		return access_token;
	}
	public String getId_token() {
		return id_token;
	}
	public void setId_token(String id_token) {
		this.id_token = id_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public String getCsrfToken() {
		return csrfToken;
	}
	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}


}
