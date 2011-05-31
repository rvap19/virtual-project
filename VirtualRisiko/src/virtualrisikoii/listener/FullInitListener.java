/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.listener;

import java.util.List;

/**
 *
 * @author root
 */
public interface FullInitListener {
    public void init(int myTurno,int  players,List<String> names,String creatorPipe,int seed_dice,String map_name,int seed_card,int seed_region);

}
