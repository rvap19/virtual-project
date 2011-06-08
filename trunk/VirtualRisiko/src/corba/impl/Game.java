/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "game")
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
    @NamedQuery(name = "Game.findByAttiva", query = "SELECT g FROM Game g WHERE g.attiva = :attiva"),
    @NamedQuery(name = "Game.findByDataCreazione", query = "SELECT g FROM Game g WHERE g.dataCreazione = :dataCreazione"),
    @NamedQuery(name = "Game.findByFaseTorneo", query = "SELECT g FROM Game g WHERE g.faseTorneo = :faseTorneo"),
    @NamedQuery(name = "Game.findByFine", query = "SELECT g FROM Game g WHERE g.fine = :fine"),
    @NamedQuery(name = "Game.findByIDTorneo", query = "SELECT g FROM Game g WHERE g.iDTorneo = :iDTorneo"),
    @NamedQuery(name = "Game.findByInizio", query = "SELECT g FROM Game g WHERE g.inizio = :inizio"),
    @NamedQuery(name = "Game.findByNome", query = "SELECT g FROM Game g WHERE g.nome = :nome"),
    @NamedQuery(name = "Game.findByNumeroTurniMax", query = "SELECT g FROM Game g WHERE g.numeroTurniMax = :numeroTurniMax"),
    @NamedQuery(name = "Game.findByNumeroGiocatoriMax", query = "SELECT g FROM Game g WHERE g.numeroGiocatoriMax = :numeroGiocatoriMax")})
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "attiva")
    private boolean attiva;
    @Basic(optional = false)
    @Column(name = "dataCreazione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCreazione;
    @Column(name = "faseTorneo")
    private Boolean faseTorneo;
    @Column(name = "fine")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fine;
    @Column(name = "IDTorneo")
    private Integer iDTorneo;
    @Basic(optional = false)
    @Column(name = "inizio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inizio;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "numeroTurniMax")
    private int numeroTurniMax;
    @Column(name = "numeroGiocatoriMax")
    private Integer numeroGiocatoriMax;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Collection<Gameregistration> gameregistrationCollection;

    public Game() {
    }

    public Game(Integer id) {
        this.id = id;
    }

    public Game(Integer id, boolean attiva, Date dataCreazione, Date inizio, String nome, int numeroTurniMax) {
        this.id = id;
        this.attiva = attiva;
        this.dataCreazione = dataCreazione;
        this.inizio = inizio;
        this.nome = nome;
        this.numeroTurniMax = numeroTurniMax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Boolean getFaseTorneo() {
        return faseTorneo;
    }

    public void setFaseTorneo(Boolean faseTorneo) {
        this.faseTorneo = faseTorneo;
    }

    public Date getFine() {
        return fine;
    }

    public void setFine(Date fine) {
        this.fine = fine;
    }

    public Integer getIDTorneo() {
        return iDTorneo;
    }

    public void setIDTorneo(Integer iDTorneo) {
        this.iDTorneo = iDTorneo;
    }

    public Date getInizio() {
        return inizio;
    }

    public void setInizio(Date inizio) {
        this.inizio = inizio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroTurniMax() {
        return numeroTurniMax;
    }

    public void setNumeroTurniMax(int numeroTurniMax) {
        this.numeroTurniMax = numeroTurniMax;
    }

    public Integer getNumeroGiocatoriMax() {
        return numeroGiocatoriMax;
    }

    public void setNumeroGiocatoriMax(Integer numeroGiocatoriMax) {
        this.numeroGiocatoriMax = numeroGiocatoriMax;
    }

    public Collection<Gameregistration> getGameregistrationCollection() {
        return gameregistrationCollection;
    }

    public void setGameregistrationCollection(Collection<Gameregistration> gameregistrationCollection) {
        this.gameregistrationCollection = gameregistrationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "corba.impl.Game[id=" + id + "]";
    }

}
