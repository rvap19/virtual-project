package corba;

/**
* corba/SummaryHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 16.28.52 CEST
*/

public final class SummaryHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.Summary value = null;

  public SummaryHolder ()
  {
  }

  public SummaryHolder (corba.Summary initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.SummaryHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.SummaryHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.SummaryHelper.type ();
  }

}
