package reader;

public class CoerceProduction extends Production {
        private  int initId;
    
          
    public CoerceProduction(int fId, int _initId) 
    {
	super(1, fId);
	initId = _initId;
    }
		  
    public String toString()
     {return "Initial id : "+initId; };
		  
    public int getInitId() {return initId;}
    

		}


