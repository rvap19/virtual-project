/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MicroPanel.java
 *
 * Created on 8-apr-2011, 17.04.47
 */

package virtualrisikoii.Map.classical;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author root
 */
public class MicroPanel extends javax.swing.JPanel {
    private Image img;
    /** Creates new form MicroPanel */
    public MicroPanel() {
        img=new ImageIcon("C:/Documents and Settings/root/Documenti/NetBeansProjects/VirtualRisikoII/src/virtualrisikoii/resources/maps/classic/anteprima.jpg").getImage();
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    public void paintComponent(Graphics g) {
         g.drawImage(img, 0, 0, null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
