/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
 public  class  MessageWaiter extends Thread{
        private VirtualCommunicator communicator;
        private ElectionController electionManager;
        private boolean messageReceived;
        private int sleepTime=45 * 1000 ;
        private int interval=1;

        private AtomicBoolean continueTimer;

        public MessageWaiter(VirtualCommunicator communicator){
            this.communicator=communicator;
            this.continueTimer=new AtomicBoolean(true);
        }

    public ElectionController getElectionManager() {
        return electionManager;
    }

    public void setElectionManager(ElectionController electionManager) {
        this.electionManager = electionManager;
    }

    public boolean isMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(boolean messageReceived) {
        this.messageReceived = messageReceived;
    }

        
        public void stopTimer(){
            this.continueTimer.set(false);
        }
        
        @Override
        public void run() {
            System.out.println("Avvio message waiter ");
            while(continueTimer.get()){
                try {
                    messageReceived=false;
                    
                    for(int i=0;i<interval;i++){
                        this.sleep(sleepTime);
                        System.out.println("Message Waiter ... intervallo "+i+" messaggio ricevuto :: "+messageReceived);
                    }
                    if(!messageReceived){
                        System.out.println("Nessun messaggio ricevuto dal manager .... riconnessione");
                        boolean connectSuccess=communicator.connect();
                        continueTimer.set(connectSuccess);
                        if(connectSuccess){
                            System.out.println("Riconnessione riuscita");
                        }else{
                            System.out.println("rinnessione fallita");
                        }
                    }else{
                        System.out.println("ricevuto dal coordinatore");
                    }
            
                } catch (Exception ex) {
                   ex.printStackTrace();
                }

            }

           
            
                if(!electionManager.isStarted()){
                    try {
                        electionManager.startElection();
                    } catch (IOException ex) {
                        Logger.getLogger(MessageWaiter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            


        }

        

        
    }