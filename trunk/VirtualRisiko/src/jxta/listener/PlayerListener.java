/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.listener;

import jxta.advertisement.PlayerAdvertisement;

/**
 *
 * @author root
 */
public interface PlayerListener {
    public void presenceUpdated(PlayerAdvertisement playerInfo);

}
