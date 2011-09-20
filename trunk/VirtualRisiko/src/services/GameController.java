/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import jxta.communication.Communicator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import middle.AbstractGameController;
import middle.EventTypes;
import middle.Middle;
import middle.RisikoMessageGenerator;
import middle.VirtualCommunicator;
import middle.event.ApplianceEvent;
import middle.event.AttackEvent;
import middle.event.ChangeCardEvent;
import middle.event.MovementEvent;
import middle.event.PassEvent;
import middle.event.PongEvent;
import middle.event.RecoveryEvent;
import middle.event.RisikoEvent;
import middle.listener.PongEventListener;
import middle.listener.RecoveryEventListener;
import middle.messages.ApplianceMessage;
import middle.messages.AttackMessage;
import middle.messages.ChangeCardMessage;
import middle.messages.ChatMessage;
import middle.messages.MovementMessage;
import middle.messages.PassMessage;
import middle.messages.PongMessage;
import middle.messages.RecoveryMessage;
import middle.messages.RisikoMessage;
import util.RecoveryUtil;
import virtualrisikoii.RecoveryParameter;


import virtualrisikoii.risiko.Attacco;
import virtualrisikoii.risiko.Azione;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.JFrameTurno;
import virtualrisikoii.risiko.Rinforzo;
import virtualrisikoii.risiko.Spostamento;
import virtualrisikoii.risiko.StatoGiocoPanel;
import virtualrisikoii.risiko.Tavolo;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author root
 */
public class GameController extends AbstractGameController implements ChatSender,TimeoutNotifier,RecoveryEventListener,PongEventListener{
    
    private Middle middle;
    private MapListener mapListener;
    private HistoryListener historyListener;
    private PlayerDataListener playerDataListener;
    private List<VictoryListener> victoryListeners;
    private TroopsSelector troopsSelector;
    private CardListener cardListener;
    private CardChangeListener cardChangeListener;
    
    
    private TimeoutNotifier timeoutNotifier;
    private GameTimer timer;

    //private Tavolo tavolo;
    private TableLocker locker;

    private Territorio firstSelection;
    private Territorio secondSelection;
    private int truppeSelezionate;
    private boolean[] reconnectionNeeds;
    private boolean[] messageReceived;
    private ManagerPingerThread managerTimer;
    private static GameController instance=null;
    private HashMap<String,Integer> turns;
    private boolean gameOver;
    
    private VirtualCommunicator communicator;
    private RisikoMessageGenerator messageBuilder;

    public static GameController createGameController(Middle middle){
        
            instance=new GameController(middle);
        
        return instance;
    }

    public static GameController getInstance(){

        return instance;
    }

    

