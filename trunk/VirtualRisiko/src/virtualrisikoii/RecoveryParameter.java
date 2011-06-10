/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii;

/**
 *
 * @author root
 */
public class RecoveryParameter {
    private  String mapName;
    private  boolean inizializzazione;
    private  int[] idOccupante;
    private  int[] numeroTruppe;
    private  int[] objectives;
    private  int[] armateDisponibili;
    private  int turno;
    private  int numeroGiocatori;
    private  int seed_card;
    private  int seed_dice;
    private  int dice_lanch;
    private  int cards_lanch;
    private int turnoMyGiocatore;



    public RecoveryParameter(String mapName,boolean inizializzazione,int[] idOccupante,int[] numeroTruppe,int[] objectives,int[] armateDisponibili,int turno,int numeroGiocatori,int seed_card,int seed_dice,int dice_lanch,int card_lanch){
        this.mapName=mapName;
        this.inizializzazione=inizializzazione;
        this.idOccupante=idOccupante;
        this.numeroTruppe=numeroTruppe;
        this.objectives=objectives;
        this.armateDisponibili=armateDisponibili;
        this.turno=turno;
        this.numeroGiocatori=numeroGiocatori;
        this.seed_card=seed_card;
        this.seed_dice=seed_dice;
        this.dice_lanch=dice_lanch;
        this.cards_lanch=card_lanch;
    }

    public RecoveryParameter(){

    }

    public int getCards_lanch() {
        return cards_lanch;
    }

    public void setCards_lanch(int cards_lanch) {
        this.cards_lanch = cards_lanch;
    }

    public int getDice_lanch() {
        return dice_lanch;
    }

    public void setDice_lanch(int dice_lanch) {
        this.dice_lanch = dice_lanch;
    }

    public int[] getIdOccupante() {
        return idOccupante;
    }

    public void setIdOccupante(int[] idOccupante) {
        this.idOccupante = idOccupante;
    }

    public boolean isInizializzazione() {
        return inizializzazione;
    }

    public void setInizializzazione(boolean inizializzazione) {
        this.inizializzazione = inizializzazione;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int[] getArmateDisponibili() {
        return armateDisponibili;
    }

    public void setArmateDisponibili(int[] armateDisponibili) {
        this.armateDisponibili = armateDisponibili;
    }

  

    public int getNumeroGiocatori() {
        return numeroGiocatori;
    }

    public void setNumeroGiocatori(int numeroGiocatori) {
        this.numeroGiocatori = numeroGiocatori;
    }

    public int[] getNumeroTruppe() {
        return numeroTruppe;
    }

    public void setNumeroTruppe(int[] numeroTruppe) {
        this.numeroTruppe = numeroTruppe;
    }

    public int[] getObjectives() {
        return objectives;
    }

    public void setObjectives(int[] objectives) {
        this.objectives = objectives;
    }

    public int getSeed_card() {
        return seed_card;
    }

    public void setSeed_card(int seed_card) {
        this.seed_card = seed_card;
    }

    public int getSeed_dice() {
        return seed_dice;
    }

    public void setSeed_dice(int seed_dice) {
        this.seed_dice = seed_dice;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public void setTurnoMyGiocatore(int indexMyGiocatore) {
        this.turnoMyGiocatore=indexMyGiocatore;
    }

    public int getTurnoMyGiocatore() {
        return turnoMyGiocatore;
    }

    

    


}
