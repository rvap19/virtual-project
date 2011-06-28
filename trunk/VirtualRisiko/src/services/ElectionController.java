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
import jxta.communication.VirtualCommunicator;
import jxta.communication.messages.AckMessage;
import jxta.communication.messages.ElectionMessage;
import jxta.communication.messages.ElectionRequestMessage;
import jxta.communication.messages.VirtualRisikoMessage;
import jxta.communication.messages.listener.ElectionListener;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class ElectionController implements PipeMsgListener{

    private ElectionListener electionListener;
    private HashMap<String,PipeAdvertisement> advertisements;
    private HashMap<String,JxtaBiDiPipe> pipes;
    private PeerGroup peerGroup;
    private String playerName;
    private boolean ackReceived;

    private boolean started=false;

    

    public ElectionController(String playerName,PeerGroup group,HashMap<String,PipeAdvertisement> advertisements){
        this.pipes=new HashMap<String, JxtaBiDiPipe>();
        this.advertisements=advertisements;
        this.playerName=playerName;
        this.peerGroup=group;
        ackReceived=false;
        started=false;

    }

    public boolean isStarted() {
        return started;
    }

    public ElectionListener getElectionListener() {
        return electionListener;
    }

    public void setElectionListener(ElectionListener electionListener) {
        this.electionListener = electionListener;
    }

    
    
    public void startElection(boolean messageReceived) throws IOException{
        started=true;
        System.out.println("start election");
        List<Giocatore> giocatori =Tavolo.getInstance().getGiocatori();
        Giocatore myGiocatore=Tavolo.getInstance().getMyGiocatore();

        List<Giocatore> pred=findGiocatoriPrecedenti(giocatori,myGiocatore);
        Iterator<Giocatore> iter=pred.iterator();
        
        boolean send=false;

        while(iter.hasNext()){
            String name=iter.next().getUsername();
            send=sendElectionRequestMessages(name);                       
        }

        if(send){
                try {
                    Thread.sleep(90 * 1000);
                } catch (InterruptedException ex) {
                   ex.printStackTrace();
                }

              //  closePipes();
        }

        ElectionMessage message=null;

        if(!ackReceived&&send||messageReceived||!send){
                    message=sendElectionMessages();
                     //closePipes();
        }
        
        
        if(message!=null&&ackReceived){
            ackReceived=false;
            
            try {
                    Thread.sleep(90 * 1000);
           } catch (InterruptedException ex) {
                   ex.printStackTrace();
           }
            if(!ackReceived){
                this.electionListener.notifyElection(message);
            }
        }else{
            System.err.println("Impossibile connettersi ai partecipanti");
            System.err.println("l'applicazione verra chiusa");
            System.exit(-1);
        }

        started=false;
                System.out.println("XXX");

        
        
       

    }

    private boolean sendElectionRequestMessages(String name) throws IOException{

        JxtaBiDiPipe pipe=connect(name, this.advertisements.get(name+" Pipe"));
        if(pipe!=null){
            pipes.put(name, pipe);
            ElectionRequestMessage message=new ElectionRequestMessage();
            System.out.println("send election request to "+name);
            return this.sendMessage(pipe, message);
        }
        return false;

    }

    private ElectionMessage sendElectionMessages() throws IOException{
        ElectionMessage message=null;
        Iterator<String> iter=this.advertisements.keySet().iterator();
        while(iter.hasNext()){
            String name=iter.next();
            if(!name.equals(playerName+" Pipe")){
                JxtaBiDiPipe pipe=connect(name, this.advertisements.get(name));
                if(pipe!=null){
                    pipes.put(name, pipe);
                     message=new ElectionMessage(playerName,Tavolo.getInstance().getTurno());
                    System.out.println("send election message to "+name);
                     this.sendMessage(pipe, message);
                }
            }
        }
        return message;

    }

     private  JxtaBiDiPipe connect(String player,PipeAdvertisement adv) {
        int counter=0;
        int limit=1;
        JxtaBiDiPipe pipe=new JxtaBiDiPipe();
        System.out.println("trying to connect to "+player);
        while((!pipe.isBound())&&counter<limit){
            try {
                pipe = new JxtaBiDiPipe(peerGroup, adv, 25 * 1000, this, true);
            } catch (Exception ex) {
                System.err.println("impossibie contattare il player "+player);
            }

            counter++;
        }

        if(pipe.isBound()){
            System.err.println("player "+player+" contattato");

         
            
            return pipe;
        }
        System.err.println("impossibile contattare player "+player);
        return null;
    }



    public void pipeMsgEvent(PipeMsgEvent pme) {
        Message msg=pme.getMessage();
        String type=msg.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.TYPE).toString();
        if(type.equals(VirtualRisikoMessage.ACK)){
            System.out.println("ack ricevuto");
            ackReceived=true;
        }
    }

    private boolean sendMessage(JxtaBiDiPipe pipe,Message message) throws IOException{
        StringMessageElement mE=new StringMessageElement(VirtualRisikoMessage.GAMER, playerName, null);
        message.addMessageElement(VirtualRisikoMessage.namespace, mE);
        mE=new StringMessageElement(VirtualRisikoMessage.ID_MSG, "0", null);
        message.addMessageElement(VirtualRisikoMessage.namespace, mE);
        return pipe.sendMessage(message);
    }

    private void closePipes() throws IOException{
        System.out.println("closing pipes");
        Iterator<JxtaBiDiPipe> jxtaPips=this.pipes.values().iterator();
        while(jxtaPips.hasNext()){
            JxtaBiDiPipe x=jxtaPips.next();
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

    public void notifyRequestElectionMessage(JxtaBiDiPipe pipe,ElectionRequestMessage msg) {
       String gamer=msg.playerName();
        AckMessage ack=new AckMessage(0);
        try {
            this.sendMessage(pipe, ack);
            if(!this.isStarted()){
                this.startElection(true);
            }
        } catch (IOException ex) {
            System.err.println("impossibile inviare risposta in notify connection");
        }

        return;
    }

    public void notifyElectionMessage(JxtaBiDiPipe pipe,ElectionMessage eMsg) {
        if(!VirtualCommunicator.instance.isManagerOnLine()){
            AckMessage ack=new AckMessage(0);
            try {
                this.sendMessage(pipe, ack);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            this.electionListener.notifyElection(eMsg);
            started=false;
        }
    }

   
}
