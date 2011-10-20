package virtualrisikoii;

import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JFrame;
import virtualrisikoii.risiko.Suono;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFrameDadi.java
 *
 * Created on 23-mag-2011, 12.48.47
 */

/**
 *
 * @author Administrator
 */
public class JFrameDadi extends javax.swing.JFrame implements Runnable {
    Thread animazione;
     private String coloreGiocatoreAtt=null;
     private String coloreGiocatoreDif = null;
    Image img;
    Image imgDadoVuoto, imgFrecciaVuota;
    private int numAtt1;
    private int numAtt2;
    private int numAtt3;
    private int numDif1;
    private int numDif2;
    private int numDif3;
    private int contVittorie=0;
    private int contSconfitte=0;


    /** Creates new form JFrameDadi */
   public JFrameDadi(String descrizione,int numAtt1,int numAtt2,int numAtt3,int numDif1,int numDif2,int numDif3,int giocAtt, int giocDif) {

       this.numAtt1=numAtt1;
       this.numAtt2=numAtt2;
       this.numAtt3=numAtt3;
       this.numDif1=numDif1;
       this.numDif2=numDif2;
       this.numDif3=numDif3;
       setCenterScreen(this);
       initComponents();
       TuoThread tt = new TuoThread( this, 10000);
       tt.start();

         //String coloreGiocatoreAtt=null;
       // String coloreGiocatoreDif = null;

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
            if(numAtt1!=0 && numDif1!=0)
            contVittorie++;
           // Suono.playSound("/virtualrisikoii/resources/dadi/vittoria.wav");
        }
               else {
            versofreccia1 = "difesa";
            colorefreccia1=coloreGiocatoreDif;
            if(numAtt1!=0 && numDif1!=0)
            contSconfitte++;
             // Suono.playSound("/virtualrisikoii/resources/dadi/sconfitta.wav");
        }
                       if(numAtt2 > numDif2) {
            versofreccia2 = "attacco";
            colorefreccia2=coloreGiocatoreAtt;
            if(numAtt2!=0 && numDif2!=0)
            contVittorie++;
        }
               else {
            versofreccia2 = "difesa";
            colorefreccia2=coloreGiocatoreDif;
            if(numAtt2!=0 && numDif2!=0)
            contSconfitte++;
        }
                       if(numAtt3 > numDif3) {
            versofreccia3 = "attacco";
            colorefreccia3=coloreGiocatoreAtt;
            if(numAtt3!=0 && numDif3!=0)
            contVittorie++;
        }
               else {
            versofreccia3 = "difesa";
            colorefreccia3=coloreGiocatoreDif;
            if(numAtt3!=0 && numDif3!=0)
            contSconfitte++;
        }

         String path="src/virtualrisikoii/resources/dadi/";

        pathfreccia1=path+"frecce/"+versofreccia1+colorefreccia1+".png";
        pathfreccia2=path+"frecce/"+versofreccia2+colorefreccia2+".png";
        pathfreccia3=path+"frecce/"+versofreccia3+colorefreccia3+".png";

            if(numAtt1==0) {
                pathAtt1 = "src/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia1="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
                pathAtt1 = path + coloreGiocatoreAtt + "/" + numAtt1 + ".png";
            }

             if(numAtt2==0) {
                pathAtt2 = "src/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia2="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
                pathAtt2 = path + coloreGiocatoreAtt + "/" + numAtt2 + ".png";
            }



             if(numAtt3==0) {
                pathAtt3 = "src/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia3="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
                pathAtt3= path+coloreGiocatoreAtt+"/"+numAtt3+".png";
            }

          if(numDif1==0) {
                pathDif1 = "src/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia1="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
               pathDif1= path+coloreGiocatoreDif+"/"+numDif1+".png";
            }

            if(numDif2==0) {
                pathDif2 = "src/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia2="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
               pathDif2= path+coloreGiocatoreDif+"/"+numDif2+".png";
            }

