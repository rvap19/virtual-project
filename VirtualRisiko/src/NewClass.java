



import corba.impl.User;
import corba.impl.dao.UserJpaController;
import java.util.Date;
import java.util.logging.LogManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author root
 */
public class NewClass {
     public static void main(String args[] )throws Exception{
         LogManager.getLogManager().reset();
        UserJpaController controller=new UserJpaController();
        corba.impl.User u=new User();
        u.setCognome("memmolo");
        u.setEmail("raf@libero.it");
        u.setUsername("pipollllo");
        u.setPassword("xxxxx");
        u.setDataDiIscrizione(new Date());
        u.setDataDiNascita(new Date());
        u.setNazione("italia");
        u.setNome("raf");
        
        controller.create(u);
        
        boolean result=controller.findUserByUsername(u.getUsername()).getPassword().equals(u.getPassword());
        System.out.println("Utente :"+u.getUsername()+" trovato ?"+result);
    }

}
