package reader;

public class PrintName {
 String absName ;
 String printName;
 
 public PrintName(String _absName, String _printName)
 {absName = _absName;
  printName = _printName;
 }
 
public String toString()
{return "Abstract Name : "+absName + " , Print Name : "+printName;}
}
