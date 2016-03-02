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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Common.Define;
import Common.Lib;
import Data.DAO.MapSegmentDAO;
import Map.MapResources;
import Map.MapSegment;
import Object.Skill;


public class MapSegmentPanel extends JPanel implements ActionListener,KeyListener,MouseListener,MouseMotionListener{
	JPanel leftPanel,rightPanel;
	int xpos,ypos,width,height, y_start_line,select_x1,select_y1,select_x2,select_y2;
	JComboBox<String> mapTypeSelector;
	JTextField xField,yField,wField,hField,nameField,loadImagePath,idField;
	JButton loadImageButton,addMapSegment,delMapSegment;
	JFileChooser jFileChooser;
	JPanel bitmapCanvas,cutImageCanvas;
	JLabel cutIcon;
	BufferedImage bimg,cutimg;
	boolean selecting,pointing;
	
	float scale_factor = 3.0f;

	public MapSegmentPanel(){
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

		xField = new JTextField("");
		yField = new JTextField("");
		wField = new JTextField("");
		hField = new JTextField("");
		xField.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
		yField.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
		wField.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
		hField.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
		nameField = new JTextField("");
		idField = new JTextField("");
		idField.setEditable(false);

		mapTypeSelector= new JComboBox<String>();
		String[] typesStrings = Lib.getInstance().concatenateArray(Define.MAP_Land_TYPE, Define.MAP_Object_TYPE);
		Lib.getInstance().generateBox(new ArrayList<String>(Arrays.asList(typesStrings)) , mapTypeSelector);

		addMapSegment = new JButton("Add Segment");
		addMapSegment.addActionListener(this);
		delMapSegment = new JButton("Delete Segment");
		delMapSegment.addActionListener(this);
		
		rightPanel.setLayout(new GridLayout(8, 4,10,30));
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
		rightPanel.add(idField);
		rightPanel.add(addMapSegment);
		
		
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(new JLabel());
		rightPanel.add(delMapSegment);
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

		reloadBitMap("worldmap inquisitor.gif");

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
	private void reloadBitMap(String bitMapPath){
		bimg =  MapResources.getInstance().getResources(bitMapPath);
		Editor.getInstance().setSize( (int)( (bimg.getWidth()*2.2>Editor.getInstance().getWidth())?bimg.getWidth()*2.2: Editor.getInstance().getWidth()), (int)( (bimg.getHeight()*1.5>Editor.getInstance().getHeight())?bimg.getHeight()*1.5 :Editor.getInstance().getHeight() ));
		bitmapCanvas.setBounds(0, 0, bimg.getWidth()+2, bimg.getHeight()+2);
		bitmapCanvas.setBorder(BorderFactory.createLineBorder(Color.black));
		loadImagePath.setText(bitMapPath);
		y_start_line = (int)(bimg.getHeight()*1.1);
		cutImageCanvas.setBounds(5,y_start_line+100,100,100);
		repaint();
	}

	private void getSubImage(int x, int y, int w, int h){
		
		if(x+w>bimg.getWidth() || y+h>bimg.getHeight()){
			
//			System.out.println(x+"+"+w+"="+(x+w)+">?"+bimg.getWidth() + "||"+ y+"+"+h+"="+(y+h)+">?" + bimg.getHeight());
			return;
			
		}
		
		if(x>=0 && y>=0 && w>0 && h>0){
			cutimg = bimg.getSubimage(x,y, w,h);
			
			int c_w = cutimg.getWidth();
			int c_h = cutimg.getHeight();
			BufferedImage after = new BufferedImage((int)(c_w*scale_factor), (int)(c_h*scale_factor), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scale_factor, scale_factor);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(cutimg, after);
			cutimg = after;
			
			cutImageCanvas.setSize(cutimg.getWidth()+1, cutimg.getHeight()+1);
			cutIcon.setIcon(new ImageIcon(cutimg));
			
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
//		System.out.println("Start:"+select_x1+"|"+select_y1);
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
			else
				newPos = 0;
		}
		if(newPos<=0)
			newPos=0;
		textField.setText(Integer.toString(newPos));
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_CONTROL){
			pointing = true;
		}
		int keyCode = e.getKeyCode();
		switch( keyCode ) { 
		case KeyEvent.VK_W:
			arrawControl(yField,-1);
			e.consume();
			break;
		case KeyEvent.VK_S:
			if(Integer.parseInt(yField.getText())+1>bimg.getHeight())
				break;
			arrawControl(yField,1);
			e.consume();
			break;
		case KeyEvent.VK_A:
			arrawControl(xField,-1);
			e.consume();
			break;
		case KeyEvent.VK_D :
			if(Integer.parseInt(xField.getText())+1>bimg.getWidth())
				break;
			arrawControl(xField,1);
			e.consume();
			break;
		case KeyEvent.VK_Q:
			if(Integer.parseInt(xField.getText())+Integer.parseInt(wField.getText())-1<1)
				break;
			arrawControl(wField,-1);
			e.consume();
			break;
		case KeyEvent.VK_E:
			if(Integer.parseInt(xField.getText())+Integer.parseInt(wField.getText())+1>bimg.getWidth())
				break;
			arrawControl(wField,1);
			e.consume();
			break;
		case KeyEvent.VK_Z:
			if(Integer.parseInt(yField.getText())+Integer.parseInt(hField.getText())-1<1)
				break;
			arrawControl(hField,-1);
			e.consume();
			break;
		case KeyEvent.VK_X:
			if(Integer.parseInt(yField.getText())+Integer.parseInt(hField.getText())+1>bimg.getHeight())
				break;
			arrawControl(hField,1);
			e.consume();
			break;
		}
		getSubImage(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()),
				Integer.parseInt(wField.getText()), Integer.parseInt(hField.getText()));
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
//				System.out.println(Integer.parseInt(xField.getText()) +"|"+Integer.parseInt(yField.getText())+"|"+
//						Integer.parseInt(wField.getText())+"|"+ Integer.parseInt(hField.getText()));
				getSubImage(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()),
						Integer.parseInt(wField.getText()), Integer.parseInt(hField.getText()));
			}
		}
		repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loadImageButton){
			int returnVal = jFileChooser.showOpenDialog(MapSegmentPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = jFileChooser.getSelectedFile();
				if(file.exists()){
					reloadBitMap(file.getName());
				}
			}
		}else if(e.getSource() == addMapSegment){
			String name=nameField.getText();
			String type=(String) mapTypeSelector.getSelectedItem();
			String file_path=loadImagePath.getText();  
			if(xField.getText()=="" || yField.getText()=="" || wField.getText()=="" || hField.getText()=="")
				return;
			int xpos=Integer.parseInt(xField.getText());
			int ypos=Integer.parseInt(yField.getText());
			int width=Integer.parseInt(wField.getText());
			int height=Integer.parseInt(hField.getText());
			
			if(xpos>=0 && ypos>=0 && width>0 && height>0){
				MapSegment mapSegment= new MapSegment(name, type, file_path, xpos, ypos, width, height);
				int new_id = new MapSegmentDAO().insert(mapSegment);
				if(new_id==-1){
					idField.setText("Error");
				}else{
					idField.setText(Integer.toString(new_id));
				}
			}else{
				System.out.println(xpos+"|"+ypos+"|"+width+"|"+height+"|");
			}
		}else if(e.getSource() == delMapSegment){
			if(idField.getText()!=null && idField.getText()!=""){
				int id = Integer.parseInt(idField.getText());
				MapSegment mapSegment = new MapSegment();
				mapSegment.id = id;
				if(new MapSegmentDAO().delete(mapSegment)){
					idField.setText("");
				}
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
