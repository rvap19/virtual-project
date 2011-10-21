package org.domain.risikoweb.entity;


public class TournamentregistrationId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1694928943682872233L;
	private String userUsername;
	private int tournamentId;

	public TournamentregistrationId() {
	}

	public TournamentregistrationId(String userUsername, int tournamentId) {
		this.userUsername = userUsername;
		this.tournamentId = tournamentId;
	}

	public String getUserUsername() {
		return this.userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public int getTournamentId() {
		return this.tournamentId;
	}

	public void setTournamentId(int tournamentId) {
		this.tournamentId = tournamentId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TournamentregistrationId))
			return false;
		TournamentregistrationId castOther = (TournamentregistrationId) other;

		return ((this.getUserUsername() == castOther.getUserUsername()) || (this
				.getUserUsername() != null
				&& castOther.getUserUsername() != null && this
				.getUserUsername().equals(castOther.getUserUsername())))
				&& (this.getTournamentId() == castOther.getTournamentId());
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getUserUsername() == null ? 0 : this.getUserUsername()
						.hashCode());
		result = 37 * result + this.getTournamentId();
		return result;
	}

}
