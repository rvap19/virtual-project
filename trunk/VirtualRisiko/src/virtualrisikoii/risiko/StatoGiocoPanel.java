/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StatoGiocoPanel.java
 *
 * Created on 15-giu-2011, 11.57.02
 */

package virtualrisikoii.risiko;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import jxta.communication.VirtualCommunicator;
import jxta.communication.messages.StatusPeerMessage;
import jxta.communication.messages.listener.StatusPeerListener;

/**
 *
 * @author Administrator
 */
public class StatoGiocoPanel extends javax.swing.JPanel implements StatusPeerListener{


    /** Creates new form StatoGiocoPanel */
  

    public StatoGiocoPanel() {
        initComponents();
        //String pathColore=null;
        this.tavolo=Tavolo.getInstance();
        VirtualCommunicator.getInstance().addStatusListener(this);
        String colore=null;
        Color color;
        List<Giocatore> giocatori=tavolo.getGiocatori();
        Giocatore giocatoreCorrente=tavolo.getGiocatoreCorrente();
        numGioc=giocatori.size();
        int i=0;
        String nomeGiocatore=null;
        List<String> nomeGiocatori=new ArrayList<String>();


         List<String> pathColore = new ArrayList<String>();


        while(i<numGioc){

                  switch(giocatori.get(i).getID()){
                    case 0: colTurno="rosso" ;
                            colore="Rosso";
                            nomeGiocatore=giocatori.get(i).getUsername();

                    break;
                    case 1: colTurno="giallo";
                            colore="Giallo";
                            nomeGiocatore=giocatori.get(i).getUsername();
                    break;
                    case 2: colTurno="verde";
                            colore="Verde";
                            nomeGiocatore=giocatori.get(i).getUsername();
                    break;
                    case 3: colTurno="nero";
                            colore="Nero";
                            nomeGiocatore=giocatori.get(i).getUsername();
                    break;
                    case 4: colTurno="viola";
                            colore="Viola";
                            nomeGiocatore=giocatori.get(i).getUsername();
                    break;
                    case 5: colTurno="blu";
                            colore="Blu";
                            nomeGiocatore=giocatori.get(i).getUsername();
                    break;
                }
                  pathColore.set(i, "/virtualrisikoii/resources/tanks/"+colTurno+".png");
                  nomeGiocatori.set(i,nomeGiocatore);
                  i++;
            }

         int lunghPathColore =pathColore.size();

          iconStateLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(0))));
          nomeGiocatoreLabel1.setText(nomeGiocatori.get(0));
          puntLabel1.setText("0");
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          


          if(lunghPathColore>1){
          iconStateLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(1))));
          nomeGiocatoreLabel2.setText(nomeGiocatori.get(1));
         puntLabel2.setText("0");
         statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          }
          if(lunghPathColore>2){
          iconStateLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(2))));
          nomeGiocatoreLabel3.setText(nomeGiocatori.get(2));
          puntLabel3.setText("0");
          statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        }
          if(lunghPathColore>3){
          iconStateLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(3))));
          nomeGiocatoreLabel4.setText(nomeGiocatori.get(3));
          puntLabel4.setText("0");
          statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        }
          if(lunghPathColore>4){
          iconStateLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(4))));
          nomeGiocatoreLabel5.setText(nomeGiocatori.get(4));
          puntLabel5.setText("0");
          statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        }
          if(lunghPathColore>5){
          iconStateLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(5))));
          nomeGiocatoreLabel6.setText(nomeGiocatori.get(5));
          puntLabel6.setText("0");
          statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        }




    }

    public void updateGiocatore(Giocatore giocatore){




        if(giocatore.getID()==0){
            if(numGioc>0){
          puntLabel1.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
           statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            }
           if(numGioc>1) {
                statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>2) {
           statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>3) {
           statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>4) {
           statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
          }
         if(giocatore.getID()==1){

          puntLabel2.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 2));
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

            if(numGioc>2) {
           statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>3) {
           statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>4) {
           statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
          }


         if(giocatore.getID()==2){
          puntLabel3.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 255, 0), 2));
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

            if(numGioc>3) {
           statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>4) {
           statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }

           }
         if(giocatore.getID()==3){
          puntLabel4.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

            if(numGioc>4) {
           statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
          }
         if(giocatore.getID()==4){
          puntLabel5.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 204), 2));
           statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
           statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
           statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
           statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
            }
          }
         if(giocatore.getID()==5){
          puntLabel6.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          }
    }

    public void NotifyOutPlayer(Giocatore giocatore, boolean outLine){

        ////// outLine=true  ==> il giocatore non è on line!
        String path="/virtualrisikoii/resources/tanks/out.png";
       if(outLine==true){
        switch(giocatore.getID()){
            case 0:  iconStateLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
            break;
            case 1:  iconStateLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
            break;
            case 2:  iconStateLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
            break;
            case 3:  iconStateLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
            break;
            case 4:  iconStateLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
            break;
            case 5:  iconStateLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
            break;
        }
        }else{
            switch(giocatore.getID()){
            case 0:  iconStateLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/rosso.png")));
            break;
            case 1:  iconStateLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/giallo.png")));
            break;
            case 2:  iconStateLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/verde.png")));
            break;
            case 3:  iconStateLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/nero.png")));
            break;
            case 4:  iconStateLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/viola.png")));
            break;
            case 5:  iconStateLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/blu.png")));
            break;
        }
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        puntLabel5 = new javax.swing.JLabel();
        puntLabel4 = new javax.swing.JLabel();
        puntLabel3 = new javax.swing.JLabel();
        puntLabel2 = new javax.swing.JLabel();
        puntLabel1 = new javax.swing.JLabel();
        puntLabel6 = new javax.swing.JLabel();
        iconStateLabel1 = new javax.swing.JLabel();
        iconStateLabel2 = new javax.swing.JLabel();
        iconStateLabel3 = new javax.swing.JLabel();
        iconStateLabel4 = new javax.swing.JLabel();
        iconStateLabel6 = new javax.swing.JLabel();
        iconStateLabel5 = new javax.swing.JLabel();
        nomeGiocatoreLabel1 = new javax.swing.JLabel();
        nomeGiocatoreLabel6 = new javax.swing.JLabel();
        nomeGiocatoreLabel2 = new javax.swing.JLabel();
        nomeGiocatoreLabel3 = new javax.swing.JLabel();
        nomeGiocatoreLabel4 = new javax.swing.JLabel();
        nomeGiocatoreLabel5 = new javax.swing.JLabel();
        statoLabel6 = new javax.swing.JLabel();
        statoLabel2 = new javax.swing.JLabel();
        statoLabel1 = new javax.swing.JLabel();
        statoLabel3 = new javax.swing.JLabel();
        statoLabel4 = new javax.swing.JLabel();
        statoLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");
        jLabel3.setName("jLabel3"); // NOI18N

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        puntLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntLabel5.setName("puntLabel5"); // NOI18N
        add(puntLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, -1, -1));

        puntLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntLabel4.setName("puntLabel4"); // NOI18N
        add(puntLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        puntLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntLabel3.setName("puntLabel3"); // NOI18N
        add(puntLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, -1, -1));

        puntLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntLabel2.setName("puntLabel2"); // NOI18N
        add(puntLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        puntLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntLabel1.setName("puntLabel1"); // NOI18N
        add(puntLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        puntLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntLabel6.setName("puntLabel6"); // NOI18N
        add(puntLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, -1, -1));

        iconStateLabel1.setName("iconStateLabel1"); // NOI18N
        add(iconStateLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        iconStateLabel2.setName("iconStateLabel2"); // NOI18N
        add(iconStateLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        iconStateLabel3.setName("iconStateLabel3"); // NOI18N
        add(iconStateLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        iconStateLabel4.setName("iconStateLabel4"); // NOI18N
        add(iconStateLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        iconStateLabel6.setName("iconStateLabel6"); // NOI18N
        add(iconStateLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        iconStateLabel5.setName("iconStateLabel5"); // NOI18N
        add(iconStateLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        nomeGiocatoreLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel1.setName("nomeGiocatoreLabel1"); // NOI18N
        add(nomeGiocatoreLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 30, 70, -1));

        nomeGiocatoreLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel6.setName("nomeGiocatoreLabel6"); // NOI18N
        add(nomeGiocatoreLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 180, 70, -1));

        nomeGiocatoreLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel2.setName("nomeGiocatoreLabel2"); // NOI18N
        add(nomeGiocatoreLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 60, 70, -1));

        nomeGiocatoreLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel3.setName("nomeGiocatoreLabel3"); // NOI18N
        add(nomeGiocatoreLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 90, 70, -1));

        nomeGiocatoreLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel4.setName("nomeGiocatoreLabel4"); // NOI18N
        add(nomeGiocatoreLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 120, 70, -1));

        nomeGiocatoreLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel5.setName("nomeGiocatoreLabel5"); // NOI18N
        add(nomeGiocatoreLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 150, 70, -1));

        statoLabel6.setName("statoLabel6"); // NOI18N
        add(statoLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 160, 30));

        statoLabel2.setName("statoLabel2"); // NOI18N
        add(statoLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 160, 30));

        statoLabel1.setName("statoLabel1"); // NOI18N
        add(statoLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 160, 30));

        statoLabel3.setName("statoLabel3"); // NOI18N
        add(statoLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 160, 30));

        statoLabel4.setName("statoLabel4"); // NOI18N
        add(statoLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 160, 30));

        statoLabel5.setName("statoLabel5"); // NOI18N
        add(statoLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 160, 30));

        jLabel1.setText(" Giocatore");
        jLabel1.setName("jLabel1"); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 60, -1));

        jLabel2.setText("Punteggio");
        jLabel2.setName("jLabel2"); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 50, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconStateLabel1;
    private javax.swing.JLabel iconStateLabel2;
    private javax.swing.JLabel iconStateLabel3;
    private javax.swing.JLabel iconStateLabel4;
    private javax.swing.JLabel iconStateLabel5;
    private javax.swing.JLabel iconStateLabel6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel nomeGiocatoreLabel1;
    private javax.swing.JLabel nomeGiocatoreLabel2;
    private javax.swing.JLabel nomeGiocatoreLabel3;
    private javax.swing.JLabel nomeGiocatoreLabel4;
    private javax.swing.JLabel nomeGiocatoreLabel5;
    private javax.swing.JLabel nomeGiocatoreLabel6;
    private javax.swing.JLabel puntLabel1;
    private javax.swing.JLabel puntLabel2;
    private javax.swing.JLabel puntLabel3;
    private javax.swing.JLabel puntLabel4;
    private javax.swing.JLabel puntLabel5;
    private javax.swing.JLabel puntLabel6;
    private javax.swing.JLabel statoLabel1;
    private javax.swing.JLabel statoLabel2;
    private javax.swing.JLabel statoLabel3;
    private javax.swing.JLabel statoLabel4;
    private javax.swing.JLabel statoLabel5;
    private javax.swing.JLabel statoLabel6;
    // End of variables declaration//GEN-END:variables
    private String colTurno;
    private Tavolo tavolo;
    private int numGioc;

    public void updateStatus(StatusPeerMessage message) {
        this.NotifyOutPlayer(tavolo.getGiocatori().get(message.getId()), !message.isOnline());
    }
}
