package org.domain.risikoweb.entity;

// Generated 12-lug-2011 16.56.55 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



public class Game implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 994505013717249392L;
	private Integer id;
	private boolean attiva;
	private String nome;
	private Date dataCreazione;
	private Date inizio;
	private Date fine;
	private int numeroTurniMax;
	private Integer idtorneo;
	private Byte faseTorneo;
	private String mappa;
	private int numeroGiocatoriMax;
	private String managerUsername;
	private Set<Gameregistration> gameregistrations = new HashSet<Gameregistration>(
			0);

	public Game() {
	}

	public Game(boolean attiva, String nome, Date dataCreazione, Date inizio,
			int numeroTurniMax, String mappa, int numeroGiocatoriMax,
			String managerUsername) {
		this.attiva = attiva;
		this.nome = nome;
		this.dataCreazione = dataCreazione;
		this.inizio = inizio;
		this.numeroTurniMax = numeroTurniMax;
		this.mappa = mappa;
		this.numeroGiocatoriMax = numeroGiocatoriMax;
		this.managerUsername = managerUsername;
	}

	public Game(boolean attiva, String nome, Date dataCreazione, Date inizio,
			Date fine, int numeroTurniMax, Integer idtorneo, Byte faseTorneo,
			String mappa, int numeroGiocatoriMax, String managerUsername,
			Set<Gameregistration> gameregistrations) {
		this.attiva = attiva;
		this.nome = nome;
		this.dataCreazione = dataCreazione;
		this.inizio = inizio;
		this.fine = fine;
		this.numeroTurniMax = numeroTurniMax;
		this.idtorneo = idtorneo;
		this.faseTorneo = faseTorneo;
		this.mappa = mappa;
		this.numeroGiocatoriMax = numeroGiocatoriMax;
		this.managerUsername = managerUsername;
		this.gameregistrations = gameregistrations;
	}

	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public boolean isAttiva() {
		return this.attiva;
	}

	public void setAttiva(boolean attiva) {
		this.attiva = attiva;
	}

	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	
	public Date getInizio() {
		return this.inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	
	public Date getFine() {
		return this.fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	
	public int getNumeroTurniMax() {
		return this.numeroTurniMax;
	}

	public void setNumeroTurniMax(int numeroTurniMax) {
		this.numeroTurniMax = numeroTurniMax;
	}

	
	public Integer getIdtorneo() {
		return this.idtorneo;
	}

	public void setIdtorneo(Integer idtorneo) {
		this.idtorneo = idtorneo;
	}

	
	public Byte getFaseTorneo() {
		return this.faseTorneo;
	}

	public void setFaseTorneo(Byte faseTorneo) {
		this.faseTorneo = faseTorneo;
	}

	
	public String getMappa() {
		return this.mappa;
	}

	public void setMappa(String mappa) {
		this.mappa = mappa;
	}

	
	public int getNumeroGiocatoriMax() {
		return this.numeroGiocatoriMax;
	}

	public void setNumeroGiocatoriMax(int numeroGiocatoriMax) {
		this.numeroGiocatoriMax = numeroGiocatoriMax;
	}

	
	public String getManagerUsername() {
		return this.managerUsername;
	}

	public void setManagerUsername(String managerUsername) {
		this.managerUsername = managerUsername;
	}

	
	public Set<Gameregistration> getGameregistrations() {
		return this.gameregistrations;
	}

	public void setGameregistrations(Set<Gameregistration> gameregistrations) {
		this.gameregistrations = gameregistrations;
	}

}
