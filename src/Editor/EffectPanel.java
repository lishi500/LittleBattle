package Editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class EffectPanel extends JPanel {
	JPanel leftPanel,rightPanel;
	public EffectPanel(){
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
		// TODO Auto-generated method stub
		
	}
	private void drawLeftPanel() {
		// TODO Auto-generated method stub
		
	}
}
