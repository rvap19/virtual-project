/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author root
 */
public class ObiettivoClassico extends Obiettivo{
    public static final int OBIETTIVO1=0;
    public static final int OBIETTIVO2=1;
    public static final int OBIETTIVO3=2;
    public static final int OBIETTIVO4=3;
    public static final int OBIETTIVO5=4;
    public static final int OBIETTIVO6=5;
    public static final int OBIETTIVO7=6;
    public static final int OBIETTIVO8=7;
    public static final int OBIETTIVO9=8;
    public static final int OBIETTIVO10=9;
    public static final int OBIETTIVO11=10;
    public static final int OBIETTIVO12=11;
    public static final int OBIETTIVO13=12;
    public static final int OBIETTIVO14=13;



    private final String descrizione1="Conquistare 18 territori presidiandoli con almeno due armate ciascuno";
    private final String descrizione2="Conquistare 24 territori";
    private final String descrizione3="Conquistare la totalita del Nord America e dell Africa";
    private final String descrizione4="Conquistare la totalita del Nord America e dell Oceania";
    private final String descrizione5="Conquistare la totalita dell Asia e del Sud America";
    private final String descrizione6="Conquistare la totalita dell Asia e dell Africa";
    private final String descrizione7="Conquistare la totalita dell Europa, del Sud America e di un terzo continente a scelta";
    private final String descrizione8="Conquistare la totalita dell Europa, dell Oceania e di un terzo continente a scelta";
    private final String descrizione9="Distruggere completamente le armate rosse. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l obiettivo di distruggerle o se l ultima armata viene distrutta da un altro giocatore, l obiettivo diventa conquistare 24 territori.";
    private final String descrizione10="Distruggere completamente le armate gialle. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l obiettivo di distruggerle o se l ultima armata viene distrutta da un altro giocatore, l obiettivo diventa conquistare 24 territori.";
    private final String descrizione11="Distruggere completamente le armate verdi. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l obiettivo di distruggerle o se l ultima armata viene distrutta da un altro giocatore, l obiettivo diventa conquistare 24 territori.";
    private final String descrizione12="Distruggere completamente le armate nere. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l obiettivo di distruggerle o se l ultima armata viene distrutta da un altro giocatore, l obiettivo diventa conquistare 24 territori.";
    private final String descrizione13="Distruggere completamente le armate viola. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l obiettivo di distruggerle o se l ultima armata viene distrutta da un altro giocatore, l obiettivo diventa conquistare 24 territori.";
    private final String descrizione14="Distruggere completamente le armate blu. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l obiettivo di distruggerle o se l ultima armata viene distrutta da un altro giocatore, l obiettivo diventa conquistare 24 territori.";

    private final int NORD_AMERICA=1;
    private final int SUD_AMERICA=2;
    private final int EUROPA=3;
    private final int AFRICA=4;
    private final int ASIA=5;
    private final int OCEANIA=6;

    private String descrizione;
    private int codice;

    public ObiettivoClassico(){

    }

