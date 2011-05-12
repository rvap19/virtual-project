/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CardChangeDialog.java
 *
 * Created on 29-apr-2011, 12.21.13
 */

package virtualrisikoii;

import services.CardChangeController;

/**
 *
 * @author root
 */
public class CardChangeDialog extends javax.swing.JDialog {


    /** Creates new form CardChangeDialog */
    public CardChangeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setController(CardChangeController controller) {
        cardChangePanel1.setController(controller);
    }

    public CardChangeController getController() {
        return cardChangePanel1.getController();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardChangePanel1 = new virtualrisikoii.CardChangePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        cardChangePanel1.setName("cardChangePanel1"); // NOI18N
        getContentPane().add(cardChangePanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private virtualrisikoii.CardChangePanel cardChangePanel1;
    // End of variables declaration//GEN-END:variables

}
