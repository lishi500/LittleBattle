package Editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Common.Define;
import Common.Lib;
import Data.DAO.SkillDAO;
import Object.Attri;
import Object.Skill;
import Object.SkillAttribute;

public class SkillPanel extends JPanel implements ActionListener,ListSelectionListener{
	JPanel leftPanel,rightPanel;
	JComboBox<Skill> skillSelector;
	Skill selectedSkill;
	
	JComboBox<Integer> selectTypeBox,targetTypeBox,damageTypeBox,scaleTypeBox;
	JList<SkillAttribute> attributeJList;
	JButton deleteSkillButton,addSkillButton,updateSkillButton,addSkillAttributeButton,deleteAttributeButton;
	JTextField nameField,levelField,cooldownField,consumeField,triggerChangeField,areaField,targetSelectField;
	JTextArea descArea;
	public SkillPanel(){
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
		attributeJList = new JList<SkillAttribute>();

	}
	private void drawLeftPanel() {
		JPanel leftTopPanel = new JPanel();
		JPanel leftMidPanel = new JPanel();
		JPanel leftBottomPanel = new JPanel();
		
		skillSelector = new JComboBox<Skill>();
		reloadAllSkills();
		skillSelector.setRenderer(new SkillListlRenderer());
		skillSelector.addActionListener(this);
		skillSelector.setName("haha");
		
		deleteSkillButton = new JButton("Delete Skill");
		deleteSkillButton.addActionListener(this);
		addSkillButton = new JButton("Add Skill");
		addSkillButton.addActionListener(this);
		leftTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		leftTopPanel.add(skillSelector);
		leftTopPanel.add(deleteSkillButton);
		leftTopPanel.add(addSkillButton);
		
		leftMidPanel.setLayout(new GridLayout(10, 2, 20, 5));
		leftMidPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		JLabel nameLabel,levelLabel,selectLabel,targetLabel,cooldownLabel,consumeLabel,triggerChangeLabel,areaLabel,targetSelectLabel,descriptionLabel;
		//nameField,levelField,cooldownField,consumeField,triggerChangeField,areaField,targetSelectField;
		nameLabel = new JLabel("name");
		levelLabel = new JLabel("level");
		selectLabel = new JLabel("select");
		targetLabel = new JLabel("target");
		cooldownLabel = new JLabel("cooldown");
		consumeLabel = new JLabel("consume");
		triggerChangeLabel = new JLabel("triggerChange");
		areaLabel = new JLabel("area");
		targetSelectLabel = new JLabel("targetSelect");
		descriptionLabel = new JLabel("description");
		
		nameField = new JTextField("");
		levelField = new JTextField("");
		cooldownField = new JTextField("");
		consumeField = new JTextField("");
		triggerChangeField = new JTextField("");
		areaField = new JTextField("");
		targetSelectField = new JTextField("");
		
		descArea = new JTextArea();
		
		selectTypeBox = new JComboBox<Integer>();
		targetTypeBox = new JComboBox<Integer>();
		damageTypeBox = new JComboBox<Integer>();
		scaleTypeBox = new JComboBox<Integer>();
		
		ArrayList<Integer> selectList = new ArrayList<Integer>( Arrays.asList(new Integer(0),new Integer(1),new Integer(2),new Integer(3),new Integer(4),new Integer(5)));
		ArrayList<Integer> targetList = new ArrayList<Integer>(Arrays.asList(new Integer(0),new Integer(1),new Integer(2),new Integer(3),new Integer(4)));
		ArrayList<Integer> damageList = new ArrayList<Integer>(Arrays.asList(new Integer(0),new Integer(1),new Integer(2),new Integer(3),new Integer(4)));
		ArrayList<Integer> scaleList = new ArrayList<Integer>( Arrays.asList(new Integer(0),new Integer(1),new Integer(2),new Integer(3),new Integer(5),new Integer(6),new Integer(7),new Integer(8),new Integer(9),new Integer(13)));
		
		Lib.getInstance().generateBox(selectList, selectTypeBox);
		selectTypeBox.setRenderer(new selectTypeBoxRenderer());
		selectTypeBox.addActionListener(this);
		
		Lib.getInstance().generateBox(targetList, targetTypeBox);
		targetTypeBox.setRenderer(new targetTypeBoxRenderer());
		targetTypeBox.addActionListener(this);
		
		Lib.getInstance().generateBox(damageList, damageTypeBox);
		damageTypeBox.setRenderer(new damageTypeBoxRenderer());
		damageTypeBox.addActionListener(this);
		
		Lib.getInstance().generateBox(scaleList, scaleTypeBox);
		scaleTypeBox.setRenderer(new scaleTypeBoxRenderer());
		scaleTypeBox.addActionListener(this);
		// nameLabel,levelLabel,selectLabel,targetLabel,cooldownLabel,consumeLabel,triggerChangeLabel,areaLabel,targetSelectLabel,descriptionLabel;
		leftMidPanel.add(nameLabel);leftMidPanel.add(nameField);
		leftMidPanel.add(levelLabel);leftMidPanel.add(levelField);
		leftMidPanel.add(selectLabel);leftMidPanel.add(selectTypeBox);
		leftMidPanel.add(targetLabel);leftMidPanel.add(targetTypeBox);
		leftMidPanel.add(cooldownLabel);leftMidPanel.add(cooldownField);
		leftMidPanel.add(consumeLabel);leftMidPanel.add(consumeField);
		leftMidPanel.add(triggerChangeLabel);leftMidPanel.add(triggerChangeField);
		leftMidPanel.add(areaLabel);leftMidPanel.add(areaField);
		leftMidPanel.add(targetSelectLabel);leftMidPanel.add(targetSelectField);
		leftMidPanel.add(descriptionLabel);leftMidPanel.add(descArea);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(leftTopPanel,BorderLayout.NORTH);
		leftPanel.add(leftMidPanel,BorderLayout.CENTER);
		leftPanel.add(leftBottomPanel,BorderLayout.SOUTH);
	}

