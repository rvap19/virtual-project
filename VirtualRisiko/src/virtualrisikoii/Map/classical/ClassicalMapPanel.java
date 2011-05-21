/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClassicalMapPanel.java
 *
 * Created on 20-apr-2011, 22.08.01
 */

package virtualrisikoii.Map.classical;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import jxta.communication.Communicator;
import net.jxta.endpoint.Message;
import virtualrisikoii.ActionDialog;
import virtualrisikoii.InformationPanel;
import virtualrisikoii.VirtualRisikoIIApp;
import virtualrisikoii.VirtualRisikoIIEndGameBox;
import virtualrisikoii.listener.ApplianceListener;
import virtualrisikoii.listener.AttackListener;
import virtualrisikoii.listener.ChangeCardListener;
import virtualrisikoii.listener.MovementListener;
import virtualrisikoii.risiko.Attacco;
import virtualrisikoii.risiko.Azione;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Rinforzo;
import virtualrisikoii.risiko.Spostamento;
import virtualrisikoii.risiko.Tavolo;
import virtualrisikoii.risiko.Territorio;
import virtualrisikoii.risiko.dadiGui;

/**
 *
 * @author root
 */
public class ClassicalMapPanel extends javax.swing.JPanel implements ApplianceListener,AttackListener,MovementListener,ChangeCardListener {

    private JLabel[] territoriLabels;
    private Tavolo tavolo;
    private ImageIcon[] icons;
    private InformationPanel informationPanel;

    private Territorio firstSelection;
    private Territorio secondSelection;
    private int truppeSelezionate;

    private Communicator comunicator;


    /** Creates new form ClassicalMapPanel */
    public ClassicalMapPanel() {
        initComponents();
        territoriLabels=new JLabel[42];
        territoriLabels[0]=alaskaLabel;
        territoriLabels[1]=territorioNOLabel;
        territoriLabels[2]=groenlandiaLabel;
        territoriLabels[3]=albertaLabel;
        territoriLabels[4]=ontarioLabel;
        territoriLabels[5]=quebecLabel;
        territoriLabels[6]=statiUnitiOccLabel;
        territoriLabels[7]=statiUnitiOriLabel;
        territoriLabels[8]=messicoLabel;
        territoriLabels[9]=venezuelaLabel;
        territoriLabels[10]=peruLabel;
        territoriLabels[11]=brasileLabel;
        territoriLabels[12]=argentinaLabel;
        territoriLabels[13]=islandaLabel;
        territoriLabels[14]=scandinaviaLabel;
        territoriLabels[15]=granBretagnaLabel;
        territoriLabels[16]=europaSettentrionaleLabel;
        territoriLabels[17]=europaOccidentaleLabel;
        territoriLabels[18]=europaMeridionaleLabel;
        territoriLabels[19]=ucrainaLabel;
        territoriLabels[20]=africaNordLabel;
        territoriLabels[21]=egittoLabel;
        territoriLabels[22]=congoLabel;
        territoriLabels[23]=africaOrientaleLabel;
        territoriLabels[24]=africaSudLabel;
        territoriLabels[25]=madagascarLabel;
        territoriLabels[26]=uraliLabel;
        territoriLabels[27]=siberiaLabel;
        territoriLabels[28]=jacuziaLabel;
        territoriLabels[29]=citaLabel;
        territoriLabels[30]=kamchatkaLabel;
        territoriLabels[31]=giapponeLabel;
        territoriLabels[32]=mongoliaLabel;
        territoriLabels[33]=kanakistanLabel;
        territoriLabels[34]=medioOrienteLabel;
        territoriLabels[35]=indiaLabel;
        territoriLabels[36]=cinaLabel;
        territoriLabels[37]=siamLabel;
        territoriLabels[38]=indonesiaLabel;
        territoriLabels[39]=nuovaGuineaLabel;
        territoriLabels[40]=australiaOrientaleLabel;
        territoriLabels[41]=australiaOccidentaleLabel;

        LabelSelector selector=new LabelSelector();
        for(int i=0;i<territoriLabels.length;i++){
            this.territoriLabels[i].addMouseListener(selector);
        }
        this.comunicator=Communicator.getInstance();
        comunicator.addApplianceListener(this);
        comunicator.addAttackListener(this);
        comunicator.addMovementListener(this);
        comunicator.addChangeCardsListener(this);
    }

   
    public void setInformationPanel(InformationPanel panel){
        this.informationPanel=panel;
    }
    public void initMap(Tavolo tavolo){
        this.tavolo=tavolo;
        this.icons=new ImageIcon[6];
        icons[Giocatore.ROSSO]=new ImageIcon("src/virtualrisikoii/resources/tanks/rosso.png");
        icons[Giocatore.BLU]=new ImageIcon("src/virtualrisikoii/resources/tanks/blu.png");
        icons[Giocatore.GIALLO]=new ImageIcon("src/virtualrisikoii/resources/tanks/giallo.png");
        icons[Giocatore.NERO]=new ImageIcon("src/virtualrisikoii/resources/tanks/nero.png");
        icons[Giocatore.VERDE]=new ImageIcon("src/virtualrisikoii/resources/tanks/verde.png");
        icons[Giocatore.VIOLA]=new ImageIcon("src/virtualrisikoii/resources/tanks/viola.png");
       Territorio[] territori=tavolo.getMappa().getNazioni();
        for(int i=0;i<territori.length;i++){
            this.setLabel(territori[i]);
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

        jLabel2 = new javax.swing.JLabel();
        mapPanel1 = new virtualrisikoii.Map.classical.MapPanel();
        scandinaviaLabel = new javax.swing.JLabel();
        ucrainaLabel = new javax.swing.JLabel();
        granBretagnaLabel = new javax.swing.JLabel();
        uraliLabel = new javax.swing.JLabel();
        siberiaLabel = new javax.swing.JLabel();
        jacuziaLabel = new javax.swing.JLabel();
        kamchatkaLabel = new javax.swing.JLabel();
        alaskaLabel = new javax.swing.JLabel();
        ontarioLabel = new javax.swing.JLabel();
        citaLabel = new javax.swing.JLabel();
        territorioNOLabel = new javax.swing.JLabel();
        albertaLabel = new javax.swing.JLabel();
        statiUnitiOccLabel = new javax.swing.JLabel();
        quebecLabel = new javax.swing.JLabel();
        groenlandiaLabel = new javax.swing.JLabel();
        statiUnitiOriLabel = new javax.swing.JLabel();
        messicoLabel = new javax.swing.JLabel();
        venezuelaLabel = new javax.swing.JLabel();
        brasileLabel = new javax.swing.JLabel();
        peruLabel = new javax.swing.JLabel();
        argentinaLabel = new javax.swing.JLabel();
        australiaOrientaleLabel = new javax.swing.JLabel();
        australiaOccidentaleLabel = new javax.swing.JLabel();
        nuovaGuineaLabel = new javax.swing.JLabel();
        indonesiaLabel = new javax.swing.JLabel();
        siamLabel = new javax.swing.JLabel();
        indiaLabel = new javax.swing.JLabel();
        cinaLabel = new javax.swing.JLabel();
        mongoliaLabel = new javax.swing.JLabel();
        giapponeLabel = new javax.swing.JLabel();
        kanakistanLabel = new javax.swing.JLabel();
        medioOrienteLabel = new javax.swing.JLabel();
        europaSettentrionaleLabel = new javax.swing.JLabel();
        europaMeridionaleLabel = new javax.swing.JLabel();
        europaOccidentaleLabel = new javax.swing.JLabel();
        egittoLabel = new javax.swing.JLabel();
        africaOrientaleLabel = new javax.swing.JLabel();
        africaNordLabel = new javax.swing.JLabel();
        congoLabel = new javax.swing.JLabel();
        africaSudLabel = new javax.swing.JLabel();
        madagascarLabel = new javax.swing.JLabel();
        islandaLabel = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(ClassicalMapPanel.class);
        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        mapPanel1.setName("mapPanel1"); // NOI18N

        scandinaviaLabel.setFont(resourceMap.getFont("scandinaviaLabel.font")); // NOI18N
        scandinaviaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        scandinaviaLabel.setText(resourceMap.getString("scandinaviaLabel.text")); // NOI18N
        scandinaviaLabel.setToolTipText(resourceMap.getString("scandinaviaLabel.toolTipText")); // NOI18N
        scandinaviaLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        scandinaviaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        scandinaviaLabel.setName("scandinaviaLabel"); // NOI18N
        mapPanel1.add(scandinaviaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 60, 70));

        ucrainaLabel.setFont(resourceMap.getFont("ucrainaLabel.font")); // NOI18N
        ucrainaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ucrainaLabel.setText(resourceMap.getString("ucrainaLabel.text")); // NOI18N
        ucrainaLabel.setToolTipText(resourceMap.getString("ucrainaLabel.toolTipText")); // NOI18N
        ucrainaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ucrainaLabel.setName("ucrainaLabel"); // NOI18N
        mapPanel1.add(ucrainaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 60, 110));

