package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.w3c.dom.events.MouseEvent;

import Common.Define;
import Map.LandLoader;

public class MenuPanel extends JPanel implements MouseListener{
	public Font menuFont;
	public int font_size = 28;
	ArrayList<MenuItem> menuItems;
	
	public MenuPanel(){
		this.setBounds(0,0,GUIFrame.FrameW, GUIFrame.FrameH);
		this.setBackground(Color.white);
		File fontfile = new File("font/V5PRD.TTF");
		FileInputStream fi;
		try {
			fi = new FileInputStream(fontfile);
			BufferedInputStream fb = new BufferedInputStream(fi);
			menuFont = Font.createFont(Font.TRUETYPE_FONT, fb);
			menuFont=menuFont.deriveFont(Font.PLAIN,font_size);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialMenu();
	}
	
	
	private void initialMenu() {
		menuItems = new ArrayList<MenuPanel.MenuItem>();
		menuItems.add(addMenu("Start Game", 0));
		menuItems.add(addMenu("Game Rank", 1));
		menuItems.add(addMenu("Close Game", 2));
	}

	private MenuItem addMenu(String name, int index){
		MenuItem menuItem = new MenuItem(name, (int)(GUIFrame.FrameW*0.25), (int)(GUIFrame.FrameH*0.3+index*50));
		menuItem.setBounds((int)(GUIFrame.FrameW*0.25), (int)(GUIFrame.FrameH*0.2+index*50), name.length()*22, 30);
		menuItem.addMouseListener(this);
		this.add(menuItem);
		return menuItem;
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
//		for(MenuItem menuItem: menuItems){
//			menuItem.paintChildren(g);
//		}
		BufferedImage bm = LandLoader.getInstance().getTest();
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bm, null, 0, 0);
	
	}
	
	private void menu_start_game(){
		
	}
	private void menu_view_record(){
		
	}
	private void menu_close_game(){
		GUIFrame.game_stage=Define.GAME_CLOSE;
		GUIFrame.getGUI().run_game();
	}
	class MenuItem extends JComponent{
		public String menuName;
		int xpos,ypos;
		boolean isHover;
		public  MenuItem(String name,int xpos,int ypox) {
			menuName = name;
			this.xpos = xpos;
			this.ypos = ypox;
			//this.setBounds(xpos,ypox,name.length()*font_size,font_size);

		}
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setFont(menuFont);
			if(isHover)
				g2d.setColor(Color.blue);
			g2d.drawString(menuName, 0, font_size);		
		}
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if(e.getSource() instanceof MenuItem){
			MenuItem clickedMenuItem = (MenuItem)e.getSource();
			switch (clickedMenuItem.menuName) {
			case "Start Game":
				menu_start_game();
				break;
			case "Game Rank":
				menu_view_record();
				break;
			case "Close Game":
				menu_close_game();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		if(e.getSource() instanceof MenuItem){
			for(MenuItem menuItem: menuItems){
				menuItem.isHover = false;
			}
			MenuItem hoverMenuItem = (MenuItem)e.getSource();
			hoverMenuItem.isHover = true;
		}
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		if(e.getSource() instanceof MenuItem){
			for(MenuItem menuItem: menuItems){
				menuItem.isHover = false;
			}
		}
	}
}
