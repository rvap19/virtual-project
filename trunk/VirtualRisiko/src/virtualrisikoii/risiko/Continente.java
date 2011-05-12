/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.List;
import java.util.Set;

/**
 *
 * @author root
 */
public class Continente {

    private String nome;
    private int rinforzo;
    private int codice;
    private Set<Territorio> territori;

    public Continente(int codice){
        this.codice=codice;
    }

    public Continente(Set<Territorio> t){
        this.territori=t;
    }

    public int getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public int getRinforzo() {
        return rinforzo;
    }

    public Set<Territorio> getTerritori() {
        return territori;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRinforzo(int rinforzo) {
        this.rinforzo = rinforzo;
    }

    public void setTerritori(Set<Territorio> territori) {
        this.territori = territori;
    }




}
