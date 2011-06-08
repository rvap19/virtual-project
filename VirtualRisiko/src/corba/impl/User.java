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
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name= "User.findByUsernamePassword",query = "SELECT u FROM User u WHERE u.username = :username and u.password = :password"),
    @NamedQuery(name = "User.findByCognome", query = "SELECT u FROM User u WHERE u.cognome = :cognome"),
    @NamedQuery(name = "User.findByConfermato", query = "SELECT u FROM User u WHERE u.confermato = :confermato"),
    @NamedQuery(name = "User.findByDataDiIscrizione", query = "SELECT u FROM User u WHERE u.dataDiIscrizione = :dataDiIscrizione"),
    @NamedQuery(name = "User.findByDataDiNascita", query = "SELECT u FROM User u WHERE u.dataDiNascita = :dataDiNascita"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByLogged", query = "SELECT u FROM User u WHERE u.logged = :logged"),
    @NamedQuery(name = "User.findByNazione", query = "SELECT u FROM User u WHERE u.nazione = :nazione"),
    @NamedQuery(name = "User.findByNome", query = "SELECT u FROM User u WHERE u.nome = :nome"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @Column(name = "cognome")
    private String cognome;
    @Basic(optional = false)
    @Column(name = "confermato")
    private boolean confermato;
    @Basic(optional = false)
    @Column(name = "dataDiIscrizione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDiIscrizione;
    @Basic(optional = false)
    @Column(name = "dataDiNascita")
    @Temporal(TemporalType.DATE)
    private Date dataDiNascita;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "logged")
    private boolean logged;
    @Basic(optional = false)
    @Column(name = "Nazione")
    private String nazione;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Tournamentregistration> tournamentregistrationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Gameregistration> gameregistrationCollection;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String cognome, boolean confermato, Date dataDiIscrizione, Date dataDiNascita, String email, boolean logged, String nazione, String nome, String password) {
        this.username = username;
        this.cognome = cognome;
        this.confermato = confermato;
        this.dataDiIscrizione = dataDiIscrizione;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.logged = logged;
        this.nazione = nazione;
        this.nome = nome;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public boolean getConfermato() {
        return confermato;
    }

    public void setConfermato(boolean confermato) {
        this.confermato = confermato;
    }

    public Date getDataDiIscrizione() {
        return dataDiIscrizione;
    }

    public void setDataDiIscrizione(Date dataDiIscrizione) {
        this.dataDiIscrizione = dataDiIscrizione;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Tournamentregistration> getTournamentregistrationCollection() {
        return tournamentregistrationCollection;
    }

    public void setTournamentregistrationCollection(Collection<Tournamentregistration> tournamentregistrationCollection) {
        this.tournamentregistrationCollection = tournamentregistrationCollection;
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
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "corba.impl.User[username=" + username + "]";
    }

}
