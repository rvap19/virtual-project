/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.IOException;
import java.util.List;
//import jxta.communication.Communicator;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxta.communication.VirtualCommunicator;

import net.jxta.endpoint.Message;
import virtualrisikoii.listener.ApplianceListener;
import virtualrisikoii.listener.AttackListener;
import virtualrisikoii.listener.ChangeCardListener;
import virtualrisikoii.listener.ChatListener;
import virtualrisikoii.listener.MovementListener;
import virtualrisikoii.listener.PassListener;
import virtualrisikoii.listener.ReconnectionRequestListener;
import virtualrisikoii.risiko.Attacco;
import virtualrisikoii.risiko.Azione;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.JFrameTurno;
import virtualrisikoii.risiko.Rinforzo;
import virtualrisikoii.risiko.Spostamento;
import virtualrisikoii.risiko.Tavolo;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author root
 */
public class GameController implements ApplianceListener,AttackListener,MovementListener,ChangeCardListener,ChatSender,PassListener,TimeoutNotifier,ReconnectionRequestListener{
    private MapListener mapListener;
    private HistoryListener historyListener;
    private PlayerDataListener playerDataListener;
    private VictoryListener victoryListener;
    private TroopsSelector troopsSelector;
    private CardListener cardListener;
    private CardChangeListener cardChangeListener;
    private VirtualCommunicator comunicator;
    private TimeoutNotifier timeoutNotifier;
    private GameTimer timer;

    //private Tavolo tavolo;
    private TableLocker locker;

    private Territorio firstSelection;
    private Territorio secondSelection;
    private int truppeSelezionate;
    private boolean[] reconnectionNeeds;

    private static GameController instance=null;

    

    public static GameController createGameController(){
        if(instance==null){
            instance=new GameController();
        }
        return instance;
    }

    public static GameController getInstance(){
        return instance;
    }

    

    private GameController(){
        //this.comunicator=Communicator.getInstance();
        this.comunicator=VirtualCommunicator.getInstance();
        
        comunicator.addPassListener(this);
        comunicator.addApplianceListener(this);
        comunicator.addAttackListener(this);
        comunicator.addMovementListener(this);
        comunicator.addChangeCardsListener(this);
        comunicator.setRecoveryRequestListener(this);
        Tavolo tavolo=Tavolo.getInstance();
        

        this.reconnectionNeeds=new boolean[tavolo.getGiocatori().size()];
        this.locker=new TableLocker(tavolo);
        for(int i=0;i<reconnectionNeeds.length;i++){
            reconnectionNeeds[i]=false;
        }

        if(comunicator.isManager()){
            this.timer=new GameTimer(this, GameTimer.ACTION);
            timer.start();
        }

        
       
        

    }

    public TimeoutNotifier getTimeoutNotifier() {
        return timeoutNotifier;
    }

    public void setTimeoutNotifier(TimeoutNotifier timeoutNotifier) {
        this.timeoutNotifier = timeoutNotifier;
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

    public VictoryListener getVictoryListener() {
        return victoryListener;
    }

    public void setVictoryListener(VictoryListener victoryListener) {
        this.victoryListener = victoryListener;
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

    public void updateAppliance(int troops_number, int region) {

        Tavolo tavolo=Tavolo.getInstance();
        Territorio territorio=tavolo.getMappa().getTerritorio(region);
        String message="Il "+territorio.getOccupante().getNome()+" posiziona "+troops_number+" in "+territorio.getNome();
        historyListener.appendActionInHistory(message);
        System.out.println("ricevuto messaggio di appliance per territorio "+territorio.getNome()+" di "+troops_number+" unita");
        if(tavolo.assegnaUnita(troops_number, territorio)){
            this.mapListener.setLabelAttributes(region, territorio.getNumeroUnita(), territorio.getOccupante().getID());
            //this.historyListener.appendActionInHistory(message);
            Giocatore giocatoreCorrente=tavolo.getGiocatoreCorrente();
            this.playerDataListener.updateDatiGiocatore(giocatoreCorrente.getNome(),giocatoreCorrente.getNumeroTruppe(),giocatoreCorrente.getArmateDisposte(),giocatoreCorrente.getNazioni().size());
        }else{
            System.out.println("errore comunicazione appliance");

        }
        //showInfo("Disposizione", message);
    }

    public void updateAttack(int troops_number, int from, int to) {
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

                   this.victoryListener.notifyVictory(tavolo.getGiocatori(), tavolo.getGiocatoreCorrente());
                }

            }else{
            System.out.println("errore comunicazione attacco");

        }

    }

