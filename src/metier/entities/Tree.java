package metier.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Tree implements Serializable{

    // this class field is use to automatic identify of node 
	
	
    public static int compt = 0;

    // the symbol labeling the node
    private Symbol symbol;

    // all the sun of node
    private ArrayList<Tree> suns;

    // a boolean use to specify which node is consider as a bud
    private boolean bud;
    
    // indicator for graphics purpose no use by the compiler
    
    public static final int NOEDITABLENODE=-1;
    
    public static final int SIMPLEEDITABLENODE=0;
    
    public static final int FULLEDITABLENODE=1;
    
    private int editingMode;

    // the id of the node
    private int id;

    public Tree() {
        suns = new ArrayList<Tree>();
        compt++;
        id = compt;
        bud = false;
        editingMode=0;
    }

    public Tree(Symbol sym) {
        suns = new ArrayList<Tree>();
        symbol = sym;
        compt++;
        id = compt;
        bud = false;
    }

    public Tree(Symbol sym, boolean bud) {
        suns = new ArrayList<Tree>();
        symbol = sym;
        compt++;
        id = compt;
        this.bud = true;
    }

    public boolean isBud() {
        return bud;
    }

    public void setBud(boolean bud) {
        this.bud = bud;
    }

    public static int getCompt() {
        return compt;
    }

    public static void setCompt(int compt) {
        Tree.compt = compt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
        
    }

    public ArrayList<Tree> getSuns() {
        return suns;
    }

    public void setSuns(ArrayList<Tree> sun) {
        this.suns = sun;
        if(sun.size()>0){
        	bud=false;
        }
    }

	public int getEditingMode() {
		return editingMode;
	}

	public void setEditingMode(int editingMode) {
		this.editingMode = editingMode;
	}

    
}
