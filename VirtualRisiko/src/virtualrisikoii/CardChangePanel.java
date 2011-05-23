/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CardChangePanel.java
 *
 * Created on 27-apr-2011, 22.20.24
 */

package virtualrisikoii;

import javax.swing.JList;
import services.CardChangeController;
import virtualrisikoii.InformationPanel;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class CardChangePanel extends javax.swing.JPanel {

    private CardChangeController controller;
    private Carta[] carte=new Carta[3];
    
    /** Creates new form CardChangePanel */
    public CardChangePanel() {
        
       this.controller = new CardChangeController();
       controller.setGiocatore(Tavolo.getInstance().getMyGiocatore());
       controller.setMappa(Tavolo.getInstance().getMappa());
        initComponents();
        this.selectJolly.setEnabled(false);
        this.fanteLittleIcon.setText("0");
        this.cannoneLittleIcon.setText("0");
        this.cavaliereLittleIcon.setText("0");
        this.jollyLittleIcon.setText("non selezionato");
        this.scambiaButton.setEnabled(false);
        
    }

    public CardChangeController getController() {
        return controller;
    }

    public void setController(CardChangeController controller) {
        this.controller=controller;
        
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

        jPanel9 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        cardPanels = new javax.swing.JPanel();
        firstCardPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        fanteIconLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        cannoneIconLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        secondcardPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        cavaliereIconLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        selectJolly = new javax.swing.JCheckBox();
        changePanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        fanteLittleIcon = new javax.swing.JLabel();
        cavaliereLittleIcon = new javax.swing.JLabel();
        cannoneLittleIcon = new javax.swing.JLabel();
        jollyLittleIcon = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        stimarinforzi = new javax.swing.JLabel();
        scambiaButton = new javax.swing.JButton();

        jPanel9.setName("jPanel9"); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(CardChangePanel.class);
        jCheckBox1.setText(resourceMap.getString("jCheckBox1.text")); // NOI18N
        jCheckBox1.setName("jCheckBox1"); // NOI18N

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        cardPanels.setName("cardPanels"); // NOI18N
        cardPanels.setLayout(new java.awt.GridLayout(1, 2));

        firstCardPanel.setName("firstCardPanel"); // NOI18N
        firstCardPanel.setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setName("jPanel7"); // NOI18N
        jPanel7.setLayout(new java.awt.GridLayout(1, 2));

        fanteIconLabel.setIcon(resourceMap.getIcon("fanteIconLabel.icon")); // NOI18N
        fanteIconLabel.setText(resourceMap.getString("fanteIconLabel.text")); // NOI18N
        fanteIconLabel.setName("fanteIconLabel"); // NOI18N
        jPanel7.add(fanteIconLabel);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setName("jList1"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${controller.fanti}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jList1);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${territorio.nome}"));
        bindingGroup.addBinding(jListBinding);

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jPanel7.add(jScrollPane1);

        jPanel1.add(jPanel7, java.awt.BorderLayout.CENTER);

        firstCardPanel.add(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel10.setName("jPanel10"); // NOI18N
        jPanel10.setLayout(new java.awt.GridLayout(1, 2));

        cannoneIconLabel.setIcon(resourceMap.getIcon("cannoneIconLabel.icon")); // NOI18N
        cannoneIconLabel.setText(resourceMap.getString("cannoneIconLabel.text")); // NOI18N
        cannoneIconLabel.setName("cannoneIconLabel"); // NOI18N
        jPanel10.add(cannoneIconLabel);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jList3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList3.setName("jList3"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${controller.cannoni}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jList3);
        bindingGroup.addBinding(jListBinding);

        jList3.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList3ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList3);

        jPanel10.add(jScrollPane3);

        jPanel2.add(jPanel10, java.awt.BorderLayout.CENTER);

        firstCardPanel.add(jPanel2);

        cardPanels.add(firstCardPanel);

        secondcardPanel.setName("secondcardPanel"); // NOI18N
        secondcardPanel.setLayout(new java.awt.GridLayout(2, 1));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel8.setName("jPanel8"); // NOI18N
        jPanel8.setLayout(new java.awt.GridLayout(1, 2));

        cavaliereIconLabel.setIcon(resourceMap.getIcon("cavaliereIconLabel.icon")); // NOI18N
        cavaliereIconLabel.setText(resourceMap.getString("cavaliereIconLabel.text")); // NOI18N
        cavaliereIconLabel.setName("cavaliereIconLabel"); // NOI18N
        jPanel8.add(cavaliereIconLabel);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setName("jList2"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${controller.cavalieri}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jList2);
        bindingGroup.addBinding(jListBinding);

        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jPanel8.add(jScrollPane2);

        jPanel3.add(jPanel8, java.awt.BorderLayout.CENTER);

        secondcardPanel.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel11.setName("jPanel11"); // NOI18N
        jPanel11.setLayout(new java.awt.GridLayout(1, 2));

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel11.add(jLabel7);

        selectJolly.setText(resourceMap.getString("selectJolly.text")); // NOI18N
        selectJolly.setName("selectJolly"); // NOI18N
        selectJolly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectJollyActionPerformed(evt);
            }
        });
        jPanel11.add(selectJolly);

        jPanel4.add(jPanel11, java.awt.BorderLayout.CENTER);

        secondcardPanel.add(jPanel4);

        cardPanels.add(secondcardPanel);

        add(cardPanels, java.awt.BorderLayout.CENTER);

        changePanel.setMaximumSize(new java.awt.Dimension(100, 295));
        changePanel.setMinimumSize(new java.awt.Dimension(100, 295));
        changePanel.setName("changePanel"); // NOI18N
        changePanel.setPreferredSize(new java.awt.Dimension(200, 295));
        changePanel.setLayout(new java.awt.GridLayout(2, 1));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel5.border.title"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new java.awt.GridLayout(4, 2));

        fanteLittleIcon.setIcon(resourceMap.getIcon("fanteLittleIcon.icon")); // NOI18N
        fanteLittleIcon.setText(resourceMap.getString("fanteLittleIcon.text")); // NOI18N
        fanteLittleIcon.setName("fanteLittleIcon"); // NOI18N
        jPanel5.add(fanteLittleIcon);

        cavaliereLittleIcon.setIcon(resourceMap.getIcon("cavaliereLittleIcon.icon")); // NOI18N
        cavaliereLittleIcon.setText(resourceMap.getString("cavaliereLittleIcon.text")); // NOI18N
        cavaliereLittleIcon.setName("cavaliereLittleIcon"); // NOI18N
        jPanel5.add(cavaliereLittleIcon);

        cannoneLittleIcon.setIcon(resourceMap.getIcon("cannoneLittleIcon.icon")); // NOI18N
        cannoneLittleIcon.setText(resourceMap.getString("cannoneLittleIcon.text")); // NOI18N
        cannoneLittleIcon.setName("cannoneLittleIcon"); // NOI18N
        jPanel5.add(cannoneLittleIcon);

        jollyLittleIcon.setIcon(resourceMap.getIcon("jollyLittleIcon.icon")); // NOI18N
        jollyLittleIcon.setText(resourceMap.getString("jollyLittleIcon.text")); // NOI18N
        jollyLittleIcon.setName("jollyLittleIcon"); // NOI18N
        jPanel5.add(jollyLittleIcon);

        changePanel.add(jPanel5);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel6.border.title"))); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        stimarinforzi.setText(resourceMap.getString("stimarinforzi.text")); // NOI18N
        stimarinforzi.setName("stimarinforzi"); // NOI18N

        scambiaButton.setText(resourceMap.getString("scambiaButton.text")); // NOI18N
        scambiaButton.setName("scambiaButton"); // NOI18N
        scambiaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scambiaButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stimarinforzi)
                    .addComponent(jLabel1))
                .addContainerGap(98, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addComponent(scambiaButton)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(stimarinforzi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(scambiaButton)
                .addContainerGap())
        );

        changePanel.add(jPanel6);

        add(changePanel, java.awt.BorderLayout.EAST);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void selectJollyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectJollyActionPerformed
        // TODO add your handling code here:
        this.adjustList(null, null);
        if(this.selectJolly.isSelected()){
            this.jollyLittleIcon.setText("selezionato");
        }else{
            this.jollyLittleIcon.setText("non selezionato");
        }
    }//GEN-LAST:event_selectJollyActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:

        if(evt.getValueIsAdjusting())
            this.adjustList(jList1,evt);
    }//GEN-LAST:event_jList1ValueChanged

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        // TODO add your handling code here:
        if(evt.getValueIsAdjusting())
            this.adjustList(jList2,evt);
    }//GEN-LAST:event_jList2ValueChanged

    private void jList3ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList3ValueChanged
        // TODO add your handling code here:
        if(evt.getValueIsAdjusting())
            this.adjustList(jList3,evt);
    }//GEN-LAST:event_jList3ValueChanged

    private void scambiaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scambiaButtonActionPerformed
        // TODO add your handling code here:
        if(!Tavolo.getInstance().isTurnoMyGiocatore()){
            return;
        }

        if(Tavolo.getInstance().existLastAttack()){
            return;
        }

        this.controller.ottieniRinforzi(carte[0], carte[1], carte[2]);

        this.jList1.clearSelection();
        this.jList2.clearSelection();
        this.jList3.clearSelection();
        this.selectJolly.setSelected(false);
        if(this.controller.hasJolly()){
            this.selectJolly.setEnabled(true);
        }else{
            this.selectJolly.setEnabled(false);
        }
        this.scambiaButton.setEnabled(false);
        this.jList1.repaint();
        this.jList2.repaint();
        this.jList3.repaint();
        this.fanteLittleIcon.setText("0");
        this.cavaliereLittleIcon.setText("0");
        this.cannoneLittleIcon.setText("0");
        this.jollyLittleIcon.setText("non selezionato");
        this.stimarinforzi.setText("###");

        
    }//GEN-LAST:event_scambiaButtonActionPerformed


    private void adjustList(JList source,javax.swing.event.ListSelectionEvent evt){
        int rinforzi=0;
        this.scambiaButton.setEnabled(false);
        this.stimarinforzi.setText("###");
        int selected=0;
        if(this.selectJolly.isSelected()){
            selected++;
        }
        selected=selected+this.jList1.getSelectedIndices().length;
        selected=selected+this.jList2.getSelectedIndices().length;
        selected=selected+this.jList3.getSelectedIndices().length;
        if(selected>3){
            if(source!=null)
                source.clearSelection();
             this.stimarinforzi.setText("###");
            return;
        }
        if(source==jList1){
            this.fanteLittleIcon.setText(Integer.toString(jList1.getSelectedIndices().length));
        }else if(source==jList2){
            this.cavaliereLittleIcon.setText(Integer.toString(jList2.getSelectedIndices().length));
        }else if(source==jList3){
            this.cannoneLittleIcon.setText(Integer.toString(jList3.getSelectedIndices().length));
        }

        if(selected==3){
            
            int currentIndex=0;
            if(this.selectJolly.isSelected()){
                carte[currentIndex]=this.controller.getJolly().get(0);
                currentIndex++;
            }
            int[] indices1=this.jList1.getSelectedIndices();
            int[] indices2=this.jList2.getSelectedIndices();
            int[] indices3=this.jList3.getSelectedIndices();

            for(int i=0;i<indices1.length;i++){
                carte[currentIndex]=this.controller.getFanti().get(indices1[i]);
                currentIndex++;
            }
            for(int i=0;i<indices2.length;i++){
                carte[currentIndex]=this.controller.getCavalieri().get(indices2[i]);
                currentIndex++;
            }
            for(int i=0;i<indices3.length;i++){
                carte[currentIndex]=this.controller.getCannoni().get(indices3[i]);
                currentIndex++;
            }
            int rinforzo=this.controller.valutaRinforzo(carte[0], carte[1], carte[2]);

            this.stimarinforzi.setText(Integer.toString(rinforzo));
            if(rinforzo<=0){
                this.scambiaButton.setEnabled(false);
            }else if(Tavolo.getInstance().isTurnoMyGiocatore()){
                this.scambiaButton.setEnabled(true);
            }
        }else{
            this.stimarinforzi.setText("###");
        }

       
      
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cannoneIconLabel;
    private javax.swing.JLabel cannoneLittleIcon;
    private javax.swing.JPanel cardPanels;
    private javax.swing.JLabel cavaliereIconLabel;
    private javax.swing.JLabel cavaliereLittleIcon;
    private javax.swing.JPanel changePanel;
    private javax.swing.JLabel fanteIconLabel;
    private javax.swing.JLabel fanteLittleIcon;
    private javax.swing.JPanel firstCardPanel;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jollyLittleIcon;
    private javax.swing.JButton scambiaButton;
    private javax.swing.JPanel secondcardPanel;
    private javax.swing.JCheckBox selectJolly;
    private javax.swing.JLabel stimarinforzi;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
