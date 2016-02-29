package Editor;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Object.Player;

public class Editor extends JFrame implements ActionListener {
	private static Editor editor;
	JPanel ButtonPanel, ContentPanel, swapPanel;
	HomePanel defaultPanel;
	PlayerPanl playPanel;
	RolePanel RolePanel;
	AttriPanel AttriPanel;
	GearPanel GearPanel;
	EquipmentPanel EquipmentPanel;
	SkillPanel SkillPanel;
	EffectPanel effectPanel;
	JButton home, addPlayer, addRole, addAttri, addGear, equipment, addSkill,
			addEffectButton;

	private Editor() {
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Initial();
		this.setVisible(true);
		this.setTitle("Lala");
	}

	public static Editor getInstance() {
		if (editor == null)
			editor = new Editor();
		return editor;
	}

	private void Initial() {
		getContentPane().setLayout(new BorderLayout());
		ButtonPanel = new JPanel();
		// ContentPanel = new JPanel();
		defaultPanel = new HomePanel();
		ContentPanel = defaultPanel;
		JPanel panel = new PlayerPanl();

		getContentPane().add(ButtonPanel, BorderLayout.NORTH);
		getContentPane().add(ContentPanel, BorderLayout.CENTER);
		// ContentPanel.add(swapPanel);
		// LeftPanel.setBackground(Color.red);
		// RightPanel.setBackground(Color.green);

		ButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		ButtonPanel.setBackground(Color.white);
		home = initialButton(home, "Home");
		addPlayer = initialButton(addPlayer, "Player");
		addRole = initialButton(addRole, "Role");
		addAttri = initialButton(addAttri, "Attri");
		equipment = initialButton(equipment, "Equipment");
		addGear = initialButton(addGear, "Gear");
		addSkill = initialButton(addSkill, "Skill");
		addEffectButton = initialButton(addEffectButton, "Effect");

	}

	public void swapPanel(JPanel panel) {
		getContentPane().remove(ContentPanel);
		ContentPanel = panel;
		getContentPane().add(ContentPanel, BorderLayout.CENTER);
		validate();
		repaint();
	}

	private JButton initialButton(JButton button, String name) {
		button = new JButton(name);
		button.addActionListener(this);
		ButtonPanel.add(button);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			// this.setTitle(button.getText());
			switch (button.getText()) {
			case "Home":
				if (defaultPanel == null)
					defaultPanel = new HomePanel();
				swapPanel(defaultPanel);
				break;
			case "Player":
				if (playPanel == null)
					playPanel = new PlayerPanl();
				if (ContentPanel == defaultPanel)
					if (defaultPanel.selected != null)
						playPanel.setCurrent(defaultPanel.selected);
				swapPanel(playPanel);
				break;
			case "Role":
				if (RolePanel == null)
					RolePanel = new RolePanel();
				if (ContentPanel == playPanel) {
					RolePanel.setPlayer(playPanel.current);
					RolePanel.setRole(playPanel.selectedRole);
				}
				swapPanel(RolePanel);
				break;
			case "Attri":
				if (AttriPanel == null)
					AttriPanel = new AttriPanel();
				swapPanel(AttriPanel);
				break;
			case "Gear":
				if (GearPanel == null)
					GearPanel = new GearPanel();
				if (ContentPanel == EquipmentPanel) {
					if (EquipmentPanel.selectGear != null) {
						GearPanel.setGear(EquipmentPanel.selectGear);
					}
				}
				swapPanel(GearPanel);
				break;
			case "Equipment":
				if (EquipmentPanel == null)
					EquipmentPanel = new EquipmentPanel();
				if (ContentPanel == playPanel) {
					if (playPanel.selectedRole != null)
						EquipmentPanel.setRole(playPanel.selectedRole);
				} else if (ContentPanel == RolePanel) {
					if (RolePanel.role != null)
						EquipmentPanel.setRole(RolePanel.role);
				}
				swapPanel(EquipmentPanel);
				break;
			case "Skill":
				if (SkillPanel == null)
					SkillPanel = new SkillPanel();
				swapPanel(SkillPanel);
				break;
			case "Effect":
				if (effectPanel == null)
					effectPanel = new EffectPanel();
				swapPanel(effectPanel);
				break;

			default:
				break;
			}
		}
	}
}
