package remote;

// Generated 6-lug-2011 21.18.40 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class Tournamentregistration implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8765762959757839478L;
	private TournamentregistrationId id;
	private Tournament tournament;
	private User user;
	private Date dataIscrizione;
	private int punteggio;
	private boolean vincitore;

	public Tournamentregistration() {
	}

	public Tournamentregistration(TournamentregistrationId id,
			Tournament tournament, User user, Date dataIscrizione,
			int punteggio, boolean vincitore) {
		this.id = id;
		this.tournament = tournament;
		this.user = user;
		this.dataIscrizione = dataIscrizione;
		this.punteggio = punteggio;
		this.vincitore = vincitore;
	}

	public TournamentregistrationId getId() {
		return this.id;
	}

	public void setId(TournamentregistrationId id) {
		this.id = id;
	}
	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Date getDataIscrizione() {
		return this.dataIscrizione;
	}

	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}

	
	public int getPunteggio() {
		return this.punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	
	public boolean isVincitore() {
		return this.vincitore;
	}

	public void setVincitore(boolean vincitore) {
		this.vincitore = vincitore;
	}

}
