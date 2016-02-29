package Editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Common.Define;
import Common.Lib;
import Data.DAO.AttriDAO;
import Object.Attri;
import Object.RoleClass;
import Object.ValueType;
 
public class AttriPanel extends JPanel implements ActionListener,ListSelectionListener,ListCellRenderer{
	JPanel leftPanel,rightPanel;
	JTextField nameField,valueField,attriIdField;
	JList<ValueType> typeList;
	JComboBox<Attri> attriSelector;
	ArrayList<Attri> attriDefine;
	ValueType selectValueType;
	int attri_id = -1;
	
	public AttriPanel(){
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		rightPanel.setBackground(Color.white);
		this.setLayout(new GridLayout(1, 2));
		this.add(leftPanel);
		this.add(rightPanel);
		
		drawLeftPanel();
		drawRightPanel();
	}
	private void drawRightPanel() {
		JLabel nameLabel,typeLabel,valueLabel,attriIdLabel;
		nameLabel = new JLabel("Attribute Name");
		typeLabel = new JLabel("Value Type");
		attriIdLabel = new JLabel("Attribute ID");
		valueLabel = new JLabel("Value");
		
		nameField = new JTextField();
		valueField = new JTextField();
		attriIdField = new JTextField();
		attriIdField.setEditable(false);
		
		attriDefine = new AttriDAO().getAllAttriDefine();
		attriSelector = new JComboBox<Attri>();
		Lib.getInstance().generateBox(attriDefine, attriSelector);
		attriSelector.setRenderer(this);
		attriSelector.addActionListener(this);
		
		JButton addButton = new JButton("Add New Attribute");
		addButton.addActionListener(this);
		
		typeList = new JList<ValueType>(Define.TYPE_DEFINE);
		typeList.addListSelectionListener(this);
		JScrollPane scroll = new JScrollPane(typeList);
		
		rightPanel.setLayout(new GridLayout(7,2,10,10));
		rightPanel.setBorder(new EmptyBorder(30, 10, 10, 30));
		JPanel nameSelectPanel = new JPanel();
		
		nameSelectPanel.setLayout(new GridLayout(2,1,0,10));
		nameSelectPanel.add(nameField);
		nameSelectPanel.add(attriSelector);
		
		rightPanel.add(nameLabel);rightPanel.add(nameSelectPanel);
		rightPanel.add(typeLabel);rightPanel.add(scroll);
		rightPanel.add(attriIdLabel);rightPanel.add(attriIdField);
		rightPanel.add(valueLabel);rightPanel.add(valueField);
		rightPanel.add(addButton);
	}
	private void drawLeftPanel() {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton button = (JButton)e.getSource();
			if(button.getText().equals("Add New Attribute")){
				String nameString = nameField.getText();
				ValueType valueType = selectValueType;
				String value = valueField.getText();
				
				Attri attri = null;
				switch (valueType.getType()) {
				case Define.INT_TYPE:
					attri = new Attri(nameString, attri_id, valueType.getType(), Integer.valueOf(value));
					break;
				case Define.FLOAT_TYPE:
					attri = new Attri(nameString, attri_id, valueType.getType(), Float.valueOf(value));			
					break;
				case Define.STRINF_TYPE:
					attri = new Attri(nameString, attri_id, valueType.getType(), value);
					break;
				case Define.BOOLEAN_TYPE:
					attri = new Attri(nameString, attri_id, valueType.getType(), Boolean.valueOf(value));
					break;
				default:
					break;
				}
				attri = new AttriDAO().createNewAttribute(attri);
				if(attri!=null)
					attriIdField.setText(String.valueOf(attri.getDefineID()));
				attri_id = -1;
			} 
		}else if(e.getSource() instanceof JComboBox){
			JComboBox<Attri> box = (JComboBox<Attri>)e.getSource();
			Attri attri = (Attri)box.getSelectedItem();
			nameField.setText(attri.getName());
			typeList.setSelectedIndex(attri.getType());
			attri_id = attri.getDefineID();
			attriIdField.setText(String.valueOf(attri_id));
		
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList eventList = (JList) e.getSource();
		if(typeList==eventList){
			selectValueType = typeList.getSelectedValue();
		}
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel  c = new JLabel();
		if(value instanceof Attri){
			Attri attri = (Attri) value;
			c.setText(attri.getName());
			return c;
		}
		return null;
	}
	
	
}
