package metier.entities;

import java.io.Serializable;

public class Author implements Serializable{

    // the full name of the author
    private String name;

    // the id of the author eg. author1
    private String Id;

    // the view of the author 
    private View view;

    // the site of the author
    private String site;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

}
