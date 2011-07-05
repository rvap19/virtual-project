/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages;


import java.util.ArrayList;
import java.util.List;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;

/**
 *
 * @author root
 */
public class InitMessage extends VirtualRisikoMessage{
    public static final String SEED_DICE="seed_dice";
    public static final String MAP_NAME="map_name";
    public static final String SEED_CARDS="seed_cards";
    public static final String SEED_REGION="seed_region";
    public static final String PLAYERS="players_number";
    public static final String TURN="myTurn";
    public static final String PLAYER_NAMES="players_names";
    public static final String MAX_TURNS="max_turns";

    private int players;
    private int seed_dice;
    private String map_name;
    private int seed_card;
    private int seed_region;
    private int myTurno;
    private List<String> names;
    private int maxTurns;

    

    public InitMessage(int maxTurns,int players,int seed_dice,String map_name,int seed_card,int seed_region,List<String> playerNames){
        super();
        this.maxTurns=maxTurns;
        this.players=players;
        this.seed_dice=seed_dice;
        this.map_name=map_name;
        this.seed_card=seed_card;
        this.seed_region=seed_region;
       
        
        StringMessageElement mE=new StringMessageElement(TYPE, INIT, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(SEED_DICE,Integer.toString(seed_dice), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(MAP_NAME,map_name, null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(SEED_CARDS,Integer.toString(seed_card), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(SEED_REGION,Integer.toString(seed_region), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(PLAYERS,Integer.toString(players), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(MAX_TURNS,Integer.toString(maxTurns), null);
        addMessageElement(namespace, mElement);


        String info;

        for(int i=0;i<players;i++){
            info=playerNames.get(i);
            mE=new StringMessageElement(PLAYER_NAMES,info , null);
            addMessageElement(namespace, mE);
        }
         
        
    }

    public InitMessage(Message message){
         super(message);
         seed_dice=Integer.parseInt(message.getMessageElement(namespace, SEED_DICE).toString());
         map_name=message.getMessageElement(namespace, MAP_NAME).toString();
         seed_card=Integer.parseInt(message.getMessageElement(namespace, SEED_CARDS).toString());
         seed_region=Integer.parseInt(message.getMessageElement(namespace, SEED_REGION).toString());
         players=Integer.parseInt(message.getMessageElement(namespace, PLAYERS).toString());
         myTurno=Integer.parseInt(message.getMessageElement(namespace, TURN).toString());
         this.maxTurns=Integer.parseInt(message.getMessageElement(namespace, MAX_TURNS).toString());
         this.names=new ArrayList<String>();
         ElementIterator iter=message.getMessageElements(namespace, PLAYER_NAMES);
         while(iter.hasNext()){
            names.add(iter.next().toString());
         }
    }

    public String getMap_name() {
        return map_name;
    }

    public int getMyTurno() {
        return myTurno;
    }

    public int getMaxTurns() {
        return maxTurns;
    }
    

    public int getPlayers() {
        return players;
    }

    public int getSeed_card() {
        return seed_card;
    }

    public int getSeed_dice() {
        return seed_dice;
    }

    public int getSeed_region() {
        return seed_region;
    }

    public List<String> getNames() {
        return names;
    }

    
    





}
