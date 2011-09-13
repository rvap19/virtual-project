/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages;

import java.util.ArrayList;
import java.util.List;
import middle.MessageTypes;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;
import virtualrisikoii.RecoveryParameter;

/**
 *
 * @author root
 */
public class RecoveryMessage extends VirtualRisikoMessage implements middle.messages.RecoveryMessage{


        public static final String OBJECTIVE_INFO="objective_info";
        public static final String TURN_INFO="turn_info";
        public static final String PLAYERS_INFO="players_info";
        public static final String SEED_DICE="seed_dice";
        public static final String SEED_CARDS="seed_card";
        public static final String DICE_LANCH="dice_lanch";
        public static final String CARDS_LANCH="cards_lanch";
        public static final String ARMATE="armate_lanch";
        public static final String INIT_INFO="inizializzazione";
        public static final String REGION_OCCUPANTE_INFO="occupante";
        public static final String REGION_TROOPS_NUMBER_INFO="troops_number";
        public static final String REGION_NUMBER="regions";
        public static final String MAP_NAME="map_name";
        public static final String MY_TURNO="my_turno";
        public static final String NAMES="play_names";
        public static final String CARD_PLAYERS="carte_giocatori";
        public static final String CARD_POSITION="card_position";
        public static final String MAX_TURNS="max_turns";
        public static final String ELAPSED_TURNS="elapsed_turns";
        
    private  RecoveryParameter parameter;
    

    public RecoveryMessage(RecoveryParameter parameter){
        super();
        setType( MessageTypes.RECOVERY);
        this.parameter=parameter;
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.RECOVERY, null);
        addMessageElement(namespace, mE);
        addMapNameElement(parameter.getMapName());

        //appendo i dati sull inizializzaione
        addInitElement( parameter.isInizializzazione());
        //appendo i dati sul territorio
        addTerritoriOccupanteElement(parameter.getIdOccupante());
        addTerritoriNumeroTruppeElement( parameter.getNumeroTruppe());
        addRegionsNumberElement( parameter.getIdOccupante().length);
        //appendo i dati sugli obiettivi
        addObjectiveElement( parameter.getObjectives());
        //appendo i dati sul turno
        addTurnElement( parameter.getTurno());
        addMaxTurnsElement(parameter.getMaxTurns());
        addElapsedTurnsElement(parameter.getElapsedTurns());
        //appendo i dati sul num giocatori
        addPlayersNumberElement( parameter.getNumeroGiocatori());
        //appendo i dati sulseed dadi
        addSeedDiceElement( parameter.getSeed_dice());
        //appendo i dati sul seed cards
        addSeedCardsElement( parameter.getSeed_card());

        //appendo i dati sul numero di volte che i dadi sono stati lanciati

        addDiceLanchElement( parameter.getDice_lanch());

        //appendo dati lancio carte
        addCardLanchElement( parameter.getCards_lanch());

        //appendoi dati sulle armate
        addArmateElement( parameter.getArmateDisponibili());

        addTurnoMyGiocatoreElement( parameter.getTurnoMyGiocatore());

        addNames(parameter.getPlayersNames());

