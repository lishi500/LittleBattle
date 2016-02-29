package Editor;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public abstract class IconComponent extends JComponent{

	public BufferedImage img;
	public Object obj;
	public int x,y,w,h;
	public boolean selected = false;
	public IconComponent(BufferedImage img,Object obj){
		this.img = img;
		this.obj = obj;
	}
	public IconComponent(){
	}
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public void setH(int h) {
		this.h = h;
	}
	
	public void SetBound(int x, int y, int w, int h){
		this.w = w;this.h = h;
		this.x = x;this.y =y;
	}
//	public abstract void paintComponent(Graphics g);
	
}
