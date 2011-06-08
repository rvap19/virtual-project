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
public class GameregistrationPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "GameID")
    private int gameID;
    @Basic(optional = false)
    @Column(name = "UserUsername")
    private String userUsername;

    public GameregistrationPK() {
    }

    public GameregistrationPK(int gameID, String userUsername) {
        this.gameID = gameID;
        this.userUsername = userUsername;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
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
        hash += (int) gameID;
        hash += (userUsername != null ? userUsername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameregistrationPK)) {
            return false;
        }
        GameregistrationPK other = (GameregistrationPK) object;
        if (this.gameID != other.gameID) {
            return false;
        }
        if ((this.userUsername == null && other.userUsername != null) || (this.userUsername != null && !this.userUsername.equals(other.userUsername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "corba.impl.GameregistrationPK[gameID=" + gameID + ", userUsername=" + userUsername + "]";
    }

}
