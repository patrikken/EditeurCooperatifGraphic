package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;

import metier.entities.Production;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

public class ProductionChoiceDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Collection<Production> listProduction;
	private Production choice;
	private JCheckBox chckbxBourgeon;
	private JComboBox productionBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ProductionChoiceDialog dialog = new ProductionChoiceDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ProductionChoiceDialog(Collection<Production> col) {
		setModal(true);
		listProduction=col;
		
		setBounds(100, 100, 395, 184);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		chckbxBourgeon = new JCheckBox("Bourgeon");
		sl_contentPanel.putConstraint(SpringLayout.WEST, chckbxBourgeon, 85, SpringLayout.WEST, contentPanel);
		chckbxBourgeon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(chckbxBourgeon.isSelected()){
				productionBox.disable();
				}else{
					productionBox.enable();
				}
			}
		} );
		contentPanel.add(chckbxBourgeon);
		
		{
			JSeparator separator = new JSeparator();
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, chckbxBourgeon, -6, SpringLayout.NORTH, separator);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, separator, -7, SpringLayout.SOUTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, separator, 15, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, separator, -5, SpringLayout.SOUTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, separator, -5, SpringLayout.EAST, contentPanel);
			contentPanel.add(separator);
		}
		
		productionBox = new JComboBox();
		sl_contentPanel.putConstraint(SpringLayout.WEST, productionBox, 85, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, productionBox, -144, SpringLayout.EAST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, productionBox, -55, SpringLayout.NORTH, chckbxBourgeon);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, productionBox, -18, SpringLayout.NORTH, chckbxBourgeon);
		contentPanel.add(productionBox);
		
		JLabel lblChoisirUneProduction = new JLabel("Choisir une production");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblChoisirUneProduction, 0, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblChoisirUneProduction, 0, SpringLayout.EAST, productionBox);
		contentPanel.add(lblChoisirUneProduction);
	    for(Production prod:col){
				productionBox.addItem(prod);
			}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						choice=(Production)productionBox.getSelectedItem();
						setVisible(false);
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						setVisible(false);
					}
				});
			}
		}
	}

	

	public Production getChoice() {
		return choice;
	}

	public void setChoice(Production choice) {
		this.choice = choice;
	}
	
	public boolean isBudSelected(){
		return chckbxBourgeon.isSelected();
	}
}
