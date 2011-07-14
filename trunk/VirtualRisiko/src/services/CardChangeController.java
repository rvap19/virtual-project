/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jxta.communication.VirtualCommunicator;
import jxta.communication.messages.ChangeCardMessage;
import net.jxta.endpoint.Message;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.Rinforzo;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class CardChangeController {
    private Giocatore giocatore;

    private List<Carta> selezionate;
    private List<Carta> cavalieri;
    private List<Carta> fanti;
    private List<Carta> jolly;
    private List<Carta> cannoni;

    private Mappa mappa;

    public boolean jollyAvailable;

    public CardChangeController (){

    }

    public boolean isJollyAvailable() {
        this.jollyAvailable=this.hasJolly();
        return jollyAvailable;
    }

    public void setJollyAvailable(boolean jollyAvailable) {
        this.jollyAvailable = jollyAvailable;
    }

    
    public Mappa getMappa() {
        return mappa;
    }

    public void setMappa(Mappa mappa) {
        this.mappa = mappa;
    }



    public Giocatore getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore=giocatore;

        cavalieri=new ArrayList<Carta>();
       fanti=new ArrayList<Carta>();
       jolly=new ArrayList<Carta>();
       cannoni=new ArrayList<Carta>();
       selezionate=new ArrayList<Carta>();
      

       Iterator<Carta> iter=this.giocatore.getCarte().iterator();
       while(iter.hasNext()){
           Carta current=iter.next();
           if(current.getCodice()==Carta.CANNONE){
               cannoni.add(current);
           }else if (current.getCodice()==Carta.CAVALIERE){
               cavalieri.add(current);
           }else if(current.getCodice()==Carta.FANTE){
               fanti.add(current);
           }else{
               jolly.add(current);
           }
       }
       
    }

    
    public List<Carta> getCarte(int type){
        if(type==Carta.CANNONE){
               return cannoni;
        }else if (type==Carta.CAVALIERE){
               return cavalieri;
        }else if(type==Carta.FANTE){
               return fanti;
        }else{
               return jolly;
        }
    }

    public int getNumeroCarteDisponibili(int tipo){
        if(tipo==Carta.FANTE){
            return this.fanti.size();
        }else if(tipo==Carta.CANNONE){
            return this.cannoni.size();
        }else if(tipo==Carta.CAVALIERE){
            return this.cavalieri.size();
        }else if(tipo==Carta.JOLLY){
            return this.jolly.size();
        }
        return -1;
    }

  public boolean selectCarta(Carta c){
      if(this.selezionate.size()<3){
          if(!this.selezionate.contains(c)){
              this.selezionate.add(c);
              
              return true;
          }
      }
      return false;
  }

 

  

    public List<Carta> getSelectedCards(){
        return this.selezionate;
    }


    

    private void removeCartaFromList(Carta c) {
        int tipo=c.getCodice();
        if(tipo==Carta.FANTE){
            this.fanti.remove(c);
        }else if(tipo==Carta.CANNONE){
             this.cannoni.remove(c);
        }else if(tipo==Carta.CAVALIERE){
             this.cavalieri.remove(c);
        }else if(tipo==Carta.JOLLY){
             this.jolly.remove(c);
        }
        
    }

    public List<Carta> getCannoni() {
        return cannoni;
    }

    public List<Carta> getCavalieri() {
        return cavalieri;
    }

    public List<Carta> getFanti() {
        return fanti;
    }

    public List<Carta> getJolly() {
        return jolly;
    }

    public boolean hasJolly(){
        return this.jolly.size()>0;
    }



    public void ottieniRinforzi(Carta c1,Carta c2,Carta c3){
        int rinforzi=this.valutaRinforzo(c1, c2, c3);
        this.removeCartaFromList(c1);
        this.removeCartaFromList(c2);
        this.removeCartaFromList(c3);
        this.giocatore.setNumeroTruppe(this.giocatore.getNumeroTruppe()+rinforzi);
        Set<Carta> carte=this.giocatore.getCarte();
        carte.remove(c1);
        carte.remove(c2);
        carte.remove(c3);
        List<Carta> list=Tavolo.getInstance().getCarte();
        list.add(c1);
        list.add(c2);
        list.add(c3);

        VirtualCommunicator communicator=VirtualCommunicator.getInstance();
        Message msg=new ChangeCardMessage(c1.getTerritorio().getCodice(), c2.getTerritorio().getCodice(), c3.getTerritorio().getCodice());
        try {
            communicator.sendMessage(msg,false);
        } catch (IOException ex) {
            System.out.println("Impossibile inviare messaggio di change card");
        }
    }

    

    public int valutaRinforzo(Carta c1,Carta c2,Carta c3){

            return Rinforzo.getRinfornzo(giocatore, c1, c2, c3, mappa);
      
    }

    

    

}