           if(numDif3==0) {
                pathDif3 = "src/virtualrisikoii/resources/dadi/dadoVuoto.png";
                pathfreccia3="/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png";
            }
            else {
                      pathDif3= path+coloreGiocatoreDif+"/"+numDif3+".png";
            }

         this.descrizioneAttacco.setText(descrizione);
          imgDadoVuoto=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/dadoVuoto.png");
          dadoAtt1.setImage(imgDadoVuoto);
          dadoAtt1.repaint();
          dadoAtt2.setImage(imgDadoVuoto);
          dadoAtt2.repaint();
          dadoAtt2.setImage(imgDadoVuoto);
          dadoAtt2.repaint();
          dadoDif1.setImage(imgDadoVuoto);
          dadoDif1.repaint();
          dadoDif2.setImage(imgDadoVuoto);
          dadoDif2.repaint();
          dadoDif3.setImage(imgDadoVuoto);
          dadoDif3.repaint();
          //imgFrecciaVuota=Toolkit.getDefaultToolkit().createImage("/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png");
          
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dadoAtt1 = new virtualrisikoii.JPanelDadi();
        dadoAtt2 = new virtualrisikoii.JPanelDadi();
        dadoAtt3 = new virtualrisikoii.JPanelDadi();
        dadoDif1 = new virtualrisikoii.JPanelDadi();
        dadoDif2 = new virtualrisikoii.JPanelDadi();
        dadoDif3 = new virtualrisikoii.JPanelDadi();
        titolo = new javax.swing.JLabel();
        descrizioneAttacco = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        freccia2 = new javax.swing.JLabel();
        freccia3 = new javax.swing.JLabel();
        freccia1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        dadoAtt1.setPreferredSize(new java.awt.Dimension(46, 45));

