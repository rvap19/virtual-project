package corba;


/**
* corba/UsersHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 16.28.52 CEST
*/

public final class UsersHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.UserInfo value[] = null;

  public UsersHolder ()
  {
  }

  public UsersHolder (corba.UserInfo[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.UsersHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.UsersHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.UsersHelper.type ();
  }

}
