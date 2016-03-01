package Editor;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Common.Lib;
import Object.Skill;


public class MapPanel extends JPanel implements ActionListener,KeyListener,MouseListener,MouseMotionListener{
	JPanel leftPanel,rightPanel;
	int xpos,ypos,width,height, y_start_line,select_x1,select_y1,select_x2,select_y2;
	JComboBox<String> mapTypeSelector;
	JTextField xField,yField,wField,hField,nameField,pathField,loadImagePath;
	JButton loadImageButton;
	JFileChooser jFileChooser;
	JPanel bitmapCanvas,cutImageCanvas;
	JLabel cutIcon;
	BufferedImage bimg,cutimg;
	boolean selecting,pointing;
	
	public MapPanel(){
		xpos=0;ypos=0;width=0;height=0;
		selecting = false;
		pointing = false;
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
		rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
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
		nameField = new JTextField("");
		pathField = new JTextField("");
		
		mapTypeSelector= new JComboBox<String>();
		
		rightPanel.setLayout(new GridLayout(8, 4,20,10));
		rightPanel.add(xJLabel);
		rightPanel.add(xField);
		rightPanel.add(yJLabel);
		rightPanel.add(yField);
		rightPanel.add(wJLabel);
		rightPanel.add(wField);
		rightPanel.add(hJLabel);
		rightPanel.add(hField);
		rightPanel.add(nameJLabel);
		rightPanel.add(nameField);
		rightPanel.add(typeJLabel);
		rightPanel.add(mapTypeSelector);
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		

		xField.addKeyListener(this);
		yField.addKeyListener(this);
		wField.addKeyListener(this);
		hField.addKeyListener(this);
	}
	private void drawLeftPanel() {
		
		leftPanel.setLayout(null);
		
		bitmapCanvas = new JPanel();
		bitmapCanvas.addMouseListener(this);
		bitmapCanvas.addMouseMotionListener(this);
		leftPanel.add(bitmapCanvas);
		
		cutImageCanvas=new JPanel(); 
		cutImageCanvas.setBorder(BorderFactory.createLineBorder(Color.black));
		cutImageCanvas.setLayout(new BorderLayout());
		
	
		cutIcon = new JLabel();
		cutImageCanvas.add(cutIcon,BorderLayout.CENTER);
		leftPanel.add(cutImageCanvas);
		
		loadImagePath = new JTextField();
		loadImagePath.setEditable(false);
		
		reloadBitMap(new File("img/resources/worldmap inquisitor.gif"));
		
		y_start_line = 300;
		loadImagePath.setBounds(5,y_start_line,250,50);
		
		
		loadImageButton = new JButton("Load Image");
		loadImageButton.addActionListener(this);
		loadImageButton.setBounds(280, y_start_line, 140, 50);
		
		jFileChooser = new JFileChooser("img/resources");
		
		
		leftPanel.add(loadImagePath);
		leftPanel.add(loadImageButton);
		
		Editor.getInstance().setFocusable(true);
		Editor.getInstance().requestFocusInWindow();
		Editor.getInstance().addKeyListener(this);
		
	}
	private void reloadBitMap(File bitMapFile){
		try {
			bimg = ImageIO.read(bitMapFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Editor.getInstance().setSize( (int)( (bimg.getWidth()*2.2>Editor.getInstance().getWidth())?bimg.getWidth()*2.2: Editor.getInstance().getWidth()), (int)( (bimg.getHeight()*1.5>Editor.getInstance().getHeight())?bimg.getHeight()*1.5 :Editor.getInstance().getHeight() ));
		bitmapCanvas.setBounds(0, 0, bimg.getWidth()+2, bimg.getHeight()+2);
		bitmapCanvas.setBorder(BorderFactory.createLineBorder(Color.black));
		loadImagePath.setText(bitMapFile.getName());
		y_start_line = (int)(bimg.getHeight()*1.1);
		cutImageCanvas.setBounds(5,y_start_line+100,100,100);
		repaint();
	}
	
	private void getSubImage(int x, int y, int w, int h){
		if(x>=0 && y>=0 && w>0 && h>0){
			cutimg = bimg.getSubimage(x,y, w,h);
			cutIcon.setIcon(new ImageIcon(cutimg));
			cutImageCanvas.setSize(cutimg.getWidth()+1, cutimg.getHeight()+1);
		}
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bimg, null, 1, 1);
		if(selecting){
			g2d.setColor(new Color(174,207,240));
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.8f));
			g2d.drawRect(select_x1, select_y1, select_x2-select_x1, select_y2-select_y1);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.4f));
			g2d.fillRect(select_x1+1, select_y1+1, select_x2-select_x1-1, select_y2-select_y1-1);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==bitmapCanvas){
			int xpos = e.getX()-1;
			int ypox = e.getY()-1;
			xField.setText(Integer.toString(xpos));
			yField.setText(Integer.toString(ypox));
		}
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		selecting = true;
		select_x1 = e.getX();
		select_y1 = e.getY();
		System.out.println("Start:"+select_x1+"|"+select_y1);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		selecting = false;
		if(select_x2-select_x1>0 && select_y2-select_y1>0 && select_x1>0 && select_y1>0){
			getSubImage(select_x1-1, select_y1-1, Math.abs(select_x2-select_x1), Math.abs(select_y2-select_y1));
			xField.setText(Integer.toString(select_x1-1));
			yField.setText(Integer.toString(select_y1-1));
			wField.setText(Integer.toString(select_x2-select_x1));
			hField.setText(Integer.toString(select_y2-select_y1));
		}
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		int keyCode = e.getKeyCode();
	    switch( keyCode ) { 
	        case KeyEvent.VK_UP:
	        	arrawControl(yField,-1);
	            break;
	        case KeyEvent.VK_DOWN:
	        	arrawControl(yField,1);
	            break;
	        case KeyEvent.VK_LEFT:
	        	arrawControl(xField,-1);
	            break;
	        case KeyEvent.VK_RIGHT :
	        	arrawControl(xField,1);
	            break;
	     }
	}
	private void arrawControl(JTextField textField, int amount){
		String str = textField.getText();
		int newPos = 0;
		if(str!="" && str!=null){
			if(Lib.getInstance().isNumber(str)){
				newPos = Integer.parseInt(str)+amount;
			}
		}else{
			if(amount>0)
				newPos = amount;
		}
		textField.setText(Integer.toString(newPos));
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_CONTROL){
			pointing = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_CONTROL){
			pointing = false;
		}
		if(e.getSource() instanceof JTextField){
			if(Lib.getInstance().isNumber(xField.getText())
				&&	Lib.getInstance().isNumber(yField.getText())	
				&&	Lib.getInstance().isNumber(wField.getText())	
				&&	Lib.getInstance().isNumber(hField.getText())	
				){
				System.out.println(Integer.parseInt(xField.getText()) +"|"+Integer.parseInt(yField.getText())+"|"+
						Integer.parseInt(wField.getText())+"|"+ Integer.parseInt(hField.getText()));
				getSubImage(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()),
						Integer.parseInt(wField.getText()), Integer.parseInt(hField.getText()));
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loadImageButton){
			int returnVal = jFileChooser.showOpenDialog(MapPanel.this);
			  if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = jFileChooser.getSelectedFile();
	                if(file.exists()){
	                	reloadBitMap(file);
	                }
	            } else {
	            }
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getSource()==bitmapCanvas){
			if(selecting){
				select_x2 = e.getX();
				select_y2 = e.getY();
				repaint();
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getSource()==bitmapCanvas){
			if(pointing){
				int xpos = e.getX()-1;
				int ypox = e.getY()-1;
				xField.setText(Integer.toString(xpos));
				yField.setText(Integer.toString(ypox));
			}
			
		}
	}
}
