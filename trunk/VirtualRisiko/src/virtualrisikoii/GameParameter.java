/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii;

/**
 *
 * @author root
 */
public  class GameParameter {
    private  String mapName;
    private  int seed_dice;
    private  int seed_region;
    private  int seed_cards;
    private  int maxPlayers;
    private int maxTurns;

    public GameParameter(String mapName){
        maxPlayers=6;
        maxTurns=1000;
        this.mapName=mapName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public int getSeed_cards() {
        return seed_cards;
    }

    public void setSeed_cards(int seed_cards) {
        this.seed_cards = seed_cards;
    }

    public int getSeed_dice() {
        return seed_dice;
    }

    public void setSeed_dice(int seed_dice) {
        this.seed_dice = seed_dice;
    }

    public int getSeed_region() {
        return seed_region;
    }

    public void setSeed_region(int seed_region) {
        this.seed_region = seed_region;
    }

    

}