        javax.swing.GroupLayout dadoAtt1Layout = new javax.swing.GroupLayout(dadoAtt1);
        dadoAtt1.setLayout(dadoAtt1Layout);
        dadoAtt1Layout.setHorizontalGroup(
            dadoAtt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        dadoAtt1Layout.setVerticalGroup(
            dadoAtt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        dadoAtt2.setPreferredSize(new java.awt.Dimension(46, 45));

        javax.swing.GroupLayout dadoAtt2Layout = new javax.swing.GroupLayout(dadoAtt2);
        dadoAtt2.setLayout(dadoAtt2Layout);
        dadoAtt2Layout.setHorizontalGroup(
            dadoAtt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        dadoAtt2Layout.setVerticalGroup(
            dadoAtt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        dadoAtt3.setPreferredSize(new java.awt.Dimension(46, 45));

        javax.swing.GroupLayout dadoAtt3Layout = new javax.swing.GroupLayout(dadoAtt3);
        dadoAtt3.setLayout(dadoAtt3Layout);
        dadoAtt3Layout.setHorizontalGroup(
            dadoAtt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        dadoAtt3Layout.setVerticalGroup(
            dadoAtt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        dadoDif1.setPreferredSize(new java.awt.Dimension(46, 45));

        javax.swing.GroupLayout dadoDif1Layout = new javax.swing.GroupLayout(dadoDif1);
        dadoDif1.setLayout(dadoDif1Layout);
        dadoDif1Layout.setHorizontalGroup(
            dadoDif1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        dadoDif1Layout.setVerticalGroup(
            dadoDif1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        dadoDif2.setPreferredSize(new java.awt.Dimension(46, 45));

        javax.swing.GroupLayout dadoDif2Layout = new javax.swing.GroupLayout(dadoDif2);
        dadoDif2.setLayout(dadoDif2Layout);
        dadoDif2Layout.setHorizontalGroup(
            dadoDif2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        dadoDif2Layout.setVerticalGroup(
            dadoDif2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        dadoDif3.setPreferredSize(new java.awt.Dimension(46, 45));

        javax.swing.GroupLayout dadoDif3Layout = new javax.swing.GroupLayout(dadoDif3);
        dadoDif3.setLayout(dadoDif3Layout);
        dadoDif3Layout.setHorizontalGroup(
            dadoDif3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        dadoDif3Layout.setVerticalGroup(
            dadoDif3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        titolo.setFont(new java.awt.Font("Times New Roman", 1, 14));
        titolo.setText("Lancio dei dadi");

        descrizioneAttacco.setFont(new java.awt.Font("Times New Roman", 1, 14));
        descrizioneAttacco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setText("Attacco");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("Difesa");

        freccia2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png"))); // NOI18N

        freccia3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png"))); // NOI18N

        freccia1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/dadi/frecce/frecciaVuota.png"))); // NOI18N

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(dadoAtt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dadoAtt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dadoAtt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(28, 28, 28)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(freccia3)
                                        .addComponent(freccia2)
                                        .addComponent(freccia1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(28, 28, 28)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(dadoDif3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dadoDif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dadoDif1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(titolo)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(descrizioneAttacco, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titolo)
                .addGap(1, 1, 1)
                .addComponent(descrizioneAttacco, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dadoAtt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dadoDif1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(freccia1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dadoDif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dadoAtt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(freccia2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(dadoDif3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(dadoAtt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addComponent(freccia3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
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
   /* public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameDadi().setVisible(true);
            }
        });
    }*/
 public void init() {

    // img=Toolkit.getDefaultToolkit().createImage("/animazione/dadi/red/4.png");
    //dadoAtt1.setImage(img);
    //dadoAtt1.repaint();


       }

 /*  public void start() {
    if (animazione == null) {
        animazione = new Thread(this);
        animazione.start();
    }
   }

  public void stop() {
     if (animazione != null) {
       animazione = null;
     }
   }*/

   public void run() {
   int i=0;
   int time=300;

   Random random=new Random();

   // Prima fila di dadi

   
   if(numAtt1!=0||numAtt2!=0 || numAtt3!=0){
       while(i<5) {
        if(i==0){
            Suono.playSound("/virtualrisikoii/resources/dadi/lanciodadi.wav");
        }
        if(numAtt1!=0){
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/"+coloreGiocatoreAtt+"/"+(random.nextInt(6)+1)+".png");
        dadoAtt1.setImage(img);
        dadoAtt1.repaint();
           }

        if(numAtt2!=0){
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/"+coloreGiocatoreAtt+"/"+(random.nextInt(6)+1)+".png");
        dadoAtt2.setImage(img);
        dadoAtt2.repaint();
              }
        if(numAtt3!=0){
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/"+coloreGiocatoreAtt+"/"+(random.nextInt(6)+1)+".png");
        dadoAtt3.setImage(img);
        dadoAtt3.repaint();

                }
        

        try { Thread.sleep(time);}
       catch (InterruptedException e) {
       e.printStackTrace();}
        i++;
            time *= 1.2;

            if(i==5){
                    img=Toolkit.getDefaultToolkit().createImage(pathAtt1);
                    dadoAtt1.setImage(img);
                    dadoAtt1.repaint();

                    img=Toolkit.getDefaultToolkit().createImage(pathAtt2);
                    dadoAtt2.setImage(img);
                    dadoAtt2.repaint();

                    img=Toolkit.getDefaultToolkit().createImage(pathAtt3);
                    dadoAtt3.setImage(img);
                    dadoAtt3.repaint();

                    
             }
      

       }

                   try { Thread.sleep(1000);}
                   catch (InterruptedException e) {
                   e.printStackTrace();}

       }
        //Seconda fila di dadi
        i=0;
        time=300;

     if(numDif1!=0||numDif2!=0 || numDif3!=0){
          while(i<5) {
        if(i==0){
            Suono.playSound("/virtualrisikoii/resources/dadi/lanciodadi.wav");
        }

        if(numDif1!=0){
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/"+coloreGiocatoreDif+"/"+(random.nextInt(6)+1)+".png");
        dadoDif1.setImage(img);
        dadoDif1.repaint();
           }
        if(numDif2!=0){
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/"+coloreGiocatoreDif+"/"+(random.nextInt(6)+1)+".png");
        dadoDif2.setImage(img);
        dadoDif2.repaint();
              }
        if(numDif3!=0){
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/"+coloreGiocatoreDif+"/"+(random.nextInt(6)+1)+".png");
        dadoDif3.setImage(img);
        dadoDif3.repaint();
         }

        try { Thread.sleep(time);}
       catch (InterruptedException e) {
       e.printStackTrace();}
        i++;
            time *= 1.2;

            if(i==5){

                img=Toolkit.getDefaultToolkit().createImage(pathDif1);
                dadoDif1.setImage(img);
                dadoDif1.repaint();
                System.out.println(freccia1);
                freccia1.setIcon(new javax.swing.ImageIcon(pathfreccia1));

                img=Toolkit.getDefaultToolkit().createImage(pathDif2);
                dadoDif2.setImage(img);
                dadoDif2.repaint();
                freccia2.setIcon(new javax.swing.ImageIcon((pathfreccia2)));

                img=Toolkit.getDefaultToolkit().createImage(pathDif3);
                dadoDif3.setImage(img);
                dadoDif3.repaint();
                freccia3.setIcon(new javax.swing.ImageIcon((pathfreccia3)));

             }
         

       }

                 

       }
        //terza fila di dadi
      /* i=0;
        time=300;

        if(numAtt3!=0 || numDif3!=0) {
          while(i<5) {
        if(i==0){
            Suono.playSound("/virtualrisikoii/resources/dadi/lanciodadi.wav");
        }

        if(numAtt3!=0){
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/"+coloreGiocatoreAtt+"/"+(random.nextInt(6)+1)+".png");
        dadoAtt3.setImage(img);
        dadoAtt3.repaint();
              }

        if(numDif3!=0){
        img=Toolkit.getDefaultToolkit().createImage("src/virtualrisikoii/resources/dadi/"+coloreGiocatoreDif+"/"+(random.nextInt(6)+1)+".png");
        dadoDif3.setImage(img);
        dadoDif3.repaint();
         }
        
        try { Thread.sleep(time);}
       catch (InterruptedException e) {
       e.printStackTrace();}
        i++;
            time *= 1.2;
            
            if(i==5){

                

                img=Toolkit.getDefaultToolkit().createImage(pathDif3);
                dadoDif3.setImage(img);
                dadoDif3.repaint();

                freccia3.setIcon(new javax.swing.ImageIcon((pathfreccia3)));
              }
            }
   
       }*/


        if(contVittorie>=contSconfitte){
            Suono.playSound("/virtualrisikoii/resources/dadi/vittoria.wav");
        }
        else{
            Suono.playSound("/virtualrisikoii/resources/dadi/sconfitta.wav");
        }
   
   
   }






    // Variables declaration - do not modify//GEN-BEGIN:variables
    private virtualrisikoii.JPanelDadi dadoAtt1;
    private virtualrisikoii.JPanelDadi dadoAtt2;
    private virtualrisikoii.JPanelDadi dadoAtt3;
    private virtualrisikoii.JPanelDadi dadoDif1;
    private virtualrisikoii.JPanelDadi dadoDif2;
    private virtualrisikoii.JPanelDadi dadoDif3;
    private javax.swing.JLabel descrizioneAttacco;
    private javax.swing.JLabel freccia1;
    private javax.swing.JLabel freccia2;
    private javax.swing.JLabel freccia3;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel titolo;
    // End of variables declaration//GEN-END:variables

    
public void avviaDadi(){
    init();
    setVisible(true);
    Thread t= new Thread(this);
    t.start();
}
   public static void main(String[] args) throws Exception{
       JFrameDadi frame=new JFrameDadi("ciao",1,1,1,4,5,2,4,3);
       frame.init();
       frame.setVisible(true);
       Thread t=new Thread(frame);
       t.start();
   }

    //SETTA IL jFRAME A CENTRO DELLO SCHERMO
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
