/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.List;

/**
 *
 * @author root
 */
public class Carta {
    public static final int CAVALIERE=1;
    public static final int FANTE=10;
    public static final int CANNONE=100;
    public static final int JOLLY=1000;

    private int codice;
    private Territorio territorio;

    public Carta(){

    }

    public Carta(int codice){
        this.codice=codice;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public Territorio getTerritorio() {
        return territorio;
    }

    public void setTerritorio(Territorio territorio) {
        this.territorio = territorio;
    }

    public String getNome() {
        switch(this.codice){
            case Carta.CANNONE:return "cannone";
            case Carta.CAVALIERE:return "cavaliere";
            case Carta.FANTE:return "fante";
            case Carta.JOLLY:return "jolly";
            default:return null;
        }
    }

    public String toString(){
        return  this.getTerritorio().getNome();

    }

    @Override
    public boolean equals(Object obj) {
        Carta c=(Carta)obj;
        return c.getTerritorio().getCodice()==this.getTerritorio().getCodice();
    }




}
