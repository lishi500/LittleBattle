package Editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Common.Define;
import Common.Lib;
import Data.DAO.AttriDAO;
import Data.DAO.GearDAO;
import Object.Attri;
import Object.Gear;
import Object.Role;
import Object.Skill;
import Object.Imp.SimpleGear;
 
public class GearPanel extends JPanel implements ActionListener, ListSelectionListener,MouseListener{
	JPanel leftPanel,rightPanel,listPanel,topAddPanelAttri,topAddPanelSkill,buttomButtonPanelAttri,buttomButtonPanelSkill,topPanel,buttonPanel;
	JList<Attri> attriList;
	JList<Skill> skillList;
	JComboBox<Gear> partList;
	JComboBox<Integer> categaryList;
	JComboBox<Attri> attriSelector;
	ArrayList<Attri> attriDefine;
	JButton iconButton,saveButton,createButton,addAttriButton,deleteAttriButton;
	JTextField gearNameFiled,attriValueField;
	JTextArea gearDescription;
	JComboBox<Integer> slotList;
	String ImageFileName;
	Gear selectedGear;
	Attri selectedAttri;
	
	public GearPanel(){
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
		rightPanel.setLayout(new BorderLayout());
		listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1,2,10,10));
		listPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		attriList = new JList<Attri>();
		listPanel.add(attriList,0);
		skillList = new JList<Skill>();
		listPanel.add(skillList,1);
		
		{
			topAddPanelAttri = new JPanel();
			topAddPanelAttri.setLayout(new GridLayout(1,3,10,10));
			topAddPanelAttri.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
			topAddPanelAttri.setBackground(Color.white);
			
			attriDefine = new AttriDAO().getAllAttriDefine();
			attriSelector = new JComboBox<Attri>();
			Lib.getInstance().generateBox(attriDefine, attriSelector);
			attriSelector.setRenderer(new ColorCellRenderer());
			attriSelector.addActionListener(this);
			attriValueField = new JTextField();
			addAttriButton = new JButton("Add Attribute");
			addAttriButton.addActionListener(this);
			
			topAddPanelAttri.add(attriSelector);
			topAddPanelAttri.add(attriValueField);
			topAddPanelAttri.add(addAttriButton);			
			
			topPanel = topAddPanelAttri;
		}
		{
			buttomButtonPanelAttri = new JPanel();
			buttomButtonPanelAttri.setLayout(new GridLayout(1,3,10,10));
			buttomButtonPanelAttri.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
			buttomButtonPanelAttri.setBackground(Color.white);
			
			deleteAttriButton = new JButton("Delete Attri");
			deleteAttriButton.addActionListener(this);
			buttomButtonPanelAttri.add(deleteAttriButton);
			buttonPanel = buttomButtonPanelAttri;
		}
		rightPanel.add(topAddPanelAttri,BorderLayout.NORTH);
		rightPanel.add(listPanel,BorderLayout.CENTER);
		rightPanel.add(buttomButtonPanelAttri,BorderLayout.SOUTH);
	}
	private void drawLeftPanel() {
		leftPanel.setLayout(new BorderLayout());
		JPanel comboboxPanel = new JPanel();
		leftPanel.add(comboboxPanel,BorderLayout.NORTH);
		comboboxPanel.setLayout(new GridLayout(1,2,10,10));
		comboboxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		comboboxPanel.setBackground(Color.white);
		categaryList = new JComboBox<Integer>();
		for(int i=0;i<=7;i++){
			categaryList.addItem(Integer.valueOf(i));
		}
		categaryList.setRenderer(new GearCategoryRender());
		categaryList.addActionListener(this);

		partList = new JComboBox<Gear>();
		partList.addActionListener(this);
		
		comboboxPanel.add(categaryList);
		comboboxPanel.add(partList);
		
		JPanel gearInfoPanel  = new JPanel();
		JLabel nameLabel, iconLabel,slotLabel,describLabel;
		nameLabel = new JLabel("Gear Name");
		iconLabel = new JLabel("Gear Icon");
		slotLabel = new JLabel("Gear Slot");
		describLabel = new JLabel("Gear Description");
		gearInfoPanel.setLayout(new GridLayout(5,2,40,40));
		gearInfoPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
		gearInfoPanel.setBackground(Color.white);
		iconButton = new JButton();
		iconButton.setSize(65, 65);
		iconButton.addActionListener(this);	
		slotList = new JComboBox<Integer>();
		for(int i=0;i<=7;i++){
			slotList.addItem(Integer.valueOf(i));
		}
		slotList.setRenderer(new GearCategoryRender());
		slotList.addActionListener(this);
		gearNameFiled = new JTextField("");
		leftPanel.add(gearInfoPanel,BorderLayout.CENTER);
		gearDescription = new JTextArea("");
		gearDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		saveButton = new JButton("Save");saveButton.addActionListener(this);
		createButton = new JButton("Create");createButton.addActionListener(this);
		
		gearInfoPanel.add(nameLabel);	gearInfoPanel.add(gearNameFiled);
		gearInfoPanel.add(iconLabel);	gearInfoPanel.add(iconButton);
		gearInfoPanel.add(slotLabel);	gearInfoPanel.add(slotList);
		gearInfoPanel.add(describLabel);	gearInfoPanel.add(gearDescription);
		gearInfoPanel.add(saveButton); gearInfoPanel.add(createButton);
	
	}
	
	public void setGear(Gear gear){
		this.selectedGear = gear;
		if(this.selectedGear != null){
			gearNameFiled.setText(selectedGear.name);
			iconButton.setIcon(new ImageIcon("img/items/"+selectedGear.img));
			slotList.setSelectedItem(Integer.valueOf(selectedGear.SLOT));
			gearDescription.setText(selectedGear.description);
		}
		refreshAttriList(selectedGear);
		refreshSkillList(selectedGear);
		validate();
		repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource() instanceof JComboBox){
			JComboBox box = (JComboBox)e.getSource();
			if(e.getSource()==categaryList){
				Integer category = (Integer)categaryList.getSelectedItem();
				refreshPartList(category);
			}else if(e.getSource()==partList){
				selectedGear = (Gear) partList.getSelectedItem();
				if(selectedGear!=null){
					gearNameFiled.setText(selectedGear.name);
					iconButton.setIcon(new ImageIcon("img/"+selectedGear.img));
					slotList.setSelectedItem(Integer.valueOf(selectedGear.SLOT));
					gearDescription.setText(selectedGear.description);
				}
			}
		}else if(e.getSource() instanceof JButton){
			JButton button = (JButton) e.getSource();
			if(button==iconButton){
				FileDialog fileDialog = new FileDialog(Editor.getInstance());
				fileDialog.setFile("*.jpg;*.jpeg;*.png");
				fileDialog.setVisible(true);
				fileDialog.setMode(FileDialog.LOAD);
				String f = fileDialog.getFile();
				if(f!=null && f.length()>0){
					 File file=new File(fileDialog.getDirectory(),fileDialog.getFile());
					 ImageFileName = fileDialog.getFile();
					 iconButton.setIcon(new ImageIcon(file.getAbsolutePath()));
				}
			}else if(button==saveButton){
				
			}else if(button==createButton){
				String name = gearNameFiled.getText();
				String img = (ImageFileName!=null)?ImageFileName:selectedGear.img;
				int Slot = (Integer)slotList.getSelectedItem();
				String description  = gearDescription.getText();
				
				Gear newGear = new SimpleGear(Slot);
				newGear.img = img;
				newGear.name = name;
				newGear.description = description;
				
				newGear = new GearDAO().createGear(newGear);
				if(newGear!=null){
					ImageFileName = null;
					gearNameFiled.setText("");
					gearDescription.setText("");
					refreshPartList(Slot);
				}
			}else if(button==addAttriButton){
				Attri attri = (Attri)attriSelector.getSelectedItem();
				String value = attriValueField.getText();
				if(selectedGear!=null &&  attri!=null && value!=null && value.length()>0){
					attri.setValueWithString(value);
					attri = new AttriDAO().createNewAttribute(attri);
					new AttriDAO().linkAttriTo("Gear", attri, selectedGear);
				}
//				attriSelector.setSelectedIndex(-1);
				attriValueField.setText("");
			}else if(button==deleteAttriButton){
				Attri attri = attriList.getSelectedValue();
				if(attri!=null && selectedGear!=null)
					new AttriDAO().deleteAttriFromGear(attri, selectedGear);
			}else{
				System.out.println("Why Not in");
			}
		}
		refreshAttriList(selectedGear);
		refreshSkillList(selectedGear);
		validate();
		repaint();
	}
	private void refreshPartList(int category){
		ArrayList<Gear> partGears = new GearDAO().getGearsByPart(category);
		partList.removeActionListener(this);
		partList.removeAllItems();
		Lib.getInstance().generateBox(partGears, partList);
		partList.setRenderer(new GearListIconRender());
		partList.addActionListener(this);
		partList.setSelectedIndex(0);
	}
	private void refreshSkillList(Gear gear2) {
		if(gear2==null)
			return;
	}
	private void refreshAttriList(Gear gear2) {
		if(gear2==null)
			return;
		ArrayList<Attri> attris = new AttriDAO().getAttriByGear(gear2);
		attriList = Lib.getInstance().reloadList(attris, attriList, listPanel);
		attriList.setCellRenderer(new GearAttributeRender());
		attriList.addListSelectionListener(this);
		listPanel.add(attriList,0);
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource() == attriList){
			selectedAttri = attriList.getSelectedValue();
		}else if(e.getSource() == skillList){
			
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof JList){
			JList list = (JList) e.getSource();
			if(list==attriList){
				if(topPanel!=topAddPanelAttri){
					rightPanel.remove(topPanel);
					rightPanel.remove(buttonPanel);
					topPanel=topAddPanelAttri;
					buttonPanel = buttomButtonPanelAttri;
					
					rightPanel.add(topPanel,BorderLayout.NORTH);
					rightPanel.add(buttonPanel,BorderLayout.NORTH);
				}
			}else if(list==skillList){
				if(topPanel!=topAddPanelSkill){
					rightPanel.remove(topPanel);
					rightPanel.remove(buttonPanel);
					
					topPanel=topAddPanelSkill;
					buttonPanel = buttomButtonPanelSkill;
					
					rightPanel.add(topPanel,BorderLayout.NORTH);
					rightPanel.add(buttonPanel,BorderLayout.NORTH);
				}
			}
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
class GearCategoryRender implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel  c = new JLabel();
			if(value instanceof Integer){
				c.setText(Define.PARTS_NAME[((Integer) value).intValue()]);
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

