package pgf.parse.pmcfg

class PassiveEdge
  ( val constituentIndex: Int
  , val baseCategory: Int
  , val freshCategory: Int
  ) {

    override def toString() 
    = "[" + this.baseCategory + ", " 
    + this.constituentIndex + ", " 
    + this.freshCategory + "]";

}
