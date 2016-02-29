package Editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Data.DAO.PlayerDAO;
import Object.Player;
import Object.Imp.PlayerImp;

public class HomePanel extends JPanel implements ActionListener,ListSelectionListener{
	JPanel leftPanel,rightPanel;
	public Player selected;
	JList<Player> playList;
	JLabel label;
	JTextField idField,usernameField,passwordField,nicknameField,emailField;
	public HomePanel(){
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
//		leftPanel.setBackground(Color.yellow);
//		rightPanel.setBackground(Color.blue);
		
		Player[] players = new PlayerDAO().getAllPlayer();
		playList = new JList<Player>(players);
		playList.addListSelectionListener(this);
		playList.setBorder(BorderFactory.createEtchedBorder());
		leftPanel.add(playList);
		
		drawRightPanel();
	}
	
	private void drawRightPanel() {
		JLabel idLabel,usernameLabel,passwordLabel,nicknameLabel,emailLabel;
		
		idLabel = new JLabel("Id: ");
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password");
		nicknameLabel = new JLabel("nickname");
		emailLabel = new JLabel("email");
		
		idField = new JTextField();
		idField.setEditable(false);
		usernameField  = new JTextField();
		passwordField = new JTextField();
		nicknameField = new JTextField();
		emailField = new JTextField();
		
		rightPanel.setLayout(new GridLayout(8, 2, 10, 20));
		rightPanel.setBorder(new EmptyBorder(10, 10, 10, 30) );
		rightPanel.add(idLabel);
		rightPanel.add(idField);
		rightPanel.add(usernameLabel);
		rightPanel.add(usernameField);
		rightPanel.add(passwordLabel);rightPanel.add(passwordField);
		rightPanel.add(nicknameLabel);rightPanel.add(nicknameField);
		rightPanel.add(emailLabel);rightPanel.add(emailField);
		JButton add = new JButton("Add Player");
		add.addActionListener(this);
		rightPanel.add(add);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton button = (JButton)e.getSource();
			switch (button.getText()) {
			case "Add Player":
				newInsertion();
				break;

			default:
				break;
			}
		}
	}
	private void newInsertion() {
		String usernameString = usernameField.getText();
		String passwordString = passwordField.getText();
		String nicknameString = nicknameField.getText();
		String emailString = emailField.getText();
		Player p = new PlayerImp(usernameString, nicknameString, emailString, passwordString);
		p = new PlayerDAO().createPlayer(p);
		idField.setText(String.valueOf(p.id));
		Player[] players = new PlayerDAO().getAllPlayer();
		playList.setListData(players);
		validate();
		repaint();
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		selected = playList.getSelectedValue();
		idField.setText(String.valueOf(selected.id));
		usernameField.setText(selected.username);
		passwordField.setText(selected.password);
		nicknameField.setText(selected.nickname);
		emailField.setText(selected.email);
		validate();
		repaint();
	}
	
}
