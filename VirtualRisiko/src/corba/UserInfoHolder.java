package corba;

/**
* corba/UserInfoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* venerd� 1 luglio 2011 14.23.09 CEST
*/

public final class UserInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.UserInfo value = null;

  public UserInfoHolder ()
  {
  }

  public UserInfoHolder (corba.UserInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.UserInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.UserInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.UserInfoHelper.type ();
  }

}
