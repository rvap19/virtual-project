/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import virtualrisikoii.RecoveryParameter;
import virtualrisikoii.XMapPanel;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.MappaException;
import virtualrisikoii.risiko.Obiettivo;
import virtualrisikoii.risiko.Tavolo;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author root
 */
public class RecoveryUtil {
    private Mappa mappa;
    private List<Obiettivo> obiettivi;
    private XMapPanel panel;
    private GameFactory gameFactory;
    public RecoveryParameter createBackup(){
        Tavolo tavolo=Tavolo.getInstance();
        RecoveryParameter parameter=new RecoveryParameter();
        parameter.setCards_lanch(tavolo.getCardLanch());
        parameter.setDice_lanch(tavolo.getDiceLanch());
        parameter.setIdOccupante(this.getOccupanti(tavolo));
        parameter.setInizializzazione(tavolo.isInizializzazione());
        parameter.setMapName(tavolo.getNameMap());
        parameter.setArmateDisponibili(getNumeroArmateDisponibili(tavolo));
        parameter.setNumeroGiocatori(tavolo.getGiocatori().size());
        parameter.setNumeroTruppe(getNumeroTruppe(tavolo));
        parameter.setObjectives(getObiettivi(tavolo));
        parameter.setSeed_card(tavolo.getCardSeed());
        parameter.setSeed_dice(tavolo.getDiceSeed());
        parameter.setMaxTurns(tavolo.getMaxTurns());
        parameter.setElapsedTurns(tavolo.getTurnElapsed());
        parameter.setTurno(tavolo.getTurno());
        parameter.setPlayersNames(getPlayersNames(tavolo));
        parameter.setTurnoMyGiocatore(tavolo.getTurno());
        parameter.setCarteGiocatori(this.getCarte(tavolo));
        parameter.setPosizioneCarte(this.getPosizioneCarte(tavolo));
        return parameter;
    }

    public void recoveryTable(RecoveryParameter parameter) throws MappaException, ObiettiviException{
         gameFactory=new GameFactory();
        gameFactory.loadGame(parameter.getMapName());

         mappa=gameFactory.getMappa();
         obiettivi=gameFactory.getObiettivi();

        Tavolo tavolo=Tavolo.createInstance(mappa, obiettivi, parameter.getTurno(),parameter.getMaxTurns(), parameter.getNumeroGiocatori(), parameter.getTurnoMyGiocatore(), parameter.getSeed_dice(), 0, parameter.getSeed_card(),parameter.getPlayersNames());
        tavolo.setInizializzazione(parameter.isInizializzazione());
        tavolo.setNameMap(parameter.getMapName());
        tavolo.setTurnElapsed(parameter.getElapsedTurns());
        int diceLanch=tavolo.getDiceLanch();
        int limit=parameter.getDice_lanch();
        System.out.println("allineamento dadi");
        for(int i=diceLanch;i<limit;i++){
            System.out.print(tavolo.lanciaDado()+" - ");
        }System.out.println();

         limit=parameter.getCards_lanch();
        for(int i=0;i<limit;i++){
            tavolo.getNuovaCartaID();
        }

        Iterator<Giocatore> iter=tavolo.getGiocatori().iterator();
        while(iter.hasNext()){
            iter.next().getNazioni().clear();
        }
        setOccupanti(tavolo,parameter.getIdOccupante());
        setNumeroTruppe(tavolo, parameter.getNumeroTruppe());
        setNumeroArmateDisponibili(tavolo,parameter.getArmateDisponibili());
        setCarte(parameter.getCarteGiocatori(),parameter.getPosizioneCarte() , tavolo);
        
       
    }

    private int[] getObiettivi(Tavolo tavolo){
        List<Giocatore> giocatori=tavolo.getGiocatori();
        int[] obiettivi=new int[giocatori.size()];

        for(int i=0;i<obiettivi.length;i++){
            obiettivi[i]=giocatori.get(i).getObiettivo().getCodice();
        }

        return obiettivi;
    }

    private int[] getCarte(Tavolo tavolo){
        int size=tavolo.getMappa().getNazioni().length;
        int[] carte=new int[size+2];

        List<Giocatore> giocatori=tavolo.getGiocatori();
        int numGiocatori=giocatori.size();
        for(int i=0;i<numGiocatori;i++){
            Giocatore g=giocatori.get(i);
            Iterator<Carta> iter=g.getCarte().iterator();
            while(iter.hasNext()){
                Carta current=iter.next();
                carte[current.getTerritorio().getCodice()]=g.getID();
            }
        }

        Iterator<Carta> iter=tavolo.getCarte().iterator();
        while(iter.hasNext()){
            Carta current=iter.next();
            carte[current.getTerritorio().getCodice()]=-1;
        }
        return carte;
    }

