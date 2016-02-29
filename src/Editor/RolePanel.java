package Editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Common.Lib;
import Data.DAO.AttriDAO;
import Data.DAO.RoleAttibutesDAO;
import Object.Attri;
import Object.Gear;
import Object.Player;
import Object.Role;
import Object.RoleAttributes;

public class RolePanel extends JPanel implements ListCellRenderer,ActionListener,ListSelectionListener,MouseListener{
	JPanel leftPanel,rightPanel;
	JLabel roleInfoLabel;
	JList<Attri> roleAttriList;
	JList<Gear> equipList;
	JComboBox<Attri> attriSelector;
	ArrayList<Attri> attributes;
	ArrayList<Attri> attriDefine;
	JPanel leftListPanel,rightBoundPanelTop,rightBoundPanelBottom,rightCenterPanel;
	Attri selectedAttri;
	Gear selectedGear;
	JTextField attri_name,attri_value,add_attri_value;
	JButton UpdateAttri,DeleteAttri,AddAttri;
	public Player player;
	public Role role;
	public RolePanel(){
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		rightPanel.setBackground(Color.white);
		leftPanel.setBackground(new Color(250,250,189));
		this.setLayout(new GridLayout(1, 2));
		this.add(leftPanel);
		this.add(rightPanel);
		drawLeftPanel();
		drawRightPanel();
	}
	
	private void drawRightPanel(){
		rightPanel.setLayout(new BorderLayout());
		rightBoundPanelTop = new JPanel();
		rightCenterPanel= new JPanel();
		rightBoundPanelBottom = new JPanel();
		rightCenterPanel.setLayout(new GridLayout(2,1,0,10));
		rightPanel.add(rightCenterPanel,BorderLayout.CENTER);
		
		rightCenterPanel.add(rightBoundPanelTop,0);
		rightCenterPanel.add(rightBoundPanelBottom,1);
		
		UpdateAttri = new JButton("Update");
		DeleteAttri = new JButton("Delete");
		AddAttri = new JButton("Add");
		
		UpdateAttri.addActionListener(this);
		DeleteAttri.addActionListener(this);
		AddAttri.addActionListener(this);
	}
	private void drawLeftPanel(){

		leftListPanel  = new JPanel();
		leftListPanel.setLayout(new GridLayout(1,2,10,0));
		
		roleInfoLabel = new JLabel("Role");
		roleAttriList = new JList<Attri>();
		equipList = new JList<Gear>();
		equipList.addMouseListener(this);
		leftListPanel.add(roleAttriList);
		leftListPanel.add(equipList);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(roleInfoLabel,BorderLayout.NORTH);
		leftPanel.add(leftListPanel,BorderLayout.CENTER);
	}
	
	private void reloadList(){
		if(role==null)
			System.out.println("Sha ye mei you");
		RoleAttributes roleattributes =new RoleAttibutesDAO().getRollAttributesByRole(role);
		try {
			attributes = roleattributes.getAllAttributes();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		roleAttriList = Lib.getInstance().reloadList(attributes, roleAttriList, leftListPanel);
		roleAttriList.addMouseListener(this);
		roleAttriList.setCellRenderer(this);
		roleAttriList.addListSelectionListener(this);
		leftListPanel.add(roleAttriList,0);
	}
	
	public void setPlayer(Player p){
		this.player = p;
	}
	public void setRole(Role r){
		this.role = r;
		roleInfoLabel.setText("Role: " + r.name);
		reloadList();
		
		attriDefine = new AttriDAO().getAllAttriDefine();
		
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel  c = new JLabel();
		if(value instanceof Attri){
			Attri attri = (Attri) value;
			c.setText(attri.getName()+":    " + attri.getValueInString());
			if(isSelected){
				c.setBackground(new Color(189, 235, 252, 200));
				c.setBorder(BorderFactory.createLineBorder(new Color(112, 188, 252, 255)));
				c.setOpaque(true);
			}
			return c;
		}
		return null;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		 if (!e.getValueIsAdjusting()) {//This line prevents double events
			JList list = (JList)e.getSource();
			if(list == roleAttriList){
				selectedAttri = roleAttriList.getSelectedValue();
				boundAttiPanel();
			}else if(list==equipList){
				selectedGear = equipList.getSelectedValue();
				boundGearPanel();
			}
			validate();
			repaint();
		 }
	}

	private void boundGearPanel() {
		
	}

	private void boundAttiPanel() {
		JPanel attripanel = new JPanel();
		JPanel addAttripanel = new JPanel();
		attripanel.setLayout(new GridLayout(4,2,20,30));
		attripanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 30));
		addAttripanel.setLayout(new GridLayout(4,2,20,30));
		addAttripanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 30));
//		attripanel.setSize(320, 320);
		
		JLabel attri_nameLabel, attri_valueLabel,add_nameLabel,add_valueLable;
		attri_nameLabel = new JLabel("Attribute Name");
		attri_valueLabel = new JLabel("Attribute Value");
		add_nameLabel = new JLabel("Attribute Name");
		add_valueLable = new JLabel("Attribute Value");
		
		attri_name = new JTextField((selectedAttri==null)?"":selectedAttri.getName());
		attri_name.setEditable(false);
		attri_value = new JTextField((selectedAttri==null)?"":selectedAttri.getValueInString());
		
		attripanel.add(attri_nameLabel);attripanel.add(attri_name);
		attripanel.add(attri_valueLabel);attripanel.add(attri_value);
		attripanel.add(UpdateAttri);attripanel.add(DeleteAttri);

		rightCenterPanel.remove(rightBoundPanelTop);
		rightBoundPanelTop = attripanel;
		rightCenterPanel.add(rightBoundPanelTop,0);
		
		
		attriSelector = new JComboBox<Attri>();
		Lib.getInstance().generateBox(attriDefine, attriSelector);
		attriSelector.setRenderer(new ColorCellRenderer());
		attriSelector.addActionListener(this);
		
		add_attri_value = new JTextField();
		addAttripanel.add(add_nameLabel);addAttripanel.add(attriSelector);
		addAttripanel.add(add_valueLable);addAttripanel.add(add_attri_value);
		addAttripanel.add(AddAttri);
		
		rightCenterPanel.remove(rightBoundPanelBottom);
		rightBoundPanelBottom = addAttripanel;
		rightCenterPanel.add(rightBoundPanelBottom,1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton button = (JButton) e.getSource();
			if(button==AddAttri){
				Attri attri =(Attri)attriSelector.getSelectedItem();
				Attri attri_clone = Attri.cloneTo(attri);
				String valueString = add_attri_value.getText();
				attri_clone.setValueWithString(valueString);
				attri_clone = new AttriDAO().createNewAttribute(attri_clone);
				if(attri_clone!=null){
					new AttriDAO().linkAttriTo("Role", attri_clone, role);
					reloadList();
				}
				
			}else if(button ==UpdateAttri){
//				new AttriDAO().updateAttriValue();
			}else if(button == DeleteAttri){
				new AttriDAO().removeAttriLink("Role", selectedAttri, role);
				reloadList();
			}
		}
		validate();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == roleAttriList){
			System.out.println("attri");
			boundAttiPanel();
		}
		validate();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
class ColorCellRenderer implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();


	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel  c = new JLabel();
			if(value instanceof Attri){
				Attri attri = (Attri) value;
				c.setText(attri.getName());
				if(isSelected){
					c.setBackground(new Color(189, 235, 252, 200));
					c.setBorder(BorderFactory.createLineBorder(new Color(112, 188, 252, 255)));
					c.setOpaque(true);
				}
				return c;
			}
			return null;
	  }
}