    private GameController(Middle middle){
        //this.comunicator=Communicator.getInstance();
        this.middle=middle;
        this.middle.setGameController(this);
        middle.addListener(EventTypes.PONG, this);
        middle.addListener(EventTypes.RECOVERY, this);
        communicator=middle.getCommunicator();
        this.messageBuilder=middle.getMessageBuilder();
        
        this.turns=new HashMap<String, Integer>();
        gameOver=false;
        this.victoryListeners=new ArrayList<VictoryListener>();
        Tavolo tavolo=Tavolo.getInstance();

        Iterator<Giocatore> iter=tavolo.getGiocatori().iterator();
        while(iter.hasNext()){
            Giocatore g=iter.next();
            turns.put(g.getUsername(), g.getID());
        }

        this.reconnectionNeeds=new boolean[tavolo.getGiocatori().size()];
        this.locker=new TableLocker();
        for(int i=0;i<reconnectionNeeds.length;i++){
            reconnectionNeeds[i]=false;
           
        }

        messageReceived=new boolean[Tavolo.getInstance().getGiocatori().size()];
        for(int i=0;i<messageReceived.length;i++){
            messageReceived[i]=false;
        }
        this.timer=new GameTimer(this, GameTimer.ACTION);
        if(tavolo.isTurnoMyGiocatore()){
                 timer.start();
             }
        if(communicator.isCentral()){
            messageReceived[Tavolo.getInstance().getMyGiocatore().getID()]=true;



             this.managerTimer=new ManagerPingerThread();
             managerTimer.start();
             
        }else{
            middle.startMessageWaiter();
        }

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Middle getMiddle() {
        return middle;
    }

    public void setMiddle(Middle middle) {
        this.middle = middle;
    }

    
    

    public void restartTimers(){
        this.timer=new GameTimer(this, GameTimer.ACTION);
        if(Tavolo.getInstance().isTurnoMyGiocatore()){
                 timer.start();
        }
        if(communicator.isCentral()){
            messageReceived[Tavolo.getInstance().getMyGiocatore().getID()]=true;



             this.managerTimer=new ManagerPingerThread();
             managerTimer.start();

        }
    }

    public TimeoutNotifier getTimeoutNotifier() {
        return timeoutNotifier;
    }

    public void setTimeoutNotifier(TimeoutNotifier timeoutNotifier) {
        this.timeoutNotifier = timeoutNotifier;
    }

    public RisikoMessageGenerator getMessageBuilder() {
        return messageBuilder;
    }

    public void setMessageBuilder(RisikoMessageGenerator messageBuilder) {
        this.messageBuilder = messageBuilder;
    }


    
 
    
    public int getIDObiettivo(){
        return Tavolo.getInstance().getMyGiocatore().getObiettivo().getCodice();
    }

    public String getDescrizioneObiettivo(){
        return Tavolo.getInstance().getMyGiocatore().getObiettivo().toString();
    }
    public HistoryListener getHistoryListener() {
        return historyListener;
    }

    public String getNomeMyGiocatore(){
        return Tavolo.getInstance().getMyGiocatore().getNome();
    }
    public void setHistoryListener(HistoryListener historyListener) {
        this.historyListener = historyListener;
    }

    public MapListener getMapListener() {
        return mapListener;
    }

    public void setMapListener(MapListener mapListener) {
        this.mapListener = mapListener;
    }

    public PlayerDataListener getPlayerDataListener() {
        return playerDataListener;
    }

    public void setPlayerDataListener(PlayerDataListener playerDataListener) {
        this.playerDataListener = playerDataListener;
    }

    public TroopsSelector getTroopsSelector() {
        return troopsSelector;
    }

    public void setTroopsSelector(TroopsSelector troopsSelector) {
        this.troopsSelector = troopsSelector;
    }

   public void addVictoryListener(VictoryListener listener){
       this.victoryListeners.add(listener);
   }

   public void removeVictoryListener(VictoryListener listener){
       this.victoryListeners.remove(listener);
   }



    public void initMap(){
        Territorio[] territori=Tavolo.getInstance().getMappa().getNazioni();
        for(int i=0;i<territori.length;i++){
            this.mapListener.setLabelAttributes(territori[i].getCodice(), territori[i].getNumeroUnita(), territori[i].getOccupante().getID());
        }

    }

    public void initCurrentPlayerData(){
        Giocatore g=Tavolo.getInstance().getGiocatoreCorrente();
        this.playerDataListener.updateDatiGiocatore(g.getNome(), g.getNumeroTruppe(), g.getArmateDisposte(), g.getNazioni().size());
    }

    public void notify(ApplianceEvent event) {
        ApplianceMessage msg=(ApplianceMessage)event.getSource();
        if(gameOver){
            return;
        }
        int troops_number=msg.getTroops_number();
        int region=msg.getRegion();
        
        Tavolo tavolo=Tavolo.getInstance();
        Territorio territorio=tavolo.getMappa().getTerritorio(region);
        String message="Il "+territorio.getOccupante().getNome()+" posiziona "+troops_number+" in "+territorio.getNome();
        
        System.out.println("ricevuto messaggio di appliance per territorio "+territorio.getNome()+" di "+troops_number+" unita");
        if(tavolo.assegnaUnita(troops_number, territorio)){
            this.mapListener.setLabelAttributes(region, territorio.getNumeroUnita(), territorio.getOccupante().getID());
            //this.historyListener.appendActionInHistory(message);
            Giocatore giocatoreCorrente=tavolo.getGiocatoreCorrente();
            this.playerDataListener.updateDatiGiocatore(giocatoreCorrente.getNome(),giocatoreCorrente.getNumeroTruppe(),giocatoreCorrente.getArmateDisposte(),giocatoreCorrente.getNazioni().size());
        }else{
            System.out.println("errore comunicazione appliance");
            message=message+" -> ERRORE :: turno "+Tavolo.getInstance().getTurno()+" tavolo in init? "+Tavolo.getInstance().isInizializzazione();
        }
        historyListener.appendActionInHistory(message);
        //showInfo("Disposizione", message);
    }

    public void notify(AttackEvent event) {
        AttackMessage msg=(AttackMessage) event.getSource();
        if(gameOver){
            return;
        }
        int troops_number=msg.getTroopNumber();
        int from=msg.getFrom();
        int to=msg.getTo();

        
        Tavolo tavolo=Tavolo.getInstance();
        Territorio fromTerritorio=tavolo.getMappa().getTerritorio(from);
        Territorio toTerritorio=tavolo.getMappa().getTerritorio(to);
        Giocatore  attaccante=fromTerritorio.getOccupante();
        Giocatore difensore=toTerritorio.getOccupante();

        Azione azione=tavolo.preparaAttacco(fromTerritorio, toTerritorio);

        System.out.println("ricevuto messaggio di attacco da territorio "+fromTerritorio.getNome()+"a territorio "+toTerritorio.getNome()+" con "+troops_number+" unita");
            if(azione!=null){
                azione.setNumeroTruppe(troops_number);
                tavolo.eseguiAttacco((Attacco)azione);

                this.historyListener.appendActionInHistory(azione.toString());
                this.mapListener.setLabelAttributes(fromTerritorio.getCodice(),fromTerritorio.getNumeroUnita(),fromTerritorio.getOccupante().getID());

                this.mapListener.setLabelAttributes(toTerritorio.getCodice(),toTerritorio.getNumeroUnita(),toTerritorio.getOccupante().getID());
                String message="Il "+fromTerritorio.getOccupante().getNome()+" attacca da "+fromTerritorio.getNome()+"\n"+" a "+toTerritorio.getNome()+"\n"+" con "+troops_number+" unita"+
                        "attacco : ";

                if(azione.isAttacco()){

                    //variabili per i dati di Luigi
                    int [] att={0,0,0};
                    int [] dif={0,0,0};

                    int[] a=((Attacco)azione).getPunteggio();
                    String s="";
                    for(int i=0;i<a.length;i++){
                        s=s+" - "+Integer.toString(a[i]);
                        att[i]=a[i];
                    }
                    this.historyListener.appendActionInHistory(s);
                    message=message+s+"\n"+"difesa : " ;
                    a=((Attacco)azione).getPunteggioAvversario();
                     s="";
                    for(int i=0;i<a.length;i++){
                        s=s+" - "+Integer.toString(a[i]);
                        dif[i]=a[i];
                    }
                     message=message+s;
                    this.historyListener.appendActionInHistory(s);

                    //lancia il panel di Luigi
                    mapListener.notifyAttacco("Attacco da "+fromTerritorio.getNome()+" a "+toTerritorio.getNome(),att[0],att[1],att[2],dif[0],dif[1],dif[2],attaccante.getID(),difensore.getID());
                   // new dadiGui("Attacco da "+azione.getDaTerritorio().getNome()+" a "+azione.getaTerritorio().getNome(),att[0],att[1],att[2],dif[0],dif[1],dif[2],attaccante.getID(),difensore.getID()).setVisible(true);


                }

                //azione diversa da null::controllare stato obiettivi
                if(tavolo.controllaObiettivoGiocatore(tavolo.getGiocatoreCorrente())){

                   this.notifyVictory(tavolo.getGiocatori(), tavolo.getGiocatoreCorrente(),true);
                }

            }else{
            System.out.println("errore comunicazione attacco");

        }

    }

    public void notify(MovementEvent event) {
        MovementMessage msg=(MovementMessage) event.getSource();
        if(gameOver){
            return;
        }
        int troops_number=msg.getTroopNumber();
        int from=msg.getFrom();
        int to=msg.getTo();
        
        Tavolo tavolo=Tavolo.getInstance();
        Territorio fromTerritorio=tavolo.getMappa().getTerritorio(from);
        Territorio toTerritorio=tavolo.getMappa().getTerritorio(to);
        System.out.println("Spostamento :Il "+tavolo.getGiocatoreCorrente()+" sposta da "+fromTerritorio.getNome()+" a "+toTerritorio.getNome()+" con "+troops_number+" unita");
        Azione azione=tavolo.preparaSpostamento(fromTerritorio, toTerritorio);
        
        if(azione!=null){
            azione.setNumeroTruppe(troops_number);
            tavolo.eseguiSpostamento((Spostamento) azione);

                this.historyListener.appendActionInHistory(azione.toString());
                this.mapListener.setLabelAttributes(fromTerritorio.getCodice(),fromTerritorio.getNumeroUnita(),fromTerritorio.getOccupante().getID());//azione.getDaTerritorio());
                this.mapListener.setLabelAttributes(toTerritorio.getCodice(), toTerritorio.getNumeroUnita() , toTerritorio.getOccupante().getID());



                //azione diversa da null::controllare stato obiettivi
                if(tavolo.controllaObiettivoGiocatore(tavolo.getGiocatoreCorrente())){

                   this.notifyVictory(tavolo.getGiocatori(), tavolo.getGiocatoreCorrente(),true);
                }

            }

    }

    public void notify(ChangeCardEvent event) {
        ChangeCardMessage msg=(ChangeCardMessage) event.getSource();
        if(gameOver){
            return;
        }
        int card1=msg.getCard1();
        int card2=msg.getCard2();
        int card3=msg.getCard3();
        
        Tavolo tavolo=Tavolo.getInstance();
        Giocatore g=tavolo.getGiocatoreCorrente();
        Carta c1=g.getCarta(card1);
        Carta c2=g.getCarta(card2);
        Carta c3=g.getCarta(card3);
        int rinforzi=Rinforzo.getRinfornzo(g, c1, c2, c3, tavolo.getMappa());
        g.setNumeroTruppe(g.getNumeroTruppe()+rinforzi);
        System.out.println("Ricevuta cambio dal "+g.getNome()+" con "+c1.getTerritorio().getNome()+" "+c2.getTerritorio().getNome()+" "+c3.getTerritorio().getNome());
        this.playerDataListener.updateDatiGiocatore(g.getNome(), g.getNumeroTruppe(), g.getArmateDisposte(), g.getNazioni().size());
        List<Carta> carte=tavolo.getCarte();
        carte.add(c1);
        carte.add(c2);
        carte.add(c3);
        this.cardChangeListener.notifyChangeCard(g, c1, c2, c3, rinforzi);

    }

    public boolean canMove(){
        Tavolo tavolo=locker.acquireTavolo();
        boolean result=tavolo.isTurnoMyGiocatore();
        locker.releaseTavolo();
        return result;
    }


    public void makeFirstSelection(int terrID){
        if(gameOver){
            return;
        }
        Tavolo tavolo=locker.acquireTavolo();
        Territorio t=tavolo.getMappa().getTerritorio(terrID);
        Giocatore corrente=tavolo.getGiocatoreCorrente();
        locker.releaseTavolo();
        if(firstSelection==null){

            if(corrente!=t.getOccupante()){

                return;
            }
            if(t.getNumeroUnita()>1){
                firstSelection=t;
            }

        }
    }

    public void resetActionData(){
        firstSelection=null;
        secondSelection=null;
        truppeSelezionate=0;
    }

    public boolean makeAttack(int fromTerritorioID,int toTerritorioID){
        if(gameOver){
            return false;
        }
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()){
            locker.releaseTavolo();
            return false;
        }
        firstSelection=tavolo.getMappa().getTerritorio(fromTerritorioID);
        secondSelection=tavolo.getMappa().getTerritorio(toTerritorioID);

        Azione azione=tavolo.preparaAttacco(firstSelection, secondSelection);

        Giocatore attaccante=null;
        Giocatore difensore=null;

            if(azione!=null){
           
                if(timer.getInterval()==0){
                    timer.stopTimer();
                    timer=new GameTimer(this, GameTimer.ACTION);
                    
                    timer.start();
                }else{
                    timer.setInterval(GameTimer.ACTION);
                }
                 attaccante=firstSelection.getOccupante();
                 difensore=secondSelection.getOccupante();

                  truppeSelezionate=-1;
                  if(firstSelection.confina(secondSelection)){
                        truppeSelezionate=this.troopsSelector.selectTroops(true, firstSelection.getNumeroUnita()-1,firstSelection.getCodice(), secondSelection.getCodice());
                  }

                  if(truppeSelezionate<=0){
                      firstSelection=null;
                      secondSelection=null;
                      locker.releaseTavolo();
                      return false;
                  }
                  azione.setNumeroTruppe(truppeSelezionate);
                tavolo.eseguiAttacco((Attacco)azione);
                
              
                        AttackMessage msg=this.messageBuilder.generateAttackMSG(firstSelection.getCodice(), secondSelection.getCodice(), truppeSelezionate);
                     
                       // Message msg = comunicator.createAttackMessage(truppeSelezionate, firstSelection.getCodice(), secondSelection.getCodice());
                        communicator.sendMessage(msg);

                    

                this.historyListener.appendActionInHistory(azione.toString()); //setAzione(azione.toString());

                this.mapListener.setLabelAttributes(azione.getDaTerritorio().getCodice(), azione.getDaTerritorio().getNumeroUnita(), azione.getDaTerritorio().getOccupante().getID());
                this.mapListener.setLabelAttributes(azione.getaTerritorio().getCodice(), azione.getaTerritorio().getNumeroUnita(), azione.getaTerritorio().getOccupante().getID());


               

                    //variabili per i dati di Luigi
                    int [] att={0,0,0};
                    int [] dif={0,0,0};

                    int[] a=((Attacco)azione).getPunteggio();
                    String s="";
                    for(int i=0;i<a.length;i++){
                        s=s+" - "+Integer.toString(a[i]);
                        att[i]=a[i];
                    }
                    this.historyListener.appendActionInHistory(s);

                    a=((Attacco)azione).getPunteggioAvversario();
                     s="";
                    for(int i=0;i<a.length;i++){
                        s=s+" - "+Integer.toString(a[i]);
                        dif[i]=a[i];
                    }
                    this.historyListener.appendActionInHistory(s);

                    //lancia il panel di Luigi
                    System.out.println();
                    this.mapListener.notifyAttacco("Attacco da"+azione.getDaTerritorio().getNome()+" a "+azione.getaTerritorio().getNome(), att[0],att[1],att[2],dif[0],dif[1],dif[2],attaccante.getID(),difensore.getID());
//                    new dadiGui("Attacco da "+azione.getDaTerritorio().getNome()+" a "+azione.getaTerritorio().getNome(),att[0],att[1],att[2],dif[0],dif[1],dif[2],attaccante.getID(),difensore.getID()).setVisible(true);

                if(tavolo.controllaObiettivoGiocatore(tavolo.getGiocatoreCorrente())){

                   this.notifyVictory(tavolo.getGiocatori(), tavolo.getGiocatoreCorrente(),true);
                }
                locker.releaseTavolo();
                return true;
            }
        locker.releaseTavolo();
        return false;
    }

