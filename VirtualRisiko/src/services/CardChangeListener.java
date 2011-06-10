/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;

/**
 *
 * @author Administrator
 */
public interface CardChangeListener {

    public void notifyChangeCard(Giocatore giocatore, Carta carta1, Carta carta2, Carta carta3, int rinforzi);

}
