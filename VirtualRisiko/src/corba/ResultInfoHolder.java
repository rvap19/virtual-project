package corba;

/**
* corba/ResultInfoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 14.30.15 CEST
*/

public final class ResultInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.ResultInfo value = null;

  public ResultInfoHolder ()
  {
  }

  public ResultInfoHolder (corba.ResultInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.ResultInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.ResultInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.ResultInfoHelper.type ();
  }

}
