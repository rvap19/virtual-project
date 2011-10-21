/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_service;

/**
 *
 * @author root
 */
public class UserRegistratorImpl implements UserRegistrator{
    public static  final String ERROR1="username non disponibile";
    public static final String ERROR2="email gia presente";
    public static final String ERROR3="impossibile registrare utente";
    public static  final String SUCCESS="utente registrato ... il tuo codice di attivazione è stato inviato all'email specificata";
    private RisikoStatisticalClient client;
    
    private String errorMessage;
    private boolean success;
    public UserRegistratorImpl(RisikoStatisticalClient client){
        this.client=client;
        success=false;       
    }
    
 

    public String  createUser(User user) {
        success=false;
        if(!this.client.checkEmail(user.getEmail())){
            return ERROR2;
        }
        if(!this.client.checkUsername(user.getUsername())){
            return ERROR1;
        }
        
        if(this.client.createUser(user)){
            success=true;
            return SUCCESS;
        }else{
            return ERROR3;
        }
        
    }

    public boolean isSuccess() {
        return success;
    }
    
    
    
}
