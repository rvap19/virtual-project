/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;



/**
 *
 * @author root
 */
public abstract class Obiettivo {
    
    private String descrizione;
    private int codice;

   


    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice=codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String d){
        this.descrizione=d;
    }

    public abstract boolean controllaObiettivo(Giocatore giocatore,Tavolo tavolo);

    public abstract int getPunteggio(Giocatore giocatore,Tavolo tavolo);

    public abstract String toString();

}
