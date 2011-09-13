/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package middle.management.listener;

import middle.management.advertisement.PlayerAdvertisement;



/**
 *
 * @author root
 */
public interface PlayerListener {
    public void presenceUpdated(PlayerAdvertisement playerInfo);

}
