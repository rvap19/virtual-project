package corba;

/**
* corba/RisikoServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 14.30.15 CEST
*/

public final class RisikoServerHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.RisikoServer value = null;

  public RisikoServerHolder ()
  {
  }

  public RisikoServerHolder (corba.RisikoServer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.RisikoServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.RisikoServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.RisikoServerHelper.type ();
  }

}
