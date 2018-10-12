package basictest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import layout.StructuredLayout;
import metier.Compiler;
import metier.TreeUtil;
import metier.entities.Author;
import metier.entities.Grammar;
import metier.entities.Production;
import metier.entities.Project;
import metier.entities.Replicat;
import metier.entities.Symbol;
import metier.entities.Tree;
import metier.entities.View;

import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;

import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.mxConstants;

import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

public class BaseTest {

	
	public static Project createTestProject(){
		
		Project p= new Project();
		Symbol A=new Symbol("A"), B=new Symbol("B") ,C= new Symbol("C");
		Grammar g= new Grammar();
		p.setName("projet test");
		ArrayList<Symbol> symbs= new ArrayList<>();
		Production prod1= new Production();
		Production prod2= new Production();
		Production prod3= new Production();
		prod1.setLeftSide(A);
		prod2.setLeftSide(B);
		prod3.setLeftSide(C);
		prod1.setName("P1");
		prod2.setName("P2");
		prod3.setName("P3");
		ArrayList<Symbol> array1=new ArrayList<>();
		ArrayList<Symbol> array2=new ArrayList<>();
		ArrayList<Symbol> array3=new ArrayList<>();
		
		array1.add(A);
		array1.add(B);
		array1.add(C);
		array1.add(A);
		
		
		array2.add(B);
		array2.add(C);
		array2.add(A);
		
		
		array3.add(C);
		array3.add(B);
		array3.add(A);
		
		prod1.setRightSide(array1);
		prod2.setRightSide(array2);
		prod3.setRightSide(array3);
		
		ArrayList<Production> prods= new ArrayList<>();
		prods.add(prod1);
		prods.add(prod2);
		prods.add(prod3);
		
		g.setProductions(prods);
		symbs.add(A);
		symbs.add(B);
		symbs.add(C);
		g.setSymbols(symbs);
		g.setAxiom(A);
		p.setGrammar(g);
		
		// create Initial Doc
		Tree t=new Tree();
		t.setSymbol(A);
		ArrayList<Tree> sunRoot= new ArrayList<>();
		Tree f1= new Tree(A), f2= new Tree(B) , f3=new Tree(C) ,f4=new Tree(A);
		sunRoot.add(f1);
		sunRoot.add(f2);
		sunRoot.add(f3);
		sunRoot.add(f4);
		t.setSuns(sunRoot);
		Tree f21= new Tree(B,true), f22= new Tree(C,true) , f23=new Tree(A);
		ArrayList<Tree> sunRoot2= new ArrayList<>();
		sunRoot2.add(f21);
		sunRoot2.add(f22);
		sunRoot2.add(f23);
		f2.setSuns(sunRoot2);
		
		p.setInitialDocument(t);
		
		// creating of static view
		
		View v1= new View();
		v1.setSymbols(new ArrayList());
		
		
		View v2= new View();
		v2.setSymbols(new ArrayList());
		
		v1.getSymbols().add(A);
		v1.getSymbols().add(C);
		
		v2.getSymbols().add(A);
		v2.getSymbols().add(B);
		
		v1.setName("V1");
		v2.setName("V2");
		p.setViews(new ArrayList<View>());
		
		// create of static author
		Author a1= new Author();
		Author a2= new Author();
		a1.setId("Author1");
		a1.setName("Francois");
		a1.setSite("Campus A");
		a1.setView(v1);
		a2.setId("Author2");
		a2.setName("Mitterand");
		a2.setSite("Campus B");
		a2.setView(v2);
		
		Collection<Tree> foret1= TreeUtil.projection(p.getInitialDocument(), v1);
		Collection<Tree> foret2= TreeUtil.projection(p.getInitialDocument(), v2);
		Tree t1=new Tree(), t2=new Tree();
		for(Tree tf:foret1){
			t1=tf;
		}
		for(Tree tf:foret2){
			t2=tf;
		}
		Tree t11 =new Tree() ,t12=new Tree();
		Tree t21 =new Tree() ,t22=new Tree();
		t11.setSymbol(C);
		t12.setSymbol(A);
		t21.setSymbol(B);
		t22.setSymbol(A);
		t1.getSuns().get(1).setBud(false);
		t1.getSuns().get(1).getSuns().add(t11);
		t1.getSuns().get(1).getSuns().add(t12);
		
		t2.getSuns().get(1).getSuns().get(0).setBud(false);
		t2.getSuns().get(1).getSuns().get(0).getSuns().add(t21);
		t2.getSuns().get(1).getSuns().get(0).getSuns().add(t22);
		
		Replicat R1= new Replicat();
		R1.setAuthor(a1);
		R1.setTree(t1);
		
		Replicat R2= new Replicat();
		R2.setAuthor(a2);
		R2.setTree(t2);
		
		p.getViews().add(v1);
		p.getViews().add(v2);
		
		p.getReplicats().add(R1);
		p.getReplicats().add(R2);
		p.getAuthors().add(a1);
		p.getAuthors().add(a2);
		return p;
	}
	
	public static void main(String[] args){
		
		mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
		mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";

		JFrame frame= new JFrame("Test");

		JPanel panel= new JPanel();
		panel.setBorder(new MatteBorder(5, 5, 5, 5, (Color) Color.LIGHT_GRAY));
		
		
		//frame.getContentPane().setLayout(new FlowLayout());
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel);
		new StructuredLayout().dispose(panel, BaseTest.createTestProject());
		System.out.println(Compiler.compile(BaseTest.createTestProject()));
		frame.setSize(950,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