        granBretagnaLabel.setFont(resourceMap.getFont("granBretagnaLabel.font")); // NOI18N
        granBretagnaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        granBretagnaLabel.setText(resourceMap.getString("granBretagnaLabel.text")); // NOI18N
        granBretagnaLabel.setToolTipText(resourceMap.getString("granBretagnaLabel.toolTipText")); // NOI18N
        granBretagnaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        granBretagnaLabel.setName("granBretagnaLabel"); // NOI18N
        mapPanel1.add(granBretagnaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 50, 50));

        uraliLabel.setFont(resourceMap.getFont("uraliLabel.font")); // NOI18N
        uraliLabel.setText(resourceMap.getString("uraliLabel.text")); // NOI18N
        uraliLabel.setToolTipText(resourceMap.getString("uraliLabel.toolTipText")); // NOI18N
        uraliLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        uraliLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        uraliLabel.setName("uraliLabel"); // NOI18N
        mapPanel1.add(uraliLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 50, 50));

        siberiaLabel.setFont(resourceMap.getFont("siberiaLabel.font")); // NOI18N
        siberiaLabel.setText(resourceMap.getString("siberiaLabel.text")); // NOI18N
        siberiaLabel.setToolTipText(resourceMap.getString("siberiaLabel.toolTipText")); // NOI18N
        siberiaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        siberiaLabel.setName("siberiaLabel"); // NOI18N
        mapPanel1.add(siberiaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 60, 110));

        jacuziaLabel.setFont(resourceMap.getFont("jacuziaLabel.font")); // NOI18N
        jacuziaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jacuziaLabel.setText(resourceMap.getString("jacuziaLabel.text")); // NOI18N
        jacuziaLabel.setToolTipText(resourceMap.getString("jacuziaLabel.toolTipText")); // NOI18N
        jacuziaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jacuziaLabel.setName("jacuziaLabel"); // NOI18N
        mapPanel1.add(jacuziaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 80, 60));

        kamchatkaLabel.setFont(resourceMap.getFont("kamchatkaLabel.font")); // NOI18N
        kamchatkaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        kamchatkaLabel.setText(resourceMap.getString("kamchatkaLabel.text")); // NOI18N
        kamchatkaLabel.setToolTipText(resourceMap.getString("kamchatkaLabel.toolTipText")); // NOI18N
        kamchatkaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kamchatkaLabel.setName("kamchatkaLabel"); // NOI18N
        mapPanel1.add(kamchatkaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, 70, 80));

        alaskaLabel.setFont(resourceMap.getFont("alaskaLabel.font")); // NOI18N
        alaskaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        alaskaLabel.setText(resourceMap.getString("alaskaLabel.text")); // NOI18N
        alaskaLabel.setToolTipText(resourceMap.getString("alaskaLabel.toolTipText")); // NOI18N
        alaskaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alaskaLabel.setName("alaskaLabel"); // NOI18N
        mapPanel1.add(alaskaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 80, 70));

        ontarioLabel.setFont(resourceMap.getFont("ontarioLabel.font")); // NOI18N
        ontarioLabel.setText(resourceMap.getString("ontarioLabel.text")); // NOI18N
        ontarioLabel.setToolTipText(resourceMap.getString("ontarioLabel.toolTipText")); // NOI18N
        ontarioLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ontarioLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ontarioLabel.setName("ontarioLabel"); // NOI18N
        mapPanel1.add(ontarioLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 160, 70, 60));

        citaLabel.setFont(resourceMap.getFont("citaLabel.font")); // NOI18N
        citaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        citaLabel.setText(resourceMap.getString("citaLabel.text")); // NOI18N
        citaLabel.setToolTipText(resourceMap.getString("citaLabel.toolTipText")); // NOI18N
        citaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        citaLabel.setName("citaLabel"); // NOI18N
        mapPanel1.add(citaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 170, 60, 40));

        territorioNOLabel.setFont(resourceMap.getFont("territorioNOLabel.font")); // NOI18N
        territorioNOLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        territorioNOLabel.setText(resourceMap.getString("territorioNOLabel.text")); // NOI18N
        territorioNOLabel.setToolTipText(resourceMap.getString("territorioNOLabel.toolTipText")); // NOI18N
        territorioNOLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        territorioNOLabel.setName("territorioNOLabel"); // NOI18N
        mapPanel1.add(territorioNOLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 110, 40));

        albertaLabel.setFont(resourceMap.getFont("albertaLabel.font")); // NOI18N
        albertaLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        albertaLabel.setText(resourceMap.getString("albertaLabel.text")); // NOI18N
        albertaLabel.setToolTipText(resourceMap.getString("albertaLabel.toolTipText")); // NOI18N
        albertaLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        albertaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        albertaLabel.setName("albertaLabel"); // NOI18N
        mapPanel1.add(albertaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 160, 70, 60));

        statiUnitiOccLabel.setFont(resourceMap.getFont("statiUnitiOccLabel.font")); // NOI18N
        statiUnitiOccLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statiUnitiOccLabel.setText(resourceMap.getString("statiUnitiOccLabel.text")); // NOI18N
        statiUnitiOccLabel.setToolTipText(resourceMap.getString("statiUnitiOccLabel.toolTipText")); // NOI18N
        statiUnitiOccLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        statiUnitiOccLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        statiUnitiOccLabel.setName("statiUnitiOccLabel"); // NOI18N
        mapPanel1.add(statiUnitiOccLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 230, 60, 60));

        quebecLabel.setFont(resourceMap.getFont("quebecLabel.font")); // NOI18N
        quebecLabel.setText(resourceMap.getString("quebecLabel.text")); // NOI18N
        quebecLabel.setToolTipText(resourceMap.getString("quebecLabel.toolTipText")); // NOI18N
        quebecLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        quebecLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        quebecLabel.setName("quebecLabel"); // NOI18N
        mapPanel1.add(quebecLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 160, 60, 60));

        groenlandiaLabel.setFont(resourceMap.getFont("groenlandiaLabel.font")); // NOI18N
        groenlandiaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        groenlandiaLabel.setText(resourceMap.getString("groenlandiaLabel.text")); // NOI18N
        groenlandiaLabel.setToolTipText(resourceMap.getString("groenlandiaLabel.toolTipText")); // NOI18N
        groenlandiaLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        groenlandiaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groenlandiaLabel.setName("groenlandiaLabel"); // NOI18N
        mapPanel1.add(groenlandiaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 140, 110));

        statiUnitiOriLabel.setFont(resourceMap.getFont("statiUnitiOriLabel.font")); // NOI18N
        statiUnitiOriLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statiUnitiOriLabel.setText(resourceMap.getString("statiUnitiOriLabel.text")); // NOI18N
        statiUnitiOriLabel.setToolTipText(resourceMap.getString("statiUnitiOriLabel.toolTipText")); // NOI18N
        statiUnitiOriLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        statiUnitiOriLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        statiUnitiOriLabel.setName("statiUnitiOriLabel"); // NOI18N
        mapPanel1.add(statiUnitiOriLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 250, 80, 60));

        messicoLabel.setFont(resourceMap.getFont("messicoLabel.font")); // NOI18N
        messicoLabel.setText(resourceMap.getString("messicoLabel.text")); // NOI18N
        messicoLabel.setToolTipText(resourceMap.getString("messicoLabel.toolTipText")); // NOI18N
        messicoLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        messicoLabel.setName("messicoLabel"); // NOI18N
        mapPanel1.add(messicoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 310, 70, 50));

        venezuelaLabel.setFont(resourceMap.getFont("venezuelaLabel.font")); // NOI18N
        venezuelaLabel.setText(resourceMap.getString("venezuelaLabel.text")); // NOI18N
        venezuelaLabel.setToolTipText(resourceMap.getString("venezuelaLabel.toolTipText")); // NOI18N
        venezuelaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        venezuelaLabel.setName("venezuelaLabel"); // NOI18N
        mapPanel1.add(venezuelaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 380, 60, 30));

        brasileLabel.setFont(resourceMap.getFont("brasileLabel.font")); // NOI18N
        brasileLabel.setText(resourceMap.getString("brasileLabel.text")); // NOI18N
        brasileLabel.setToolTipText(resourceMap.getString("brasileLabel.toolTipText")); // NOI18N
        brasileLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        brasileLabel.setName("brasileLabel"); // NOI18N
        mapPanel1.add(brasileLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 400, 120, 60));

        peruLabel.setFont(resourceMap.getFont("peruLabel.font")); // NOI18N
        peruLabel.setText(resourceMap.getString("peruLabel.text")); // NOI18N
        peruLabel.setToolTipText(resourceMap.getString("peruLabel.toolTipText")); // NOI18N
        peruLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        peruLabel.setName("peruLabel"); // NOI18N
        mapPanel1.add(peruLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 490, 60, 30));

        argentinaLabel.setFont(resourceMap.getFont("argentinaLabel.font")); // NOI18N
        argentinaLabel.setText(resourceMap.getString("argentinaLabel.text")); // NOI18N
        argentinaLabel.setToolTipText(resourceMap.getString("argentinaLabel.toolTipText")); // NOI18N
        argentinaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        argentinaLabel.setName("argentinaLabel"); // NOI18N
        mapPanel1.add(argentinaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 70, 80));

        australiaOrientaleLabel.setFont(resourceMap.getFont("australiaOrientaleLabel.font")); // NOI18N
        australiaOrientaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        australiaOrientaleLabel.setText(resourceMap.getString("australiaOrientaleLabel.text")); // NOI18N
        australiaOrientaleLabel.setToolTipText(resourceMap.getString("australiaOrientaleLabel.toolTipText")); // NOI18N
        australiaOrientaleLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        australiaOrientaleLabel.setName("australiaOrientaleLabel"); // NOI18N
        mapPanel1.add(australiaOrientaleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 580, 50, 110));

        australiaOccidentaleLabel.setFont(resourceMap.getFont("australiaOccidentaleLabel.font")); // NOI18N
        australiaOccidentaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        australiaOccidentaleLabel.setText(resourceMap.getString("australiaOccidentaleLabel.text")); // NOI18N
        australiaOccidentaleLabel.setToolTipText(resourceMap.getString("australiaOccidentaleLabel.toolTipText")); // NOI18N
        australiaOccidentaleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        australiaOccidentaleLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        australiaOccidentaleLabel.setName("australiaOccidentaleLabel"); // NOI18N
        mapPanel1.add(australiaOccidentaleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 570, 50, 90));

        nuovaGuineaLabel.setFont(resourceMap.getFont("nuovaGuineaLabel.font")); // NOI18N
        nuovaGuineaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nuovaGuineaLabel.setText(resourceMap.getString("nuovaGuineaLabel.text")); // NOI18N
        nuovaGuineaLabel.setToolTipText(resourceMap.getString("nuovaGuineaLabel.toolTipText")); // NOI18N
        nuovaGuineaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nuovaGuineaLabel.setName("nuovaGuineaLabel"); // NOI18N
        mapPanel1.add(nuovaGuineaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 490, 70, 40));

        indonesiaLabel.setFont(resourceMap.getFont("indonesiaLabel.font")); // NOI18N
        indonesiaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        indonesiaLabel.setText(resourceMap.getString("indonesiaLabel.text")); // NOI18N
        indonesiaLabel.setToolTipText(resourceMap.getString("indonesiaLabel.toolTipText")); // NOI18N
        indonesiaLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        indonesiaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        indonesiaLabel.setName("indonesiaLabel"); // NOI18N
        mapPanel1.add(indonesiaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 460, 80, 70));

        siamLabel.setFont(resourceMap.getFont("siamLabel.font")); // NOI18N
        siamLabel.setText(resourceMap.getString("siamLabel.text")); // NOI18N
        siamLabel.setToolTipText(resourceMap.getString("siamLabel.toolTipText")); // NOI18N
        siamLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        siamLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        siamLabel.setName("siamLabel"); // NOI18N
        mapPanel1.add(siamLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 380, 70, 70));

        indiaLabel.setFont(resourceMap.getFont("indiaLabel.font")); // NOI18N
        indiaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        indiaLabel.setText(resourceMap.getString("indiaLabel.text")); // NOI18N
        indiaLabel.setToolTipText(resourceMap.getString("indiaLabel.toolTipText")); // NOI18N
        indiaLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        indiaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        indiaLabel.setName("indiaLabel"); // NOI18N
        mapPanel1.add(indiaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, 70, 80));

        cinaLabel.setFont(resourceMap.getFont("cinaLabel.font")); // NOI18N
        cinaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cinaLabel.setText(resourceMap.getString("cinaLabel.text")); // NOI18N
        cinaLabel.setToolTipText(resourceMap.getString("cinaLabel.toolTipText")); // NOI18N
        cinaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cinaLabel.setName("cinaLabel"); // NOI18N
        mapPanel1.add(cinaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 290, 130, 50));

        mongoliaLabel.setFont(resourceMap.getFont("mongoliaLabel.font")); // NOI18N
        mongoliaLabel.setText(resourceMap.getString("mongoliaLabel.text")); // NOI18N
        mongoliaLabel.setToolTipText(resourceMap.getString("mongoliaLabel.toolTipText")); // NOI18N
        mongoliaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mongoliaLabel.setName("mongoliaLabel"); // NOI18N
        mapPanel1.add(mongoliaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, 80, 30));

        giapponeLabel.setFont(resourceMap.getFont("giapponeLabel.font")); // NOI18N
        giapponeLabel.setText(resourceMap.getString("giapponeLabel.text")); // NOI18N
        giapponeLabel.setToolTipText(resourceMap.getString("giapponeLabel.toolTipText")); // NOI18N
        giapponeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        giapponeLabel.setName("giapponeLabel"); // NOI18N
        mapPanel1.add(giapponeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 50, 50));

        kanakistanLabel.setFont(resourceMap.getFont("kanakistanLabel.font")); // NOI18N
        kanakistanLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        kanakistanLabel.setText(resourceMap.getString("kanakistanLabel.text")); // NOI18N
        kanakistanLabel.setToolTipText(resourceMap.getString("kanakistanLabel.toolTipText")); // NOI18N
        kanakistanLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        kanakistanLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kanakistanLabel.setName("kanakistanLabel"); // NOI18N
        mapPanel1.add(kanakistanLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 70, 50));

        medioOrienteLabel.setFont(resourceMap.getFont("medioOrienteLabel.font")); // NOI18N
        medioOrienteLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        medioOrienteLabel.setText(resourceMap.getString("medioOrienteLabel.text")); // NOI18N
        medioOrienteLabel.setToolTipText(resourceMap.getString("medioOrienteLabel.toolTipText")); // NOI18N
        medioOrienteLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        medioOrienteLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        medioOrienteLabel.setName("medioOrienteLabel"); // NOI18N
        mapPanel1.add(medioOrienteLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 70, 80));

        europaSettentrionaleLabel.setFont(resourceMap.getFont("europaSettentrionaleLabel.font")); // NOI18N
        europaSettentrionaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        europaSettentrionaleLabel.setText(resourceMap.getString("europaSettentrionaleLabel.text")); // NOI18N
        europaSettentrionaleLabel.setToolTipText(resourceMap.getString("europaSettentrionaleLabel.toolTipText")); // NOI18N
        europaSettentrionaleLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        europaSettentrionaleLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        europaSettentrionaleLabel.setName("europaSettentrionaleLabel"); // NOI18N
        mapPanel1.add(europaSettentrionaleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 70, 40));

        europaMeridionaleLabel.setFont(resourceMap.getFont("europaMeridionaleLabel.font")); // NOI18N
        europaMeridionaleLabel.setText(resourceMap.getString("europaMeridionaleLabel.text")); // NOI18N
        europaMeridionaleLabel.setToolTipText(resourceMap.getString("europaMeridionaleLabel.toolTipText")); // NOI18N
        europaMeridionaleLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        europaMeridionaleLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        europaMeridionaleLabel.setName("europaMeridionaleLabel"); // NOI18N
        mapPanel1.add(europaMeridionaleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 60, 50));

        europaOccidentaleLabel.setFont(resourceMap.getFont("europaOccidentaleLabel.font")); // NOI18N
        europaOccidentaleLabel.setText(resourceMap.getString("europaOccidentaleLabel.text")); // NOI18N
        europaOccidentaleLabel.setToolTipText(resourceMap.getString("europaOccidentaleLabel.toolTipText")); // NOI18N
        europaOccidentaleLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        europaOccidentaleLabel.setName("europaOccidentaleLabel"); // NOI18N
        mapPanel1.add(europaOccidentaleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 50, 50));

        egittoLabel.setFont(resourceMap.getFont("egittoLabel.font")); // NOI18N
        egittoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        egittoLabel.setText(resourceMap.getString("egittoLabel.text")); // NOI18N
        egittoLabel.setToolTipText(resourceMap.getString("egittoLabel.toolTipText")); // NOI18N
        egittoLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        egittoLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        egittoLabel.setName("egittoLabel"); // NOI18N
        mapPanel1.add(egittoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, 70, 40));

        africaOrientaleLabel.setFont(resourceMap.getFont("africaOrientaleLabel.font")); // NOI18N
        africaOrientaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        africaOrientaleLabel.setText(resourceMap.getString("africaOrientaleLabel.text")); // NOI18N
        africaOrientaleLabel.setToolTipText(resourceMap.getString("africaOrientaleLabel.toolTipText")); // NOI18N
        africaOrientaleLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        africaOrientaleLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        africaOrientaleLabel.setName("africaOrientaleLabel"); // NOI18N
        mapPanel1.add(africaOrientaleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 80, 40));

        africaNordLabel.setFont(resourceMap.getFont("africaNordLabel.font")); // NOI18N
        africaNordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        africaNordLabel.setText(resourceMap.getString("africaNordLabel.text")); // NOI18N
        africaNordLabel.setToolTipText(resourceMap.getString("africaNordLabel.toolTipText")); // NOI18N
        africaNordLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        africaNordLabel.setName("africaNordLabel"); // NOI18N
        mapPanel1.add(africaNordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 90, 110));

        congoLabel.setFont(resourceMap.getFont("congoLabel.font")); // NOI18N
        congoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        congoLabel.setText(resourceMap.getString("congoLabel.text")); // NOI18N
        congoLabel.setToolTipText(resourceMap.getString("congoLabel.toolTipText")); // NOI18N
        congoLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        congoLabel.setName("congoLabel"); // NOI18N
        mapPanel1.add(congoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 50, 40));

        africaSudLabel.setFont(resourceMap.getFont("africaSudLabel.font")); // NOI18N
        africaSudLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        africaSudLabel.setText(resourceMap.getString("africaSudLabel.text")); // NOI18N
        africaSudLabel.setToolTipText(resourceMap.getString("africaSudLabel.toolTipText")); // NOI18N
        africaSudLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        africaSudLabel.setName("africaSudLabel"); // NOI18N
        mapPanel1.add(africaSudLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 580, 60, 90));

        madagascarLabel.setFont(resourceMap.getFont("madagascarLabel.font")); // NOI18N
        madagascarLabel.setText(resourceMap.getString("madagascarLabel.text")); // NOI18N
        madagascarLabel.setToolTipText(resourceMap.getString("madagascarLabel.toolTipText")); // NOI18N
        madagascarLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        madagascarLabel.setName("madagascarLabel"); // NOI18N
        mapPanel1.add(madagascarLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 550, 50, 70));

        islandaLabel.setFont(resourceMap.getFont("islandaLabel.font")); // NOI18N
        islandaLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        islandaLabel.setText(resourceMap.getString("islandaLabel.text")); // NOI18N
        islandaLabel.setToolTipText(resourceMap.getString("islandaLabel.toolTipText")); // NOI18N
        islandaLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        islandaLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        islandaLabel.setName("islandaLabel"); // NOI18N
        mapPanel1.add(islandaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 50, 40));

        add(mapPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void makeAction(JLabel label,java.awt.event.MouseEvent event){
        if(!tavolo.isTurnoMyGiocatore()){
            return;
        }

        Territorio t=this.getTerritorio(label);
        if(this.tavolo.isInizializzazione()){
            if(truppeSelezionate<3&&this.tavolo.getGiocatoreCorrente().getNumeroTruppe()>0){
                
                if(tavolo.assegnaUnita(t)){
                    try {
                        Message msg = comunicator.createApplicanceMessage(1, t.getCodice());
                        comunicator.sendMessage(msg);
                       
                        truppeSelezionate++;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } 
                }
            }

            this.setLabel(t);

            if((truppeSelezionate==3)||(tavolo.getGiocatoreCorrente().getNumeroTruppe()==0)){
                try{

                    System.out.println(tavolo.getTurno()+" sono dentrooooooooooooooooooooooooooooooooooooooooooooooooo"+tavolo.getGiocatori().size());
                    Thread.sleep(2000);
                    if((tavolo.getTurno()==(tavolo.getGiocatori().size()-1))&&(this.tavolo.getGiocatoreCorrente().getNumeroTruppe()==0)){
                        tavolo.setInizializzazione(false);
                    }
                    tavolo.passaTurno();

                    Message msg=comunicator.createPassesMessage(tavolo.getTurnoSuccessivo());
                    
                    comunicator.sendMessage(msg);
                   
                    
                }catch (Exception ex) {
                    ex.printStackTrace();
                } 
                this.informationPanel.updateDatiGiocatore(tavolo.getGiocatoreCorrente());
                truppeSelezionate=0;
            }
            
           /* if(!tavolo.isInizializzazione()){
                tavolo.avviaGioco();
                
                this.informationPanel.updateDatiGiocatore(tavolo.getGiocatoreCorrente());
                truppeSelezionate=0;
            }*/
            return;
        }

        Giocatore attaccante=null;
        Giocatore difensore=null;

        if(this.tavolo.getGiocatoreCorrente().getNumeroTruppe()>0&&t.getOccupante()==this.tavolo.getGiocatoreCorrente()){
            if(tavolo.assegnaUnita(t)){
                try {
                        Message msg = comunicator.createApplicanceMessage(1, t.getCodice());
                        comunicator.sendMessage(msg);
                        

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } 
                setLabel(t);
            }
            return;
        }

        if(firstSelection==null){
            
            if(this.tavolo.getGiocatoreCorrente()!=this.getTerritorio(label).getOccupante()){
                return;
            }
            if(this.getTerritorio(label).getNumeroUnita()>1){
                firstSelection=this.getTerritorio(label);
            }
            
            return;
        }

        Territorio currentSelection=this.getTerritorio(label);

        VirtualRisikoIIEndGameBox endGameBox;
        if(currentSelection==firstSelection){
         /*   if(event.getButton()==event.BUTTON1&&firstSelection.getNumeroUnita()>truppeSelezionate+1){
                this.truppeSelezionate++;
                label.setText(Integer.toString(firstSelection.getNumeroUnita()-truppeSelezionate));

            }else if(event.getButton()==event.BUTTON2&&truppeSelezionate>0){
                this.truppeSelezionate--;
                label.setText(Integer.toString(firstSelection.getNumeroUnita()-truppeSelezionate));
            }*/
            return ;

        }else {
            this.secondSelection=currentSelection;



            Azione azione=tavolo.preparaAttacco(firstSelection, secondSelection);

            if(azione!=null){
                 attaccante=firstSelection.getOccupante();
                 difensore=secondSelection.getOccupante();

                  truppeSelezionate=this.showActionDialog(true, firstSelection, secondSelection);
                  if(truppeSelezionate<0){
                      firstSelection=null;
                      secondSelection=null;
                      return;
                  }
                  azione.setNumeroTruppe(truppeSelezionate);
                tavolo.eseguiAttacco((Attacco)azione);
                try {
                        Message msg = comunicator.createAttackMessage(truppeSelezionate, firstSelection.getCodice(), secondSelection.getCodice());
                        comunicator.sendMessage(msg);
                      
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } 
                
                
                    
                
                
            }else{
                azione=tavolo.preparaSpostamento(firstSelection, secondSelection);

                if(azione!=null){
                    truppeSelezionate=this.showActionDialog(false, firstSelection  , secondSelection);
                    if(truppeSelezionate<0){
                        firstSelection=null;
                        secondSelection=null;
                        return;
                    }
                    azione.setNumeroTruppe(truppeSelezionate);
                    tavolo.eseguiSpostamento((Spostamento) azione);

                    tavolo.passaTurno();
                    this.informationPanel.updateDatiGiocatore(tavolo.getGiocatoreCorrente());
                    try {
                        Message msg = comunicator.createMovementMessage(truppeSelezionate, firstSelection.getCodice(),secondSelection.getCodice());
                        comunicator.sendMessage(msg);
                        Thread.sleep(3000);
                        msg=comunicator.createPassesMessage(tavolo.getTurnoSuccessivo());
                        comunicator.sendMessage(msg);
                       

                    } catch (Exception ex) {
                       ex.printStackTrace();
                    } 
                    
                }
            }

            //this.firstSelection.setNumeroUnita(firstSelection.getNumeroUnita()+truppeSelezionate);
            truppeSelezionate=0;

            if(azione!=null){
                this.informationPanel.appendActionInHistory(azione.toString()); //setAzione(azione.toString());
                this.setLabel(azione.getDaTerritorio());
                this.setLabel(azione.getaTerritorio());
                firstSelection=null;
                if(azione.isAttacco()){

                    //variabili per i dati di Luigi
                    int [] att={0,0,0};
                    int [] dif={0,0,0};

                    int[] a=((Attacco)azione).getPunteggio();
                    String s="";
                    for(int i=0;i<a.length;i++){
                        s=s+" - "+Integer.toString(a[i]);
                        att[i]=a[i];
                    }
                    this.informationPanel.appendActionInHistory(s);
                    
                    a=((Attacco)azione).getPunteggioAvversario();
                     s="";
                    for(int i=0;i<a.length;i++){
                        s=s+" - "+Integer.toString(a[i]);
                        dif[i]=a[i];
                    }
                    this.informationPanel.appendActionInHistory(s);

                    //lancia il panel di Luigi
                    new dadiGui("Attacco da "+azione.getDaTerritorio().getNome()+" a "+azione.getaTerritorio(),att[0],att[1],att[2],dif[0],dif[1],dif[2],attaccante.getID(),difensore.getID()).setVisible(true);


                }

                //azione diversa da null::controllare stato obiettivi
                if(this.tavolo.controllaObiettivoGiocatore(this.tavolo.getGiocatoreCorrente())){

                    JFrame mainFrame = VirtualRisikoIIApp.getApplication().getMainFrame();
                    endGameBox = new VirtualRisikoIIEndGameBox(mainFrame);
                    endGameBox.setLocationRelativeTo(mainFrame);

                     VirtualRisikoIIApp.getApplication().show(endGameBox);
                     System.exit(0);
                }
                
            }else{
                setLabel(firstSelection);
                firstSelection=null;
                secondSelection=null;
                this.truppeSelezionate=0;
            }
        }
    }

   private void setLabel(Territorio territorio){
       Giocatore giocatore=territorio.getOccupante();
       JLabel label=this.territoriLabels[territorio.getCodice()];
       label.setText(Integer.toString(territorio.getNumeroUnita()));
       label.setIcon(icons[giocatore.getID()]);
   }

   private int showActionDialog(boolean isAttacco,Territorio daTerritorio,Territorio aTerritorio){
       String title="Spostamento ";
       int maxTruppe=daTerritorio.getNumeroUnita()-1;
       if(isAttacco){
           title="Attacco";
           if(maxTruppe>3){
               maxTruppe=3;
           }
       }

       if(!daTerritorio.confina(aTerritorio)){
           return -1;
       }


       ActionDialog dialog=new ActionDialog(null, true);
       dialog.setTitle(title);
       dialog.getDaTerritorioTextName().setText(daTerritorio.getNome());
       dialog.getaTerritorioTextName().setText(aTerritorio.getNome());
       dialog.setMaximum(maxTruppe);
       dialog.setMinimum(1);
       dialog.setValue(maxTruppe/2);
       dialog.setVisible(true);

       if(dialog.getReturnStatus()==ActionDialog.RET_OK){
           return Integer.parseInt(dialog.getNumeroTruppeText().getText());
       }

       return -1;
   }
   private Territorio getTerritorio(JLabel label){
       int size=this.territoriLabels.length;
       boolean trovato=false;
       int i;
       for(i=0;i<size&&!trovato;i++){
           trovato=(label==territoriLabels[i]);
       }
       return this.tavolo.getMappa().getTerritorio(i-1);
   }

    public void updateAppliance(int troops_number, int region) {

        Territorio territorio=this.tavolo.getMappa().getTerritorio(region);
        String message="Il "+territorio.getOccupante().getNome()+" posiziona "+troops_number+" in "+territorio.getNome();
        this.informationPanel.appendActionInHistory(message);
        System.out.println("ricevuto messaggio di appliance per territorio "+territorio.getNome()+" di "+troops_number+" unita");
        if(tavolo.assegnaUnita(troops_number, territorio)){
            this.territoriLabels[region].setText(Integer.toString(territorio.getNumeroUnita()));
        }else{
            System.out.println("errore comunicazione appliance");
            
        }
        //showInfo("Disposizione", message);
    }

    public void updateAttack(int troops_number, int from, int to) {
        Territorio fromTerritorio=this.tavolo.getMappa().getTerritorio(from);
        Territorio toTerritorio=this.tavolo.getMappa().getTerritorio(to);
        Giocatore  attaccante=fromTerritorio.getOccupante();
        Giocatore difensore=toTerritorio.getOccupante();
        Azione azione=tavolo.preparaAttacco(fromTerritorio, toTerritorio);
        VirtualRisikoIIEndGameBox endGameBox;
        System.out.println("ricevuto messaggio di attacco da territorio "+fromTerritorio.getNome()+"a territorio "+toTerritorio.getNome()+" con "+troops_number+" unita");
            if(azione!=null){
                azione.setNumeroTruppe(troops_number);
                tavolo.eseguiAttacco((Attacco)azione);

                this.informationPanel.appendActionInHistory(azione.toString());
                this.setLabel(azione.getDaTerritorio());
                this.setLabel(azione.getaTerritorio());
                String message="Il "+fromTerritorio.getOccupante().getNome()+" attacca da "+fromTerritorio.getNome()+"\n"+" a "+toTerritorio.getNome()+"\n"+" con "+troops_number+" unita"+
                        "attacco : ";
                if(azione.isAttacco()){

                    //variabili per i dati di Luigi
                    int [] att={0,0,0};
                    int [] dif={0,0,0};

                    int[] a=((Attacco)azione).getPunteggio();
                    String s="";
                    for(int i=0;i<a.length;i++){
                        s=s+" - "+Integer.toString(a[i]);
                        att[i]=a[i];
                    }
                    this.informationPanel.appendActionInHistory(s);
                    message=message+s+"\n"+"difesa : " ;
                    a=((Attacco)azione).getPunteggioAvversario();
                     s="";
                    for(int i=0;i<a.length;i++){
                        s=s+" - "+Integer.toString(a[i]);
                        dif[i]=a[i];
                    }
                     message=message+s;
                    this.informationPanel.appendActionInHistory(s);

                    //lancia il panel di Luigi
                    new dadiGui("Attacco da "+azione.getDaTerritorio()+" a "+azione.getaTerritorio(),att[0],att[1],att[2],dif[0],dif[1],dif[2],attaccante.getID(),difensore.getID()).setVisible(true);
                

                }

                //azione diversa da null::controllare stato obiettivi
                if(this.tavolo.controllaObiettivoGiocatore(this.tavolo.getGiocatoreCorrente())){

                    JFrame mainFrame = VirtualRisikoIIApp.getApplication().getMainFrame();
                    endGameBox = new VirtualRisikoIIEndGameBox(mainFrame);
                    endGameBox.setLocationRelativeTo(mainFrame);

                     VirtualRisikoIIApp.getApplication().show(endGameBox);
                     System.exit(0);
                }

            }else{
            System.out.println("errore comunicazione attacco");
            
        }

    }

    public void updateMovement(int troops_number, int from, int to) {
        Territorio fromTerritorio=this.tavolo.getMappa().getTerritorio(from);
        Territorio toTerritorio=this.tavolo.getMappa().getTerritorio(to);
        System.out.println("Spostamento :Il "+tavolo.getGiocatoreCorrente()+" sposta da "+fromTerritorio.getNome()+" a "+toTerritorio.getNome()+" con "+troops_number+" unita");
        Azione azione=tavolo.preparaSpostamento(fromTerritorio, toTerritorio);
        VirtualRisikoIIEndGameBox endGameBox;
        if(azione!=null){
            azione.setNumeroTruppe(troops_number);
            tavolo.eseguiSpostamento((Spostamento) azione);

                this.informationPanel.appendActionInHistory(azione.toString());
                this.setLabel(azione.getDaTerritorio());
                this.setLabel(azione.getaTerritorio());

                

                //azione diversa da null::controllare stato obiettivi
                if(this.tavolo.controllaObiettivoGiocatore(this.tavolo.getGiocatoreCorrente())){

                    JFrame mainFrame = VirtualRisikoIIApp.getApplication().getMainFrame();
                    endGameBox = new VirtualRisikoIIEndGameBox(mainFrame);
                    endGameBox.setLocationRelativeTo(mainFrame);

                     VirtualRisikoIIApp.getApplication().show(endGameBox);
                     System.exit(0);
                }

            }

    }

    public void updateChangeCards(int card1, int card2, int card3) {

        Tavolo tavolo=Tavolo.getInstance();
        Giocatore g=tavolo.getGiocatoreCorrente();
        Carta c1=g.getCarta(card1);
        Carta c2=g.getCarta(card2);
        Carta c3=g.getCarta(card3);
        int rinforzi=Rinforzo.getRinfornzo(g, c1, c2, c3, tavolo.getMappa());
        g.setNumeroTruppe(g.getNumeroTruppe()+rinforzi);
        System.out.println("Ricevuta cambio dal "+g.getNome()+" con "+c1.getTerritorio().getNome()+" "+c2.getTerritorio().getNome()+" "+c3.getTerritorio().getNome());
        this.informationPanel.updateDatiGiocatore(g);
        List<Carta> carte=tavolo.getCarte();
        carte.add(c1);
        carte.add(c2);
        carte.add(c3);

    }

   private class LabelSelector extends MouseAdapter{
       public void mouseClicked(MouseEvent e){
           makeAction((JLabel)e.getSource(),e);
       }
   }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel africaNordLabel;
    private javax.swing.JLabel africaOrientaleLabel;
    private javax.swing.JLabel africaSudLabel;
    private javax.swing.JLabel alaskaLabel;
    private javax.swing.JLabel albertaLabel;
    private javax.swing.JLabel argentinaLabel;
    private javax.swing.JLabel australiaOccidentaleLabel;
    private javax.swing.JLabel australiaOrientaleLabel;
    private javax.swing.JLabel brasileLabel;
    private javax.swing.JLabel cinaLabel;
    private javax.swing.JLabel citaLabel;
    private javax.swing.JLabel congoLabel;
    private javax.swing.JLabel egittoLabel;
    private javax.swing.JLabel europaMeridionaleLabel;
    private javax.swing.JLabel europaOccidentaleLabel;
    private javax.swing.JLabel europaSettentrionaleLabel;
    private javax.swing.JLabel giapponeLabel;
    private javax.swing.JLabel granBretagnaLabel;
    private javax.swing.JLabel groenlandiaLabel;
    private javax.swing.JLabel indiaLabel;
    private javax.swing.JLabel indonesiaLabel;
    private javax.swing.JLabel islandaLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jacuziaLabel;
    private javax.swing.JLabel kamchatkaLabel;
    private javax.swing.JLabel kanakistanLabel;
    private javax.swing.JLabel madagascarLabel;
    private virtualrisikoii.Map.classical.MapPanel mapPanel1;
    private javax.swing.JLabel medioOrienteLabel;
    private javax.swing.JLabel messicoLabel;
    private javax.swing.JLabel mongoliaLabel;
    private javax.swing.JLabel nuovaGuineaLabel;
    private javax.swing.JLabel ontarioLabel;
    private javax.swing.JLabel peruLabel;
    private javax.swing.JLabel quebecLabel;
    private javax.swing.JLabel scandinaviaLabel;
    private javax.swing.JLabel siamLabel;
    private javax.swing.JLabel siberiaLabel;
    private javax.swing.JLabel statiUnitiOccLabel;
    private javax.swing.JLabel statiUnitiOriLabel;
    private javax.swing.JLabel territorioNOLabel;
    private javax.swing.JLabel ucrainaLabel;
    private javax.swing.JLabel uraliLabel;
    private javax.swing.JLabel venezuelaLabel;
    // End of variables declaration//GEN-END:variables


}
