package Editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Common.Define;
import Common.Lib;
import Data.DAO.AttriDAO;
import Data.DAO.EquipmentDAO;
import Data.DAO.GearDAO;
import Object.Attri;
import Object.Equipment;
import Object.Gear;
import Object.Role;
import Object.Skill;
import Object.Imp.SimpleGear;

public class EquipmentPanel extends JPanel implements MouseListener, ListSelectionListener,ActionListener{
	JPanel leftPanel,rightPanel,listPanel;
	HashMap<Integer,IconComponent> gears;
	JComboBox<Gear> gearList;
	Image selectedImage;
	JLabel selectedLabel; 
	JList<Attri> gearAttriList;
	JList<Skill> gearSkillList;
	JButton select,edit;
	public Gear selectGear;
	public Role role;
	Equipment equipment;
	
	public EquipmentPanel(){
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		rightPanel.setBackground(Color.white);
		this.setLayout(new GridLayout(1, 2));
		this.add(leftPanel);
		this.add(rightPanel);
		gears = new HashMap<Integer,IconComponent>();
	
		drawLeftPanel();
		drawRightPanel();
	}

	private void initialGears() {
		leftPanel.removeAll();
		gears.clear();
		for(int i=0;i<=7;i++){
			Gear g = equipment.getBySlot(i);
			if(g!=null){
				IconComponent gearCom = new GearComponent();
				gearCom.setObj(g);
				gearCom.setBounds(Define.GEAR_POSITIONS[i].getX(), Define.GEAR_POSITIONS[i].getY(), Define.BOX, Define.BOX);
				gearCom.addMouseListener(this);
				leftPanel.add(gearCom);
				gears.put(g.SLOT,gearCom);
			}else{
				IconComponent gearCom = new GearComponent();
				Gear gear = new SimpleGear(i);
				gearCom.setObj(gear);
				gearCom.setBounds(Define.GEAR_POSITIONS[i].getX(), Define.GEAR_POSITIONS[i].getY(), Define.BOX, Define.BOX);
				gearCom.addMouseListener(this);
				leftPanel.add(gearCom);
				gears.put(gear.SLOT,gearCom);
			}
		}
	}
	private void drawRightPanel() {
		rightPanel.setLayout(new BorderLayout());
		JPanel selectPanel = new JPanel();
		JPanel displayPanel = new JPanel();
		listPanel = new JPanel();
		selectPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		displayPanel.setLayout(new BorderLayout());
		listPanel.setLayout(new  GridLayout(1, 2,10,10));
		listPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		displayPanel.add(listPanel,BorderLayout.CENTER);
		
		gearList = new JComboBox<Gear>();
		gearList.addActionListener(this);
		select = new JButton("Equip");
		select.addActionListener(this);
		edit = new JButton("Edit");
		edit.addActionListener(this);
		selectPanel.add(gearList);
		selectPanel.add(select);
		selectPanel.add(edit);

		gearAttriList = new JList<Attri>();
		gearSkillList = new JList<Skill>();
		listPanel.add(gearAttriList);
		listPanel.add(gearSkillList);
		
		selectedLabel = new JLabel("Gear:");
		displayPanel.add(selectedLabel,BorderLayout.NORTH);
		
		rightPanel.add(selectPanel,BorderLayout.NORTH);
		rightPanel.add(displayPanel,BorderLayout.CENTER);
	}
	private void drawLeftPanel() {
		leftPanel.setLayout(null);
	}

	public void setRole(Role r){
		System.out.println("Equipment>>setRole>> " + r.toString());
		this.role = r;
		equipment = new EquipmentDAO().getEquipmentByRole(role);
		initialGears();
		validate();
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof IconComponent){
			GearComponent gearComponent = (GearComponent)e.getSource();
			clearSelected();
			gearComponent.selected = true;
			Gear g = (Gear)gearComponent.obj;
			selectedLabel.setText("Gear: " + g.name);
			refreshList(g);
			for(int i=0; i< gearList.getItemCount();i++){
				Gear gear = gearList.getItemAt(i);
				if(g.name!=null && gear.name!=null  && g.name.equals(gear.name)){
					gearList.setSelectedIndex(i);
				}
			}
			validate();
			repaint();
		}
	}

	private void clearSelected(){
		for(Integer i : gears.keySet()){
			IconComponent c = gears.get(i);
			if(c!=null){
				c.selected = false;
			}
		}
	}
	private void refreshList(Gear g) {
		// TODO Auto-generated method stub
		refreshComboBox(g);
		refreshGearAttributeList(g);
		refreshGearSkillList(g);
	}

	private void refreshGearSkillList(Gear g) {
		
	}

	private void refreshGearAttributeList(Gear g) {
		ArrayList<Attri> gear_attri = new AttriDAO().getAttriByGear(g); 
		gearAttriList = Lib.getInstance().reloadList(gear_attri, gearAttriList, listPanel);
		gearAttriList.setCellRenderer(new GearAttributeRender());
		gearAttriList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		listPanel.add(gearAttriList,0);
	}

	private void refreshComboBox(Gear g) {
		ArrayList<Gear> partList = new GearDAO().getGearsByPart(g.SLOT);
		gearList.removeAllItems();
		Lib.getInstance().generateBox(partList, gearList);
		gearList.setRenderer(new GearListIconRender());
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
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox){
			Gear gear = (Gear)gearList.getSelectedItem();
			if(gear!=null){
				selectedLabel.setText("Gear: " + gear.name);
				refreshGearAttributeList(gear);
			}
		}else if(e.getSource() instanceof JButton){
			JButton button = (JButton)e.getSource();
			if(button==select){
				Gear gear = (Gear)gearList.getSelectedItem();
				if(gear!=null){
					new EquipmentDAO().equipGearToRole(gear, role);
					IconComponent gearOld = gears.get(gear.SLOT);
					leftPanel.remove(gearOld);
					IconComponent gearNew = new GearComponent();
					gearNew.setBounds(Define.GEAR_POSITIONS[gear.SLOT].getX(), Define.GEAR_POSITIONS[gear.SLOT].getY(), Define.BOX, Define.BOX);
					gearNew.addMouseListener(this);
					gearNew.setObj(gear);
					leftPanel.add(gearNew);
					gears.put(gear.SLOT, gearNew);
				}else{
					System.out.println("NUll GEAR");
				}
			}else if(button==edit){
				Gear gear = (Gear)gearList.getSelectedItem();
				if(gear!=null){
					selectGear = gear;
					Editor.getInstance().addGear.doClick();
				}
			}
		}
		validate();
		repaint();
	}
}
class GearListIconRender implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel c = null;
			if(value instanceof Gear){
				Gear gear = (Gear) value;
				String img = gear.img;
				if(img!=null && img.length()>0){
					ImageIcon image = new ImageIcon("img/"+img);
					c = new JLabel(image);
				}else{
					c=new JLabel(gear.toString());
				}
				c.setBackground(Color.white);
			
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
class GearAttributeRender implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel  c = new JLabel();
			if(value instanceof Attri){
				Attri attri = (Attri) value;
				c.setText(attri.getName()+": " + attri.getValueInString());
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
