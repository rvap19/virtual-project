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
@Table(name = "tournament")
@NamedQueries({
    @NamedQuery(name = "Tournament.findAll", query = "SELECT t FROM Tournament t"),
    @NamedQuery(name = "Tournament.findById", query = "SELECT t FROM Tournament t WHERE t.id = :id"),
    @NamedQuery(name = "Tournament.findByAttivo", query = "SELECT t FROM Tournament t WHERE t.attivo = :attivo"),
    @NamedQuery(name = "Tournament.findByDataFine", query = "SELECT t FROM Tournament t WHERE t.dataFine = :dataFine"),
    @NamedQuery(name = "Tournament.findByDatainizio", query = "SELECT t FROM Tournament t WHERE t.datainizio = :datainizio"),
    @NamedQuery(name = "Tournament.findByNome", query = "SELECT t FROM Tournament t WHERE t.nome = :nome")})
public class Tournament implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "attivo")
    private boolean attivo;
    @Column(name = "dataFine")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFine;
    @Basic(optional = false)
    @Column(name = "datainizio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainizio;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    private Collection<Tournamentregistration> tournamentregistrationCollection;

    public Tournament() {
    }

    public Tournament(Integer id) {
        this.id = id;
    }

    public Tournament(Integer id, boolean attivo, Date datainizio, String nome) {
        this.id = id;
        this.attivo = attivo;
        this.datainizio = datainizio;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Date getDatainizio() {
        return datainizio;
    }

    public void setDatainizio(Date datainizio) {
        this.datainizio = datainizio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<Tournamentregistration> getTournamentregistrationCollection() {
        return tournamentregistrationCollection;
    }

    public void setTournamentregistrationCollection(Collection<Tournamentregistration> tournamentregistrationCollection) {
        this.tournamentregistrationCollection = tournamentregistrationCollection;
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
        if (!(object instanceof Tournament)) {
            return false;
        }
        Tournament other = (Tournament) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "corba.impl.Tournament[id=" + id + "]";
    }

}
