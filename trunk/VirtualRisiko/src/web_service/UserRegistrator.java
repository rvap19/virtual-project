/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web_service;

/**
 *
 * @author root
 */
public interface UserRegistrator {
    public String  createUser(User user) ;

    public boolean isSuccess() ;
}
