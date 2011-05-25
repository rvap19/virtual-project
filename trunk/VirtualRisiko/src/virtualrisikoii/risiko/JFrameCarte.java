/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFrameCarte.java
 *
 * Created on 24-mag-2011, 15.29.23
 */

package virtualrisikoii.risiko;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

/**
 *
 * @author Administrator
 */
public class JFrameCarte extends javax.swing.JFrame implements Runnable {

    /** Creates new form JFrameCarte */
    public JFrameCarte(int carta, String territorio) {
        this.carta=carta;
        this.territorio=territorio;
        nomeCarta = null;
        Image img;

        switch(carta){
            case 1: nomeCarta="cavaliere";
            break;
            case 10:nomeCarta="fante";
            break;
            case 100: nomeCarta="cannone";
            break;
            case 1000:nomeCarta="jolly";
            break;

        }

        pathCarta="src/virtualrisikoii/resources/cards/"+nomeCarta+".jpg";
            setCenterScreen(this);
            initComponents();
            TuoThread tt = new TuoThread( this, 8000);
            tt.start();

            labelCarta.setSize(174, 131);
             
             img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/cards/coperta.jpg");
             labelCarta.setImage(img);
             labelCarta.repaint();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        labelTerritorio = new javax.swing.JLabel();
        labelCarta = new virtualrisikoii.risiko.JPanelDadi();
        labelNomeCarta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel1.setText("Hai ottenuto una carta");
        jLabel1.setName("jLabel1"); // NOI18N

        jButton1.setText("OK");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        labelTerritorio.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelTerritorio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTerritorio.setName("labelTerritorio"); // NOI18N

        labelCarta.setName("labelCarta"); // NOI18N

        javax.swing.GroupLayout labelCartaLayout = new javax.swing.GroupLayout(labelCarta);
        labelCarta.setLayout(labelCartaLayout);
        labelCartaLayout.setHorizontalGroup(
            labelCartaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );
        labelCartaLayout.setVerticalGroup(
            labelCartaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
        );

        labelNomeCarta.setFont(new java.awt.Font("Times New Roman", 1, 18));
        labelNomeCarta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNomeCarta.setName("labelNomeCarta"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelNomeCarta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(labelCarta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(162, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(labelTerritorio, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNomeCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(labelCarta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(labelTerritorio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    
    
    @SuppressWarnings("static-access")
   
  public void run() {
   int i=0;
   Image img;
   int time=1500;
   Random random = new Random();
   String nomeCartaProb = null;
   Border border = null;
              
             img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/cards/coperta.jpg");
             labelCarta.setImage(img);
             labelCarta.repaint();

       try { Thread.sleep(1000);}
       catch (InterruptedException e) {
       e.printStackTrace();}


   while(i<4){

       int intCarta = random.nextInt(4);
         switch(intCarta){
            case 0: nomeCartaProb="cavaliere";
            break;
            case 1:nomeCartaProb="fante";
            break;
            case 2: nomeCartaProb="cannone";
            break;
            case 3:nomeCartaProb="jolly";
            break;

        }

        Suono.playSound("/virtualrisikoii/resources/dadi/card.wav");
        if(i==0){
            try { Thread.sleep(500);}
       catch (InterruptedException e) {
       e.printStackTrace();}

        }
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/cards/"+nomeCartaProb+".jpg");
        labelCarta.setImage(img);
        labelCarta.repaint();
        try { Thread.sleep(300);}
       catch (InterruptedException e) {
       e.printStackTrace();}
        i++;
   }

  /* try { Thread.sleep(time);}
       catch (InterruptedException e) {
       e.printStackTrace();}*/

           
               Suono.playSound("/virtualrisikoii/resources/dadi/card.wav");
               img=Toolkit.getDefaultToolkit().createImage(pathCarta);
               labelCarta.setImage(img);
               labelCarta.repaint();
               labelTerritorio.setText(territorio);
               labelNomeCarta.setText(nomeCarta);        

   }
 

   public static void main(String[] args) throws Exception{
       JFrameCarte frame=new JFrameCarte(1000, "Frigento");
       frame.setVisible(true);
       Thread t=new Thread(frame);
       t.start();
   }

   public static void setCenterScreen(JFrame frame) {
       
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Point center = ge.getCenterPoint();
        java.awt.Rectangle bounds = ge.getMaximumWindowBounds();
        int w = Math.max(bounds.width/2, Math.min(frame.getWidth(), bounds.width));
        int h = Math.max(bounds.height/2, Math.min(frame.getHeight(), bounds.height));
        int x = center.x - w/2, y = center.y - h/2;
        frame.setBounds(x, y, w, h);
        if (w == bounds.width && h == bounds.height)
            frame.setExtendedState(frame.MAXIMIZED_BOTH);
        frame.validate();


    }

   public void avviaCarta(){
    setVisible(true);
    Thread t= new Thread(this);
    t.start();
}
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private virtualrisikoii.risiko.JPanelDadi labelCarta;
    private javax.swing.JLabel labelNomeCarta;
    private javax.swing.JLabel labelTerritorio;
    // End of variables declaration//GEN-END:variables
    private String territorio;
    private int carta;
    private String pathCarta;
    private String nomeCarta;
    }
