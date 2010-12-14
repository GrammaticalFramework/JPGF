package org.grammaticalframework.linearizer;

import java.util.Vector;


public class LinTriple {
    private int fId;
    private CncType cncType;
    private Vector<Vector<BracketedTokn>> linTable;

    public LinTriple(int _fId, CncType _cncType,
                     Vector<Vector<BracketedTokn>> _linTable) {
        fId = _fId; cncType = _cncType; linTable = _linTable;
    }

    public int getFId() {return fId;}
    public CncType getCncType() {return cncType;}
    public Vector<Vector<BracketedTokn>> getLinTable() {return linTable;}
    public String toString() {
        String rez = "id : "+fId+ " cncType : ("+cncType.toString()+
            ") bracketedToken :["+linTable.toString();
        rez+="]";
        return rez;
    }
}
