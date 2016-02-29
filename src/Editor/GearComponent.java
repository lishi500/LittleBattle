package Editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Object.Gear;

public class GearComponent extends IconComponent{
	final static float dash1[] = {30.0f};
    final static BasicStroke dashed =
        new BasicStroke(7.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, dash1, 0.0f);
    final static Color c = new Color(100, 255, 150, 220);
	@Override
	public void paint(Graphics g) {
		Gear gear = (Gear)obj;
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		if(gear==null){
			System.out.println("null gear");
		}
		if(gear.img!=null && gear.img.length()>0){
			ImageIcon icon = new ImageIcon("img/"+gear.img);
			g.drawImage(icon.getImage(), 0, 0, null);
			
			g.setColor(Color.white);
			FontMetrics fm = g.getFontMetrics();
			int stringWidth = fm.stringWidth(gear.name);
			int stringAscent = fm.getAscent();
			int xCoordinate = w/2 - stringWidth/2;
			int yCoordinate = h/2 +stringAscent/2;
			g.drawString(gear.name, getX()+xCoordinate,getY()+yCoordinate);
			if(selected){
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(dashed);
				g2d.setColor(c);
				g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
			}
		}
		
	}
}

/*else{
			FontMetrics fm = g.getFontMetrics();
			int stringWidth = fm.stringWidth(gear.name);
			int stringAscent = fm.getAscent();
			int xCoordinate = w/2 - stringWidth/2;
			int yCoordinate = h/2 +stringAscent/2;
			g.drawString(gear.name, x+xCoordinate,y+yCoordinate);
		}*/
