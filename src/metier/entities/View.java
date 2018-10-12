package metier.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class View implements Serializable{

    // the name of the view eg. view1
    private String name;

    // all symbol contained in the view
    private ArrayList<Symbol> symbols;

    public View() {
        symbols = new ArrayList<Symbol>();
    }

    public View(String name) {
        this.name = name;
    }

    
    
    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final View other = (View) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    

}
