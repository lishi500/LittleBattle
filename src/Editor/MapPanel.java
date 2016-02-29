package Editor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MapPanel extends JPanel{
	JPanel leftPanel,rightPanel;
	int xpos,ypos,width,height;
	JTextField xField,yField,wField,hField,nameField,typeField,pathField;
	BufferedImage bimg;
	
	public MapPanel(){
		xpos=0;ypos=0;width=0;height=0;
		try {
			bimg = ImageIO.read(new File("img/resources/worldmap inquisitor.gif"));
			Editor.getInstance().setSize( (int)(bimg.getWidth()*2.2), (int)( (bimg.getHeight()*1.5>Editor.getInstance().getHeight())?bimg.getHeight()*1.5 :Editor.getInstance().getHeight() ));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		JLabel xJLabel,yJLabel,wJLabel,hJLabel,nameJLabel,typeJLabel,pathJlabel;
		xJLabel = new JLabel("X: ");
		yJLabel = new JLabel("Y: ");
		wJLabel = new JLabel("W: ");
		hJLabel = new JLabel("H: ");
		nameJLabel = new JLabel("name: ");
		typeJLabel = new JLabel("type: ");
		pathJlabel = new JLabel("path: ");
		
		xField = new JTextField("");
		yField = new JTextField("");
		wField = new JTextField("");
		hField = new JTextField("");
		
		rightPanel.setLayout(new GridLayout(6, 4,10,10));
		rightPanel.add(xJLabel);
		rightPanel.add(xField);
		rightPanel.add(yJLabel);
		rightPanel.add(yField);
		rightPanel.add(wJLabel);
		rightPanel.add(wField);
		rightPanel.add(hJLabel);
		rightPanel.add(hField);
		
		
	}
	private void drawLeftPanel() {
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bimg, null, 0, 0);
		g2d.drawRect(0, 0, bimg.getWidth()+1, bimg.getHeight()+1);
		
	}
}
