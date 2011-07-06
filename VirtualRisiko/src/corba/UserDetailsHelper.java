package corba;


/**
* corba/UserDetailsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* mercoledý 6 luglio 2011 18.07.58 CEST
*/

abstract public class UserDetailsHelper
{
  private static String  _id = "IDL:corba/UserDetails:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.UserDetails that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.UserDetails extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [6];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "username",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "password",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "email",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[4] = new org.omg.CORBA.StructMember (
            "surname",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[5] = new org.omg.CORBA.StructMember (
            "birthday",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (corba.UserDetailsHelper.id (), "UserDetails", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.UserDetails read (org.omg.CORBA.portable.InputStream istream)
  {
    corba.UserDetails value = new corba.UserDetails ();
    value.username = istream.read_wstring ();
    value.password = istream.read_wstring ();
    value.email = istream.read_wstring ();
    value.name = istream.read_wstring ();
    value.surname = istream.read_wstring ();
    value.birthday = istream.read_wstring ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.UserDetails value)
  {
    ostream.write_wstring (value.username);
    ostream.write_wstring (value.password);
    ostream.write_wstring (value.email);
    ostream.write_wstring (value.name);
    ostream.write_wstring (value.surname);
    ostream.write_wstring (value.birthday);
  }

}
