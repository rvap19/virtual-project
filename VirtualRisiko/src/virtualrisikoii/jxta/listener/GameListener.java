/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.jxta.listener;

import virtualrisikoii.jxta.advertisement.GameAdvertisement;

/**
 *
 * @author root
 */
public interface GameListener {
    public void gameUpdated(GameAdvertisement adv);

}
