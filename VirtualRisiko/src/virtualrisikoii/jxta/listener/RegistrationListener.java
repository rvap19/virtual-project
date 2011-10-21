/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.jxta.listener;

import virtualrisikoii.jxta.advertisement.RegistrationAdvertisement;

/**
 *
 * @author root
 */
public interface RegistrationListener {
    public void registrationUpdated(RegistrationAdvertisement adv);

}
