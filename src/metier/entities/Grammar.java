package metier.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class Grammar implements Serializable{

    private Symbol axiom;
    private ArrayList<Symbol> symbols;
    private ArrayList<Production> productions;

    public Grammar() {
        symbols = new ArrayList<Symbol>();
        productions = new ArrayList<Production>();
    }

    public Symbol getAxiom() {
        return axiom;
    }

    public void setAxiom(Symbol axiom) {
        this.axiom = axiom;
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    public void setProductions(ArrayList<Production> productions) {
        this.productions = productions;
    }

}
