/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author patrik
 */
public class CustumTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

        // Assuming you have a tree of Strings
        String node = (String) ((DefaultMutableTreeNode) value).getUserObject();
        DefaultMutableTreeNode nod = (DefaultMutableTreeNode) value;
        // If the node is a leaf and ends with "xxx"  && node.startsWith("v")
        if (leaf && nod.getParent().toString().equals("Auteurs")) {
            // Paint the node in blue 
            if (node.startsWith("+")) {
                setIcon(new ImageIcon(getClass().getResource("/views/img/user-plus-icon.png")));
                setToolTipText("Clicquer ici pour ajouter un auteur");
            } else {
                setIcon(new ImageIcon(getClass().getResource("/views/img/author.png")));
            }
            setForeground(new Color(13, 57, 240));
        }
        if (leaf && nod.getParent().toString().equals("Vues")) {
            // Paint the node in blue 
            if (node.startsWith("+")) {
                setIcon(new ImageIcon(getClass().getResource("/views/img/view.png"))); 
            } else { 
            }
            setForeground(new Color(240, 57, 13));
        }

        return this;
    }
}
