package metier.entities;

import java.io.Serializable;

public class ViewTree implements Serializable{

    public View view;
    public Tree tree;

    public ViewTree() {
        tree = new Tree();
        view = new View();
    }

    public ViewTree(View v, Tree t) {
        tree = t;
        view = v;
    }

}