    private void setCarte(int[]carte,int[] posizioni,Tavolo tavolo){
        List<Carta> listCarte=tavolo.getCarte();
        int numCarte=listCarte.size();

        List<Giocatore> giocatori=tavolo.getGiocatori();
        Giocatore giocatore;
        int idGiocatore;
        for(int i=0;i<numCarte;i++){
            idGiocatore=carte[i];
            if(idGiocatore>=0){
                giocatore=giocatori.get(carte[i]);
                giocatore.addCarta(listCarte.get(i));
            }

        }

        ArrayList<Carta> newList=new ArrayList<Carta>(numCarte);
        for(int i=0;i<numCarte;i++){
            newList.add(null);
        }

        for(int i=0;i<numCarte;i++){
            int position=posizioni[i];
            if(position>=0){
                newList.set(position, listCarte.get(i));

            }
        }

        tavolo.setCarte(newList);


    }

    

    private int[] getPosizioneCarte(Tavolo tavolo){
        int size=tavolo.getMappa().getNazioni().length;
        int[] posizioniCarte=new int[size+2];

        List<Giocatore> giocatori=tavolo.getGiocatori();
        int numGiocatori=giocatori.size();
        for(int i=0;i<numGiocatori;i++){
            Giocatore g=giocatori.get(i);
            Iterator<Carta> iter=g.getCarte().iterator();
            while(iter.hasNext()){
                Carta current=iter.next();
                posizioniCarte[current.getTerritorio().getCodice()]=-1;
            }
        }

        Iterator<Carta> iter=tavolo.getCarte().iterator();
        int pos=0;
        while(iter.hasNext()){
            Carta current=iter.next();
            posizioniCarte[current.getTerritorio().getCodice()]=pos;
            pos++;
        }
        return posizioniCarte;
    }

    private int[] getOccupanti(Tavolo tavolo){
        Mappa mappa=tavolo.getMappa();
        Territorio[] territori=mappa.getNazioni();
        int[] occupanti=new int[territori.length];

        for(int i=0;i<territori.length;i++){
            occupanti[i]=territori[i].getOccupante().getColore();
        }

        return occupanti;
    }

    private void setOccupanti(Tavolo tavolo,int[] occupanti){
        Mappa mappa=tavolo.getMappa();
        List<Giocatore> giocatori=tavolo.getGiocatori();
        System.out.println("stampa occupazione territori");
        for(int i=0;i<occupanti.length;i++){
            mappa.getTerritorio(i).setOccupante(giocatori.get(occupanti[i]));
            System.out.println(" Territorio "+mappa.getTerritorio(i).getNome()+" occupato da "+giocatori.get(occupanti[i]).getUsername());
        }
    }

    private int[] getNumeroTruppe(Tavolo tavolo){
        Mappa mappa=tavolo.getMappa();
        Territorio[] territori=mappa.getNazioni();
        int[] numeroTruppe=new int[territori.length];

        for(int i=0;i<territori.length;i++){
            numeroTruppe[i]=territori[i].getNumeroUnita();
        }
        return numeroTruppe;
    }

    private void setNumeroTruppe(Tavolo tavolo,int[] numeroTruppe){
        Mappa mappa=tavolo.getMappa();
        Territorio[] territorio=mappa.getNazioni();
        for(int i=0;i<territorio.length;i++){
            territorio[i].setNumeroUnita(numeroTruppe[i]);
        }
    }

    private void setNumeroArmateDisponibili(Tavolo tavolo,int[] armate){
        List<Giocatore> giocatori=tavolo.getGiocatori();
        for(int i=0;i<armate.length;i++){
            giocatori.get(i).setNumeroTruppe(armate[i]);
        }
    }

    public int[] getNumeroArmateDisponibili(Tavolo tavolo){
        List<Giocatore> giocatori=tavolo.getGiocatori();
        int[] armate=new int[giocatori.size()];

        for(int i=0;i<armate.length;i++){
            armate[i]=giocatori.get(i).getNumeroTruppe();
        }
        return armate;
    }

    public List<String> getPlayersNames(Tavolo tavolo){
        Iterator<Giocatore> giocatori=tavolo.getGiocatori().iterator();
        List<String> names=new ArrayList<String>();
        while(giocatori.hasNext()){
            names.add(giocatori.next().getUsername());
        }
        return names;

    }

    public Mappa getMappa() {
        return mappa;
    }

    public List<Obiettivo> getObiettivi() {
        return obiettivi;
    }

    public XMapPanel getPanel() {
        gameFactory.loadMapPanel();
             panel = gameFactory.getMapPanel();
        return panel;
    }

    

}
