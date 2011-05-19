/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;



/**
 *
 * @author root
 */
public  class Tavolo {
    private Mappa mappa;
    private List<Carta> carte;
    private List<Giocatore> giocatori;
    private Giocatore myGiocatore;
    private int numeroGiocatori;
    private int turno;
    private int minGiocatori=3;
    private int maxGiocatori=6;
    private List<Obiettivo> obiettivi;
    private Random dado;

    private boolean inizializzazione;

    private Attacco lastAttacco;



    private static Tavolo instance=null;
    private  Tavolo(){

    }

    public static Tavolo getInstance(){
        return instance;
    }

    public static Tavolo createInstance(Mappa mappa,List<Obiettivo> obiettivi,int turno,int numeroGiocatori,int myTurno,int seed_dice,int seed_region,int seed_cards) throws MappaException{
        instance=new Tavolo();
        instance.initTavolo(mappa, obiettivi, turno, numeroGiocatori, myTurno, seed_dice, seed_region, seed_cards);
        return instance;
    }

    private void initTavolo(Mappa mappa,List<Obiettivo> obiettivi,int turno,int numeroGiocatori,int myTurno,int seed_dice,int seed_region,int seed_cards) throws MappaException{
        dado=new Random(seed_dice);
        this.mappa=mappa;
        this.obiettivi=obiettivi;
        this.turno=turno;
        this.numeroGiocatori=numeroGiocatori;
        this.initGiocatori(numeroGiocatori,seed_dice);
        this.assegnaTerritori(seed_region);
        this.initCarte(seed_cards);
        this.myGiocatore=giocatori.get(myTurno);
        inizializzazione=true;
    }

    public boolean isInizializzazione(){
        if(!inizializzazione){
            return false;
        }
        boolean hasTruppe=false;
        for(int i=0;i<this.numeroGiocatori;i++){
            hasTruppe=hasTruppe||(this.giocatori.get(i).getNumeroTruppe()>0);
        }

        inizializzazione=inizializzazione&&hasTruppe;
        return inizializzazione;
    }

    public boolean isTurno(Giocatore g){
        return giocatori.get(turno)==g;
    }

    public List<Giocatore> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(List<Giocatore> giocatori) {
        this.giocatori = giocatori;
    }
 
    public Giocatore getGiocatoreCorrente(){
        return giocatori.get(turno);
    }

    public boolean isTurnoMyGiocatore(){
        return getGiocatoreCorrente()==this.myGiocatore;
    }
    
    public void passaTurno(){
        turno=(turno+1)%this.numeroGiocatori;
        while(getGiocatoreCorrente().getStato()==Giocatore.FUORI_GIOCO){
            turno=(turno+1)%this.numeroGiocatori;
        }
        if(!inizializzazione){
            assegnaRinforziSuTerritori(this.getGiocatoreCorrente());
        }
    }

    private void assegnaRinforziSuTerritori(Giocatore g){
        
        g.setNumeroTruppe(g.getNumeroTruppe()+Rinforzo.getRinforzo(g));
    }
    
    public Attacco preparaAttacco(Territorio da,Territorio a,int numeroTruppe){

        if(this.giocatori.get(turno)!=da.getOccupante()){
            return null;
        }
        if(!da.getConfinanti().contains(a)){
            return null;
        }
        Attacco attacco=new Attacco();
        attacco.setGiocatore(da.getOccupante());

        if(da.getOccupante()==a.getOccupante()){
            return null;
        }
       
            attacco.setTerritori(da, a);
            int truppe=3;
            if(numeroTruppe>truppe){
                numeroTruppe=truppe;
            }
            attacco.setNumeroTruppe(numeroTruppe);
        
        return attacco;

    }

