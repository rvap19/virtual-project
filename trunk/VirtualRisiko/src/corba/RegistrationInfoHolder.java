package corba;

/**
* corba/RegistrationInfoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* venerd� 17 giugno 2011 4.01.34 CEST
*/

public final class RegistrationInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.RegistrationInfo value = null;

  public RegistrationInfoHolder ()
  {
  }

  public RegistrationInfoHolder (corba.RegistrationInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.RegistrationInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.RegistrationInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.RegistrationInfoHelper.type ();
  }

}
