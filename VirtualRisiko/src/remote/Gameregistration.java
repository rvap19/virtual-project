package remote;

// Generated 6-lug-2011 21.18.40 by Hibernate Tools 3.4.0.CR1




public class Gameregistration implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7280366682091469755L;
	private GameregistrationId id;
	private Game game;
	private User user;
	private Integer punteggio;
	private Boolean vincitore;

	public Gameregistration() {
	}

	public Gameregistration(GameregistrationId id, Game game, User user) {
		this.id = id;
		this.game = game;
		this.user = user;
	}

	public Gameregistration(GameregistrationId id, Game game, User user,
			Integer punteggio, Boolean vincitore) {
		this.id = id;
		this.game = game;
		this.user = user;
		this.punteggio = punteggio;
		this.vincitore = vincitore;
	}

	public GameregistrationId getId() {
		return this.id;
	}

	public void setId(GameregistrationId id) {
		this.id = id;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Integer getPunteggio() {
		return this.punteggio;
	}

	public void setPunteggio(Integer punteggio) {
		this.punteggio = punteggio;
	}

	
	public Boolean getVincitore() {
		return this.vincitore;
	}

	public void setVincitore(Boolean vincitore) {
		this.vincitore = vincitore;
	}

}
