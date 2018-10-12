package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mxgraph.io.mxCodec;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
//import com.sun.management.jmx.Trace;

import metier.ToolKit;
import metier.entities.Grammar;
import metier.entities.Production;
import metier.entities.Project;
import metier.entities.Tree;

public class StructuredLayout implements LayoutProject {

    public static final NumberFormat numberFormat = NumberFormat.getInstance();

    private CustomGraphComponent graphComponent;
    private mxGraph graph;
    private boolean pageView;
    private JPanel panel;
    private Project project;
	private JFileChooser chooser= new JFileChooser();

    @Override
    public void dispose(JPanel jpanel, Project project) {

		// TODO Auto-generated method stub
    	this.project = project ;
    	jpanel.removeAll();
    	jpanel.setLayout(new BorderLayout());
        mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
        mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";
        graph = new CustomGraph();
        Object defaultParent = graph.getDefaultParent();
        double numberOfView = 0.8;
        if (project.getViews() != null) {
            numberOfView = project.getViews().size();
        }

        jpanel.setPreferredSize(new Dimension((int) (850 * numberOfView), 1200));
        jpanel.setLayout(new BorderLayout());
        panel=jpanel;
        graph.getModel().beginUpdate();
        try {
            String styleParent = mxConstants.STYLE_FILLCOLOR + "=#ffffff";

            Object parent = graph.insertVertex(defaultParent, null, "", 20, 20, 400, 120, styleParent);

            StructuredLayoutTools tk = new StructuredLayoutTools();
            if (project.getGrammar() != null && project.getGrammar().getAxiom()!=null && project.getGrammar().getAxiom()!=null) {
            	
                Object levelGrammar = graph.insertVertex(parent, null, "grammar", 150, 50, 500, 120, styleParent);

                Object v1 = graph.insertVertex(levelGrammar, null, "Grammar", 30, 50, 130, 40, mxConstants.STYLE_SPACING_TOP + "=8");
                String prodSt = "";
                int prodBlockSize = 0;
                for (Production prod : project.getGrammar().getProductions()) {
                    prodSt += "" + prod.getName() + ": "+prod.getLeftSide().getName()+" -> " + (ToolKit.transformToString(prod.getRightSide(), " ").equals("")?"ε":ToolKit.transformToString(prod.getRightSide(), " ")) + "\n";
                    prodBlockSize += 20;
                }
                Object levelProduction = graph.insertVertex(levelGrammar, null, prodSt, 250, 50, 100, prodBlockSize, mxConstants.STYLE_FILLCOLOR + "=#ffffff");
                //Object vObject= graph.insertVertex(levelProduction, null,prodSt, 240, 150, 80, 40);
                StructuredLayout.printSymbols(graph, levelGrammar, v1, project);
                StructuredLayout.printAxiom(graph, levelGrammar, v1, project);
                graph.insertEdge(levelGrammar, null, "PRODUCTIONS", v1, levelProduction);
                mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
                layout.setOrientation(SwingConstants.WEST);

                layout.execute(levelGrammar);

                if (project.getInitialDocument() != null) {

                    Object levelInitialDoc = graph.insertVertex(parent, null, "", 600, 30, 500, 120, styleParent);
                    printInitialDoc(project.getInitialDocument(), graph, levelInitialDoc, v1, project);
                    graph.insertEdge(levelGrammar, null, "INITIAL DOCUMENT", levelGrammar, levelInitialDoc, mxConstants.STYLE_EDGE + "=" + mxConstants.EDGESTYLE_ENTITY_RELATION + ";" + mxConstants.STYLE_DASHED + "=1;" + mxConstants.STYLE_DASH_PATTERN + "=10;");
                    mxHierarchicalLayout layoutInitDoc = new mxHierarchicalLayout(graph);
                    layoutInitDoc.execute(levelInitialDoc);

                    if (project.getViews() != null) {

                        Object parentView = graph.insertVertex(defaultParent, null, "", 0, 400, 400, 120, styleParent);
                        Collection<CoupleViewTreeRepresentation> treeCol = tk.disposeView(graph, parentView, defaultParent, levelInitialDoc, project);

                        mxHierarchicalLayout layoutForView = new mxHierarchicalLayout(graph);
                        layoutForView.execute(parentView);

                        if (project.getReplicats() != null && project.getAuthors() != null) {

                            mxHierarchicalLayout layoutForParentReplicat = new mxHierarchicalLayout(graph);
                            Object parentReplicat = graph.insertVertex(defaultParent, null, "", 0, 700, 400, 120, styleParent);

                            for (CoupleViewTreeRepresentation couple : treeCol) {
                                tk.disposeReplicat(graph, parentReplicat, defaultParent, couple.treePresentation, couple.view, project);
                            }
                            layoutForParentReplicat.execute(parentReplicat);

                        }
                    }
                }
            }

	    	// start of rendering of initial document
        } finally {
            graph.getModel().endUpdate();
        }

     graphComponent = new CustomGraphComponent(graph);
	    //graphComponent.setBackground(Color.GREEN);
        //graphComponent.setSize(new Dimension(500, 500));
        //graphComponent.setPreferredSize(new Dimension(500,500));
        graphComponent.setBorder(null);
        graphComponent.setAutoScroll(false);

	  //  graphComponent.repaint();
        // graphComponent.validate();
        // graphComponent.validateGraph();
        graphComponent.refresh();
     
        graphComponent.setLayoutStructure(this);
        graphComponent.setProject(project);
        //graphComponent.repaint();
        jpanel.add(graphComponent);
        jpanel.updateUI();
       // graphComponent.getC
    }

