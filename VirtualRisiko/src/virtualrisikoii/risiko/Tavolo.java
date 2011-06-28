/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.ArrayList;
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
    private Random nextCard;

    private String nameMap;

    private boolean inizializzazione;

    private Attacco lastAttacco;

    private int diceLanch;
    private int cardLanch;

    private int cardSeed;
    private int diceSeed;


    private static Tavolo instance=null;
    private  Tavolo(){

    }


    public static Tavolo getInstance(){
        return instance;
    }

    public static Tavolo createInstance(Mappa mappa,List<Obiettivo> obiettivi,int turno,int numeroGiocatori,int myTurno,int seed_dice,int seed_region,int seed_cards,List<String> playersnames) throws MappaException{
        instance=new Tavolo();
        instance.initTavolo(mappa, obiettivi, turno, numeroGiocatori, myTurno, seed_dice, seed_region, seed_cards,playersnames);
        return instance;
    }

    public int getCardSeed() {
        return cardSeed;
    }

    public int getDiceSeed() {
        return diceSeed;
    }

    
    private void initTavolo(Mappa mappa,List<Obiettivo> obiettivi,int turno,int numeroGiocatori,int myTurno,int seed_dice,int seed_region,int seed_cards,List<String> names) throws MappaException{
        this.diceSeed=seed_dice;
        System.out.println("seed dice "+seed_dice);
        this.cardSeed=seed_cards;
        dado=new Random(seed_dice);
        this.mappa=mappa;
        this.obiettivi=obiettivi;
        this.turno=turno;
        this.numeroGiocatori=numeroGiocatori;
        this.initGiocatori(numeroGiocatori,names,seed_dice);
        this.assegnaTerritori(seed_region);
        this.initCarte(seed_cards);
        this.myGiocatore=giocatori.get(myTurno);
        inizializzazione=true;
        this.diceLanch=0;
        this.cardLanch=0;
    }

    public String getNameMap() {
        return nameMap;
    }

    public void setNameMap(String nameMap) {
        this.nameMap = nameMap;
    }

    
    public boolean isInizializzazione(){
       
        return inizializzazione;
    }

    public void setInizializzazione(boolean init){
        this.inizializzazione=init;
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
        if(!this.isInizializzazione()){
            assegnaRinforziSuTerritori(this.getGiocatoreCorrente());
        }
        lastAttacco=null;
    }



    private void assegnaRinforziSuTerritori(Giocatore g){
        
        g.setNumeroTruppe(g.getNumeroTruppe()+Rinforzo.getRinforzo(g));
    }
    
    public Attacco preparaAttacco(Territorio da,Territorio a){

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

            
            
        
        return attacco;

    }

    public Attacco eseguiAttacco(Attacco attacco){
        
        Territorio daTerritorio=attacco.getDaTerritorio();
        Territorio aTerritorio=attacco.getaTerritorio();

        Giocatore attaccante=daTerritorio.getOccupante();
        Giocatore difensore=aTerritorio.getOccupante();

        int att=attacco.getNumeroTruppeInAttacco();
        int avv=attacco.getNumeroTruppeAvversario();

        daTerritorio.setNumeroUnita(daTerritorio.getNumeroUnita()-attacco.getNumeroTruppe());
        aTerritorio.setNumeroUnita(aTerritorio.getNumeroUnita()-avv);

        attacco.setPunteggio(this.lanciaDadi(att));
        attacco.setPunteggioAvversario(this.lanciaDadi(avv));

        
        attacco.eseguiAzione();
        if(attacco.isVittoria()){
            this.lastAttacco=attacco;
            if(difensore.getNazioni().size()==0){
                difensore.setStato(Giocatore.FUORI_GIOCO);
                attaccante.getCarte().addAll(difensore.getCarte());
                difensore.getCarte().clear();
            }
        }
        


        return attacco;

        
        
    }


    public Spostamento preparaSpostamento(Territorio da,Territorio a){
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
        
        
        return s;

    }



    public void eseguiSpostamento(Spostamento s){
        s.eseguiAzione();
        
    }

    public Carta recuperaCarta(Giocatore giocatore){
        if(this.getGiocatoreCorrente()!=giocatore){
            return null;
        }
        if(this.lastAttacco==null){
            return null;
        }
        if(this.lastAttacco.getGiocatore()!=giocatore){
            return null;
        }

        Carta carta=null;
        if(lastAttacco.isVittoria()){
            if(this.carte.size()>0){
                 carta=this.carte.remove(this.getNuovaCartaID());
                 
                 lastAttacco.getGiocatore().getCarte().add(carta);
            }
            lastAttacco=null;
           
            return carta;
        }
        return null;
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
                
                next=random.nextInt(territori.length);
            }

            Territorio territorioCorrente=territori[next];
            Giocatore giocatore=this.giocatori.get(giocatoreCorrente);
            occupati[next]=true;
            territoriDisponibili--;
            giocatoreCorrente=(giocatoreCorrente+1)%numeroGiocatori;
            giocatore.getNazioni().add(territorioCorrente);
            territorioCorrente.setOccupante(giocatore);
            territorioCorrente.setNumeroUnita(1);
            giocatore.setNumeroTruppe(giocatore.getNumeroTruppe()-1);
            
        }

    }


    private void initGiocatori(int numeroGiocatori,List<String> names,int seed){
        if(numeroGiocatori<minGiocatori){
            System.out.println("Impossibile avviare gioco ...numero giocatori minimo :"+minGiocatori);
            System.exit(-1);
        }

        if(numeroGiocatori>maxGiocatori){
            System.out.println("Impossibile avviare gioco ...numero giocatori massimo :"+maxGiocatori);
            System.exit(-1);
        }
       giocatori=new ArrayList<Giocatore>();
      

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
           g.setUsername(names.get(i));
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
        this.nextCard=new Random(seed);
        this.carte=new ArrayList<Carta>();
        Territorio[] territori=mappa.getNazioni();
        int numTerritori=territori.length/3;
        int limit=numTerritori;
        int tipo=Carta.CANNONE;

        int i=0;
        for(i=0;i<numTerritori;i++){
            Carta c=new Carta(tipo);
            c.setTerritorio(territori[i]);
            carte.add(c);
        }

        tipo=Carta.CAVALIERE;
        limit=numTerritori*2;
        for( i=numTerritori;i<limit;i++){
            Carta c=new Carta(tipo);
            c.setTerritorio(territori[i]);
            carte.add(c);
        }

        tipo=Carta.FANTE;
        limit=numTerritori*3;
        for( i=numTerritori*2;i<limit;i++){
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
            risultato[i]=lanciaDado();
        }
        return risultato;
    }

    public int lanciaDado(){
        int result=dado.nextInt(6)+1;
        this.diceLanch++;
        return result;
    }

    public int getCardLanch() {
        return cardLanch;
    }

    public int getDiceLanch() {
        return diceLanch;
    }

    

    public int getNuovaCartaID(){
        int id=nextCard.nextInt(carte.size());
        this.cardLanch++;
        return id;
    }

  /*  public void avviaGioco() {
        this.turno=0;

        assegnaRinforziSuTerritori(this.getGiocatoreCorrente());

    }*/

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

    public int getTurno(){
        return turno;
    }

    public List<Carta> getCarte() {
        return this.carte;
    }

    public boolean existLastAttack() {
        return lastAttacco!=null;
    }


   

}
