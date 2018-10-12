package metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import metier.entities.Grammar;
import metier.entities.Production;
import metier.entities.Symbol;
import metier.entities.Tree;
import metier.entities.View;

public class TreeUtil {

    public static Collection<Production> getExpandProductions(Tree t, Grammar grammar) {

        ArrayList<Production> productions;

        return getProductionWithLeftSide(t, grammar);

    }

    ;
	
	public static Collection<Production> getProductionWithLeftSide(Tree tree, Grammar grammar) {

        ArrayList<Production> productions = new ArrayList<Production>();

        for (Production prod : grammar.getProductions()) {

            if (prod.getLeftSide().equals(tree.getSymbol())) {

                productions.add(prod);

            }
        }
        return productions;

    }
	// retourne le premier noued non conforme a la grammaire 
    // et null sinon

    public static Tree validateTree(Tree tree, Grammar grammar) {

        Collection<Production> productions = getProductionWithLeftSide(tree, grammar);
        if (!tree.getSuns().isEmpty()) {
            boolean valid = false;
            for (Production prod : productions) {
                ArrayList<Symbol> rightSide = prod.getRightSide();
                Iterator<Symbol> itProd = prod.getRightSide().iterator();
                Iterator<Tree> itTree = tree.getSuns().iterator();
                boolean parcours = true;
                while (itTree.hasNext() && itProd.hasNext() && parcours) {

                    Symbol symb = itProd.next();
                    Tree t = itTree.next();
                    if (!symb.equals(t.getSymbol())) {
                        parcours = false;
                    }
                }
                if (!itTree.hasNext() && !itProd.hasNext() && parcours) {
                    valid = true;
                    break;
                }

            }
            if (!valid) {
                return tree;
            } else {
                for (Tree t : tree.getSuns()) {

                    Tree find = TreeUtil.validateTree(t, grammar);
                    if (find != null) {
                        return find;
                    }
                }
            }
        }

        return null;
    }

	// code de la projection selon une vue 
    public static void projectionProcess(Tree t, View v, Tree parent, Collection<Tree> foret) {

        Tree res = new Tree();

        if (v.getSymbols().contains(t.getSymbol())) {

            res.setSymbol(t.getSymbol());
            if (t.isBud()) {
                res.setBud(true);
            }
            for (int i = 0; i < t.getSuns().size(); i++) {
                projectionProcess(t.getSuns().get(i), v, res, foret);
            }
            if (parent != null) {
                parent.getSuns().add(res);
            } else {
                foret.add(res);
            }
        } else {
            for (int i = 0; i < t.getSuns().size(); i++) {
                projectionProcess(t.getSuns().get(i), v, parent, foret);
            }
        }

    }

	// code de la projection selon une vue 
    public static void projectionProcess(Tree t, View v, Tree parent, Collection<Tree> foret, int mode) {

        Tree res = new Tree();
        res.setEditingMode(mode); 

        if (v.getSymbols().contains(t.getSymbol())) {

            res.setSymbol(t.getSymbol());
            if (t.isBud()) {
                res.setBud(true);
            }else if(!t.isBud() && mode==Tree.FULLEDITABLENODE){
            	res.setEditingMode(Tree.NOEDITABLENODE);
            }
            for (int i = 0; i < t.getSuns().size(); i++) {
                projectionProcess(t.getSuns().get(i), v, res, foret, mode);
            }
            if (parent != null) {
                parent.getSuns().add(res);
            } else {
                foret.add(res);
            }
        } else {
            for (int i = 0; i < t.getSuns().size(); i++) {
                projectionProcess(t.getSuns().get(i), v, parent, foret, mode);
            }
        }

    }

    // methode qui fait la projection d'un document suivant une vue

    public static Collection<Tree> projection(Tree t, View v) {

        ArrayList<Tree> array = new ArrayList<Tree>();

        projectionProcess(t, v, null, array);

        return array;
    }

    // methode qui fait la projection d'un document suivant une vue
    public static Collection<Tree> projection(Tree t, View v, int mode) {

        ArrayList<Tree> array = new ArrayList<Tree>();

        projectionProcess(t, v, null, array, mode);

        return array;
    }

	// methode qui renvoie une collection de fils a patir d'une liste de symbol
    public static ArrayList<Tree> getSunsFrom(Collection<Symbol> symbols) {

        ArrayList<Tree> treeArray = new ArrayList<Tree>();
        for (Symbol symb : symbols) {
            Tree t = new Tree();
            t.setSymbol(symb);
            treeArray.add(t);
        }
        return treeArray;
    }
}