	// fonction which print on graph the symbols
    public static void printSymbols(mxGraph graph, Object parent, Object grammar, Project project) {
        String styleParent = mxConstants.STYLE_FILLCOLOR + "=#ffffff";
        Object levelSymbol = graph.insertVertex(parent, null, ToolKit.transformToString(project.getGrammar().getSymbols(), ","), 80, 230, 10 * ToolKit.transformToString(project.getGrammar().getSymbols(), ",").length(), 30, mxConstants.STYLE_FILLCOLOR + "=#ffffff");
        graph.insertEdge(parent, null, "SYMBOLS", grammar, levelSymbol);

    }

    // print of initial doc
    public static void printInitialDoc(Tree t, mxGraph graph, Object parent, Object grammar, Project project) {
        String styleParent = mxConstants.STYLE_FILLCOLOR + "=green;" + mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ELLIPSE + ";" + mxConstants.STYLE_FONTCOLOR + "=white;";
        Object levelAxiom = graph.insertVertex(parent, null, t, 80, 70, 30, 20, styleParent);
        
        for (Tree sun : t.getSuns()) {
            processDoc(sun, levelAxiom, graph, parent, project);
        }
    }

    // print of initial doc
    public static void processDoc(Tree t, Object treeParent, mxGraph graph, Object parent, Project project) {

        String styleParent = mxConstants.STYLE_FILLCOLOR + "=green;" + mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ELLIPSE + ";" + mxConstants.STYLE_FONTCOLOR + "=white;";
        String label = "";
        if (t.isBud()) {
            label = t.getSymbol().getName() + "ω";
        } else {
            label = t.getSymbol().getName();
        }
        Object levelNode = graph.insertVertex(parent, null,t, 80, 70, 30, 20, styleParent);
        graph.insertEdge(parent, null, "", treeParent, levelNode);
        if (!t.getSuns().isEmpty()) {
            for (Tree sun : t.getSuns()) {
                processDoc(sun, levelNode, graph, parent, project);
            }
        }

    }

    // print of axiom
    public static void printAxiom(mxGraph graph, Object parent, Object grammar, Project project) {
        String styleParent = mxConstants.STYLE_FILLCOLOR + "=red;" + mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ELLIPSE + ";";//+mxConstants.STYLE_GRADIENTCOLOR+"=#;";

        Object levelAxiom = graph.insertVertex(parent, null, project.getGrammar().getAxiom().getName(), 80, 70, 40, 30, styleParent);
        graph.insertEdge(parent, null, "AXIOM", grammar, levelAxiom);

    }

    /**
     *
     */
   
    /**
     * 
     * @return
     */
    
    public mxGraphComponent getGraphComponent() {
        return graphComponent;
    }

