package metier.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import metier.TreeUtil;

public class Project implements Serializable{

    // the name of project eg. mon projet
    private String name;
    
    // the location of project in the disk
    private String location;

    // the grammar of project
    private Grammar grammar;

    // the views of project
    private ArrayList<View> views;

    // the authors of project
    private ArrayList<Author> authors;

    // the replicat of initial document written by the authors
    private ArrayList<Replicat> replicats;

    // the initial document
    private Tree initialDocument;

    public Project() {
        views = new ArrayList<View>();
        authors = new ArrayList<Author>();
        replicats = new ArrayList<Replicat>();
        grammar = new Grammar();
        location="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public ArrayList<View> getViews() {
        return views;
    }

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<Replicat> getReplicats() {
        return replicats;
    }

    public void setReplicats(ArrayList<Replicat> replicats) {
        this.replicats = replicats;
    }

    public Tree getInitialDocument() {
        return initialDocument;
    }

    public void setInitialDocument(Tree initialDocument) {
        this.initialDocument = initialDocument;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void inValidateReplicat(){
    	
    	replicats= new ArrayList<Replicat>();
    	
    	for(Author author:getAuthors()){
    		Collection<Tree> col=TreeUtil.projection(initialDocument, author.getView(),Tree.FULLEDITABLENODE);
    		for(Tree t:col){
    		Replicat r=new Replicat();
    		r.setAuthor(author);
    		r.setTree(t);
    		replicats.add(r);
    		}
    	}
    	
    	
    }
    
    
    public void createReplicatForAuthor(Author author){
    	
    	Collection<Tree> col=TreeUtil.projection(initialDocument, author.getView(),Tree.FULLEDITABLENODE);
		for(Tree t:col){
		Replicat r=new Replicat();
		r.setAuthor(author);
		r.setTree(t);
		replicats.add(r);
		}
    }
}