    public boolean makeSpostamento(int fromterritorioID,int toTerritorioID){
        if(gameOver){
            return false;
        }
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()){
            locker.releaseTavolo();
            return false;
        }
         firstSelection=tavolo.getMappa().getTerritorio(fromterritorioID);
        secondSelection=tavolo.getMappa().getTerritorio(toTerritorioID);
        Giocatore corrente=tavolo.getGiocatoreCorrente();
       Azione azione=tavolo.preparaSpostamento(firstSelection, secondSelection);
                if(azione!=null){
             
                    truppeSelezionate=-1;
                    if(firstSelection.confina(secondSelection)){
                        truppeSelezionate=this.troopsSelector.selectTroops(false,firstSelection.getNumeroUnita()-1, firstSelection.getCodice()  , secondSelection.getCodice());
                    }
                    if(truppeSelezionate<=0){
                        firstSelection=null;
                        secondSelection=null;
                        locker.releaseTavolo();
                        return false;
                    }
                    azione.setNumeroTruppe(truppeSelezionate);
                    tavolo.eseguiSpostamento((Spostamento) azione);
                    timer.stopTimer();


                    //codice per il recupero carta
                    Carta carta=tavolo.recuperaCarta(corrente);
                    if(carta!=null){
                        String s="null";
                        Territorio territorio=carta.getTerritorio();
                        if(territorio!=null){
                            s=territorio.getNome();
                        }
                        this.cardListener.notifyCard(carta.getCodice(), s);
                    }
                    tavolo.passaTurno();
                    corrente=tavolo.getGiocatoreCorrente();
                //    new JFrameTurno(corrente.getID()).setVisible(true);
                    this.playerDataListener.updateDatiGiocatore(corrente.getNome(), corrente.getNumeroTruppe(), corrente.getArmateDisposte(), corrente.getNazioni().size());
                    
                        MovementMessage msg1=this.messageBuilder.generateMovementMSG(firstSelection.getCodice(), secondSelection.getCodice(), truppeSelezionate);
                        //Message msg = comunicator.createMovementMessage(truppeSelezionate, firstSelection.getCodice(),secondSelection.getCodice());
                        communicator.sendMessage(msg1);
                        PassMessage msg2=this.messageBuilder.generatePassMSG(tavolo.getTurno());
                        
                        StatoGiocoPanel.instance.updateGiocatore(corrente);
                        playerDataListener.updateDatiGiocatore(corrente.getNome(), corrente.getNumeroTruppe(), corrente.getArmateDisposte(), corrente.getNazioni().size());
                        communicator.sendMessage(msg2);
                        


                    

                    

                    
                    this.historyListener.appendActionInHistory(azione.toString()); //setAzione(azione.toString());
                this.mapListener.setLabelAttributes(azione.getDaTerritorio().getCodice(), azione.getDaTerritorio().getNumeroUnita(), azione.getDaTerritorio().getOccupante().getID());
                this.mapListener.setLabelAttributes(azione.getaTerritorio().getCodice(), azione.getaTerritorio().getNumeroUnita(), azione.getaTerritorio().getOccupante().getID());                
                
                    locker.releaseTavolo();
                    return true;

                }
       locker.releaseTavolo();
       return false;

    }

    private synchronized  void sendRecoveryMessage(int giocatore){
        if(gameOver){
            return;
        }
        
        

        if(this.reconnectionNeeds[giocatore]){
            try {
                String name=Tavolo.getInstance().getGiocatori().get(giocatore).getUsername();
                RecoveryUtil util=new RecoveryUtil();
                RecoveryParameter backup=util.createBackup();
                RecoveryMessage msg=this.messageBuilder.generateRecoveryMSG(backup);
                this.communicator.sendMessageTo(msg,name);
                this.reconnectionNeeds[giocatore] = false;
                System.out.println("send recovery message to player "+name+" with ID "+giocatore);
            } catch (Exception ex) {
                System.err.println("Impossibile inviare messaggio di recupero");
                ex.printStackTrace();
            }
        }
        
    }

    public void makeSecondSelection(int terrID){
        if(gameOver){
            return;
        }
        Territorio currentSelection=Tavolo.getInstance().getMappa().getTerritorio(terrID);


        if(currentSelection==firstSelection){

            secondSelection=null;

        }else {
            this.secondSelection=currentSelection;
        }
    }

    public int getFirstSelectionID(){
        if(firstSelection==null){
            return -1;
        }
        return firstSelection.getCodice();
    }

    public int getSecondSelectionID(){
        if(secondSelection==null){
            return -1;
        }
        return secondSelection.getCodice();
    }

    public void assegnaUnita(int terrID){
        if(gameOver){
            return;
        }
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()&&!this.communicator.isCentral()){
            locker.releaseTavolo();
            return;
        }
        Territorio t=tavolo.getMappa().getTerritorio(terrID);
        Giocatore corrente=tavolo.getGiocatoreCorrente();

        if(tavolo.getGiocatoreCorrente().getNumeroTruppe()>0&&t.getOccupante()==corrente){
            if(tavolo.assegnaUnita(t)){
                try {
                        ApplianceMessage msg=this.messageBuilder.generateApplianceMSG(t.getCodice(), 1); 
                                //new ApplianceMessage(1, t.getCodice());
                       // Message msg = comunicator.createApplicanceMessage(1, t.getCodice());
                        communicator.sendMessage(msg);
                        this.playerDataListener.updateDatiGiocatore(corrente.getNome(), corrente.getNumeroTruppe(), corrente.getArmateDisposte(), corrente.getNazioni().size());
                        if(tavolo.getGiocatoreCorrente().getNumeroTruppe()==0){
                            
                            timer.setInterval(GameTimer.ACTION);
                            
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                this.mapListener.setLabelAttributes(t.getCodice(), t.getNumeroUnita(), t.getOccupante().getID());

            }
            locker.releaseTavolo();
            return;
        }
    }

    public boolean haTruppe(){

        return Tavolo.getInstance().getMyGiocatore().getNumeroTruppe()>0;
    }

    public boolean isInInizializzazione(){
        return Tavolo.getInstance().isInizializzazione();
    }

    public void assegnaUnitaInInizializzazione(int terrID){
        if(gameOver){
            return;
        }
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()&&!this.communicator.isCentral()){
            locker.releaseTavolo();
            return;
        }
        Territorio t=tavolo.getMappa().getTerritorio(terrID);
        Giocatore corrente=tavolo.getGiocatoreCorrente();
        if(tavolo.isInizializzazione()){
            if(truppeSelezionate<3&&tavolo.getGiocatoreCorrente().getNumeroTruppe()>0){

                if(tavolo.assegnaUnita(t)){
                    try {
                        ApplianceMessage msg=this.messageBuilder.generateApplianceMSG(t.getCodice(), 1); 
                                //new ApplianceMessage(1, t.getCodice());
                      //  Message msg = comunicator.createApplicanceMessage(1, t.getCodice());
                        communicator.sendMessage(msg);

                        truppeSelezionate++;
                        this.playerDataListener.updateDatiGiocatore(corrente.getNome(), corrente.getNumeroTruppe(), corrente.getArmateDisposte(), corrente.getNazioni().size());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                this.mapListener.setLabelAttributes(terrID, t.getNumeroUnita(), t.getOccupante().getID());
            }
            if((truppeSelezionate==3)||(corrente.getNumeroTruppe()==0)){
                try{
                    
                    timer.stopTimer();
                   
                    
                    if((tavolo.getTurno()==(tavolo.getGiocatori().size()-1))&&(corrente.getNumeroTruppe()==0)){
                        tavolo.setInizializzazione(false);
                    }
                    tavolo.passaTurno();
                    Giocatore prossimo=tavolo.getGiocatoreCorrente();
                    this.playerDataListener.updateDatiGiocatore(prossimo.getNome(), prossimo.getNumeroTruppe(), prossimo.getArmateDisposte(), prossimo.getNazioni().size());
                 //   Message msg=comunicator.createPassesMessage(tavolo.getTurnoSuccessivo());
                    PassMessage msg=this.messageBuilder.generatePassMSG(tavolo.getTurno()); 
                            //new PassMessage(tavolo.getTurno());
                    communicator.sendMessage(msg);
                    Giocatore g=tavolo.getGiocatoreCorrente();
                    StatoGiocoPanel.instance.updateGiocatore(g);
                    playerDataListener.updateDatiGiocatore(g.getNome(), g.getNumeroTruppe(), g.getArmateDisposte(), g.getNazioni().size());
                    
                    new JFrameTurno(prossimo.getID()).setVisible(true);
                    

                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                this.playerDataListener.updateDatiGiocatore(corrente.getNome(), corrente.getNumeroTruppe(), corrente.getArmateDisposte(), corrente.getNazioni().size());
                truppeSelezionate=0;
            }

            
        }
        locker.releaseTavolo();
    }

    public void sendMessage(String from, String to, String message) {

        ChatMessage msg=this.messageBuilder.generateChatMSG(middle.getPlayerName(), to, message); 
                //new ChatMessage(to, Tavolo.getInstance().getMyGiocatore().getNome(), message);
        
       
            this.communicator.sendMessage(msg);
       
    }

    public void notify(PassEvent event) {
        PassMessage msg=(PassMessage)event.getSource();
        if(gameOver){
            return;
        }
        int turno=msg.getTurno_successivo();
            Tavolo tavolo=locker.acquireTavolo();

            
          /*  if(turno!=tavolo.getTurnoSuccessivo()){
                locker.releaseTavolo();
                System.err.println("ricevo turno precedente");
                return;
            }*/
            int turnoSucc=tavolo.getTurnoSuccessivo();
            if(turnoSucc==0&&tavolo.getGiocatori().get(0).getNumeroTruppe()==0){
                tavolo.setInizializzazione(false);
            }

            Giocatore precedente=tavolo.getGiocatoreCorrente();
            tavolo.recuperaCarta(precedente);

            tavolo.passaTurno();

            Giocatore giocatore=tavolo.getGiocatoreCorrente();
            if(tavolo.isTurnoMyGiocatore()){
                new JFrameTurno(giocatore.getID()).setVisible(true);
                
                
                timer.stopTimer();
                timer=new GameTimer(this, GameTimer.ACTION);
                timer.start();
            }
            
            this.playerDataListener.updateDatiGiocatore(giocatore.getNome(), giocatore.getNumeroTruppe(), giocatore.getArmateDisposte(), giocatore.getNazioni().size());
            if(tavolo.gameOver()){
                List<Giocatore> giocatori=tavolo.getGiocatori();
                Giocatore bestP=this.findBestGiocatore(giocatori);
                this.notifyVictory(giocatori, bestP, false);
            }
            if(tavolo.isTurnoMyGiocatore()){

                this.historyListener.appendActionInHistory(tavolo.getGiocatoreCorrente().getNome()+" ancora "+tavolo.getGiocatoreCorrente().getNumeroTruppe());
                System.out.println("ancora in inizializzazione "+tavolo.isInizializzazione());
            }

            
            locker.releaseTavolo();

    }

    public   void  passaTurno() throws IOException{
        if(gameOver){
            return;
        }
         if(!Tavolo.getInstance().isTurnoMyGiocatore()){
             return;
         }

         if(Tavolo.getInstance().isInizializzazione()){
             return;
         }
         passaTurno_();
    }
    private   void  passaTurno_() throws IOException{
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()&&!this.communicator.isCentral()){
            locker.releaseTavolo();
            return;
        }
        
        
        Giocatore giocatore=Tavolo.getInstance().getGiocatoreCorrente();
        int troops=tavolo.getGiocatoreCorrente().getNumeroTruppe();
        if(troops>0&&!tavolo.isInizializzazione()){
            this.autoDispose(troops);
        }
        this.timer.stopTimer();
        if((!tavolo.isInizializzazione())){
            //codice per il recupero carta
            Carta carta=tavolo.recuperaCarta(giocatore);
            if(carta!=null){
                String s="null";
                Territorio territorio=carta.getTerritorio();
                if(territorio!=null){
                    s=territorio.getNome();
                }
                this.cardListener.notifyCard(carta.getCodice(), s);
            }
            tavolo.passaTurno();
            PassMessage msg=this.messageBuilder.generatePassMSG(tavolo.getTurno()); 
                    //new PassMessage(tavolo.getTurno());
            Giocatore g=tavolo.getGiocatoreCorrente();
            StatoGiocoPanel.instance.updateGiocatore(g);
            playerDataListener.updateDatiGiocatore(g.getNome(), g.getNumeroTruppe(), g.getArmateDisposte(), g.getNazioni().size());

            try {
                        //Thread.sleep(3000);
                        this.communicator.sendMessage(msg);
                        


                    } catch (Exception  ex) {
                        ex.printStackTrace();
                    }
           

    //        new JFrameTurno(g.getID()).setVisible(true);
            this.playerDataListener.updateDatiGiocatore(g.getNome(),g.getNumeroTruppe(),g.getArmateDisposte(),g.getNazioni().size());
            if(tavolo.gameOver()){
                List<Giocatore> giocatori=tavolo.getGiocatori();
                Giocatore bestP=this.findBestGiocatore(giocatori);
                this.notifyVictory(giocatori, bestP, false);
            }
        }
        
        locker.releaseTavolo();
    }

    public void setCardListener(CardListener aThis) {
       this.cardListener=aThis;
    }
    public void setCardChangeListener(CardChangeListener aThis) {
       this.cardChangeListener=aThis;
    }

    public void notifyReconnectionRequest(String playerName) {
        if(gameOver){
            return;
        }
        System.out.println("Ricevuto richiesta riconnessione da "+playerName);
        int turn=this.turns.get(playerName).intValue();
        reconnectionNeeds[turn]=true;
        messageReceived[turn]=true;
    }

    public void timeoutNotify() {
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()){
            System.err.println("o nooo il timer è sopravvisuuto e nun vole schatta");
            locker.releaseTavolo();;
            return;
        }
        int troops=tavolo.getMyGiocatore().getNumeroTruppe();
        if(troops>0){
            
            this.autoDispose(troops);
            
        }

        try {
            passaTurno_();
        } catch (IOException ex) {
           System.err.println("impossibile passare turno");
        }
        locker.releaseTavolo();
        
    }

    private void autoDispose(int troops){
        if(gameOver){
            return;
        }
        Tavolo tavolo=Tavolo.getInstance();
        Set<Territorio> set=tavolo.getGiocatoreCorrente().getNazioni();
            int size=set.size();
            Territorio[] territori=new Territorio[size];
            territori=set.toArray(territori);

            if(tavolo.isInizializzazione()){
                if(troops>3){
                    troops=3;
                }
            }

            Random random=new Random();
            for(int i=0;i<troops;i++){
                int nextNationID=territori[random.nextInt(size)].getCodice();
                if(tavolo.isInizializzazione()){
                    this.assegnaUnitaInInizializzazione(nextNationID);
                }else{
                    this.assegnaUnita(nextNationID);
                }
            }
    }

    public void remaingTimeNotify(int remaing) {
        if(timeoutNotifier!=null){
            this.timeoutNotifier.remaingTimeNotify(remaing);
        }
        
    }

    public void notify(PongEvent event) {
        if(!communicator.isCentral()){
            return;
        }
       PongMessage msg=(PongMessage) event.getSource();
       this.messageReceived[turns.get(msg.getPeerID())]=true;
    }

    private Giocatore findBestGiocatore(List<Giocatore> giocatori) {
        Tavolo tavolo=Tavolo.getInstance();
        Giocatore vincitore=giocatori.get(0);
        int bestScore=tavolo.getPunteggioGiocatore(vincitore);

        for(int i=1;i<giocatori.size();i++){
            int score=tavolo.getPunteggioGiocatore(giocatori.get(i));
            if(score>bestScore){
                bestScore=score;
                vincitore=giocatori.get(i);
            }
        }

        return vincitore;

    }

    private void notifyVictory(List<Giocatore> giocatori, Giocatore g,boolean vistory){
        if(gameOver){
            return;
        }
        this.gameOver=true;
        this.timer.stopTimer();
        
        Iterator<VictoryListener> iter=this.victoryListeners.iterator();
        while(iter.hasNext()){
            iter.next().notifyVictory(giocatori, g, vistory);
        }
    }
    /*
     * ApplianceEventListener,AttackEventListener,MovementEventListener,ChangeCardEventListener,PassEventListener{
     */

    public void notify(RisikoEvent event) {
        String type=event.getType();
        if(type.equals(EventTypes.APPLIANCE)){
            ApplianceEvent x=(ApplianceEvent)event;
            notify(x);
        }else if(type.equals(EventTypes.ATTACK)){
            AttackEvent x=(AttackEvent)event;
            notify(x);
        }else if(type.equals(EventTypes.MOVEMENT)){
            MovementEvent x=(MovementEvent)event;
            notify(x);
        }else if(type.equals(EventTypes.CHANGE_CARDS)){
            ChangeCardEvent x=(ChangeCardEvent)event;
            notify(x);
        }else if(type.equals(EventTypes.PASS)){
            PassEvent x=(PassEvent)event;
            notify(x);
        }else if(type.equals(EventTypes.RECOVERY)){
            RecoveryEvent x=(RecoveryEvent)event;
            notify(x);
        }else if(type.equals(EventTypes.PONG)){
            PongEvent x=(PongEvent)event;
            notify(x);
        }
    }

    

    public void notify(RecoveryEvent c) {
        if(!communicator.isCentral()){
            return;
        }
        
        this.reconnectionNeeds[turns.get(c.getSource().playerName())]=true;
        /*RecoveryUtil util=new RecoveryUtil();
        RecoveryMessage msg=(RecoveryMessage)c.getSource();
        RecoveryParameter parameter=msg.getParameter();
        try {
            util.recoveryTable(parameter);
            
        } catch (Exception ex) {
            System.out.println("Impossibile recuperare dati");
            System.exit(-1);
        } */
    }

    
    private  class  ManagerPingerThread extends Thread{

        private int sleepTime=30 * 1000 ;
        

        private AtomicBoolean continueTimer;


        public ManagerPingerThread(){
            this.continueTimer=new AtomicBoolean(true);
        }

        public void stopTimer(){
            this.continueTimer.set(false);
        }

        private  void ping(int interval) throws InterruptedException, IOException{
                
                    for(int i=0;i<interval;i++){
                        for(int j=1;j<messageReceived.length;j++){
                            messageReceived[j]=false;
                        }
                        RisikoMessage ping=messageBuilder.generatePingMSG();
                        communicator.sendMessage(ping);
                        this.sleep(sleepTime);
                        sendStatusMessage();

                    }


         }

        private void sendStatusMessage() throws IOException{
            StatoGiocoPanel panel=StatoGiocoPanel.getInstance();
            int myTurn=Tavolo.getInstance().getMyGiocatore().getID();
            List<Giocatore> giocatori=Tavolo.getInstance().getGiocatori();
            for(int i=0;i<messageReceived.length;i++){
                
                    RisikoMessage msg=messageBuilder.generateStatusPeerMSG(i, messageReceived[i]||reconnectionNeeds[i]||i==myTurn);
                    
                    communicator.sendMessage(msg);
                   // panel.updateStatus((StatusPeerMessage) msg);
                
            }
        }

        
        @Override
        public void run() {
            Giocatore g;
            while(continueTimer.get()){
                try {
                    try {
                        System.out.println("Ping (1)");
                        ping(1);
                    } catch (IOException ex) {
                       System.err.println("impossibile inviare ping");
                    }

                    


                    

                    g=Tavolo.getInstance().getGiocatoreCorrente();
                    if(messageReceived[g.getID()]){
                         sendRecoveryMessage(g.getID());

                    }
                    else if(!Tavolo.getInstance().isTurnoMyGiocatore())
                    {
                        //autoDispose(Tavolo.getInstance().getGiocatoreCorrente().getNumeroTruppe());
                        
                        try {
                                System.out.println("giocatore "+g.getUsername()+" non risponde ...");
                                System.out.println("Ping (2)");
                                ping(1);

                                 if(!Tavolo.getInstance().isTurnoMyGiocatore()&&!messageReceived[g.getID()]){
                                    System.out.println("giocatore "+g.getUsername()+" continua a non rispondere ...");
                                    if(!reconnectionNeeds[g.getID()]){
                                        System.out.println(" giocatore "+g.getUsername()+" non ha effettuato richiesta riconnessione ... chiusura pipe");
                                        communicator.closePipeFor(g.getID(),g.getUsername());
                                    }
                                    autoDispose(Tavolo.getInstance().getGiocatoreCorrente().getNumeroTruppe());
                                    passaTurno_();
                                    if(Tavolo.getInstance().isTurnoMyGiocatore()){

                                        timer.stopTimer();
                                        timer=new GameTimer(GameController.instance, GameTimer.ACTION);
                                        timer.start();
                                    }
                                }
                                
                            
                            
                            
                        } catch (IOException ex) {
                            System.err.println("impossibile inviare ping o chidere pipe");
                        }
                    }
                    
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }

            

        }


    }

    
    

    
    

  

}
