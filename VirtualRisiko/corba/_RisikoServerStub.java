package corba;


/**
* corba/_RisikoServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 12 luglio 2011 17.24.43 CEST
*/

public class _RisikoServerStub extends org.omg.CORBA.portable.ObjectImpl implements corba.RisikoServer
{

  public corba.Authentication authenticate (String username, String pwd)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("authenticate", true);
                $out.write_wstring (username);
                $out.write_wstring (pwd);
                $in = _invoke ($out);
                corba.Authentication $result = corba.AuthenticationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return authenticate (username, pwd        );
            } finally {
                _releaseReply ($in);
            }
  } // authenticate

  public void registerPlayer (String username, corba.Player player)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("registerPlayer", true);
                $out.write_wstring (username);
                corba.PlayerHelper.write ($out, player);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                registerPlayer (username, player        );
            } finally {
                _releaseReply ($in);
            }
  } // registerPlayer

  public boolean isOnline (String username)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("isOnline", true);
                $out.write_wstring (username);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return isOnline (username        );
            } finally {
                _releaseReply ($in);
            }
  } // isOnline

  public corba.PartitaInfo[] getAvailableGames ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAvailableGames", true);
                $in = _invoke ($out);
                corba.PartitaInfo $result[] = corba.GamesHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAvailableGames (        );
            } finally {
                _releaseReply ($in);
            }
  } // getAvailableGames

  public void deleteRegistration (corba.PartitaInfo partita, corba.UserInfo user)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteRegistration", true);
                corba.PartitaInfoHelper.write ($out, partita);
                corba.UserInfoHelper.write ($out, user);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteRegistration (partita, user        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteRegistration

  public void deletePartita (corba.PartitaInfo partita, corba.UserInfo info)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deletePartita", true);
                corba.PartitaInfoHelper.write ($out, partita);
                corba.UserInfoHelper.write ($out, info);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deletePartita (partita, info        );
            } finally {
                _releaseReply ($in);
            }
  } // deletePartita

  public void changeManager (corba.PartitaInfo partita, corba.UserInfo info)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("changeManager", true);
                corba.PartitaInfoHelper.write ($out, partita);
                corba.UserInfoHelper.write ($out, info);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                changeManager (partita, info        );
            } finally {
                _releaseReply ($in);
            }
  } // changeManager

  public boolean saveResult (corba.RegistrationInfo[] res)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("saveResult", true);
                corba.RegistrationsHelper.write ($out, res);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return saveResult (res        );
            } finally {
                _releaseReply ($in);
            }
  } // saveResult

  public corba.PartitaInfo createGame (corba.UserInfo user, short maxTurn, short maxPlayers, String name, String type)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("createGame", true);
                corba.UserInfoHelper.write ($out, user);
                $out.write_ushort (maxTurn);
                $out.write_ushort (maxPlayers);
                $out.write_wstring (name);
                $out.write_wstring (type);
                $in = _invoke ($out);
                corba.PartitaInfo $result = corba.PartitaInfoHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return createGame (user, maxTurn, maxPlayers, name, type        );
            } finally {
                _releaseReply ($in);
            }
  } // createGame

  public boolean signPlayer (corba.UserInfo player, corba.PartitaInfo partita)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("signPlayer", true);
                corba.UserInfoHelper.write ($out, player);
                corba.PartitaInfoHelper.write ($out, partita);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return signPlayer (player, partita        );
            } finally {
                _releaseReply ($in);
            }
  } // signPlayer

  public corba.UserInfo[] getAuthenticateUsers ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAuthenticateUsers", true);
                $in = _invoke ($out);
                corba.UserInfo $result[] = corba.UsersHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAuthenticateUsers (        );
            } finally {
                _releaseReply ($in);
            }
  } // getAuthenticateUsers

  public corba.UserInfo[] getPlayers (corba.PartitaInfo partita)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPlayers", true);
                corba.PartitaInfoHelper.write ($out, partita);
                $in = _invoke ($out);
                corba.UserInfo $result[] = corba.UsersHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getPlayers (partita        );
            } finally {
                _releaseReply ($in);
            }
  } // getPlayers

  public void shutdown ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("shutdown", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                shutdown (        );
            } finally {
                _releaseReply ($in);
            }
  } // shutdown

  public corba.PartitaInfo getPartitaInfo (int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPartitaInfo", true);
                $out.write_ulong (id);
                $in = _invoke ($out);
                corba.PartitaInfo $result = corba.PartitaInfoHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getPartitaInfo (id        );
            } finally {
                _releaseReply ($in);
            }
  } // getPartitaInfo

  public corba.PartitaInfo getActiveGame (String username)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getActiveGame", true);
                $out.write_wstring (username);
                $in = _invoke ($out);
                corba.PartitaInfo $result = corba.PartitaInfoHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getActiveGame (username        );
            } finally {
                _releaseReply ($in);
            }
  } // getActiveGame

  public void notifyNewManager (corba.PartitaInfo partita, String manager)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("notifyNewManager", true);
                corba.PartitaInfoHelper.write ($out, partita);
                $out.write_wstring (manager);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                notifyNewManager (partita, manager        );
            } finally {
                _releaseReply ($in);
            }
  } // notifyNewManager

  public boolean createUser (corba.UserDetails details)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("createUser", true);
                corba.UserDetailsHelper.write ($out, details);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return createUser (details        );
            } finally {
                _releaseReply ($in);
            }
  } // createUser

  public boolean checkUsername (String username)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("checkUsername", true);
                $out.write_wstring (username);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return checkUsername (username        );
            } finally {
                _releaseReply ($in);
            }
  } // checkUsername

  public boolean activateUser (String username, int codice)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("activateUser", true);
                $out.write_wstring (username);
                $out.write_ulong (codice);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return activateUser (username, codice        );
            } finally {
                _releaseReply ($in);
            }
  } // activateUser

  public boolean sendEmailForPassword (corba.UserInfo user)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sendEmailForPassword", true);
                corba.UserInfoHelper.write ($out, user);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return sendEmailForPassword (user        );
            } finally {
                _releaseReply ($in);
            }
  } // sendEmailForPassword

  public corba.SummaryPlayerInfo getStatistics (String username)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getStatistics", true);
                $out.write_wstring (username);
                $in = _invoke ($out);
                corba.SummaryPlayerInfo $result = corba.SummaryPlayerInfoHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getStatistics (username        );
            } finally {
                _releaseReply ($in);
            }
  } // getStatistics

  public corba.Summary[] getCompleteStatistics ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getCompleteStatistics", true);
                $in = _invoke ($out);
                corba.Summary $result[] = corba.StatisticsHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getCompleteStatistics (        );
            } finally {
                _releaseReply ($in);
            }
  } // getCompleteStatistics

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/RisikoServer:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _RisikoServerStub