	private void reloadAllSkills(){
		ArrayList<Skill> skillList = new SkillDAO().getAllSkills();
		Lib.getInstance().generateBox(skillList, skillSelector);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox){
			JComboBox box = (JComboBox)e.getSource();
			if(box == skillSelector){
				Skill skill = (Skill)skillSelector.getSelectedItem();
				refreshLeftPanelWithSkill(skill);
			}
		}else if(e.getSource() instanceof JButton){
			
		}
	}
	
	private void refreshLeftPanelWithSkill(Skill skill){
		if(skill==null)
			return;
		
	nameField.setText(skill.name);
	levelField.setText(Integer.toString(skill.level));
	selectTypeBox.setSelectedIndex(skill.selectType);
	targetTypeBox.setSelectedIndex(skill.targetType);
	cooldownField.setText(Float.toString(skill.cooldown));
	consumeField.setText(Float.toString(skill.manacost));
	triggerChangeField.setText(Float.toString(skill.triggerChance));
	areaField.setText(Integer.toString(skill.area));
	targetSelectField.setText(Integer.toString(skill.SELECT));
	descArea.setText(skill.description);
		
	}
}
class SkillListlRenderer implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel  c = new JLabel();
			if(value instanceof Skill){
				System.out.println(list.toString());
				Skill skill = (Skill) value;
				c.setText(skill.name + " ["+skill.level+"]");
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
class selectTypeBoxRenderer implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel  c = new JLabel();
			if(value instanceof Integer){
				Integer integer = (Integer) value;
				c.setText(Define.SELECT_TYPE[integer] + " ["+integer+"]");
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
class targetTypeBoxRenderer implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel  c = new JLabel();
			if(value instanceof Integer){
				Integer integer = (Integer) value;
				c.setText(Define.TARGET_TYPE[integer] + " ["+integer+"]");
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
class damageTypeBoxRenderer implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel  c = new JLabel();
			if(value instanceof Integer){
				Integer integer = (Integer) value;
				c.setText(Define.DAMAGE_TYPE[integer] + " ["+integer+"]");
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
class scaleTypeBoxRenderer implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel  c = new JLabel();
			if(value instanceof Integer){
				Integer integer = (Integer) value;
				c.setText(Define.SCALE_TYPE[integer] + " ["+integer+"]");
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