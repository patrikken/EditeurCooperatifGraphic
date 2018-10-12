package layout;

import java.util.ArrayList;
import java.util.Collection;

import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import metier.ToolKit;
import metier.TreeUtil;
import metier.entities.Project;
import metier.entities.Replicat;
import metier.entities.Tree;
import metier.entities.View;
import metier.entities.ViewTree;

public class StructuredLayoutTools {

	
	public void disposeView(){
		
	}
	
	public Collection<CoupleViewTreeRepresentation> disposeView(mxGraph graph,Object parent , Object defaultParent ,Object tree,Project project){
		String styleParent = mxConstants.STYLE_FILLCOLOR + "=white;";//+mxConstants.STYLE_GRADIENTCOLOR+"=#;";
		
		//Object levelAxiom = graph.insertVertex(parent, null,project.getGrammar().getAxiom().getName(), 80,70 ,40,30,styleParent);
		
		ArrayList<CoupleViewTreeRepresentation> treeCol=new ArrayList<CoupleViewTreeRepresentation>();
		for(View v:project.getViews()){
			
			Collection<Tree> foret=TreeUtil.projection(project.getInitialDocument(), v,Tree.NOEDITABLENODE);
			Object levelView= graph.insertVertex(parent, null,"", 80,70 ,40,30,styleParent);
			CoupleViewTreeRepresentation couple= new CoupleViewTreeRepresentation() ;
			couple.view=v;
			couple.treePresentation=levelView;
			treeCol.add(couple);
			  mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
			for(Tree t: foret){
				renderDoc(t, graph, levelView, project);
			}
			layout.execute(levelView);
			graph.insertEdge(defaultParent, null, "VIEW "+v.getName()+"={"+ ToolKit.transformToString(v.getSymbols(), ",") +"}", tree, levelView);
					
		}
		
		return treeCol;
		 
	}
	
	// print of  doc
		public  void renderDoc(Tree t,mxGraph graph,Object parent ,Project project){
			String styleParent = mxConstants.STYLE_FILLCOLOR + "=green;"+mxConstants.STYLE_SHAPE + "="+mxConstants.SHAPE_ELLIPSE+";"+mxConstants.STYLE_FONTCOLOR+"=white;";
			Object levelAxiom = graph.insertVertex(parent, null,t, 80,70 ,30,20,styleParent);
			for(Tree sun:t.getSuns()){
				processDoc(sun, levelAxiom, graph, parent, project);
			}
		}
		
		// rendred process of doc
			public  void processDoc(Tree t,Object treeParent ,mxGraph graph,Object parent, Project project){
				
				String styleParent = mxConstants.STYLE_FILLCOLOR + "=green;"+mxConstants.STYLE_SHAPE + "="+mxConstants.SHAPE_ELLIPSE+";"+mxConstants.STYLE_FONTCOLOR+"=white;";
				String label="";
				if(t.isBud()){
					label=t.getSymbol().getName()+"W";
				}else{
					label=t.getSymbol().getName();
				}
				Object levelNode = graph.insertVertex(parent, null,t, 80,70 ,30,20,styleParent);
				graph.insertEdge(parent, null, "", treeParent, levelNode);
				if(!t.getSuns().isEmpty()){
					for(Tree sun:t.getSuns()){
						processDoc(sun,levelNode, graph,parent, project);
					}
				}
				
			}
	
			// rendered a doc for a view
			// print of  doc
			public  void renderDoc(Tree t,mxGraph graph,Object parent ,Project project,View v){
				String styleParent = mxConstants.STYLE_FILLCOLOR + "=green;"+mxConstants.STYLE_SHAPE + "="+mxConstants.SHAPE_ELLIPSE+";"+mxConstants.STYLE_FONTCOLOR+"=white;";
				Object levelAxiom = graph.insertVertex(parent, null,new ViewTree(v,t), 80,70 ,30,20,styleParent);
				for(Tree sun:t.getSuns()){
					processDoc(sun, levelAxiom, graph, parent, project,v);
				}
			}
			
			
			// rendred process of doc
			// using view
						public  void processDoc(Tree t,Object treeParent ,mxGraph graph,Object parent, Project project,View view){
							
							String styleParent = mxConstants.STYLE_FILLCOLOR + "=green;"+mxConstants.STYLE_SHAPE + "="+mxConstants.SHAPE_ELLIPSE+";"+mxConstants.STYLE_FONTCOLOR+"=white;";
							String label="";
							if(t.isBud()){
								label=t.getSymbol().getName()+"W";
							}else{
								label=t.getSymbol().getName();
							}
							Object levelNode = graph.insertVertex(parent, null,new ViewTree(view, t), 80,70 ,30,20,styleParent);
							graph.insertEdge(parent, null, "", treeParent, levelNode);
							if(!t.getSuns().isEmpty()){
								for(Tree sun:t.getSuns()){
									processDoc(sun,levelNode, graph,parent, project,view);
								}
							}
							
						}
			
			public void disposeReplicat(mxGraph graph,Object parent , Object defaultParent ,Object tree,View view,Project project){
				String styleParent = mxConstants.STYLE_FILLCOLOR + "=white;";//+mxConstants.STYLE_GRADIENTCOLOR+"=#;";
				
				//Object levelAxiom = graph.insertVertex(parent, null,project.getGrammar().getAxiom().getName(), 80,70 ,40,30,styleParent);
				
				
				for(Replicat r:project.getReplicats()){
					int left=10;
					if(r.getAuthor().getView().equals(view)){
						  Object levelReplicat= graph.insertVertex(parent, null,"", 80,70 ,40,30,styleParent);
						
						  Object levelAuthor= graph.insertVertex(levelReplicat, null,r.getAuthor().getId()+"\n"+r.getAuthor().getName(), left,70 ,100,100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ACTOR+";");
						  left+=300;
						  mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
						  mxHierarchicalLayout layout1 = new mxHierarchicalLayout(graph);
						  Object levelDocument= graph.insertVertex(levelReplicat, null,"", 80,70 ,40,30,styleParent);
						  
						  Object levelSite= graph.insertVertex(levelReplicat, null,r.getAuthor().getSite(), 80,70 ,40,30,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_CLOUD+";");
						
							renderDoc(r.getTree(), graph, levelDocument, project,view);
							mxEdgeLabelLayout edgeLayout= new mxEdgeLabelLayout(graph);
							mxFastOrganicLayout lay= new mxFastOrganicLayout(graph);
						layout.execute(levelReplicat);
						layout1.execute(levelDocument);
						graph.insertEdge(levelReplicat, null,"SITE", levelAuthor,levelSite);
						graph.insertEdge(levelReplicat, null,"REPLICAT", levelAuthor,levelDocument);
						graph.insertEdge(defaultParent, null,"EDITION", tree,levelDocument);
							
					}
				}
				
				 
			}
		
}
