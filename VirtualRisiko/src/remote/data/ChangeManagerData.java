/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.data;

import domain.Game;
import java.io.Serializable;

/**
 *
 * @author root
 */
public class ChangeManagerData implements Serializable{
    private Game game;
    private String username;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
