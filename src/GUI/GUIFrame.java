package GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Common.Define;

public class GUIFrame extends JFrame{
	private static GUIFrame guiFrame;
	StartPanel startPanel;
	MenuPanel menuPanel;
	GamePanel gamePanel;
	ClosePanel	closePanel;
	JPanel mainpanel;
	private boolean keeprunning = true;
	
	public static int FrameW = 400;
	public static int FrameH = 300;
	public static int FPS = 30;
	public static long actual_FPS;
	public static long MasterClock = 0;
	public static int game_stage = 0;
	
	private GUIFrame(){
		this.setSize(FrameW, FrameH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Initial();
		this.setVisible(true);
		this.setTitle("Little Panel");
	}
	public static GUIFrame getGUI(){
		if(guiFrame==null){
			synchronized (GUIFrame.class) {
				if(guiFrame==null)
					guiFrame= new GUIFrame();
			}
		}
		return guiFrame;
	}

	public void run_game() {
		switch(game_stage){
			case Define.GAME_INITIAL:
				game_stage = Define.GAME_START;
				run_game();
			break;
			case Define.GAME_START:
				startPanel = new StartPanel();
				swapPanel(startPanel);
			break;
			
			case Define.GAME_MENU:
				menuPanel = new MenuPanel();
				swapPanel(menuPanel);
			break;
			
			case Define.GAME_PLAY:
				gamePanel = new GamePanel();
				swapPanel(gamePanel);
			break;
			
			case Define.GAME_CLOSE:
				closePanel = new ClosePanel();
				swapPanel(closePanel);
			break;
			
			default:
			break;
		
		}
	}
	private void Initial() {
		refresh_thread_start();
		this.setBounds(1000, 600, FrameW, FrameH);
		this.setLayout(null);
		mainpanel = new JPanel();
		mainpanel.setLayout(null);
		mainpanel.setBounds(0, 0, FrameW, FrameH);
		mainpanel.setBackground(Color.white);
		this.add(mainpanel);
		run_game();
	}

	private void refresh_thread_start() {
		Thread refresh_Thread = new Thread(new Runnable() {
			public void run() {
				long sys_start=0,sys_end=0;
				while (keeprunning) {
					
					actual_FPS = sys_end - sys_start;
					if( actual_FPS< FPS){
						try { 
							Thread.sleep(FPS-actual_FPS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					sys_start = System.currentTimeMillis();
					refresh_screen();
					sys_end = System.currentTimeMillis();
				}
			}
		});
		refresh_Thread.start();
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		//mainpanel.paintComponents(g);
	}
	
	public void refresh_screen() {
		MasterClock += actual_FPS;
		this.repaint();
	}
	private void swapPanel(JPanel swapPanel) {
		this.remove(mainpanel);
		mainpanel = swapPanel;
		this.add(mainpanel);
	}
}
