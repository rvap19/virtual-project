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
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import middle.EventTypes;
import middle.Middle;

import middle.event.PassEvent;
import middle.event.RisikoEvent;
import middle.event.StatusPeerEvent;
import middle.listener.PassEventListener;
import middle.listener.StatusPeerEventListener;
import middle.messages.PassMessage;
import middle.messages.StatusPeerMessage;
import services.GameController;


/**
 *
 * @author Administrator
 */
public class StatoGiocoPanel extends javax.swing.JPanel implements StatusPeerEventListener,PassEventListener {


    /** Creates new form StatoGiocoPanel */
/*  public StatoGiocoPanel(){
  initComponents();
  } */



    public static StatoGiocoPanel instance=null;

    public static StatoGiocoPanel getInstance(){
        if(instance==null){
            instance=new StatoGiocoPanel();
        }
        return instance;
    }
    private StatoGiocoPanel() {
        initComponents();
        //String pathColore=null;
        this.tavolo=Tavolo.getInstance();
        Middle middle=GameController.getInstance().getMiddle();
        middle.addListener(EventTypes.STATUS_PEER,this);
        middle.addListener(EventTypes.PASS,this);
        String colore=null;
        Color color;
        List<Giocatore> giocatori=tavolo.getGiocatori();
        Giocatore giocatoreCorrente=tavolo.getGiocatoreCorrente();
        numGioc=giocatori.size();
        int i=0;
        String nomeGiocatore=null;
        List<String> nomeGiocatori=new ArrayList<String>();


         List<String> pathColore = new ArrayList<String>();
         Tavolo tavolo=Tavolo.getInstance();

        while(i<numGioc){

                  switch(giocatori.get(i).getID()){
                    case 0: colTurno="rosso" ;
                            colore="Rosso";
                            nomeGiocatore=giocatori.get(i).getUsername();
                            this.puntLabel1.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatori.get(i))));
                    break;
                    case 1: colTurno="giallo";
                            colore="Giallo";
                            nomeGiocatore=giocatori.get(i).getUsername();
                            this.puntLabel2.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatori.get(i))));
                    break;
                    case 2: colTurno="verde";
                            colore="Verde";
                            nomeGiocatore=giocatori.get(i).getUsername();
                            this.puntLabel3.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatori.get(i))));
                    break;
                    case 3: colTurno="nero";
                            colore="Nero";
                            nomeGiocatore=giocatori.get(i).getUsername();
                            this.puntLabel4.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatori.get(i))));
                    break;
                    case 4: colTurno="viola";
                            colore="Viola";
                            nomeGiocatore=giocatori.get(i).getUsername();
                            this.puntLabel5.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatori.get(i))));
                    break;
                    case 5: colTurno="blu";
                            colore="Blu";
                            nomeGiocatore=giocatori.get(i).getUsername();
                            this.puntLabel6.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatori.get(i))));
                    break;
                }
                  pathColore.add(i, "/virtualrisikoii/resources/tanks/"+colTurno+".png");

                  nomeGiocatori.add(i,nomeGiocatore);

                  i++;
            }

         int lunghPathColore =pathColore.size();

        
          iconStateLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(0))));
          nomeGiocatoreLabel1.setText(nomeGiocatori.get(0));
        //  puntLabel1.setText("0");
         // statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
           statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));


          if(lunghPathColore>1){
             
          iconStateLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(1))));
          nomeGiocatoreLabel2.setText(nomeGiocatori.get(1));
        // puntLabel2.setText("0");
         //statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
          }
 
          if(lunghPathColore>2){
             
          iconStateLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(2))));
          nomeGiocatoreLabel3.setText(nomeGiocatori.get(2));
      //    puntLabel3.setText("0");
          //statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        }
          if(lunghPathColore>3){
          
          iconStateLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(3))));
          nomeGiocatoreLabel4.setText(nomeGiocatori.get(3));
        //  puntLabel4.setText("0");
          //statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        }
          if(lunghPathColore>4){
         
          iconStateLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(4))));
          nomeGiocatoreLabel5.setText(nomeGiocatori.get(4));
     //     puntLabel5.setText("0");
         // statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        }
          if(lunghPathColore>5){
          
          iconStateLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathColore.get(5))));
          nomeGiocatoreLabel6.setText(nomeGiocatori.get(5));
      //    puntLabel6.setText("0");
         // statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        }




    }

    public void updateGiocatore(Giocatore giocatore){




        if(giocatore.getID()==0){
            if(numGioc>0){
          puntLabel1.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
           statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            }
           if(numGioc>1) {
                statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>2) {
           statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>3) {
           statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>4) {
           statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
          }
         if(giocatore.getID()==1){

          puntLabel2.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 2));
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));

            if(numGioc>2) {
           statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>3) {
           statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>4) {
           statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
          }


         if(giocatore.getID()==2){
          puntLabel3.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 255, 0), 2));
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
          statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));

            if(numGioc>3) {
           statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>4) {
           statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }

           }
         if(giocatore.getID()==3){
          puntLabel4.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
          statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
          statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));

            if(numGioc>4) {
           statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
          }
         if(giocatore.getID()==4){
          puntLabel5.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 204), 2));
           statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
           statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
           statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
           statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));

            if(numGioc>5) {
           statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
            }
          }
         if(giocatore.getID()==5){
          puntLabel6.setText(Integer.toString(tavolo.getPunteggioGiocatore(giocatore)));
          statoLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
          statoLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
          statoLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
          statoLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
          statoLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
          statoLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 0));
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel3 = new javax.swing.JLabel();
        nomeGiocatoreLabel1 = new javax.swing.JLabel();
        nomeGiocatoreLabel2 = new javax.swing.JLabel();
        nomeGiocatoreLabel3 = new javax.swing.JLabel();
        nomeGiocatoreLabel4 = new javax.swing.JLabel();
        nomeGiocatoreLabel5 = new javax.swing.JLabel();
        nomeGiocatoreLabel6 = new javax.swing.JLabel();
        puntLabel1 = new javax.swing.JLabel();
        puntLabel2 = new javax.swing.JLabel();
        puntLabel3 = new javax.swing.JLabel();
        puntLabel4 = new javax.swing.JLabel();
        puntLabel5 = new javax.swing.JLabel();
        puntLabel6 = new javax.swing.JLabel();
        iconStateLabel1 = new javax.swing.JLabel();
        iconStateLabel2 = new javax.swing.JLabel();
        iconStateLabel3 = new javax.swing.JLabel();
        iconStateLabel4 = new javax.swing.JLabel();
        iconStateLabel6 = new javax.swing.JLabel();
        iconStateLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        statoLabel1 = new javax.swing.JLabel();
        statoLabel2 = new javax.swing.JLabel();
        statoLabel3 = new javax.swing.JLabel();
        statoLabel4 = new javax.swing.JLabel();
        statoLabel5 = new javax.swing.JLabel();
        statoLabel6 = new javax.swing.JLabel();
        sfondoLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        puntLabel7 = new javax.swing.JLabel();
        puntLabel8 = new javax.swing.JLabel();
        puntLabel9 = new javax.swing.JLabel();
        puntLabel10 = new javax.swing.JLabel();
        puntLabel11 = new javax.swing.JLabel();
        puntLabel12 = new javax.swing.JLabel();
        iconStateLabel7 = new javax.swing.JLabel();
        iconStateLabel8 = new javax.swing.JLabel();
        iconStateLabel9 = new javax.swing.JLabel();
        iconStateLabel10 = new javax.swing.JLabel();
        iconStateLabel11 = new javax.swing.JLabel();
        iconStateLabel12 = new javax.swing.JLabel();
        nomeGiocatoreLabel7 = new javax.swing.JLabel();
        nomeGiocatoreLabel8 = new javax.swing.JLabel();
        nomeGiocatoreLabel9 = new javax.swing.JLabel();
        nomeGiocatoreLabel10 = new javax.swing.JLabel();
        nomeGiocatoreLabel11 = new javax.swing.JLabel();
        nomeGiocatoreLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        statoLabel7 = new javax.swing.JLabel();
        statoLabel8 = new javax.swing.JLabel();
        statoLabel9 = new javax.swing.JLabel();
        statoLabel10 = new javax.swing.JLabel();
        statoLabel11 = new javax.swing.JLabel();
        statoLabel12 = new javax.swing.JLabel();
        sfondoLabel1 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");
        jLabel3.setName("jLabel3"); // NOI18N

        setMaximumSize(new java.awt.Dimension(174, 213));
        setMinimumSize(new java.awt.Dimension(174, 213));
        setPreferredSize(new java.awt.Dimension(174, 213));
        setVerifyInputWhenFocusTarget(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nomeGiocatoreLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel1.setName("nomeGiocatoreLabel1"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nomeGiocatoreLabel1, org.jdesktop.beansbinding.ELProperty.create("${text}"), nomeGiocatoreLabel1, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        add(nomeGiocatoreLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 70, -1));

        nomeGiocatoreLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel2.setName("nomeGiocatoreLabel2"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nomeGiocatoreLabel2, org.jdesktop.beansbinding.ELProperty.create("${text}"), nomeGiocatoreLabel2, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        add(nomeGiocatoreLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 70, -1));

        nomeGiocatoreLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel3.setName("nomeGiocatoreLabel3"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nomeGiocatoreLabel3, org.jdesktop.beansbinding.ELProperty.create("${text}"), nomeGiocatoreLabel3, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        add(nomeGiocatoreLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 70, -1));

        nomeGiocatoreLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel4.setName("nomeGiocatoreLabel4"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nomeGiocatoreLabel4, org.jdesktop.beansbinding.ELProperty.create("${text}"), nomeGiocatoreLabel4, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        add(nomeGiocatoreLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 70, -1));

        nomeGiocatoreLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel5.setName("nomeGiocatoreLabel5"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nomeGiocatoreLabel5, org.jdesktop.beansbinding.ELProperty.create("${text}"), nomeGiocatoreLabel5, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        add(nomeGiocatoreLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 70, -1));

        nomeGiocatoreLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel6.setName("nomeGiocatoreLabel6"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nomeGiocatoreLabel6, org.jdesktop.beansbinding.ELProperty.create("${text}"), nomeGiocatoreLabel6, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        add(nomeGiocatoreLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 70, -1));

        puntLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel1.setName("puntLabel1"); // NOI18N
        add(puntLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 40, -1));

        puntLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel2.setName("puntLabel2"); // NOI18N
        add(puntLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 40, -1));

        puntLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel3.setName("puntLabel3"); // NOI18N
        add(puntLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 40, -1));

        puntLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel4.setName("puntLabel4"); // NOI18N
        add(puntLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 40, -1));

        puntLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel5.setName("puntLabel5"); // NOI18N
        add(puntLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 40, -1));

        puntLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel6.setName("puntLabel6"); // NOI18N
        add(puntLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 40, -1));

        iconStateLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel1.setName("iconStateLabel1"); // NOI18N
        add(iconStateLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 30, -1, -1));

        iconStateLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel2.setName("iconStateLabel2"); // NOI18N
        add(iconStateLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 60, -1, -1));

        iconStateLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel3.setName("iconStateLabel3"); // NOI18N
        add(iconStateLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 90, -1, -1));

        iconStateLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel4.setName("iconStateLabel4"); // NOI18N
        add(iconStateLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 120, -1, -1));

        iconStateLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel6.setName("iconStateLabel6"); // NOI18N
        add(iconStateLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 180, -1, -1));

        iconStateLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel5.setName("iconStateLabel5"); // NOI18N
        add(iconStateLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 150, -1, -1));

        jLabel1.setText("  Players");
        jLabel1.setName("jLabel1"); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 5, 50, -1));

        jLabel2.setText(" Score");
        jLabel2.setName("jLabel2"); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 5, 50, -1));

        statoLabel1.setName("statoLabel1"); // NOI18N
        add(statoLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 170, 30));

        statoLabel2.setName("statoLabel2"); // NOI18N
        add(statoLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 170, 30));

        statoLabel3.setName("statoLabel3"); // NOI18N
        add(statoLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 170, 30));

        statoLabel4.setName("statoLabel4"); // NOI18N
        add(statoLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 170, 30));

        statoLabel5.setName("statoLabel5"); // NOI18N
        add(statoLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 170, 30));

        statoLabel6.setName("statoLabel6"); // NOI18N
        add(statoLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 170, 30));

        sfondoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/pergamena/174x213.png"))); // NOI18N
        sfondoLabel.setName("sfondoLabel"); // NOI18N
        add(sfondoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(170, 213));
        jPanel1.setVerifyInputWhenFocusTarget(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        puntLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel7.setName("puntLabel7"); // NOI18N
        jPanel1.add(puntLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 40, -1));

        puntLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel8.setName("puntLabel8"); // NOI18N
        jPanel1.add(puntLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 40, -1));

        puntLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel9.setName("puntLabel9"); // NOI18N
        jPanel1.add(puntLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 40, -1));

        puntLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel10.setName("puntLabel10"); // NOI18N
        jPanel1.add(puntLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 40, -1));

        puntLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel11.setName("puntLabel11"); // NOI18N
        jPanel1.add(puntLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 40, -1));

        puntLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        puntLabel12.setName("puntLabel12"); // NOI18N
        jPanel1.add(puntLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 40, -1));

        iconStateLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel7.setName("iconStateLabel7"); // NOI18N
        jPanel1.add(iconStateLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 30, -1, -1));

        iconStateLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel8.setName("iconStateLabel8"); // NOI18N
        jPanel1.add(iconStateLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 60, -1, -1));

        iconStateLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel9.setName("iconStateLabel9"); // NOI18N
        jPanel1.add(iconStateLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 90, -1, -1));

        iconStateLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel10.setName("iconStateLabel10"); // NOI18N
        jPanel1.add(iconStateLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 120, -1, -1));

        iconStateLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel11.setName("iconStateLabel11"); // NOI18N
        jPanel1.add(iconStateLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 180, -1, -1));

        iconStateLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/outx.png"))); // NOI18N
        iconStateLabel12.setName("iconStateLabel12"); // NOI18N
        jPanel1.add(iconStateLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 150, -1, -1));

        nomeGiocatoreLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel7.setName("nomeGiocatoreLabel7"); // NOI18N
        jPanel1.add(nomeGiocatoreLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 70, -1));

        nomeGiocatoreLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel8.setName("nomeGiocatoreLabel8"); // NOI18N
        jPanel1.add(nomeGiocatoreLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 70, -1));

        nomeGiocatoreLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel9.setName("nomeGiocatoreLabel9"); // NOI18N
        jPanel1.add(nomeGiocatoreLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 70, -1));

        nomeGiocatoreLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel10.setName("nomeGiocatoreLabel10"); // NOI18N
        jPanel1.add(nomeGiocatoreLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 70, -1));

        nomeGiocatoreLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel11.setName("nomeGiocatoreLabel11"); // NOI18N
        jPanel1.add(nomeGiocatoreLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 70, -1));

        nomeGiocatoreLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeGiocatoreLabel12.setName("nomeGiocatoreLabel12"); // NOI18N
        jPanel1.add(nomeGiocatoreLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 70, -1));

        jLabel4.setText("  Players");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 5, 50, -1));

        jLabel5.setText(" Score");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 5, 50, -1));

        statoLabel7.setName("statoLabel7"); // NOI18N
        jPanel1.add(statoLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 170, 30));

        statoLabel8.setName("statoLabel8"); // NOI18N
        jPanel1.add(statoLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 170, 30));

        statoLabel9.setName("statoLabel9"); // NOI18N
        jPanel1.add(statoLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 170, 30));

        statoLabel10.setName("statoLabel10"); // NOI18N
        jPanel1.add(statoLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 170, 30));

        statoLabel11.setName("statoLabel11"); // NOI18N
        jPanel1.add(statoLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 170, 30));

        statoLabel12.setName("statoLabel12"); // NOI18N
        jPanel1.add(statoLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 170, 30));

        sfondoLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/pergamena/174x213.png"))); // NOI18N
        sfondoLabel1.setName("sfondoLabel1"); // NOI18N
        jPanel1.add(sfondoLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconStateLabel1;
    private javax.swing.JLabel iconStateLabel10;
    private javax.swing.JLabel iconStateLabel11;
    private javax.swing.JLabel iconStateLabel12;
    private javax.swing.JLabel iconStateLabel2;
    private javax.swing.JLabel iconStateLabel3;
    private javax.swing.JLabel iconStateLabel4;
    private javax.swing.JLabel iconStateLabel5;
    private javax.swing.JLabel iconStateLabel6;
    private javax.swing.JLabel iconStateLabel7;
    private javax.swing.JLabel iconStateLabel8;
    private javax.swing.JLabel iconStateLabel9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nomeGiocatoreLabel1;
    private javax.swing.JLabel nomeGiocatoreLabel10;
    private javax.swing.JLabel nomeGiocatoreLabel11;
    private javax.swing.JLabel nomeGiocatoreLabel12;
    private javax.swing.JLabel nomeGiocatoreLabel2;
    private javax.swing.JLabel nomeGiocatoreLabel3;
    private javax.swing.JLabel nomeGiocatoreLabel4;
    private javax.swing.JLabel nomeGiocatoreLabel5;
    private javax.swing.JLabel nomeGiocatoreLabel6;
    private javax.swing.JLabel nomeGiocatoreLabel7;
    private javax.swing.JLabel nomeGiocatoreLabel8;
    private javax.swing.JLabel nomeGiocatoreLabel9;
    private javax.swing.JLabel puntLabel1;
    private javax.swing.JLabel puntLabel10;
    private javax.swing.JLabel puntLabel11;
    private javax.swing.JLabel puntLabel12;
    private javax.swing.JLabel puntLabel2;
    private javax.swing.JLabel puntLabel3;
    private javax.swing.JLabel puntLabel4;
    private javax.swing.JLabel puntLabel5;
    private javax.swing.JLabel puntLabel6;
    private javax.swing.JLabel puntLabel7;
    private javax.swing.JLabel puntLabel8;
    private javax.swing.JLabel puntLabel9;
    private javax.swing.JLabel sfondoLabel;
    private javax.swing.JLabel sfondoLabel1;
    private javax.swing.JLabel statoLabel1;
    private javax.swing.JLabel statoLabel10;
    private javax.swing.JLabel statoLabel11;
    private javax.swing.JLabel statoLabel12;
    private javax.swing.JLabel statoLabel2;
    private javax.swing.JLabel statoLabel3;
    private javax.swing.JLabel statoLabel4;
    private javax.swing.JLabel statoLabel5;
    private javax.swing.JLabel statoLabel6;
    private javax.swing.JLabel statoLabel7;
    private javax.swing.JLabel statoLabel8;
    private javax.swing.JLabel statoLabel9;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private String colTurno;
    private Tavolo tavolo;
    private int numGioc;

    public void notify(StatusPeerEvent event) {
        StatusPeerMessage message=(StatusPeerMessage)event.getSource();
        Giocatore g=tavolo.getGiocatori().get(message.getId());
        boolean offline=!message.isOnline();

        
        this.NotifyOutPlayer(g, offline);

    }

    public void notify(PassEvent event) {
        PassMessage m=(PassMessage)event.getSource();
        Tavolo tavolo=Tavolo.getInstance();
        int turno=tavolo.getTurno();
        List<Giocatore> giocatori=tavolo.getGiocatori();
        int numeroG=tavolo.getGiocatori().size();
        updatePoints(numGioc, giocatori);
        System.out.println();
        this.updateGiocatore(giocatori.get(m.getTurno_successivo()));
    }

    public void updatePoints(int numGioc,List<Giocatore> list){
            if(numGioc>0){
                puntLabel1.setText(Integer.toString(tavolo.getPunteggioGiocatore(list.get(0))));
            }

            if(numGioc>1){
                puntLabel2.setText(Integer.toString(tavolo.getPunteggioGiocatore(list.get(1))));
            }

            if(numGioc>2){
                puntLabel3.setText(Integer.toString(tavolo.getPunteggioGiocatore(list.get(2))));
            }

            if(numGioc>3){
                puntLabel4.setText(Integer.toString(tavolo.getPunteggioGiocatore(list.get(3))));
            }

            if(numGioc>4){
                puntLabel5.setText(Integer.toString(tavolo.getPunteggioGiocatore(list.get(4))));
            }

            if(numGioc>5){
                puntLabel6.setText(Integer.toString(tavolo.getPunteggioGiocatore(list.get(5))));
            }




    }

    public void notify(RisikoEvent event) {
        String type=event.getType();
        if(type.equals(EventTypes.STATUS_PEER)){
            StatusPeerEvent x=(StatusPeerEvent)event;
            notify(x);
        }else if(type.equals(EventTypes.PASS)){
            PassEvent x=(PassEvent)event;
            notify(x);
        }
    }
}
