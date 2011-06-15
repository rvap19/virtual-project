/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author root
 */
public class Giocatore  implements Comparable<Giocatore>{
    public static final int ROSSO=0;
    public static final int GIALLO=1;
    public static final int VERDE=2;
    public static final int NERO=3;
    public static final int VIOLA=4;
    public static final int BLU=5;

    public static final int IN_GIOCO=10;
    public static final int FUORI_GIOCO=11;

    private Set<Territorio> nazioniOccupate;
    private Obiettivo obiettivo;
    private Set<Carta> carte;
    private int colore;
    private int stato;
    private int numeroTruppeDisponibili;
    private int id;
    private String username;

    public Giocatore(int id){
        this(id,id);
    }

    public Giocatore(int id,int colore){
        this.stato=IN_GIOCO;
       
        this.nazioniOccupate=new HashSet<Territorio>();
         this.carte=new HashSet<Carta>();
         this.id=id;
         this.colore=colore;
    }

    public int getArmateDisposte(){
        int total=0;
        Iterator<Territorio> iter=this.nazioniOccupate.iterator();
        while(iter.hasNext()){
            total=total+iter.next().getNumeroUnita();
        }
        return total;
    }

    public int getID(){
        return this.id;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    
    public int getNumeroTruppe() {
        return numeroTruppeDisponibili;
    }

    public void setNumeroTruppe(int numeroTruppe) {
        this.numeroTruppeDisponibili = numeroTruppe;
    }

    public Set<Carta> getCarte() {
        return carte;
    }

    public void setCarte(Set<Carta> set) {
        this.carte=set;
    }

    public void addCarta(Carta c){
        this.carte.add(c);
    }

    public void removeCarta(Carta c){
        
         this.carte.remove(c);
    }

    public int getColore() {
        return colore;
    }

    public void setColore(int colore) {
        this.colore = colore;
    }

    public Obiettivo getObiettivo() {
        return obiettivo;
    }

    public void setObiettivo(Obiettivo obiettivo) {
        this.obiettivo = obiettivo;
    }


    public Set<Territorio> getNazioni(){
        return this.nazioniOccupate;
    }



    

    public String getNome(){
         switch (this.colore) {
            case Giocatore.BLU:   return "giocatore blu";
             case Giocatore.GIALLO: return "giocatore giallo";
             case Giocatore.NERO:   return "giocatore nero";
             case Giocatore.ROSSO: return "giocatore rosso";
             case Giocatore.VERDE:return "giocatore verde";
             case Giocatore.VIOLA:return "giocatore viola";
             default:return "giocatore";



        }
    }

    public Carta getCarta(int id){
        Iterator<Carta> iter=this.getCarte().iterator();

        Carta  carta;
        while(iter.hasNext() ){
            carta=iter.next();
            if(carta.getTerritorio().getCodice()==id){
                return carta;
            }
        }
        return null;
    }

    public int compareTo(Giocatore o) {
        if(this.colore==o.getColore()){
            return 0;
        }
        if(this.colore<o.getColore()){
            return -1;
        }
        return 1;
    }

    public void setUsername(String get) {
        this.username=get;
    }

    public String getUsername(){
        return username;
    }
    
}
