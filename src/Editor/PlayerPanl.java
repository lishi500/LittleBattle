package Editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Common.Lib;
import Data.DAO.RoleClassDAO;
import Data.DAO.RoleDAO;
import Object.Attri;
import Object.Effact;
import Object.Equipment;
import Object.Gear;
import Object.Player; 
import Object.Role;
import Object.RoleClass;
import Object.Skill;
import Object.Imp.RoleImp;

public class PlayerPanl extends JPanel implements ActionListener,ListSelectionListener{ 
	 
	JPanel leftPanel,rightPanel;
	JButton AddRole,EditPlayer;
	JLabel idLabel,usernameLabel,passwordLabel,nicknameLabel,emailLabel;
	JTextField roleidField,playeridField,nameField;
	JList<Role> roles;
	JComboBox<RoleClass> classSelection;
	public Player current;
	public Role selectedRole;
	public PlayerPanl(){
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		rightPanel.setBackground(Color.white);
		this.setLayout(new GridLayout(1, 2));
		this.add(leftPanel);
		this.add(rightPanel);
		initial();
	}
	private void initial() {
		AddRole = new JButton("Add Role");
		AddRole.addActionListener(this);
//		EditPlayer = new JButton("Edit");
		drawLeftPanel();
		drawRightPanel();
	}
	private void drawLeftPanel() {
		idLabel = new JLabel("ID: ");
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		nicknameLabel = new JLabel("Nickname: ");
		emailLabel = new JLabel("Email: ");
		JPanel playerInfoPanel = new JPanel();
		playerInfoPanel.setBackground(Color.white);
		playerInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		playerInfoPanel.add(idLabel);
		playerInfoPanel.add(usernameLabel);
		playerInfoPanel.add(passwordLabel);
		playerInfoPanel.add(nicknameLabel);
		playerInfoPanel.add(emailLabel);
		playerInfoPanel.setBorder(BorderFactory.createEtchedBorder());
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(playerInfoPanel,BorderLayout.NORTH);
		
		roles = new JList<Role>();
		leftPanel.add(roles,BorderLayout.CENTER);
		
	}
	private void drawRightPanel() {
		rightPanel.setLayout(new BorderLayout());
		JLabel title = new JLabel("Add Role");
		rightPanel.add(title,BorderLayout.NORTH);
		JPanel addPanel = new JPanel();
		addPanel.setBackground(Color.white);
		addPanel.setLayout(new GridLayout(7,2,10,20));
		addPanel.setBorder(new EmptyBorder(10, 10, 10, 30));
		rightPanel.add(addPanel,BorderLayout.CENTER);
		
		JLabel roleidJLabel,playeriJLabel,nameJLabel,classJLabel;
		roleidJLabel = new JLabel("Role: ");
		playeriJLabel = new JLabel("Player: ");
		nameJLabel = new JLabel("Name:");
		classJLabel = new JLabel("Class: ");
		
		roleidField = new JTextField();
		roleidField.setEditable(false);
		playeridField = new JTextField();
		playeridField.setEditable(false);
		nameField = new JTextField();
		classSelection = new JComboBox<RoleClass>();
		ArrayList<RoleClass> roleClasses = new RoleClassDAO().getAllRoleClass();
		for(RoleClass classes : roleClasses){
			classSelection.addItem(classes);
		}
		addPanel.add(roleidJLabel);addPanel.add(roleidField);
		addPanel.add(playeriJLabel);addPanel.add(playeridField);
		addPanel.add(nameJLabel);addPanel.add(nameField);
		addPanel.add(classJLabel);addPanel.add(classSelection);
		addPanel.add(AddRole);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton button = (JButton) e.getSource();
			if(button==AddRole){
				if(current!=null){
					int play_id = current.id;
					String name = nameField.getText();
					RoleClass roleClass = (RoleClass)classSelection.getSelectedItem();
					Role r = new RoleImp();
					r.name = name;
					r.setRollClass(roleClass);
					r = new RoleDAO().createRole(current,r);
					if(r!=null){
						roleidField.setText(String.valueOf(r.getId()));
					}
					reloadRole(current);
				}
			}else if(button == EditPlayer){
				
			}
		}
		validate();
		repaint();
	}
	public void setCurrent(Player selected) {
		this.current = selected;
		idLabel.setText("ID: "+selected.id);
		usernameLabel.setText("Username: "+selected.username);
		passwordLabel.setText("Password: "+selected.password);
		nicknameLabel.setText("Nickname: "+selected.nickname);
		emailLabel.setText("Email: "+selected.email);
		
		reloadRole(selected);
		
		validate();
		repaint();
	}
	private void reloadRole(Player selected){
		ArrayList<Role> roleList = new RoleDAO().getRolesByPlayer(selected);
		if(roleList!=null){
			roles = Lib.getInstance().reloadList(roleList, roles, leftPanel);
			roles.addListSelectionListener(this);
			leftPanel.add(roles,BorderLayout.CENTER);
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		selectedRole = roles.getSelectedValue();
	}
	
}
