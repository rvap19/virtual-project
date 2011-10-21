package org.domain.risikoweb.entity;

// Generated 6-lug-2011 19.15.54 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



public class Tournament implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -590265311599664835L;
	private Integer id;
	private String nome;
	private Date dataInizio;
	private Date dataFine;
	private int maxPartecipanti;
	private byte fase;
	private String mappa;
	private Set<Tournamentregistration> tournamentregistrations = new HashSet<Tournamentregistration>(
			0);

	public Tournament() {
	}

	public Tournament(String nome, Date dataInizio, int maxPartecipanti,
			byte fase, String mappa) {
		this.nome = nome;
		this.dataInizio = dataInizio;
		this.maxPartecipanti = maxPartecipanti;
		this.fase = fase;
		this.mappa = mappa;
	}

	public Tournament(String nome, Date dataInizio, Date dataFine,
			int maxPartecipanti, byte fase, String mappa,
			Set<Tournamentregistration> tournamentregistrations) {
		this.nome = nome;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.maxPartecipanti = maxPartecipanti;
		this.fase = fase;
		this.mappa = mappa;
		this.tournamentregistrations = tournamentregistrations;
	}

	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public Date getDataInizio() {
		return this.dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	
	public Date getDataFine() {
		return this.dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	
	public int getMaxPartecipanti() {
		return this.maxPartecipanti;
	}

	public void setMaxPartecipanti(int maxPartecipanti) {
		this.maxPartecipanti = maxPartecipanti;
	}

	
	public byte getFase() {
		return this.fase;
	}

	public void setFase(byte fase) {
		this.fase = fase;
	}

	public String getMappa() {
		return this.mappa;
	}

	public void setMappa(String mappa) {
		this.mappa = mappa;
	}
	
	
	public Set<Tournamentregistration> getTournamentregistrations() {
		return this.tournamentregistrations;
	}

	public void setTournamentregistrations(
			Set<Tournamentregistration> tournamentregistrations) {
		this.tournamentregistrations = tournamentregistrations;
	}

}
