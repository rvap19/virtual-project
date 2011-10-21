/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_service;



/**
 *
 * @author root
 */
public class RisikoStatisticalClient {
    private Risiko risiko;
    
    public RisikoStatisticalClient(Risiko risiko){
        this.risiko=risiko;
    }
    public RisikoStatisticalClient(){
        RisikoService service = new RisikoService();
        risiko = service.getRisikoPort();
    }

    public boolean sendEmailForPassword(String username) {
        return risiko.sendEmailForPassword(username);
    }

    public GameregistrationArray getPlayerStatistics(String username) {
        return risiko.getPlayerStatistics(username);
    }

    public GameregistrationArray getGameStatistics(int game) {
        return risiko.getGameStatistics(game);
    }

    public GameregistrationArray getAllStatistics() {
        return risiko.getAllStatistics();
    }

    public boolean createUser(User user) {
        return risiko.createUser(user);
    }

    public boolean checkUsername(String username) {
        return risiko.checkUsername(username);
    }

    public boolean checkEmail(String email) {
        return risiko.checkEmail(email);
    }

    public boolean activateUser(String username, String password, int codice) {
        return risiko.activateUser(username, password, codice);
    }
    
    
    
}
