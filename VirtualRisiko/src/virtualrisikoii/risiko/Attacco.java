/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.Arrays;

/**
 *
 * @author root
 */
public class Attacco extends Azione{

    private int[] punteggio=null;
    private int[] punteggioAvversario=null;
    private int numeroTruppeAvversario;
    private boolean vittoria=false;

    public Attacco(){
        super.type=Azione.ATTACCO;
    }
    @Override
    public void setNumeroTruppe(int n){
        this.numeroTruppe=n;
        if(super.aTerritorio.getNumeroUnita()<n){
            numeroTruppeAvversario=aTerritorio.getNumeroUnita();
        }else if(super.aTerritorio.getNumeroUnita()>=n){
            if(super.aTerritorio.getNumeroUnita()>3)
                numeroTruppeAvversario=3;
            else
                numeroTruppeAvversario=aTerritorio.getNumeroUnita();
        }

    }

    public void eseguiAzione() {

        int dadi;
        if(this.numeroTruppe<this.numeroTruppeAvversario){
            dadi=numeroTruppe;
        }else{
            dadi=numeroTruppeAvversario;
        }



        for(int i=0;i<dadi;i++){
            if(punteggio[i]>punteggioAvversario[i]){
                this.numeroTruppeAvversario--;
            }else{
                this.numeroTruppe--;
                
            }
        }

        this.aTerritorio.setNumeroUnita(this.aTerritorio.getNumeroUnita()+this.numeroTruppeAvversario);
        
        if(aTerritorio.getNumeroUnita()<=0){
            vittoria=true;
            
            Giocatore attaccante=daTerritorio.getOccupante();
            Giocatore difensore=aTerritorio.getOccupante();
            
            difensore.getNazioni().remove(aTerritorio);
           // aTerritorio.getOccupante().getNazioni().remove(aTerritorio);
            aTerritorio.setOccupante(attaccante);
           // aTerritorio.setOccupante(daTerritorio.getOccupante());

            aTerritorio.setNumeroUnita(numeroTruppe);
            
            attaccante.getNazioni().add(aTerritorio);
            
        }else{
            daTerritorio.setNumeroUnita(daTerritorio.getNumeroUnita()+numeroTruppe);
        }
        
    }

    public void setPunteggio(int[] p){
        punteggio=p;
        Arrays.sort(punteggio);
        int temp=punteggio[punteggio.length-1];
        punteggio[punteggio.length-1]=punteggio[0];
        punteggio[0]=temp;
    }

    public void setPunteggioAvversario(int[] p){
        punteggioAvversario=p;
        Arrays.sort(punteggioAvversario);
        int temp=punteggioAvversario[punteggioAvversario.length-1];
        punteggioAvversario[punteggioAvversario.length-1]=punteggioAvversario[0];
        punteggioAvversario[0]=temp;
    }
    
   

    

    public int[] getPunteggio() {
        return punteggio;
    }

    public int[] getPunteggioAvversario() {
        return punteggioAvversario;
    }


    public boolean isVittoria(){
        return vittoria;
    }

    public int getNumeroTruppeAvversario() {
        return numeroTruppeAvversario;
    }

    public void setNumeroTruppeAvversario(int numeroTruppeAvversario) {
        this.numeroTruppeAvversario = numeroTruppeAvversario;
    }

    @Override
    public String toString() {
        return "Attacco da "+super.daTerritorio.getNome()+" a "+super.aTerritorio.getNome()+" con "+this.punteggio.length+" truppe ";
    }

    


}
