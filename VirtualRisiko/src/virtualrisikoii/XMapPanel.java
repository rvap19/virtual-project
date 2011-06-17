/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import services.CardChangeListener;
import services.CardListener;
import services.GameController;
import services.MapListener;
import services.TroopsSelector;
import services.VictoryListener;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.JFrameCarte;
import virtualrisikoii.risiko.JFrameDadi;
import virtualrisikoii.risiko.JFrameScambioCarte;

/**
 *
 * @author root
 */
public abstract class XMapPanel extends JPanel implements MapListener,TroopsSelector,VictoryListener,CardListener, CardChangeListener{
    protected JLabel[] territoriLabels;
    protected ImageIcon[] icons;


    protected GameController gameController;

    public void initIconPlayers(){

        this.icons=new ImageIcon[6];
        icons[Giocatore.ROSSO]=new ImageIcon("src/virtualrisikoii/resources/tanks/rosso.png");
        icons[Giocatore.BLU]=new ImageIcon("src/virtualrisikoii/resources/tanks/blu.png");
        icons[Giocatore.GIALLO]=new ImageIcon("src/virtualrisikoii/resources/tanks/giallo.png");
        icons[Giocatore.NERO]=new ImageIcon("src/virtualrisikoii/resources/tanks/nero.png");
        icons[Giocatore.VERDE]=new ImageIcon("src/virtualrisikoii/resources/tanks/verde.png");
        icons[Giocatore.VIOLA]=new ImageIcon("src/virtualrisikoii/resources/tanks/viola.png");


    }

    protected int getLabelID(JLabel label){
       int size=this.territoriLabels.length;
       boolean trovato=false;
       int i;
       for(i=0;i<size&&!trovato;i++){
           trovato=(label==territoriLabels[i]);
       }
       return i-1;
   }

    protected void makeAction(JLabel label,java.awt.event.MouseEvent event){
       /* if(!gameController.canMove()){
            return;
        }

        int id=this.getLabelID(label);
        this.gameController.makeAction(id);*/

        if(!gameController.canMove()){
            return;
        }
        int idTerritorio=this.getLabelID(label);

        if(gameController.isInInizializzazione()){
            gameController.assegnaUnitaInInizializzazione(idTerritorio);
            return;
        }

        if(gameController.haTruppe()){
            gameController.assegnaUnita(idTerritorio);
            return;
        }

        if(gameController.getFirstSelectionID()==-1){
            gameController.makeFirstSelection(idTerritorio);
            return;
        }

        gameController.makeSecondSelection(idTerritorio);
        int firstSelection=gameController.getFirstSelectionID();
        int secondSelection=gameController.getSecondSelectionID();

        if(firstSelection!=-1 && secondSelection!=-1){
            if(!gameController.makeAttack(firstSelection, secondSelection)){
                gameController.makeSpostamento(firstSelection, secondSelection);
            }
        }

        gameController.resetActionData();

    }

     public int selectTroops(boolean isAttacco,int maxTruppe,int daTerritorio,int aTerritorio){
       String title="Spostamento ";

       if(isAttacco){
           title="Attacco";

       }



       ActionDialog dialog=new ActionDialog(null, true);
       dialog.setTitle(title);
       dialog.getDaTerritorioTextName().setText(this.territoriLabels[daTerritorio].getToolTipText());
       dialog.getaTerritorioTextName().setText(this.territoriLabels[aTerritorio].getToolTipText());
       dialog.setMaximum(maxTruppe);
       dialog.setMinimum(1);
       dialog.setValue(maxTruppe/2);
       dialog.setVisible(true);

       if(dialog.getReturnStatus()==ActionDialog.RET_OK){
           return Integer.parseInt(dialog.getNumeroTruppeText().getText());
       }

       return -1;
   }

     public void notifyVictory(List<Giocatore> giocatori,Giocatore vincitore){
        EndGameFrame frame=new EndGameFrame();
        frame.setVincitore(vincitore);
        Iterator<Giocatore> iter=giocatori.iterator();
        while(iter.hasNext())
            frame.setPunteggio(iter.next());
        frame.setVisible(true);
        //this.setVisible(false);
    }

    public void setLabelAttributes(int idLabel, int troopNumber, int idIcon) {
       JLabel label=this.territoriLabels[idLabel];
       label.setText(Integer.toString(troopNumber));
       label.setIcon(icons[idIcon]);
    }

    public void notifyAttacco(String string, int i, int i0, int i1, int i2, int i3, int i4, int iD, int iD0) {
        JFrameDadi jfd= new JFrameDadi(string,i,i0,i1,i2,i3,i4,iD,iD0);
        jfd.avviaDadi();
        jfd.setVisible(true);

    }

    public void notifyCard(int tipo, String territorio) {
        JFrameCarte jfc = new JFrameCarte(tipo, territorio);
        jfc.avviaCarta();
        jfc.setVisible(true);

          }

    public void notifyChangeCard(Giocatore giocatore, Carta carta1, Carta carta2, Carta carta3, int rinforzi){
        JFrameScambioCarte jfsc= new JFrameScambioCarte(giocatore, carta1, carta2, carta3, rinforzi);
        jfsc.setVisible(true);
    }

    public class LabelSelector extends MouseAdapter{
       public void mouseClicked(MouseEvent e){
           makeAction((JLabel)e.getSource(),e);
       }
    }

}
