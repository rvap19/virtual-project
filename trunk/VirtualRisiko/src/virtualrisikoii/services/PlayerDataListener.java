/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.services;


/**
 *
 * @author root
 */
public interface PlayerDataListener {
    public void updateDatiGiocatore(String nomeGiocatoreDiTurno,int numeroTruppeDisponibili,int numeroTruppeDisposte,int numeroTerritori);

}
