package com.ktdsuniversity.edu.common.security.oauth.provider.vo;

public class OAuthProviderVO {

	private String email;
	private String provider;
	private String crtDt;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getCrtDt() {
		return crtDt;
	}
	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}
	@Override
	public String toString() {
		return "OAuthProviderVO [email=" + email + ", provider=" + provider + ", crtDt=" + crtDt + "]";
	}
	
	
	
	
}
