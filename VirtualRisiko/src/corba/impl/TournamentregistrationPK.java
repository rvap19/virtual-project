/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author root
 */
@Embeddable
public class TournamentregistrationPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "TournamentID")
    private int tournamentID;
    @Basic(optional = false)
    @Column(name = "UserUsername")
    private String userUsername;

    public TournamentregistrationPK() {
    }

    public TournamentregistrationPK(int tournamentID, String userUsername) {
        this.tournamentID = tournamentID;
        this.userUsername = userUsername;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) tournamentID;
        hash += (userUsername != null ? userUsername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TournamentregistrationPK)) {
            return false;
        }
        TournamentregistrationPK other = (TournamentregistrationPK) object;
        if (this.tournamentID != other.tournamentID) {
            return false;
        }
        if ((this.userUsername == null && other.userUsername != null) || (this.userUsername != null && !this.userUsername.equals(other.userUsername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "corba.impl.TournamentregistrationPK[tournamentID=" + tournamentID + ", userUsername=" + userUsername + "]";
    }

}
