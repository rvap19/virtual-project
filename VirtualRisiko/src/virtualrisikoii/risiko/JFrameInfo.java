/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFrameInfo.java
 *
 * Created on 9-giu-2011, 14.00.03
 */

package virtualrisikoii.risiko;

import java.awt.Image;

/**
 *
 * @author Administrator
 */
public class JFrameInfo extends javax.swing.JFrame {

    /** Creates new form JFrameInfo */
    public JFrameInfo(String mappa) {
        initComponents();
        String path=null;
        if(mappa.equals("classicalMap")) {
            path = "/virtualrisikoii/resources/info/classicalInfo.png";
        }
        else if(mappa.equals("europaMap")) {
            path = "/virtualrisikoii/resources/info/europaInfo.png";
        }
        else {
            path = "/virtualrisikoii/resources/info/italiaInfo.png";
        }

        infoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
    }

    
     
    
    @Override
    public void setIconImage(Image image) {
        super.setIconImage(image);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        infoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        infoLabel.setName("infoLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameInfo("italiaMap").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel infoLabel;
    // End of variables declaration//GEN-END:variables

}
