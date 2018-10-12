package metier.entities;

import java.io.Serializable;
import java.util.ArrayList;

import metier.ToolKit;

public class Production implements Serializable{

    // the name of production eg. P1
    private String name;

    // the leftside of production
    private Symbol leftSide;

    // the righSide of production 
    private ArrayList<Symbol> rightSide;

    public Production() {
        rightSide = new ArrayList<Symbol>();
    }

    public Symbol getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(Symbol leftSide) {
        this.leftSide = leftSide;
    }

    public ArrayList<Symbol> getRightSide() {
        return rightSide;
    }

    public void setRightSide(ArrayList<Symbol> rightSide) {
        this.rightSide = rightSide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
    	return this.getName()+":"+this.getLeftSide().getName()+"->"+ToolKit.transformToString(getRightSide(), " ");
    }
}
