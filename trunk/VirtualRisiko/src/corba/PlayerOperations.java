package corba;


/**
* corba/PlayerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 12 luglio 2011 17.24.43 CEST
*/

public interface PlayerOperations 
{
  corba.UserInfo getUserInfo ();
  void notifyNewGame (corba.PartitaInfo partita);
  void notifyNewRegistration (corba.RegistrationInfo registration);
  void notifyStart (String managerName);
  void notifyNewPlayer (corba.UserInfo userInfo);
  boolean isLogged ();
} // interface PlayerOperations
