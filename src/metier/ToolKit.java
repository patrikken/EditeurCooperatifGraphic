package metier;

import java.util.Collection;

import metier.entities.Symbol;

public class ToolKit {

	
	public static String transformToString(Collection<Symbol> col,String separator){
	
		String st="";
		if(col.isEmpty())return "";
		for(Symbol symb:col){
			st+=separator+symb.getName();
		}
		st=st.substring(separator.length());
		
		return st;
	}
}
