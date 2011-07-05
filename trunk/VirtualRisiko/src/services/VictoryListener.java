/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.util.List;
import virtualrisikoii.risiko.Giocatore;

/**
 *
 * @author root
 */
public interface VictoryListener {

    public void notifyVictory(List<Giocatore> giocatori, Giocatore giocatoreCorrente,boolean victory);


}