    public void updateMovement(int troops_number, int from, int to) {
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

                     this.victoryListener.notifyVictory(tavolo.getGiocatori(), tavolo.getGiocatoreCorrente());
                }

            }

    }

    public void updateChangeCards(int card1, int card2, int card3) {

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
                    timer.setInterval(GameTimer.ACTION);
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
                
                try {
                        Message msg = comunicator.createAttackMessage(truppeSelezionate, firstSelection.getCodice(), secondSelection.getCodice());
                        comunicator.sendMessage(msg);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

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

                    this.victoryListener.notifyVictory(tavolo.getGiocatori(), tavolo.getGiocatoreCorrente());
                }
                locker.releaseTavolo();
                return true;
            }
        locker.releaseTavolo();
        return false;
    }

    public boolean makeSpostamento(int fromterritorioID,int toTerritorioID){
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
                        this.cardListener.notifyCard(carta.getCodice(), carta.getTerritorio().getNome());
                    }
                    tavolo.passaTurno();
                    corrente=tavolo.getGiocatoreCorrente();
                //    new JFrameTurno(corrente.getID()).setVisible(true);
                    this.playerDataListener.updateDatiGiocatore(corrente.getNome(), corrente.getNumeroTruppe(), corrente.getArmateDisposte(), corrente.getNazioni().size());
                    try {
                        Message msg = comunicator.createMovementMessage(truppeSelezionate, firstSelection.getCodice(),secondSelection.getCodice());
                        comunicator.sendMessage(msg);
                        Thread.sleep(3000);
                        msg=comunicator.createPassesMessage(tavolo.getTurnoSuccessivo());
                        comunicator.sendMessage(msg);


                    } catch (Exception ex) {
                       ex.printStackTrace();
                    }

                    sendRecoveryMessage();

                    
                    this.historyListener.appendActionInHistory(azione.toString()); //setAzione(azione.toString());
                this.mapListener.setLabelAttributes(azione.getDaTerritorio().getCodice(), azione.getDaTerritorio().getNumeroUnita(), azione.getDaTerritorio().getOccupante().getID());
                this.mapListener.setLabelAttributes(azione.getaTerritorio().getCodice(), azione.getaTerritorio().getNumeroUnita(), azione.getaTerritorio().getOccupante().getID());                
                
                    locker.releaseTavolo();
                    return true;

                }
       locker.releaseTavolo();
       return false;

    }

    private void sendRecoveryMessage(){
        Tavolo tavolo=locker.acquireTavolo();
        int turno=tavolo.getTurno();

        if(this.reconnectionNeeds[turno]){
            try {
                this.comunicator.sendRecoveryMessage(turno);
                this.reconnectionNeeds[turno] = false;
            } catch (Exception ex) {
                System.err.println("Impossibile inviare messaggio di recupero");
            }
        }
        locker.releaseTavolo();
    }

    public void makeSecondSelection(int terrID){
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
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()){
            locker.releaseTavolo();
            return;
        }
        Territorio t=tavolo.getMappa().getTerritorio(terrID);
        Giocatore corrente=tavolo.getGiocatoreCorrente();

        if(tavolo.getGiocatoreCorrente().getNumeroTruppe()>0&&t.getOccupante()==corrente){
            if(tavolo.assegnaUnita(t)){
                try {
                        Message msg = comunicator.createApplicanceMessage(1, t.getCodice());
                        comunicator.sendMessage(msg);
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
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()){
            locker.releaseTavolo();
            return;
        }
        Territorio t=tavolo.getMappa().getTerritorio(terrID);
        Giocatore corrente=tavolo.getGiocatoreCorrente();
        if(tavolo.isInizializzazione()){
            if(truppeSelezionate<3&&tavolo.getGiocatoreCorrente().getNumeroTruppe()>0){

                if(tavolo.assegnaUnita(t)){
                    try {
                        Message msg = comunicator.createApplicanceMessage(1, t.getCodice());
                        comunicator.sendMessage(msg);

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
                   
                    Thread.sleep(2000);
                    if((tavolo.getTurno()==(tavolo.getGiocatori().size()-1))&&(corrente.getNumeroTruppe()==0)){
                        tavolo.setInizializzazione(false);
                    }
                    tavolo.passaTurno();
                    Giocatore prossimo=tavolo.getGiocatoreCorrente();
                    this.playerDataListener.updateDatiGiocatore(prossimo.getNome(), prossimo.getNumeroTruppe(), prossimo.getArmateDisposte(), prossimo.getNazioni().size());
                    Message msg=comunicator.createPassesMessage(tavolo.getTurnoSuccessivo());

                    comunicator.sendMessage(msg);
                    new JFrameTurno(prossimo.getID()).setVisible(true);
                    sendRecoveryMessage();

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

        Message msg=this.comunicator.createChatMessage(Tavolo.getInstance().getMyGiocatore().getNome(),to, message);
        try {
            this.comunicator.sendMessage(msg);
        } catch (IOException ex) {
            System.out.println("impossibile inviare msg a "+to+" msg --> "+message);
        }
    }

    public void updatePass(int turno_successivo) {
            Tavolo tavolo=Tavolo.getInstance();

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
                
                timer=new GameTimer(this, GameTimer.ACTION);
                

                timer.start();
            }
            
            this.playerDataListener.updateDatiGiocatore(giocatore.getNome(), giocatore.getNumeroTruppe(), giocatore.getArmateDisposte(), giocatore.getNazioni().size());
            if(tavolo.isTurnoMyGiocatore()){

                this.historyListener.appendActionInHistory(tavolo.getGiocatoreCorrente().getNome()+" ancora "+tavolo.getGiocatoreCorrente().getNumeroTruppe());
                System.out.println("ancora in inizializzazione "+tavolo.isInizializzazione());
            }

            sendRecoveryMessage();


    }

    public   void  passaTurno() throws IOException{
        Tavolo tavolo=locker.acquireTavolo();
        if(!tavolo.isTurnoMyGiocatore()){
            locker.releaseTavolo();
            return;
        }
        
        this.timer.stopTimer();
        int troops=tavolo.getMyGiocatore().getNumeroTruppe();
        if(troops>0){
            this.autoDispose(troops);
        }
        if((!tavolo.isInizializzazione())&&tavolo.isTurnoMyGiocatore()){
            //codice per il recupero carta
            Carta carta=tavolo.recuperaCarta(tavolo.getMyGiocatore());
            if(carta!=null){
                this.cardListener.notifyCard(carta.getCodice(), carta.getTerritorio().getNome());
            }
            tavolo.passaTurno();
            Message msg=this.comunicator.createPassesMessage(tavolo.getTurnoSuccessivo());
            try {
                        //Thread.sleep(3000);
                        this.comunicator.sendMessage(msg);


                    } catch (Exception  ex) {
                        ex.printStackTrace();
                    }
            Giocatore g=tavolo.getGiocatoreCorrente();

    //        new JFrameTurno(g.getID()).setVisible(true);
            this.playerDataListener.updateDatiGiocatore(g.getNome(),g.getNumeroTruppe(),g.getArmateDisposte(),g.getNazioni().size());
        }
        sendRecoveryMessage();
        locker.releaseTavolo();
    }

    public void setChatListener(ChatListener aThis) {
        this.comunicator.addChatListener(aThis);
    }

    public void setCardListener(CardListener aThis) {
       this.cardListener=aThis;
    }
    public void setCardChangeListener(CardChangeListener aThis) {
       this.cardChangeListener=aThis;
    }

    public void notifyReconnectionRequest(int player) {
        reconnectionNeeds[player]=true;
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
            passaTurno();
        } catch (IOException ex) {
           System.err.println("impossibile passare turno");
        }
        locker.releaseTavolo();
        
    }

    private void autoDispose(int troops){
        Tavolo tavolo=Tavolo.getInstance();
        Set<Territorio> set=tavolo.getMyGiocatore().getNazioni();
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
        this.timeoutNotifier.remaingTimeNotify(remaing);
    }

    

    
    

  

}
