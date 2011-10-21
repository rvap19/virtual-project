/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package middle;

import middle.messages.RisikoMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import middle.listener.ElectionEventListener;
import middle.management.advertisement.PipeAdvertisement;
import middle.messages.AckMessage;
import middle.messages.ElectionMessage;
import middle.messages.ElectionRequestMessage;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public abstract class ElectionController implements ElectionEventListener{

    protected ElectionEventListener electionListener;
    protected HashMap<String,PipeAdvertisement> advertisements;
    protected HashMap<String,RisikoPipe> pipes;
    protected PeerGroup peerGroup;
    protected String playerName;
    protected boolean ackReceived;
    protected RisikoMessageGenerator messageBuilder;
    protected boolean started=false;


    protected VirtualCommunicator communicator;
    

    public ElectionController(String playerName,PeerGroup group,HashMap<String,PipeAdvertisement> advertisements){
        this.pipes=new HashMap<String, RisikoPipe>();
        this.advertisements=advertisements;
        this.playerName=playerName;
        this.peerGroup=group;
        ackReceived=false;
        started=false;
        this.messageBuilder=initMessageBuilder();

    }

    public boolean isStarted() {
        return started;
    }

    public ElectionEventListener getElectionListener() {
        return electionListener;
    }

    public void setElectionListener(ElectionEventListener electionListener) {
        this.electionListener = electionListener;
    }

    public VirtualCommunicator getCommunicator() {
        return communicator;
    }

    public void setCommunicator(VirtualCommunicator communicator) {
        this.communicator = communicator;
    }

    
   
    
    public void startElection() throws IOException{
        started=true;
        System.out.println("start election");
        List<Giocatore> giocatori =Tavolo.getInstance().getGiocatori();
        Giocatore myGiocatore=Tavolo.getInstance().getMyGiocatore();

        List<Giocatore> pred=findGiocatoriPrecedenti(giocatori,myGiocatore);
        Iterator<Giocatore> iter=pred.iterator();
        
        boolean send=false;

        while(iter.hasNext()){
            String name=iter.next().getUsername();
            send=send||sendElectionRequestMessages(name);
        }

        if(send){
            for(int i=0;i<6&&!ackReceived;i++)
                try {
                    Thread.sleep(10 * 1000);
                    System.out.println("attesa "+i+" .. nessun ack ricevuto per REQUEST election");
                } catch (InterruptedException ex) {
                   ex.printStackTrace();
                }

              //  closePipes();
        }

        if(ackReceived){
            System.out.println("ack ricevuto ....attedo comunicazione nuovo manager");
            return;
        }

        ElectionMessage message=sendElectionMessages();
        if(message==null){
            System.out.println("Invio messaggio di elezione fallito ...");
            System.out.println("provare a riavviare l 'applicazione");
            System.exit(1);
        }
        
        
        //this.electionListener.notifyElection(message);
                     //closePipes();
        ackReceived=false;
         if(message!=null){
             System.out.println("inviati messaggi di elezione");
             for(int i=0;i<6&&!ackReceived;i++)
              try {
                    Thread.sleep(10 * 1000);
                    System.out.println("attesa "+i+" .. nessun ack ricevuto per ELECTION");
           } catch (InterruptedException ex) {
                   ex.printStackTrace();
           }
            if(!ackReceived){
                System.out.println("Nessun ack ricevuto per ELECTION ...sono il nuovo manager");

            //    this.electionListener.notifyElection(message);
               sendElectionMessages();
               
                
            }
         }else{
            
            System.err.println("rieffettuare il login");
            System.exit(-1);
         }
  
    }

    private boolean sendElectionRequestMessages(String name) throws IOException{

        RisikoPipe pipe=connect(name, this.advertisements.get(name+" Pipe"));
        if(pipe!=null){
            pipes.put(name, pipe);
            ElectionRequestMessage message=this.messageBuilder.generateElectionRequestMSG();
            message.setMSG_ID(0);
                    //new ElectionRequestMessage();
            
            System.out.println("send election request to "+name);
            return pipe.send(message);
        }
        return false;

    }

    private ElectionMessage sendElectionMessages() throws IOException{
        ElectionMessage message=null;
        Iterator<String> iter=this.advertisements.keySet().iterator();
        while(iter.hasNext()){
            String name=iter.next();
            if(!name.equals(playerName+" Pipe")){
                RisikoPipe pipe=connect(name, this.advertisements.get(name));
                if(pipe!=null){
                    pipes.put(name, pipe);
                     message=this.messageBuilder.generateElectionMSG(playerName, Tavolo.getInstance().getTurno()); 
                             //new ElectionMessage(playerName,Tavolo.getInstance().getTurno());
                    System.out.println("send election message to "+name);
                    message.setMSG_ID(0);
                     pipe.send(message);
                }
            }
        }
        return message;

    }

    protected abstract  RisikoPipe connect(String player,PipeAdvertisement adv) ;

    protected abstract RisikoMessageGenerator initMessageBuilder();

  /*  public void pipeMsgEvent(PipeMsgEvent pme) {
        Message msg=pme.getMessage();
        String type=msg.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.TYPE).toString();
        if(type.equals(VirtualRisikoMessage.ACK)){
            System.out.println("ack ricevuto");
            ackReceived=true;
        }
    }*/

    

    private void closePipes() throws IOException{
        System.out.println("closing pipes");
        Iterator<RisikoPipe> jxtaPips=this.pipes.values().iterator();
        while(jxtaPips.hasNext()){
            RisikoPipe x=jxtaPips.next();
            if(x!=null){
                x.close();
            }
        }
    }

    private List<Giocatore> findGiocatoriPrecedenti(List<Giocatore> giocatori, Giocatore myGiocatore) {
        ArrayList<Giocatore> result=new ArrayList<Giocatore>();
        int size=giocatori.size();
        for(int i=myGiocatore.getID()-1;i>=0;i--){
            result.add(giocatori.get(i));
        }
        return result;
    }

    public void notifyRequestElectionMessage(RisikoPipe pipe,ElectionRequestMessage msg) {
       String gamer=msg.playerName();
        AckMessage ack=this.messageBuilder.generateAckMSG(0); 
                //new AckMessage(0);
        try {
            ack.setMSG_ID(0);
            pipe.send(ack);
            if(!this.isStarted()){
                this.startElection();
            }
        } catch (IOException ex) {
            System.err.println("impossibile inviare risposta in notify connection");
        }

        return;
    }

    public void notifyElectionMessage(RisikoPipe pipe,ElectionMessage eMsg) {
        if(communicator.isManagerOnLine()){
            AckMessage ack=messageBuilder.generateAckMSG(0); 
                    //new AckMessage(0);
            try {
                ack.setMSG_ID(0);
                pipe.send(ack);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
            started=false;
        
    }

    public void notifyMessage(RisikoMessage message) {
        if(message.getType().equals(MessageTypes.ACK)){
            System.out.println("ack ricevuto");
            ackReceived=true;
        }
    }

   
}
