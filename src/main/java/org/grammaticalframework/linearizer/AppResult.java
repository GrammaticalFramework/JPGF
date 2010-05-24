package org.grammaticalframework.linearizer;

import java.util.Vector;

import org.grammaticalframework.reader.CncFun;

public class AppResult {
  private CncFun cncFun;
  private CncType cncType;
  private Vector<CncType> cncTypes;
  
  public AppResult(CncFun _cncFun, CncType _cncType, Vector<CncType> _cncTypes)
  {cncFun = _cncFun; cncType = _cncType; cncTypes = _cncTypes;}
  public CncFun getCncFun() {return cncFun;}
  public CncType getCncType() {return cncType;}
  public Vector<CncType> getCncTypes() {return cncTypes;}
}
