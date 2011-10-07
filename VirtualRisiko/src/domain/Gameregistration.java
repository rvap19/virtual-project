package domain;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "gameregistration")
@NamedQueries({
    @NamedQuery(name = "Gameregistration.findAll", query = "SELECT g FROM Gameregistration g"),
    @NamedQuery(name = "Gameregistration.findByGameID", query = "SELECT g FROM Gameregistration g WHERE g.gameregistrationPK.gameID = :gameID"),
    @NamedQuery(name = "Gameregistration.findByGameIDAndPlayer", query = "SELECT g FROM Gameregistration g WHERE g.gameregistrationPK.gameID = :gameID and g.gameregistrationPK.userUsername = :username"),
    @NamedQuery(name = "Gameregistration.findByPlayer", query = "SELECT g FROM Gameregistration g WHERE g.gameregistrationPK.userUsername = :username"),
    @NamedQuery(name = "Gameregistration.findByUserUsername", query = "SELECT g FROM Gameregistration g WHERE g.gameregistrationPK.userUsername = :userUsername"),
    @NamedQuery(name = "Gameregistration.findByPunteggio", query = "SELECT g FROM Gameregistration g WHERE g.punteggio = :punteggio"),
    @NamedQuery(name = "Gameregistration.findByVincitore", query = "SELECT g FROM Gameregistration g WHERE g.vincitore = :vincitore")})
public class Gameregistration implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GameregistrationPK gameregistrationPK;
    @Column(name = "punteggio")
    private Integer punteggio;
    @Column(name = "vincitore")
    private Boolean vincitore;
    @JoinColumn(name = "UserUsername", referencedColumnName = "Username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "GameID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game;

    public Gameregistration() {
    }

    public Gameregistration(GameregistrationPK gameregistrationPK) {
        this.gameregistrationPK = gameregistrationPK;
    }

    public Gameregistration(int gameID, String userUsername) {
        this.gameregistrationPK = new GameregistrationPK(gameID, userUsername);
    }

    public GameregistrationPK getGameregistrationPK() {
        return gameregistrationPK;
    }

    public void setGameregistrationPK(GameregistrationPK gameregistrationPK) {
        this.gameregistrationPK = gameregistrationPK;
    }

    public Integer getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(Integer punteggio) {
        this.punteggio = punteggio;
    }

    public Boolean getVincitore() {
        return vincitore;
    }

    public void setVincitore(Boolean vincitore) {
        this.vincitore = vincitore;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameregistrationPK != null ? gameregistrationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gameregistration)) {
            return false;
        }
        Gameregistration other = (Gameregistration) object;
        if ((this.gameregistrationPK == null && other.gameregistrationPK != null) || (this.gameregistrationPK != null && !this.gameregistrationPK.equals(other.gameregistrationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "corba.impl.Gameregistration[gameregistrationPK=" + gameregistrationPK + "]";
    }

}