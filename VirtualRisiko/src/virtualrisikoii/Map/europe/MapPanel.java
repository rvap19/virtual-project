/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MapPanel.java
 *
 * Created on 8-apr-2011, 16.01.14
 */

package virtualrisikoii.Map.europe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author root
 */
public class MapPanel extends javax.swing.JPanel {
    private final Image img;
    

    /** Creates new form MapPanel */
    public MapPanel() {
        img=new ImageIcon("src/virtualrisikoii/resources/maps/europa/mappa.jpg").getImage();
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        initComponents();
        
        
    }

    public void paintComponent(Graphics g) {
         g.drawImage(img, 0, 0, null);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(1024, 680));
        setMinimumSize(new java.awt.Dimension(1024, 680));
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1024, 680));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
