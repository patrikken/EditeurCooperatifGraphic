package metier.entities;

import java.io.Serializable;

public class Replicat implements Serializable {

    // (OPTIONAL) the id of replicat, this fields is optional 
    // it is not considerate by the compiler Class "metier.Compiler"
    private String id;

    // the tree which represent the replicat or document
    private Tree tree;

    // the author of replicat
    private Author author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
