/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InformationPanel.java
 *
 * Created on 30-apr-2011, 12.31.39
 */

package virtualrisikoii;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import middle.EventTypes;
import middle.event.ChatEvent;
import middle.event.RisikoEvent;
import middle.listener.ChatEventListener;
import middle.messages.ChatMessage;

import services.GameController;
import services.HistoryListener;
import services.PlayerDataListener;
import services.TimeoutNotifier;

import virtualrisikoii.risiko.Suono;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class InformationPanel extends javax.swing.JPanel implements ChatEventListener,HistoryListener,PlayerDataListener,TimeoutNotifier{

   private GameController controller;


    /** Creates new form InformationPanel */
    public InformationPanel() {
        initComponents();
       controller=GameController.getInstance();
        int idObiettivo=controller.getIDObiettivo()+1;
        obiettivoLabel.setIcon(new ImageIcon("src/virtualrisikoii/resources/obiettivi/"+Tavolo.getInstance().getNameMap()+"/"+idObiettivo+".jpg"));
      this.obiettivoLabel.setToolTipText(controller.getDescrizioneObiettivo());
        
        TitledBorder border= (TitledBorder) this.getBorder();
        border.setTitle("Benvenuto "+controller.getNomeMyGiocatore());
        border= (TitledBorder) this.jPanel1.getBorder();
        border.setTitle("Obiettivo "+controller.getNomeMyGiocatore());
        
        controller.getMiddle().addListener(EventTypes.CHAT, this);
        controller.setPlayerDataListener(this);
        controller.setHistoryListener(this);
        controller.initCurrentPlayerData();
        controller.setTimeoutNotifier(this);
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
        obiettivoLabel = new javax.swing.JLabel();
        passaTurnoButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gameInformationArea = new javax.swing.JTextArea();
        turnoPanel = new javax.swing.JPanel();
        turnoLabel = new javax.swing.JLabel();
        truppeLabel = new javax.swing.JLabel();
        arnatedisposteLabel = new javax.swing.JLabel();
        terrLabel = new javax.swing.JLabel();
        currentTurnInfoLabel = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(InformationPanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("Form.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("Form.border.titleFont"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(255, 727));
        setMinimumSize(new java.awt.Dimension(255, 727));
        setName("Form"); // NOI18N

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(235, 188));
        jPanel1.setMinimumSize(new java.awt.Dimension(235, 188));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(235, 188));
        jPanel1.setRequestFocusEnabled(false);

        obiettivoLabel.setIcon(resourceMap.getIcon("obiettivoLabel.icon")); // NOI18N
        obiettivoLabel.setText(resourceMap.getString("obiettivoLabel.text")); // NOI18N
        obiettivoLabel.setName("obiettivoLabel"); // NOI18N

        passaTurnoButton.setText(resourceMap.getString("passaTurnoButton.text")); // NOI18N
        passaTurnoButton.setName("passaTurnoButton"); // NOI18N
        passaTurnoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passaTurnoButtonActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        timeLabel.setText(resourceMap.getString("timeLabel.text")); // NOI18N
        timeLabel.setName("timeLabel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(obiettivoLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(passaTurnoButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(obiettivoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passaTurnoButton)
                    .addComponent(jLabel1)
                    .addComponent(timeLabel)))
        );

        jPanel4.setBackground(resourceMap.getColor("jPanel4.background")); // NOI18N
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setMaximumSize(new java.awt.Dimension(235, 202));
        jPanel4.setMinimumSize(new java.awt.Dimension(235, 202));
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        chatTextArea.setColumns(20);
        chatTextArea.setEditable(false);
        chatTextArea.setFont(resourceMap.getFont("chatTextArea.font")); // NOI18N
        chatTextArea.setLineWrap(true);
        chatTextArea.setRows(5);
        chatTextArea.setWrapStyleWord(true);
        chatTextArea.setName("chatTextArea"); // NOI18N
        jScrollPane2.setViewportView(chatTextArea);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(235, 215));
        jPanel2.setMinimumSize(new java.awt.Dimension(235, 215));
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        gameInformationArea.setColumns(20);
        gameInformationArea.setEditable(false);
        gameInformationArea.setFont(resourceMap.getFont("gameInformationArea.font")); // NOI18N
        gameInformationArea.setLineWrap(true);
        gameInformationArea.setRows(5);
        gameInformationArea.setWrapStyleWord(true);
        gameInformationArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gameInformationArea.setDropMode(javax.swing.DropMode.INSERT);
        gameInformationArea.setName("gameInformationArea"); // NOI18N
        jScrollPane1.setViewportView(gameInformationArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addContainerGap())
        );

        turnoPanel.setBackground(resourceMap.getColor("turnoPanel.background")); // NOI18N
        turnoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("turnoPanel.border.title"))); // NOI18N
        turnoPanel.setMaximumSize(new java.awt.Dimension(235, 75));
        turnoPanel.setMinimumSize(new java.awt.Dimension(235, 75));
        turnoPanel.setName("turnoPanel"); // NOI18N
        turnoPanel.setPreferredSize(new java.awt.Dimension(235, 75));

        turnoLabel.setFont(resourceMap.getFont("turnoLabel.font")); // NOI18N
        turnoLabel.setText(resourceMap.getString("turnoLabel.text")); // NOI18N
        turnoLabel.setName("turnoLabel"); // NOI18N

        truppeLabel.setFont(resourceMap.getFont("turnoLabel.font")); // NOI18N
        truppeLabel.setText(resourceMap.getString("truppeLabel.text")); // NOI18N
        truppeLabel.setName("truppeLabel"); // NOI18N

        arnatedisposteLabel.setFont(resourceMap.getFont("turnoLabel.font")); // NOI18N
        arnatedisposteLabel.setText(resourceMap.getString("arnatedisposteLabel.text")); // NOI18N
        arnatedisposteLabel.setName("arnatedisposteLabel"); // NOI18N

        terrLabel.setFont(resourceMap.getFont("turnoLabel.font")); // NOI18N
        terrLabel.setText(resourceMap.getString("terrLabel.text")); // NOI18N
        terrLabel.setName("terrLabel"); // NOI18N

        currentTurnInfoLabel.setFont(resourceMap.getFont("currentTurnInfoLabel.font")); // NOI18N
        currentTurnInfoLabel.setText(resourceMap.getString("currentTurnInfoLabel.text")); // NOI18N
        currentTurnInfoLabel.setName("currentTurnInfoLabel"); // NOI18N

        javax.swing.GroupLayout turnoPanelLayout = new javax.swing.GroupLayout(turnoPanel);
        turnoPanel.setLayout(turnoPanelLayout);
        turnoPanelLayout.setHorizontalGroup(
            turnoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(turnoPanelLayout.createSequentialGroup()
                .addComponent(turnoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentTurnInfoLabel)
                .addGap(69, 69, 69))
            .addGroup(turnoPanelLayout.createSequentialGroup()
                .addComponent(truppeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(arnatedisposteLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(terrLabel)
                .addGap(101, 101, 101))
        );
        turnoPanelLayout.setVerticalGroup(
            turnoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(turnoPanelLayout.createSequentialGroup()
                .addGroup(turnoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(turnoLabel)
                    .addComponent(currentTurnInfoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(turnoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(truppeLabel)
                    .addComponent(arnatedisposteLabel)
                    .addComponent(terrLabel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(turnoPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(turnoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void passaTurnoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passaTurnoButtonActionPerformed
        try {
            // TODO add your handling code here:
            controller.passaTurno();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }//GEN-LAST:event_passaTurnoButtonActionPerformed



    public void updateDatiGiocatore(String nomeGiocatoreDiTurno, int numeroTruppeDisponibili, int numeroTruppeDisposte, int numeroTerritori) {
        this.turnoLabel.setText("Turno : "+nomeGiocatoreDiTurno);
        this.truppeLabel.setText("truppe : "+numeroTruppeDisponibili);
        this.arnatedisposteLabel.setText("armate : "+numeroTruppeDisposte);
        this.terrLabel.setText("territori : "+numeroTerritori);

        this.currentTurnInfoLabel.setText("turno "+Tavolo.getInstance().getTurnElapsed()+" di "+Tavolo.getInstance().getMaxTurns());
        this.repaint();
    }



    public void appendActionInHistory(String action){
        this.gameInformationArea.append(action+"\n");
        this.gameInformationArea.setCaretPosition(this.gameInformationArea.getDocument().getLength());
    }

    public void setRemaingTime(int time) {
        timeLabel.setText(Integer.toString(time));
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel arnatedisposteLabel;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JLabel currentTurnInfoLabel;
    private javax.swing.JTextArea gameInformationArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel obiettivoLabel;
    private javax.swing.JButton passaTurnoButton;
    private javax.swing.JLabel terrLabel;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel truppeLabel;
    private javax.swing.JLabel turnoLabel;
    private javax.swing.JPanel turnoPanel;
    // End of variables declaration//GEN-END:variables

    public void notify(ChatEvent event) {
        ChatMessage msg=(ChatMessage)event.getSource();
        String from=msg.getFrom();
        String to=msg.getTo();
        String messageString=msg.getMessageString();
        String me=Tavolo.getInstance().getMyGiocatore().getNome();
        if(to.equals(ChatEvent.TO_ALL)){
           this.chatTextArea.append(from +" > "+messageString+"\n");
           this.chatTextArea.setCaretPosition(this.chatTextArea.getDocument().getLength());
        }else if(to.equals(me)||from.equals(me)){
            this.chatTextArea.append("(PRIVATO) "+from+" > "+messageString+"\n");
            this.chatTextArea.setCaretPosition(this.chatTextArea.getDocument().getLength());
        }
    }
    
    

    public void timeoutNotify() {
       
    }

    public void remaingTimeNotify(int remaing) {
        this.setRemaingTime(remaing);
        if(remaing==11){
             Suono.playSound("/virtualrisikoii/resources/dadi/time.wav");
             
        }
    }

    public void notify(RisikoEvent event) {
        this.notify((ChatEvent)event);
    }

   





    

    
}
