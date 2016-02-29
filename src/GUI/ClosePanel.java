package GUI;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Common.Define;

public class ClosePanel extends JPanel{
	BufferedImage bimg;
	final float start_panel_duration=1500;
	float start_panel_count = -1500;
	public ClosePanel(){
		this.setBounds(0,0,GUIFrame.FrameW, GUIFrame.FrameH);
		try {
			bimg = ImageIO.read(new File("img/LittleBattleClose.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		if(start_panel_count>=start_panel_duration){
			System.exit(ABORT);
		}
		float opacity = Math.abs(1.0f - Math.abs(start_panel_count/start_panel_duration));
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,opacity));
		float w_c =  (float)GUIFrame.FrameW /(float)bimg.getWidth();
		float h_c = (float)GUIFrame.FrameH/(float)bimg.getHeight();
		AffineTransform at = new AffineTransform();
		at.scale(w_c, h_c);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		g2d.drawImage(bimg, scaleOp, 0, 0);
		count_down();
	}
	
	private void count_down(){
		if (GUIFrame.actual_FPS>GUIFrame.FPS) {
			start_panel_count+=GUIFrame.actual_FPS;
		}else{
			start_panel_count+=GUIFrame.FPS;
		}
	}
}
