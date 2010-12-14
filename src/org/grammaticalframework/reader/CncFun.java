package org.grammaticalframework.reader;

public class CncFun {
    private String name;
    private Sequence[] sequences;
    
    public CncFun(String _name, Sequence[] seqs)
    {
        name = _name;
        this.sequences = seqs;
    }
    
    /**
     * Accessors
     */
    public String name() {
        return this.name;
    }
    
    public Sequence[] sequences() {
        return this.sequences;
    }
    
    public Sequence sequence(int index) {
        return this.sequences[index];
    }
    
    public Symbol symbol(int seqIndex, int symbIndex) {
        return this.sequences[seqIndex].symbol(symbIndex);
    }
    
    public int size() {
        return this.sequences.length;
    }
    
    public String toString()
    {
        String ss = "Name : "+name + " , Indices : ";
        for(int i=0; i < sequences.length; i++)
            ss+=(" " + sequences[i]);
        return ss;
    }
// *************
// private String name;
// private int[] inds;

// public CncFun(String _name, int[] _inds)
// {name = _name;
//  inds = _inds;
// }

// public String toString()
// {String ss = "Name : "+name + " , Indices : ";
// for(int i=0; i<inds.length; i++)
//  ss+=(" "+inds[i]);
// return ss;
// }

// public String getName(){return name;}
// public int[] getInds(){return inds;}

// ^ ^ ^ ^ ^ ^ ^
}
