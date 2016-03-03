package Editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class MapPanel extends JPanel{
	JPanel leftPanel,rightPanel,canvasPanel;
	
	public MapPanel(){
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		rightPanel.setBackground(Color.white);
		leftPanel.setBounds(0,0,(int)(Editor.getInstance().getWidth()*0.7),Editor.getInstance().getHeight());
		rightPanel.setBounds((int)(Editor.getInstance().getWidth()*0.7)+1,0,(int)(Editor.getInstance().getWidth()*0.29),Editor.getInstance().getHeight());
		this.setLayout(null);
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
