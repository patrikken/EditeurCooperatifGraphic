package layout;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;

import metier.TreeUtil;
import metier.entities.Project;
import metier.entities.Symbol;
import metier.entities.Tree;
import metier.entities.ViewTree;
import views.ProductionChoiceDialog;
import views.SymboleBourgeonJDialog;

public class CustomGraphComponent extends mxGraphComponent {

    /**
     *
     */
    private static final long serialVersionUID = -6833603133512882012L;
    
    private Project project;
    private StructuredLayout layoutStructure;

    public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	public StructuredLayout getLayoutStructure() {
		return layoutStructure;
	}

	public void setLayoutStructure(StructuredLayout layoutStructure) {
		this.layoutStructure = layoutStructure;
	}

	/**
     *
     * @param graph
     */
    public CustomGraphComponent(mxGraph graph) {
        super(graph);

		// Sets switches typically used in an editor
        //setPageVisible(true);
        //setPageScale(2);
        setZoomFactor(1.3);
        setCenterZoom(true);
        setGridVisible(true);
        setToolTips(true);
        zoomOut();
		//setMinimumSize(new Dimension(1000,3000));
        //setPreferPageSize(false);
        // setPreferredSize(new Dimension(5000,3000));
        getConnectionHandler().setCreateTarget(true);

        // Loads the defalt stylesheet from an external file
        mxCodec codec = new mxCodec();
        Document doc = mxUtils.loadDocument(StructuredLayoutTools.class.getResource(
                "/com/mxgraph/examples/swing/resources/default-style.xml")
                .toString());
        codec.decode(doc.getDocumentElement(), graph.getStylesheet());

        // Sets the background to white
        getViewport().setOpaque(true);
        getViewport().setBackground(Color.WHITE);
        setPreferPageSize(true);
    }

    /**
     * Overrides drop behaviour to set the cell style if the target is not a
     * valid drop target and the cells are of the same type (eg. both
     * vertices or both edges).
     */
    public Object[] importCells(Object[] cells, double dx, double dy,
            Object target, Point location) {
        if (target == null && cells.length == 1 && location != null) {
            target = getCellAt(location.x, location.y);

            if (target instanceof mxICell && cells[0] instanceof mxICell) {
                mxICell targetCell = (mxICell) target;
                mxICell dropCell = (mxICell) cells[0];

                if (targetCell.isVertex() == dropCell.isVertex()
                        || targetCell.isEdge() == dropCell.isEdge()) {
                    mxIGraphModel model = graph.getModel();
                    model.setStyle(target, model.getStyle(cells[0]));
                    graph.setSelectionCell(target);

                    return null;
                }
            }
        }

        return super.importCells(cells, dx, dy, target, location);
    }
    
	public String getEditingValue(Object cell, EventObject trigger)
	{
		if (cell instanceof mxCell)
		{
			Object value = ((mxCell) cell).getValue();
            //mxICell cellParent = ((mxCell) cell).getParent();
            //mxHierarchicalLayout layout=new mxHierarchicalLayout(arg0)
			if (value instanceof Tree)
			{
					Tree tree = (Tree) value;
					System.out.println("simple tree");
	            System.out.println(tree.getEditingMode());
				if(tree.getEditingMode()==Tree.SIMPLEEDITABLENODE){
					//JOptionPane.showMessageDialog(null,"HI","hi", JOptionPane.INFORMATION_MESSAGE);
					ProductionChoiceDialog chDialog= new ProductionChoiceDialog(TreeUtil.getExpandProductions(tree, project.getGrammar()));
					chDialog.setVisible(true);
					if(chDialog.getChoice()!=null && !chDialog.isBudSelected()){
						
						tree.setSuns(TreeUtil.getSunsFrom(chDialog.getChoice().getRightSide()));
						project.inValidateReplicat();
					}else if(chDialog.isBudSelected()){
						tree.setSuns(new ArrayList<Tree>());
						tree.setBud(true);
						project.inValidateReplicat();
					}
					layoutStructure.redispose();
					return tree.getSymbol().getName();
					
				}else if(tree.getEditingMode()==Tree.FULLEDITABLENODE && tree.isBud()){
					
					SymboleBourgeonJDialog dialogSymbBub=new SymboleBourgeonJDialog(null, true,project.getGrammar().getSymbols());
					dialogSymbBub.setVisible(true);
					if(dialogSymbBub.isFinish()){
						if(dialogSymbBub.isBourgeon()){
							tree.setSuns(new ArrayList<Tree>());
							tree.setBud(true);
						}else{
							ArrayList<Symbol> arraySymb=dialogSymbBub.getSymbols();
							tree.setSuns(TreeUtil.getSunsFrom(arraySymb));
							for(Tree t: tree.getSuns()){
								t.setEditingMode(Tree.FULLEDITABLENODE);
							}
						}
						layoutStructure.redispose();
						return tree.getSymbol().getName();
					}
				}
			}
			else if(value instanceof ViewTree){
				System.out.println("Viewtree");
				ViewTree vt=(ViewTree)value;
				Tree tree=vt.tree;
				System.out.println("mode d'edition "+tree.getEditingMode());
				if(tree.getEditingMode()==Tree.FULLEDITABLENODE ){
					
					SymboleBourgeonJDialog dialogSymbBub=new SymboleBourgeonJDialog(null, true,vt.view.getSymbols());
					dialogSymbBub.setVisible(true);
					if(dialogSymbBub.isFinish()){
						if(dialogSymbBub.isBourgeon()){
							tree.setSuns(new ArrayList<Tree>());
							tree.setBud(true);
						}else{
							ArrayList<Symbol> arraySymb=dialogSymbBub.getSymbols();
							tree.setSuns(TreeUtil.getSunsFrom(arraySymb));
							for(Tree t: tree.getSuns()){
								t.setEditingMode(Tree.FULLEDITABLENODE);
								//t.setBud(true);
							}
						}
						tree.setEditingMode(Tree.FULLEDITABLENODE);
						layoutStructure.redispose();
						return tree.getSymbol().getName();
					}
				}
			}
		}

		return super.getEditingValue(cell, trigger);
	};

}

