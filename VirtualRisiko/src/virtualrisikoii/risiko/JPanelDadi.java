package virtualrisikoii.risiko;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class JPanelDadi extends JPanel {

    private Image img= null;

    public JPanelDadi(){}
    
     public JPanelDadi (Image img)
    {
        this.img = img;
    }

     public void setImage(Image i){
         this.img=i;
       
     }

     public Image getImage(){
         return img;
     }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(img==null){
            return;
        }
        int width = img.getWidth (this);
        int height = img.getHeight (this);
        Dimension d = getSize();
        g.drawImage (img, 0,0, d.width,d.height, this);
        // ... qui usa i metodi di Graphics per disegnare quello che vuoi ...
        // Se ti serve, il 'g' è realmente un Graphics2D, quindi puoi fare un cast
        // e usare i metodi più avanzati di Graphics2D.
    }


    @Override
        public void update(Graphics g) {

           paint(g);
         }


}