    public Attacco eseguiAttacco(Attacco attacco){
        
        Territorio daTerritorio=attacco.getDaTerritorio();
        Territorio aTerritorio=attacco.getaTerritorio();

        Giocatore attaccante=daTerritorio.getOccupante();
        Giocatore difensore=aTerritorio.getOccupante();

        int att=attacco.getNumeroTruppe();
        int avv=attacco.getNumeroTruppeAvversario();

        daTerritorio.setNumeroUnita(daTerritorio.getNumeroUnita()-att);
        aTerritorio.setNumeroUnita(aTerritorio.getNumeroUnita()-avv);

        attacco.setPunteggio(this.lanciaDadi(att));
        attacco.setPunteggioAvversario(this.lanciaDadi(avv));

        
        attacco.eseguiAzione();
        if(attacco.isVittoria()){
            this.lastAttacco=attacco;
            if(difensore.getNazioni().size()==0){
                difensore.setStato(Giocatore.FUORI_GIOCO);
                Iterator<Carta> iter=difensore.getCarte().iterator();
                while(iter.hasNext()){
                    Carta c=iter.next();
                    difensore.removeCarta(c);
                    attaccante.addCarta(c);
                }
            }
        }


        return attacco;

        
        
    }


    public Spostamento preparaSpostamento(Territorio da,Territorio a, int numeroTruppe){
        if(this.giocatori.get(turno)!=da.getOccupante()){
            return null;
        }

        if(!da.getConfinanti().contains(a)){
            return null;
        }
        Spostamento s=new Spostamento();
        s.setGiocatore(da.getOccupante());
       
        if(da.getOccupante()!=a.getOccupante()){
            return null;
        }
       
            s.setTerritori(da, a);
            s.setNumeroTruppe(numeroTruppe);
        
        return s;

    }



    public void eseguiSpostamento(Spostamento s){
        s.eseguiAzione();
        this.passaTurno();
    }

    public boolean recuperaCarta(Giocatore giocatore){
        if(this.getGiocatoreCorrente()!=giocatore){
            return false;
        }
        if(this.lastAttacco==null){
            return false;
        }
        if(this.lastAttacco.getGiocatore()!=giocatore){
            return false;
        }


        if(lastAttacco.isVittoria()){
            if(this.carte.size()>0){
                Carta carta=this.carte.remove(0);
                 lastAttacco.getGiocatore().getCarte().add(carta);
            }
            lastAttacco=null;
            this.passaTurno();
            return true;
        }
        return false;
    }

    public boolean assegnaRinforzi(Giocatore g,Carta c1,Carta c2,Carta c3){
        if(this.giocatori.get(turno)!=g){
            return false;
        }
        int rinforzo=0;
        rinforzo=Rinforzo.getRinfornzo(g, c1, c2, c3, mappa);
        if(rinforzo==0){
            return false;
        }

        g.setNumeroTruppe(g.getNumeroTruppe()+rinforzo);

        
        return true;

    }

    public void rimuoviCarteDaGiocatore(Giocatore g,Carta c1,Carta c2,Carta c3){
        g.getCarte().remove(c1);
        g.getCarte().remove(c2);
        g.getCarte().remove(c3);

        this.carte.add(c1);
        this.carte.add(c2);
        this.carte.add(c3);
    }
    

    public int calcolaRinforzi(Giocatore g,Carta c1,Carta c2,Carta c3){
        return Rinforzo.getRinfornzo(g, c1, c2, c3, mappa);
    }

    public boolean assegnaUnita(int unita,Territorio territorio){
        Giocatore g=territorio.getOccupante();
        if(this.giocatori.get(turno)!=territorio.getOccupante()){
            return false;
        }
        
        if(unita>territorio.getOccupante().getNumeroTruppe()){
            return false;
        }

        g.setNumeroTruppe(g.getNumeroTruppe()-unita);
        territorio.setNumeroUnita(territorio.getNumeroUnita()+unita);
        return true;
    }

    public boolean assegnaUnita(Territorio t){
        return this.assegnaUnita(1,t);
    }




