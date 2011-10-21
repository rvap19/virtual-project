/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualrisikoii.jxta.advertisement;

import net.jxta.document.Advertisement;
import net.jxta.document.Document;
import net.jxta.document.MimeMediaType;
import net.jxta.id.ID;
import net.jxta.protocol.PipeAdvertisement;

/**
 *
 * @author root
 */
public class JXTAPipeAdvertisement  extends Advertisement implements middle.management.advertisement.PipeAdvertisement{

    private PipeAdvertisement pipe;
    
    public JXTAPipeAdvertisement(PipeAdvertisement a){
        this.pipe=a;
    }

    public PipeAdvertisement getPipe() {
        return pipe;
    }

    public String getName() {
        return pipe.getName();
    }

    public String getAdvType() {
        return pipe.getAdvType();
    }

    @Override
    public Document getDocument(MimeMediaType mmt) {
        return pipe.getDocument(mmt);
    }

    @Override
    public ID getID() {
        return pipe.getID();
    }

    @Override
    public String[] getIndexFields() {
        return pipe.getIndexFields();
    }
    
    
    
    
    
    
}
