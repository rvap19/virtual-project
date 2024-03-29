/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EndGameFrame.java
 *
 * Created on 23-mag-2011, 10.36.37
 */

package virtualrisikoii;

import java.awt.Color;
import javax.swing.JTextField;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Suono;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class EndGameFrame extends javax.swing.JFrame {

    private JTextField[] texts=new JTextField[6];

    /** Creates new form EndGameFrame */
    public EndGameFrame() {
        initComponents();
        texts[0]=this.giocatore1;
        texts[1]=this.giocatore2;
        texts[2]=this.giocatore3;
        this.texts[3]=this.giocatore4;
        this.texts[4]=this.giocatore5;
        this.texts[5]=giocatore6;
        Suono.playSound("/virtualrisikoii/resources/dadi/win16.wav");
    }

    public void setPunteggio(Giocatore g){
        
        this.texts[g.getID()].setText(Integer.toString(Tavolo.getInstance().getPunteggioGiocatore(g)));
    }
    public void setVincitore(Giocatore g){
        this.nomeVincitoreLabel.setText(g.getNome());
        switch(g.getID()){
            case 0:  nomeVincitoreLabel.setForeground(Color.RED);
                     carroWinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/rosso.png")));
            break;
            case 1:  nomeVincitoreLabel.setForeground(Color.YELLOW);
                     carroWinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/giallo.png")));
            break;
            case 2:  nomeVincitoreLabel.setForeground(Color.GREEN);
                     carroWinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/verde.png")));
            break;
            case 3:  nomeVincitoreLabel.setForeground(Color.BLACK);
                     carroWinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/nero.png")));
            break;
            case 4:  nomeVincitoreLabel.setForeground(Color.MAGENTA);
                     carroWinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/viola.png")));
            break;
            case 5:  nomeVincitoreLabel.setForeground(Color.BLUE);
                     carroWinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/blu.png")));
            break;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        giocatore1 = new javax.swing.JTextField();
        giocatore2 = new javax.swing.JTextField();
        giocatore3 = new javax.swing.JTextField();
        giocatore4 = new javax.swing.JTextField();
        giocatore5 = new javax.swing.JTextField();
        giocatore6 = new javax.swing.JTextField();
        nomeVincitoreLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        carroWinLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Rockwell", 2, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Grazie per aver giocato con Virtual risiko");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Il vincitore è :");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel1.setText("Punteggio giocatore rosso :");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel4.setText("Punteggio giocatore giallo :");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("Punteggio giocatore verde :");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText("Punteggio giocatore nero :");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText("Punteggio giocatore viola :");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText("Punteggio giocatore blu :");
        jLabel8.setName("jLabel8"); // NOI18N

        giocatore1.setBackground(new java.awt.Color(255, 51, 51));
        giocatore1.setEditable(false);
        giocatore1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        giocatore1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giocatore1.setText("0");
        giocatore1.setName("giocatore1"); // NOI18N

        giocatore2.setBackground(new java.awt.Color(255, 255, 51));
        giocatore2.setEditable(false);
        giocatore2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        giocatore2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giocatore2.setText("0");
        giocatore2.setName("giocatore2"); // NOI18N

        giocatore3.setBackground(new java.awt.Color(0, 255, 0));
        giocatore3.setEditable(false);
        giocatore3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        giocatore3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giocatore3.setText("0");
        giocatore3.setName("giocatore3"); // NOI18N

        giocatore4.setBackground(new java.awt.Color(51, 51, 51));
        giocatore4.setEditable(false);
        giocatore4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        giocatore4.setForeground(new java.awt.Color(255, 255, 255));
        giocatore4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giocatore4.setText("0");
        giocatore4.setName("giocatore4"); // NOI18N

        giocatore5.setBackground(new java.awt.Color(255, 102, 204));
        giocatore5.setEditable(false);
        giocatore5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        giocatore5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giocatore5.setText("0");
        giocatore5.setName("giocatore5"); // NOI18N

        giocatore6.setBackground(new java.awt.Color(51, 51, 255));
        giocatore6.setEditable(false);
        giocatore6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        giocatore6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giocatore6.setText("0");
        giocatore6.setName("giocatore6"); // NOI18N

        nomeVincitoreLabel.setFont(new java.awt.Font("Rockwell", 3, 18)); // NOI18N
        nomeVincitoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeVincitoreLabel.setText("giocatore verde");
        nomeVincitoreLabel.setName("nomeVincitoreLabel"); // NOI18N

        jButton1.setText("Chiudi");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        carroWinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/tanks/blu.png"))); // NOI18N
        carroWinLabel.setName("carroWinLabel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(nomeVincitoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(carroWinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(giocatore6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(giocatore5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(giocatore4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(giocatore3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(giocatore2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(giocatore1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))))
                        .addContainerGap())))
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(carroWinLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nomeVincitoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(giocatore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(giocatore2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(giocatore3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(giocatore4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(giocatore5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(giocatore6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //this.setVisible(false);
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Giocatore giorgio=new Giocatore(5);
                EndGameFrame egf=new EndGameFrame();
                egf.setVisible(true);
                egf.setVincitore(giorgio);


            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel carroWinLabel;
    private javax.swing.JTextField giocatore1;
    private javax.swing.JTextField giocatore2;
    private javax.swing.JTextField giocatore3;
    private javax.swing.JTextField giocatore4;
    private javax.swing.JTextField giocatore5;
    private javax.swing.JTextField giocatore6;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nomeVincitoreLabel;
    // End of variables declaration//GEN-END:variables

}
