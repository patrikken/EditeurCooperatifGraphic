package metier.entities;

import java.io.Serializable;
import java.util.Objects;

public class Symbol implements Serializable{

    private String name;

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol() {
    } 
 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

  
    @Override
	public boolean equals(Object obj) {
    	
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
//		System.out.println("symb1"+name+" taille "+name.length()+" et symb2"+other.name+" taille "+other.name.length());
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "Symbol{" + "name=" + name + '}';
    }
    
    
    
}
