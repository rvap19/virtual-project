/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.data;

import domain.Game;
import domain.User;
import java.io.Serializable;

/**
 *
 * @author root
 */
public class RegistrationData implements Serializable{
    private User user;
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
