package remote;

// Generated 18-ott-2011 18.00.20 by Hibernate Tools 3.4.0.CR1



public class UserOnLine implements java.io.Serializable {

	private String userUsername;
	private User user;
	private String ipaddress;

	public UserOnLine() {
	}

	public UserOnLine(User user, String ipaddress) {
		this.user = user;
		this.ipaddress = ipaddress;
	}

	
	public String getUserUsername() {
		return this.userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

}
