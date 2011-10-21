package org.domain.risikoweb.entity;

// Generated 18-ott-2011 18.00.20 by Hibernate Tools 3.4.0.CR1



public class UserOnLine implements java.io.Serializable {

	private String userUsername;
	private String ipaddress;

	public UserOnLine() {
	}

	public UserOnLine(String userUsername, String ipaddress) {
		this.userUsername = userUsername;
		this.ipaddress = ipaddress;
	}

	
	public String getUserUsername() {
		return this.userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

}
