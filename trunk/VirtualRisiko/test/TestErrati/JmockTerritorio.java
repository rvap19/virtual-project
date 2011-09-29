/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TestErrati;

import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author Administrator
 */
public interface JmockTerritorio {

    public void addNazioni( Territorio a);
    public void setNome (String nome);
    public void setCodice (int codice);
    public void setNumeroUnita (int unita);
    public void setOccupante(Giocatore occupante);
     public int getNumeroUnita();

}