    public ObiettivoClassico(int codice){

        this.codice=codice;

         switch (codice) {
            case 1:   this.descrizione=descrizione1;break;
            case 2: this.descrizione=descrizione2;break;
            case 3:  this.descrizione=descrizione3;break;
            case 4:  this.descrizione=descrizione4;break;
            case 5:  this.descrizione=descrizione5;break;
            case 6:  this.descrizione=descrizione6;break;
            case 7:  this.descrizione=descrizione7;break;
            case 8:  this.descrizione=descrizione8;break;
            case 9:  this.descrizione=descrizione9;break;
            case 10: this.descrizione=descrizione10;break;
            case 11: this.descrizione=descrizione11;break;
            case 12: this.descrizione=descrizione12;break;
            case 13: this.descrizione=descrizione13;break;
            case 14: this.descrizione=descrizione14;break;

        }
    }


    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
           switch (codice) {
            case 1:   this.descrizione=descrizione1;break;
            case 2: this.descrizione=descrizione2;break;
            case 3:  this.descrizione=descrizione3;break;
            case 4:  this.descrizione=descrizione4;break;
            case 5:  this.descrizione=descrizione5;break;
            case 6:  this.descrizione=descrizione6;break;
            case 7:  this.descrizione=descrizione7;break;
            case 8:  this.descrizione=descrizione8;break;
            case 9:  this.descrizione=descrizione9;break;
            case 10: this.descrizione=descrizione10;break;
            case 11: this.descrizione=descrizione11;break;
            case 12: this.descrizione=descrizione12;break;
            case 13: this.descrizione=descrizione13;break;
            case 14: this.descrizione=descrizione14;break;

        }
    }

    public String getDescrizione() {
        return descrizione;
    }

    private boolean controllaObiettivo(Mappa mappa,List<Giocatore> giocatori,Giocatore g){
        int obiettivo=g.getObiettivo().getCodice();

        switch (obiettivo) {
            case 1:  return this.controllaObiettivo1(g);
            case 2:  return this.controllaObiettivo2(g);
            case 3:  return this.controllaObiettivo3(mappa, g);
            case 4:  return this.controllaObiettivo4(mappa, g);
            case 5:  return this.controllaObiettivo5(mappa, g);
            case 6:  return this.controllaObiettivo6(mappa, g);
            case 7:  return this.controllaObiettivo7(mappa, g);
            case 8:  return this.controllaObiettivo8(mappa, g);
            case 9:  return this.controllaObiettivo9(giocatori, g);
            case 10: return this.controllaObiettivo10(giocatori, g);
            case 11: return this.controllaObiettivo11(giocatori, g);
            case 12: return this.controllaObiettivo12(giocatori, g);
            case 13: return this.controllaObiettivo13(giocatori, g);
            case 14: return this.controllaObiettivo14(giocatori, g);
            default: return false;
        }


    }
    private boolean controllaObiettivo1(Giocatore g){
        int numeroTerritoriPresidiati=0;
        Set<Territorio> list=g.getNazioni();
        if(list.size()<18){
            return false;
        }

        Iterator<Territorio> iter=g.getNazioni().iterator();
        Territorio t;

        while(iter.hasNext()){
            t=iter.next();
            if(t.getNumeroUnita()>=2){
                numeroTerritoriPresidiati++;
            }
        }

        if(numeroTerritoriPresidiati>=18){
            return true;
        }

        return false;

    }

    private boolean controllaObiettivo2(Giocatore g){
        return g.getNazioni().size()>=24;
    }

    private boolean controllaObiettivo3(Mappa m,Giocatore g){
        Continente NordAmerica=m.getContinente(NORD_AMERICA);
        Continente Africa=m.getContinente(AFRICA);
        return g.getNazioni().containsAll(NordAmerica.getTerritori())&&g.getNazioni().containsAll(Africa.getTerritori());
    }

     private boolean controllaObiettivo4(Mappa m,Giocatore g){
        Continente NordAmerica=m.getContinente(NORD_AMERICA);
        Continente oceania=m.getContinente(OCEANIA);
        return g.getNazioni().containsAll(NordAmerica.getTerritori())&&g.getNazioni().containsAll(oceania.getTerritori());
    }

      private boolean controllaObiettivo5(Mappa m,Giocatore g){
        Continente asia=m.getContinente(ASIA);
        Continente sudAmerica=m.getContinente(SUD_AMERICA);
        return g.getNazioni().containsAll(asia.getTerritori())&&g.getNazioni().containsAll(sudAmerica.getTerritori());
    }

    private boolean controllaObiettivo6(Mappa m,Giocatore g){
        Continente asia=m.getContinente(ASIA);
        Continente africa=m.getContinente(AFRICA);
        return g.getNazioni().containsAll(asia.getTerritori())&&g.getNazioni().containsAll(africa.getTerritori());
    }

    private boolean controllaObiettivo7(Mappa m,Giocatore g){
        Continente africa=m.getContinente(AFRICA);
        Continente asia=m.getContinente(ASIA);
        Continente nordamerica=m.getContinente(NORD_AMERICA);
        Continente oceania=m.getContinente(OCEANIA);
        Continente sudamerica=m.getContinente(SUD_AMERICA);
        Continente europa=m.getContinente(EUROPA);

        int continentiOccupati=0;
        boolean europaOcc=false;
        boolean sudAmericaOcc=false;

        if(g.getNazioni().containsAll(africa.getTerritori())){
            continentiOccupati++;
        }
        if(g.getNazioni().containsAll(asia.getTerritori())){
            continentiOccupati++;
        }
        if(g.getNazioni().containsAll(nordamerica.getTerritori())){
            continentiOccupati++;
        }
        if(g.getNazioni().containsAll(oceania.getTerritori())){
            continentiOccupati++;
        }
        if(g.getNazioni().containsAll(sudamerica.getTerritori())){

            sudAmericaOcc=true;
        }
        if(g.getNazioni().containsAll(europa.getTerritori())){
            europaOcc=true;
        }

        return europaOcc&&sudAmericaOcc&&continentiOccupati!=0;


    }

    private boolean controllaObiettivo8(Mappa m,Giocatore g){
        Continente africa=m.getContinente(AFRICA);
        Continente asia=m.getContinente(ASIA);
        Continente nordamerica=m.getContinente(NORD_AMERICA);
        Continente oceania=m.getContinente(OCEANIA);
        Continente sudamerica=m.getContinente(SUD_AMERICA);
        Continente europa=m.getContinente(EUROPA);

        int continentiOccupati=0;
        boolean europaOcc=false;
        boolean oceaniaOcc=false;

        if(g.getNazioni().containsAll(africa.getTerritori())){
            continentiOccupati++;
        }
        if(g.getNazioni().containsAll(asia.getTerritori())){
            continentiOccupati++;
        }
        if(g.getNazioni().containsAll(nordamerica.getTerritori())){
            continentiOccupati++;
        }
        if(g.getNazioni().containsAll(oceania.getTerritori())){
            oceaniaOcc=true;
        }
        if(g.getNazioni().containsAll(sudamerica.getTerritori())){

            continentiOccupati++;
        }
        if(g.getNazioni().containsAll(europa.getTerritori())){
            europaOcc=true;
        }

        return europaOcc&&oceaniaOcc&&continentiOccupati!=0;
    }

    private boolean controllaObiettivo9(List<Giocatore> giocatori,Giocatore g){
        Iterator<Giocatore> iter=giocatori.iterator();
        Giocatore current=null;
        boolean trovato=false;

        if(g.getColore()==Giocatore.ROSSO){
            return g.getNazioni().size()>=24;
        }

        while(iter.hasNext()&&!trovato){
            current=iter.next();
            trovato=(current.getColore()==Giocatore.ROSSO);
        }

        if(!trovato){
            return g.getNazioni().size()>=24;
        }
        if(current.getStato()==Giocatore.IN_GIOCO){
            current.setStato(Giocatore.FUORI_GIOCO);
            return current.getNazioni().size()==0;
        }
        return false;

    }

    private boolean controllaObiettivo10(List<Giocatore> giocatori,Giocatore g){
        Iterator<Giocatore> iter=giocatori.iterator();
        Giocatore current=null;
        boolean trovato=false;

        if(g.getColore()==Giocatore.GIALLO){
            return g.getNazioni().size()>=24;
        }

        while(iter.hasNext()&&!trovato){
            current=iter.next();
            trovato=(current.getColore()==Giocatore.GIALLO);
        }

        if(!trovato){
            return g.getNazioni().size()>=24;
        }
        if(current.getStato()==Giocatore.IN_GIOCO){
            current.setStato(Giocatore.FUORI_GIOCO);
            return current.getNazioni().size()==0;
        }

        return false;
    }

    private boolean controllaObiettivo11(List<Giocatore> giocatori,Giocatore g){
        Iterator<Giocatore> iter=giocatori.iterator();
        Giocatore current=null;
        boolean trovato=false;

        if(g.getColore()==Giocatore.VERDE){
            return g.getNazioni().size()>=24;
        }

        while(iter.hasNext()&&!trovato){
            current=iter.next();
            trovato=(current.getColore()==Giocatore.VERDE);
        }

        if(!trovato){
            return g.getNazioni().size()>=24;
        }
        if(current.getStato()==Giocatore.IN_GIOCO){
            current.setStato(Giocatore.FUORI_GIOCO);
            return current.getNazioni().size()==0;
        }

        return false;
    }

    private boolean controllaObiettivo12(List<Giocatore> giocatori,Giocatore g){
        Iterator<Giocatore> iter=giocatori.iterator();
        Giocatore current=null;
        boolean trovato=false;

        if(g.getColore()==Giocatore.NERO){
            return g.getNazioni().size()>=24;
        }

        while(iter.hasNext()&&!trovato){
            current=iter.next();
            trovato=(current.getColore()==Giocatore.NERO);
        }

        if(!trovato){
            return g.getNazioni().size()>=24;
        }
        if(current.getStato()==Giocatore.IN_GIOCO){
            current.setStato(Giocatore.FUORI_GIOCO);
            return current.getNazioni().size()==0;
        }

        return false;
    }

    private boolean controllaObiettivo13(List<Giocatore> giocatori,Giocatore g){
        Iterator<Giocatore> iter=giocatori.iterator();
        Giocatore current=null;
        boolean trovato=false;

        if(g.getColore()==Giocatore.VIOLA){
            return g.getNazioni().size()>=24;
        }

        while(iter.hasNext()&&!trovato){
            current=iter.next();
            trovato=(current.getColore()==Giocatore.VIOLA);
        }

        if(!trovato){
            return g.getNazioni().size()>=24;
        }
        if(current.getStato()==Giocatore.IN_GIOCO){
            current.setStato(Giocatore.FUORI_GIOCO);
            return current.getNazioni().size()==0;
        }

        return false;
    }

    private boolean controllaObiettivo14(List<Giocatore> giocatori,Giocatore g){
        Iterator<Giocatore> iter=giocatori.iterator();
        Giocatore current=null;
        boolean trovato=false;

        if(g.getColore()==Giocatore.BLU){
            return g.getNazioni().size()>=24;
        }

        while(iter.hasNext()&&!trovato){
            current=iter.next();
            trovato=(current.getColore()==Giocatore.BLU);
        }

        if(!trovato){
            return g.getNazioni().size()>=24;
        }
        if(current.getStato()==Giocatore.IN_GIOCO){
            current.setStato(Giocatore.FUORI_GIOCO);
            return current.getNazioni().size()==0;
        }

        return false;
    }

    @Override
    public boolean controllaObiettivo(Giocatore giocatore, Tavolo tavolo) {
        return this.controllaObiettivo(tavolo.getMappa(), tavolo.getGiocatori(), giocatore);
    }

    @Override
    public int getPunteggio(Giocatore giocatore, Tavolo tavolo) {
        int punteggioTerritori=0;
        Iterator<Territorio> iter=giocatore.getNazioni().iterator();
        while(iter.hasNext()){
            punteggioTerritori=punteggioTerritori+iter.next().getPunteggio();
        }
        int punteggioCarte=giocatore.getCarte().size();
        int punteggioTruppe=giocatore.getNumeroTruppe();
        return punteggioTerritori+punteggioCarte/2+punteggioTruppe/10;
    }

    @Override
    public String toString() {
        return "Obiettivo "+this.codice+"\n"+this.descrizione;
    }
}
