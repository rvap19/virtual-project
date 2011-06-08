/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "tournamentregistration")
@NamedQueries({
    @NamedQuery(name = "Tournamentregistration.findAll", query = "SELECT t FROM Tournamentregistration t"),
    @NamedQuery(name = "Tournamentregistration.findByTournamentID", query = "SELECT t FROM Tournamentregistration t WHERE t.tournamentregistrationPK.tournamentID = :tournamentID"),
    @NamedQuery(name = "Tournamentregistration.findByUserUsername", query = "SELECT t FROM Tournamentregistration t WHERE t.tournamentregistrationPK.userUsername = :userUsername"),
    @NamedQuery(name = "Tournamentregistration.findByDataIscrizione", query = "SELECT t FROM Tournamentregistration t WHERE t.dataIscrizione = :dataIscrizione"),
    @NamedQuery(name = "Tournamentregistration.findByPunteggio", query = "SELECT t FROM Tournamentregistration t WHERE t.punteggio = :punteggio"),
    @NamedQuery(name = "Tournamentregistration.findByVincitore", query = "SELECT t FROM Tournamentregistration t WHERE t.vincitore = :vincitore")})
public class Tournamentregistration implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TournamentregistrationPK tournamentregistrationPK;
    @Basic(optional = false)
    @Column(name = "dataIscrizione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataIscrizione;
    @Basic(optional = false)
    @Column(name = "punteggio")
    private int punteggio;
    @Basic(optional = false)
    @Column(name = "vincitore")
    private boolean vincitore;
    @JoinColumn(name = "TournamentID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tournament tournament;
    @JoinColumn(name = "UserUsername", referencedColumnName = "Username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Tournamentregistration() {
    }

    public Tournamentregistration(TournamentregistrationPK tournamentregistrationPK) {
        this.tournamentregistrationPK = tournamentregistrationPK;
    }

    public Tournamentregistration(TournamentregistrationPK tournamentregistrationPK, Date dataIscrizione, int punteggio, boolean vincitore) {
        this.tournamentregistrationPK = tournamentregistrationPK;
        this.dataIscrizione = dataIscrizione;
        this.punteggio = punteggio;
        this.vincitore = vincitore;
    }

    public Tournamentregistration(int tournamentID, String userUsername) {
        this.tournamentregistrationPK = new TournamentregistrationPK(tournamentID, userUsername);
    }

    public TournamentregistrationPK getTournamentregistrationPK() {
        return tournamentregistrationPK;
    }

    public void setTournamentregistrationPK(TournamentregistrationPK tournamentregistrationPK) {
        this.tournamentregistrationPK = tournamentregistrationPK;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public boolean getVincitore() {
        return vincitore;
    }

    public void setVincitore(boolean vincitore) {
        this.vincitore = vincitore;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tournamentregistrationPK != null ? tournamentregistrationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tournamentregistration)) {
            return false;
        }
        Tournamentregistration other = (Tournamentregistration) object;
        if ((this.tournamentregistrationPK == null && other.tournamentregistrationPK != null) || (this.tournamentregistrationPK != null && !this.tournamentregistrationPK.equals(other.tournamentregistrationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "corba.impl.Tournamentregistration[tournamentregistrationPK=" + tournamentregistrationPK + "]";
    }

}
