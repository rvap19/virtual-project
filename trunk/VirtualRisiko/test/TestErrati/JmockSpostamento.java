/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TestErrati;

import virtualrisikoii.risiko.Giocatore;

/**
 *
 * @author Administrator
 */
public interface JmockSpostamento {

    public void setGiocatore(Giocatore g);
    public void setTerritori(JmockTerritorio da, JmockTerritorio a );

}