    public void setGraphComponent(CustomGraphComponent graphComponent) {
        this.graphComponent = graphComponent;
    }

    public mxGraph getGraph() {
        return graph;
    }

    public void setGraph(mxGraph graph) {
        this.graph = graph;
    }

    public boolean isPageView() {
        return pageView;
    }

    public void setPageView(boolean pageView) {
        this.pageView = pageView;
        if (this.pageView) {
            graphComponent.setPageVisible(true);
            graphComponent.setPageScale(2.5);
        } else {
            graphComponent.setPageVisible(false);
        }
        graphComponent.refresh();
        panel.repaint();
        panel.validate();
        zoomOut();
        zoomInt();
    }

    public void zoomOut() {
      
        graphComponent.zoomOut();
        
        panel.setPreferredSize(new Dimension((int)(panel.getPreferredSize().getWidth()/graphComponent.getZoomFactor()),(int) (panel.getPreferredSize().getHeight()/graphComponent.getZoomFactor())));
        graphComponent.refresh();
        panel.updateUI();
        panel.repaint();panel.revalidate();
    }

    public void zoomInt() {
        
        graphComponent.zoomIn();
        
        panel.setPreferredSize(new Dimension((int)(panel.getPreferredSize().getWidth()*graphComponent.getZoomFactor()),(int) (graphComponent.getZoomFactor()*panel.getPreferredSize().getHeight())));
        graphComponent.refresh();
        panel.updateUI();
        panel.repaint();panel.revalidate();
    }

    public void setZoomFactor(double factor) {
        graphComponent.setZoomFactor(factor);
    }
    
    public void exportInImage(JFrame fen){
		
		 
		 FileNameExtensionFilter filter = new FileNameExtensionFilter ("PNG Images", "png"); 
		    chooser.setFileFilter(filter); 
		    File f;
		    boolean delete=false,cancel=false;
		    String path="";
		 do{
		    // ouvre la bo�te de dialogue et bloque l�interaction (dialogue modal) 
		    chooser.setAcceptAllFileFilterUsed(false);
		    //chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		   // chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		     int returnVal = chooser.showSaveDialog(fen);
		     path="";
		     f=new File("");
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		    			path=chooser.getSelectedFile().getAbsolutePath();
		    			String extension = "";
		    			if(path.length()>4){
		    				extension=path.substring(path.length()-4,path.length());
		    				System.out.println("l'extension est "+extension);
		    				if(!extension.equals(".png"))path+=".png";
		    			}
		    			f=new File(path);
		    			if(f.exists()){
		    				int act=JOptionPane.showConfirmDialog(fen,"ce fichier existe deja voulez vous l'ecraser ?","Confirmation",JOptionPane.YES_NO_OPTION);
		    				if(act==JOptionPane.YES_OPTION)delete=true;
		    				else path="";
		    			}else delete=true;
		    			
		    			
		    			//extensionPhoto=chooser.getSelectedFile().get
		    }
		    else{
		    	cancel=true;
		    }
		 }while(!cancel && (f.exists() && !delete));
		 if(!path.equals("")){
			 if(saveImage(path)){
				 JOptionPane.showMessageDialog(fen, "Exportation terminée ! chemin: "+path,"Exportation Terminée", JOptionPane.INFORMATION_MESSAGE );
			 }else{
				 JOptionPane.showMessageDialog(fen, "Echec de l'operation ! veillez ressayez !", "Echec de l'operation !", JOptionPane.ERROR_MESSAGE );
			 }
		 }
		
	}

	private boolean saveImage( String path) {
		// TODO Auto-generated method stub
			String chemin = path;
			int width  = graphComponent.getWidth();
			int height = graphComponent.getHeight();
			BufferedImage image = new BufferedImage(width, height,  BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
		        graphComponent.paintAll(g);
			g.dispose();/*
			Component[] composants = p.getGraphComponent().getComponents();
			for(Component compo : composants){
				compo.paintAll(g);
			}*/
			String format ="png";
			File f=new File(chemin);
			/*try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			try {
				ImageIO.write(image, format, f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return false;
			}
			return true;
			
		}
	
	public void redispose(){
		this.dispose(panel, project);
	}

}
