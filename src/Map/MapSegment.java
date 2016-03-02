	package Map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapSegment {
	public MapSegment(String name, String type, String file_path, int xpos,
			int ypos, int width, int height) {
		super();
		this.name = name;
		this.type = type;
		this.file_path = file_path;
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
	}
	public MapSegment(int id, String name, String type, String file_path,
			int xpos, int ypos, int width, int height) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.file_path = file_path;
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
	}
	public int id;
	public String name;
	public String type;
	public String file_path; //file of origonal bitmap 
	public int xpos,ypos,width,height;
	
	private BufferedImage bimg; 
	public BufferedImage getImage(){
		if(bimg==null){
			if(file_path!=null && file_path!=""){
				File bitmapFile = new File(file_path);
				if(bitmapFile.exists()){
					BufferedImage OriImg;
					OriImg = MapResources.getInstance().getResources(file_path);
					bimg = OriImg.getSubimage(xpos, ypos, width, height);
					
				}
			}
		}
		return bimg;
	}
	
	
}
