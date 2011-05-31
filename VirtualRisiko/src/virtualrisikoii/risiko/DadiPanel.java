/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DadiPanel.java
 *
 * Created on 19-mag-2011, 17.56.36
 */

package virtualrisikoii.risiko;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.LogManager;

/**
 *
 * @author Administrator
 */
public class DadiPanel extends javax.swing.JPanel {

    /** Creates new form DadiPanel */


    
    public DadiPanel(String descrizione,int numAtt1,int numAtt2,int numAtt3,int numDif1,int numDif2,int numDif3,int giocAtt, int giocDif) {

         LogManager.getLogManager().reset();

        String coloreGiocatoreAtt=null;
        String coloreGiocatoreDif = null;
       
        switch(giocAtt){
            case 0: coloreGiocatoreAtt="red";
            break;
            case 1: coloreGiocatoreAtt="yellow";
            break;
            case 2: coloreGiocatoreAtt="green";
            break;
            case 3: coloreGiocatoreAtt="black";
            break;
            case 4: coloreGiocatoreAtt="purple";
            break;
            case 5: coloreGiocatoreAtt="blue";
            break;
        }
                switch(giocDif){
            case 0: coloreGiocatoreDif="red";
            break;
            case 1: coloreGiocatoreDif="yellow";
            break;
            case 2: coloreGiocatoreDif="green";
            break;
            case 3: coloreGiocatoreDif="black";
            break;
            case 4: coloreGiocatoreDif="purple";
            break;
            case 5: coloreGiocatoreDif="blue";
            break;
        }

               if(numAtt1 > numDif1) {
            versofreccia1 = "attacco";
            colorefreccia1=coloreGiocatoreAtt;
            Suono.playSound("/virtualrisikoii/resources/dadi/vittoria.wav");
        }
               else {
            versofreccia1 = "difesa";
            colorefreccia1=coloreGiocatoreDif;
              Suono.playSound("/virtualrisikoii/resources/dadi/sconfitta.wav");
        }
                       if(numAtt2 > numDif2) {
            versofreccia2 = "attacco";
            colorefreccia2=coloreGiocatoreAtt;
        }
               else {
            versofreccia2 = "difesa";
            colorefreccia2=coloreGiocatoreDif;
        }
                       if(numAtt3 > numDif3) {
            versofreccia3 = "attacco";
            colorefreccia3=coloreGiocatoreAtt;
        }
               else {
            versofreccia3 = "difesa";
            colorefreccia3=coloreGiocatoreDif;
        }


        String path="/virtualrisikoii/resources/dadi/";

        pathfreccia1=path+"frecce/"+versofreccia1+colorefreccia1+".png";
        pathfreccia2=path+"frecce/"+versofreccia2+colorefreccia2+".png";
        pathfreccia3=path+"frecce/"+versofreccia3+colorefreccia3+".png";

            if(numAtt1==0) {
                pathAtt1 = "/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia1="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
                pathAtt1 = path + coloreGiocatoreAtt + "/" + numAtt1 + ".png";
            }

             if(numAtt2==0) {
                pathAtt2 = "/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia2="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
                pathAtt2 = path + coloreGiocatoreAtt + "/" + numAtt2 + ".png";
            }



             if(numAtt3==0) {
                pathAtt3 = "/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia3="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
                pathAtt3= path+coloreGiocatoreAtt+"/"+numAtt3+".png";
            }

          if(numDif1==0) {
                pathDif1 = "/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia1="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
               pathDif1= path+coloreGiocatoreDif+"/"+numDif1+".png";
            }

            if(numDif2==0) {
                pathDif2 = "/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia2="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
               pathDif2= path+coloreGiocatoreDif+"/"+numDif2+".png";
            }

           if(numDif3==0) {
                pathDif3 = "/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia3="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
                      pathDif3= path+coloreGiocatoreDif+"/"+numDif3+".png";
            }

        
        initComponents();
        this.descrizioneAttacco.setText(descrizione);
        attacco1.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathAtt1)));
        attacco2.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathAtt2)));
        attacco3.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathAtt3)));
        difesa1.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathDif1)));
        difesa2.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathDif2)));
        difesa3.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathDif3)));
        freccia1.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathfreccia1)));
        freccia2.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathfreccia2)));
        freccia3.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathfreccia3)));
       
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descrizioneAttacco = new javax.swing.JLabel();
        attaccoLabel = new javax.swing.JLabel();
        difesaLabel = new javax.swing.JLabel();
        attacco1 = new javax.swing.JLabel();
        attacco2 = new javax.swing.JLabel();
        attacco3 = new javax.swing.JLabel();
        difesa1 = new javax.swing.JLabel();
        difesa2 = new javax.swing.JLabel();
        difesa3 = new javax.swing.JLabel();
        freccia1 = new javax.swing.JLabel();
        freccia2 = new javax.swing.JLabel();
        freccia3 = new javax.swing.JLabel();

        descrizioneAttacco.setFont(new java.awt.Font("Tahoma", 0, 10));
        descrizioneAttacco.setText("Risultato dei Dadi");
        descrizioneAttacco.setName("descrizioneAttacco"); // NOI18N

        attaccoLabel.setText("ATTACCO");
        attaccoLabel.setName("attaccoLabel"); // NOI18N

        difesaLabel.setText("DIFESA");
        difesaLabel.setName("difesaLabel"); // NOI18N

        attacco1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/dadoVuoto.png"))); // NOI18N
        attacco1.setName("attacco1"); // NOI18N

        attacco2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/dadoVuoto.png"))); // NOI18N
        attacco2.setName("attacco2"); // NOI18N

        attacco3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/dadoVuoto.png"))); // NOI18N
        attacco3.setName("attacco3"); // NOI18N

        difesa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/dadoVuoto.png"))); // NOI18N
        difesa1.setName("difesa1"); // NOI18N

        difesa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/dadoVuoto.png"))); // NOI18N
        difesa2.setName("difesa2"); // NOI18N

        difesa3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/dadoVuoto.png"))); // NOI18N
        difesa3.setName("difesa3"); // NOI18N

        freccia1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png"))); // NOI18N
        freccia1.setName("freccia1"); // NOI18N

        freccia2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/dadoVuoto.png"))); // NOI18N
        freccia2.setName("freccia2"); // NOI18N

        freccia3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/dadoVuoto.png"))); // NOI18N
        freccia3.setName("freccia3"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(attacco2)
                            .addComponent(attacco3)
                            .addComponent(attacco1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(freccia1)
                            .addComponent(freccia2)
                            .addComponent(freccia3)))
                    .addComponent(attaccoLabel))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(difesa1)
                        .addComponent(difesa2)
                        .addComponent(difesa3))
                    .addComponent(difesaLabel))
                .addContainerGap())
            .addComponent(descrizioneAttacco, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(descrizioneAttacco, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(attaccoLabel)
                    .addComponent(difesaLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(difesa1)
                        .addComponent(attacco1))
                    .addComponent(freccia1))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(attacco2)
                    .addComponent(freccia2)
                    .addComponent(difesa2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(attacco3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(freccia3)
                        .addComponent(difesa3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attacco1;
    private javax.swing.JLabel attacco2;
    private javax.swing.JLabel attacco3;
    private javax.swing.JLabel attaccoLabel;
    private javax.swing.JLabel descrizioneAttacco;
    private javax.swing.JLabel difesa1;
    private javax.swing.JLabel difesa2;
    private javax.swing.JLabel difesa3;
    private javax.swing.JLabel difesaLabel;
    private javax.swing.JLabel freccia1;
    private javax.swing.JLabel freccia2;
    private javax.swing.JLabel freccia3;
    // End of variables declaration//GEN-END:variables
    private String pathAtt1;
    private String pathAtt2;
    private String pathAtt3;
    private String pathDif1;
    private String pathDif2;
    private String pathDif3;
    private String versofreccia1;
    private String versofreccia2;
    private String versofreccia3;
    private String pathfreccia1;
    private String pathfreccia2;
    private String pathfreccia3;
    private String colorefreccia1;
    private String colorefreccia2;
    private String colorefreccia3;
}
