package reader;

public class ApplProduction extends Production {
          int ind;
          int[] args;
          
		  public ApplProduction(int _ind, int[] _args) { ind = _ind; args = _args; }
          
		  public String toString()
          {String ss =  "Index : "+ind + " Arguments : ["; 
           for(int i=0; i<args.length; i++)
        	   ss+=(" "+args[i]);
          ss+="]";
          return ss;
          };
          
		}


