/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.data;

import domain.Gameregistration;
import java.io.Serializable;

/**
 *
 * @author root
 */
public class GameResults implements Serializable{
    private Gameregistration[] registrations;

    public Gameregistration[] getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Gameregistration[] registrations) {
        this.registrations = registrations;
    }
    
}
