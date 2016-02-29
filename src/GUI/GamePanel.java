package GUI;

import javax.swing.JPanel;

import Object.Map;

public class GamePanel extends JPanel{
	MapPanel mapPanel;
	GameOptionPanel gameOptionPanel;
	BattlePanel battlePanel;
	Map gameMap;
	
	
	public GamePanel(){
		this.setBounds(0,0,GUIFrame.FrameW, GUIFrame.FrameH);
	}
	private void reInitialGame(){
		
	}
	public void setMap(Map map){
		this.gameMap = map;
	}
	private void loadMap(){
		
	}
}
