/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.MappaException;
import virtualrisikoii.risiko.Rinforzo;
import virtualrisikoii.risiko.Territorio;

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
        /*
        Carta c1=new Carta(Carta.CANNONE);
        c1.setTerritorio(new Territorio(1, "Territori del nord ovest"));
        Carta c2=new Carta(Carta.CAVALIERE);
        c2.setTerritorio(new Territorio(1, "Giappone"));
        Carta c3=new Carta(Carta.FANTE);
        c3.setTerritorio(new Territorio(1, "Madagascar"));
        Carta c4=new Carta(Carta.JOLLY);
        c4.setTerritorio(new Territorio(1, ""));
        giocatore.addCarta(c1);
        giocatore.addCarta(c2);
        giocatore.addCarta(c3);
        giocatore.addCarta(c4);


         c1=new Carta(Carta.CANNONE);
         c1.setTerritorio(new Territorio(1, "Territori del sud est"));
         c2=new Carta(Carta.CAVALIERE);
         c2.setTerritorio(new Territorio(1, "cina"));
         c3=new Carta(Carta.FANTE);
         c3.setTerritorio(new Territorio(1, "Europa Meridionale"));
         c4=new Carta(Carta.JOLLY);
         c4.setTerritorio(new Territorio(1, ""));
        giocatore.addCarta(c1);
        giocatore.addCarta(c2);
        giocatore.addCarta(c3);
        giocatore.addCarta(c4);

        c1=new Carta(Carta.CANNONE);
         c1.setTerritorio(new Territorio(1, "Territori del sud sud est"));
         c2=new Carta(Carta.CAVALIERE);
         c2.setTerritorio(new Territorio(1, "cina express"));
         c3=new Carta(Carta.FANTE);
         c3.setTerritorio(new Territorio(1, "Europa Settentrionale"));
         giocatore.addCarta(c1);
        giocatore.addCarta(c2);
        giocatore.addCarta(c3);

         

        c1=new Carta(Carta.CANNONE);
         c1.setTerritorio(new Territorio(1, "Territori del nord est"));
         c2=new Carta(Carta.CAVALIERE);
         c2.setTerritorio(new Territorio(1, "mongolia"));
         c3=new Carta(Carta.FANTE);
         c3.setTerritorio(new Territorio(1, "oceania orientale"));
         giocatore.addCarta(c1);
        giocatore.addCarta(c2);
        giocatore.addCarta(c3);

         

        c1=new Carta(Carta.CANNONE);
         c1.setTerritorio(new Territorio(1, "africa del nord"));
         c2=new Carta(Carta.CAVALIERE);
         c2.setTerritorio(new Territorio(1, "afirce del sud"));
         c3=new Carta(Carta.FANTE);
         c3.setTerritorio(new Territorio(1, "ucraina"));
         cavalieri.add(c2);
        fanti.add(c3);
        
        cannoni.add(c1);

       */

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
    }

    

    public int valutaRinforzo(Carta c1,Carta c2,Carta c3){

            return Rinforzo.getRinfornzo(giocatore, c1, c2, c3, mappa);
      
    }

    

}
