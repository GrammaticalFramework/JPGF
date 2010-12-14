package org.grammaticalframework.linearizer;

import java.util.Vector;

public class RezDesc {
private int fid;
private Vector<CncType> cncTypes;
private Vector<Vector<Vector<BracketedTokn>>> bss;

public RezDesc(int _fid, Vector<CncType> _cncTypes, Vector<Vector<Vector<BracketedTokn>>> _bss)
{fid = _fid; cncTypes = _cncTypes; bss = _bss;}

public int getFid() {return fid;}
public Vector<CncType> getCncTypes(){return cncTypes;}
public Vector<Vector<Vector<BracketedTokn>>> getBracketedTokens() {return bss;}
}

