/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author root
 */
public class Territorio {
     

    private String nome;
    private int numeroUnita;
    private int codice;
    private Giocatore occupante;
    private Set<Territorio> confinanti;
    private int punteggio;

    public Territorio(){
        this.confinanti=new HashSet<Territorio>();
    }

    public Territorio(int codice,String nome){
        this.nome=nome;
        this.codice=codice;
        this.confinanti=new HashSet<Territorio>();
    }

    public Territorio(int codice,String nome,Giocatore occupante){
        this.codice=codice;
        this.nome=nome;
        this.occupante=occupante;
        this.confinanti=new HashSet<Territorio>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }


    public int getNumeroUnita() {
        return numeroUnita;
    }

    public void setNumeroUnita(int numeroUnita) {
        this.numeroUnita = numeroUnita;
    }

    public Giocatore getOccupante() {
        return occupante;
    }
    public Set<Territorio> getConfinanti(){
        return this.confinanti;
    }

    public void setOccupante(Giocatore occupante) {
        this.occupante = occupante;
        occupante.getNazioni().add(this);
    }

    public boolean confina(Territorio nome){
        return this.confinanti.contains(nome);
    }

    public void addNazione(Territorio naz){
        this.confinanti.add(naz);
        
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio){
        this.punteggio=punteggio;
    }

}