    private void assegnaTerritori(int seed){
        int giocatoreCorrente=0;
        Territorio[] territori=mappa.getNazioni();
        boolean[] occupati=new boolean[territori.length];
        for(int i=0;i<occupati.length;i++){
            occupati[i]=false;
        }

        int territoriDisponibili=territori.length;
        Random random=new Random(seed);
        while(territoriDisponibili>0){
            int next=random.nextInt(territori.length);
            while(occupati[next]){
                System.out.println(next+" <-- > ");
                next=random.nextInt(territori.length);
            }

            Territorio territorioCorrente=territori[next];
            Giocatore giocatore=this.giocatori.get(giocatoreCorrente);
            occupati[next]=true;
            territoriDisponibili--;
            giocatoreCorrente=(giocatoreCorrente+1)%numeroGiocatori;
            giocatore.addNazione(territorioCorrente);
            territorioCorrente.setOccupante(giocatore);
            territorioCorrente.setNumeroUnita(1);
            giocatore.setNumeroTruppe(giocatore.getNumeroTruppe()-1);
            
        }

    }


    private void initGiocatori(int numeroGiocatori,int seed){
       giocatori=new ArrayList<Giocatore>();
       if(numeroGiocatori<this.minGiocatori){
           numeroGiocatori=minGiocatori;
       }else if(numeroGiocatori>maxGiocatori){
           numeroGiocatori=maxGiocatori;
       }

       Giocatore g;
       int truppe=0;
       if(numeroGiocatori==3){
           truppe=35;
       }else if(numeroGiocatori==4){
           truppe=30;
       }else if(numeroGiocatori==5){
           truppe=25;
       }else if(numeroGiocatori==6){
           truppe=20;
       }
       for(int i=0;i<numeroGiocatori;i++){
           g=new Giocatore(i);
           g.setNumeroTruppe(truppe);
           giocatori.add(g);
       }

       Random random=new Random(seed);
       for(int i=0;i<this.giocatori.size();i++){
           int obiettivoIndex=random.nextInt(this.obiettivi.size());
           Obiettivo obiettivo=obiettivi.get(obiettivoIndex);
           this.obiettivi.remove(obiettivoIndex);
           this.giocatori.get(i).setObiettivo(obiettivo);
       }

    }

    private void initCarte(int seed){
        this.carte=new ArrayList<Carta>();
        Territorio[] territori=mappa.getNazioni();
        int numTerritori=territori.length/3;
        int limit=numTerritori;
        int tipo=Carta.CANNONE;

        for(int i=0;i<numTerritori;i++){
            Carta c=new Carta(tipo);
            c.setTerritorio(territori[i]);
            carte.add(c);
        }

        tipo=Carta.CAVALIERE;
        limit=numTerritori*2;
        for(int i=numTerritori;i<limit;i++){
            Carta c=new Carta(tipo);
            c.setTerritorio(territori[i]);
            carte.add(c);
        }

        tipo=Carta.FANTE;
        limit=numTerritori*3;
        for(int i=numTerritori*2;i<limit;i++){
            Carta c=new Carta(tipo);
            c.setTerritorio(territori[i]);
            carte.add(c);
        }

        carte.add(new Carta(Carta.JOLLY));
        carte.add(new Carta(Carta.JOLLY));




    }

    public boolean controllaObiettivoGiocatore(Giocatore giocatore){
        return giocatore.getObiettivo().controllaObiettivo(giocatore, this);

    }

    public int getPunteggioGiocatore(Giocatore giocatore){
        return giocatore.getObiettivo().getPunteggio(giocatore, this);
    }
    
    


    public Mappa getMappa(){
        return this.mappa;
    }


    public int[] lanciaDadi(int numeroDadi){
        int[] risultato=new int[numeroDadi];
        for(int i=0;i<numeroDadi;i++){
            risultato[i]=dado.nextInt(6)+1;
        }
        return risultato;
    }

    public void avviaGioco() {
        this.turno=0;
        assegnaRinforziSuTerritori(this.getGiocatoreCorrente());

    }

    public int getTurnoSuccessivo() {
        int newTurno=(turno+1)%this.numeroGiocatori;
        while(getGiocatoreCorrente().getStato()==Giocatore.FUORI_GIOCO){
            newTurno=(newTurno+1)%this.numeroGiocatori;
        }
        return newTurno;
    }

    public Giocatore getMyGiocatore() {
        return this.myGiocatore;
    }

   

}