        addCarteGiocatori(parameter.getCarteGiocatori());
        addPosizioneCarte(parameter.getPosizioneCarte());

    }

    public RecoveryMessage(Message message){
        super(message);
        boolean inizializzazione=elaborateInitElement(message);

        //ricevo i dati sul territorio
        int regions=elaborateRegionsNumberElement(message);
        int[] idOccupante=elaborateTerritoriOccupanteElements(message, regions);
        int[] troopsNumber=elaborateTerritoriNumeroTruppeElements(message, regions);

         //ricevo i dati sul num giocatori
        int numeroGiocatori=elaboratePlayerNumberElement(message);

        //ricevo i dati sugli obiettivi
        int[] objectives=elaborateObjectiveElements(message, numeroGiocatori);


        //ricevo i dati sul turno
        int turno=elaborateTurnElement(message);

        int maxTurns=elaborateMaxTurnsElement(message);
        int elapsedTurns=elaborateElapsedTurnsElement(message);

        //ricevo i dati sulseed dadi
        int seed_dice=elaborateSeedDiceElement(message);

        //ricevo i dati sul seed cards
        int seed_card=elaborateSeedCardElement(message);

        //ricevo i dati sul numero di volte che i dadi sono stati lanciati
        int dice_lanch=elaborateDiceLanchElement(message);

        //appendo dati lancio carte
        int card_lanch=elaborateCardLanchElement(message);

        //appendoi dati sulle armate
        int[] num_armate=elaborateArmateElements(message, numeroGiocatori);

        int turnoMyGiocatore=elaborateTurnoMyGiocatoreElement(message);

        List<String> names=elaboratePlayersNames(message);

        String mapName=elaborateMapNameElement(message);

        parameter=new RecoveryParameter(mapName, inizializzazione, idOccupante, troopsNumber, objectives, num_armate, turno, numeroGiocatori, seed_card, seed_dice, dice_lanch, card_lanch,names);
        parameter.setTurnoMyGiocatore(turnoMyGiocatore);
        parameter.setElapsedTurns(elapsedTurns);
        parameter.setMaxTurns(maxTurns);
        parameter.setCarteGiocatori(elaborateCarteGiocatoriElements(regions,message));
        parameter.setPosizioneCarte(elaboratePosizionecarte(regions,message));
    }



    private void addCarteGiocatori(int[] carteGiocatori) {
        int size=carteGiocatori.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(carteGiocatori[i]);
            mE=new StringMessageElement(CARD_PLAYERS,info , null);
            addMessageElement(namespace, mE);
        }

    }

    private void addPosizioneCarte(int[] posizioneCarte) {
        int size=posizioneCarte.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(posizioneCarte[i]);
            mE=new StringMessageElement(CARD_POSITION,info , null);
            addMessageElement(namespace, mE);
        }
    }

    private void addNames(List<String> names){
        int size=names.size();
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=names.get(i);
            mE=new StringMessageElement(NAMES,info , null);
            addMessageElement(namespace, mE);
        }
    }



    private List<String> elaboratePlayersNames(Message message){
        ArrayList<String> list=new ArrayList<String>();
        Message.ElementIterator iter=message.getMessageElements(NAMES);

        while(iter.hasNext()){
            list.add(iter.next().toString());


        }
        return list;
    }

    private void addInitElement(boolean inizializzazione){

        String info;
        StringMessageElement mE;

            info=Boolean.toString(inizializzazione);
            mE=new StringMessageElement(INIT_INFO,info , null);
            addMessageElement(namespace, mE);



    }

    private boolean elaborateInitElement(Message message){

        boolean  inizializzazione=Boolean.parseBoolean(message.getMessageElement(namespace, INIT_INFO).toString());
        return inizializzazione;
    }

    private void addMapNameElement(String mapName){

        String info=mapName;
        StringMessageElement mE;


            mE=new StringMessageElement(MAP_NAME,info , null);
            addMessageElement(namespace, mE);



    }

    private String elaborateMapNameElement(Message message){

        String  mapName=message.getMessageElement(namespace, MAP_NAME).toString();
        return mapName;
    }

    private void addRegionsNumberElement(int regions){

        String info;
        StringMessageElement mE;

            info=Integer.toString(regions);
            mE=new StringMessageElement(REGION_NUMBER,info , null);
            addMessageElement(namespace, mE);



    }

    private int elaborateRegionsNumberElement(Message message){

        int  regionsNumber=Integer.parseInt(message.getMessageElement(namespace, REGION_NUMBER).toString());
        return regionsNumber;
    }


    private void addTerritoriOccupanteElement(int[] idOccupante){
        int size=idOccupante.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(idOccupante[i]);
            mE=new StringMessageElement(REGION_OCCUPANTE_INFO,info , null);
            addMessageElement(namespace, mE);
        }
        
    }

    

    private void addTerritoriNumeroTruppeElement(int[] numeroTruppe){
        int size=numeroTruppe.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(numeroTruppe[i]);
            mE=new StringMessageElement(REGION_TROOPS_NUMBER_INFO,info , null);
            addMessageElement(namespace, mE);
        }
        
    }

    private int[] elaborateTerritoriNumeroTruppeElements(Message message,int regions){
        int[] troopsNumber=new int[regions];
        Message.ElementIterator iter=message.getMessageElements(REGION_TROOPS_NUMBER_INFO);
        int counter=0;
        while(iter.hasNext()){
            troopsNumber[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return troopsNumber;
    }

    private void addObjectiveElement(int[] objective){
        int size=objective.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(objective[i]);
            mE=new StringMessageElement(OBJECTIVE_INFO,info , null);
            addMessageElement(namespace, mE);
        }
      

    }

    private int[] elaborateObjectiveElements(Message message,int players){
        int[] objectives=new int[players];
        Message.ElementIterator iter=message.getMessageElements(OBJECTIVE_INFO);
        int counter=0;
        while(iter.hasNext()){
            objectives[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return objectives;
    }

    private void addTurnElement(int turno){

        String info;
        StringMessageElement mE;

            info=Integer.toString(turno);
            mE=new StringMessageElement(TURN_INFO,info , null);
            addMessageElement(namespace, mE);

        

    }

    public int elaborateTurnElement(Message message){
        int turn=Integer.parseInt(message.getMessageElement(namespace, TURN_INFO).toString());
        return turn;
    }

    public int elaborateMaxTurnsElement(Message message){
        int maxTurns=Integer.parseInt(message.getMessageElement(namespace, MAX_TURNS).toString());
        return maxTurns;
    }

    public int elaborateElapsedTurnsElement(Message message){
        int elapsedTurns=Integer.parseInt(message.getMessageElement(namespace, ELAPSED_TURNS).toString());
        return elapsedTurns;
    }

    private void addPlayersNumberElement(int numeroGiocatori){

        String info;
        StringMessageElement mE;

            info=Integer.toString(numeroGiocatori);
            mE=new StringMessageElement(PLAYERS_INFO,info , null);
            addMessageElement(namespace, mE);

        

    }

    public int elaboratePlayerNumberElement(Message message){
        int players=Integer.parseInt(message.getMessageElement(namespace, PLAYERS_INFO).toString());
        return players;
    }

    private void addSeedDiceElement(int seed_dice){

        String info;
        StringMessageElement mE;

            info=Integer.toString(seed_dice);
            mE=new StringMessageElement(SEED_DICE,info , null);
            addMessageElement(namespace, mE);

       

    }

    public int elaborateSeedDiceElement(Message message){
        int seed_dice=Integer.parseInt(message.getMessageElement(namespace, SEED_DICE).toString());
        return seed_dice;
    }

    private void addSeedCardsElement(int seed_cards){

        String info;
        StringMessageElement mE;

            info=Integer.toString(seed_cards);
            mE=new StringMessageElement(SEED_CARDS,info , null);
            addMessageElement(namespace, mE);

       

    }

    public int elaborateSeedCardElement(Message message){
        int seed_cards=Integer.parseInt(message.getMessageElement(namespace, SEED_CARDS).toString());
        return seed_cards;
    }

    private void addDiceLanchElement(int dice_lanches){

        String info;
        StringMessageElement mE;

            info=Integer.toString(dice_lanches);
            mE=new StringMessageElement(DICE_LANCH,info , null);
            addMessageElement(namespace, mE);

        

    }

    private void addMaxTurnsElement(int maxTurns){

        String info;
        StringMessageElement mE;

            info=Integer.toString(maxTurns);
            mE=new StringMessageElement(MAX_TURNS,info , null);
            addMessageElement(namespace, mE);



    }
    private void addElapsedTurnsElement(int elapsedTurns){

        String info;
        StringMessageElement mE;

            info=Integer.toString(elapsedTurns);
            mE=new StringMessageElement(ELAPSED_TURNS,info , null);
            addMessageElement(namespace, mE);



    }

    public int elaborateDiceLanchElement(Message message){
        int dice_lanch=Integer.parseInt(message.getMessageElement(namespace, DICE_LANCH).toString());
        return dice_lanch;
    }

    private void addCardLanchElement(int card_lanches){

        String info;
        StringMessageElement mE;

            info=Integer.toString(card_lanches);
            mE=new StringMessageElement(CARDS_LANCH,info , null);
            addMessageElement(namespace, mE);

       

    }

    public int elaborateCardLanchElement(Message message){
        int card_lanch=Integer.parseInt(message.getMessageElement(namespace, CARDS_LANCH).toString());
        return card_lanch;
    }

    public void addTurnoMyGiocatoreElement(int myTurno){
         String info;
        StringMessageElement mE;

            info=Integer.toString(myTurno);
            mE=new StringMessageElement(MY_TURNO,info , null);
            addMessageElement(namespace, mE);

       
    }

    public int elaborateTurnoMyGiocatoreElement(Message message){
        int turno=Integer.parseInt(message.getMessageElement(namespace, MY_TURNO).toString());
        return turno;
    }

    private void addArmateElement(int[] num_armate){
        int size=num_armate.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(num_armate[i]);
            mE=new StringMessageElement(ARMATE,info , null);
            addMessageElement(namespace, mE);
        }
        

    }

    private int[] elaborateArmateElements(Message message,int players){
        int[] armate=new int[players];
        Message.ElementIterator iter=message.getMessageElements(ARMATE);
        int counter=0;
        while(iter.hasNext()){
            armate[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return armate;
    }

    public RecoveryParameter getParameter() {
        return parameter;
    }

    private int[] elaborateTerritoriOccupanteElements(Message message,int regions){
        int[] occupante=new int[regions];
        Message.ElementIterator iter=message.getMessageElements(REGION_OCCUPANTE_INFO);
        int counter=0;
        while(iter.hasNext()){
            occupante[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return occupante;
    }

    private int[] elaborateCarteGiocatoriElements(int regions, Message message) {
        int[] carteGiocatore=new int[regions+2];
        Message.ElementIterator iter=message.getMessageElements(CARD_PLAYERS);
        int counter=0;
        while(iter.hasNext()){
            carteGiocatore[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return carteGiocatore;
    }

    private int[] elaboratePosizionecarte(int regions, Message message) {
        int[] posizioneCarte=new int[regions+2];
        Message.ElementIterator iter=message.getMessageElements(CARD_POSITION);
        int counter=0;
        while(iter.hasNext()){
            posizioneCarte[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return posizioneCarte;
    }

    




    

}
