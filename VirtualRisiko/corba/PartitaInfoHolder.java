package corba;

/**
* corba/PartitaInfoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 12 luglio 2011 17.24.43 CEST
*/

public final class PartitaInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.PartitaInfo value = null;

  public PartitaInfoHolder ()
  {
  }

  public PartitaInfoHolder (corba.PartitaInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.PartitaInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.PartitaInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.PartitaInfoHelper.type ();
  }

}
