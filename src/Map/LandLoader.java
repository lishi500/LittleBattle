package Map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import Common.Define;


public class LandLoader {
	private static LandLoader landLoader;
	Map<Integer,BufferedImage> landmap = new HashMap<Integer,BufferedImage>();
	BufferedImage bimg;
	
	public void loadResourceImage(){
		File imageFile = new File("img/resources/worldmap inquisitor.gif");
		try {
			bimg =  ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//int land
	}
	
	public BufferedImage getTest(){
		return bimg.getSubimage(0, 144, 48, 48);
	}
	private LandLoader(){loadResourceImage();}
	public static LandLoader getInstance(){
		if(landLoader==null){
			synchronized (LandLoader.class) {
				if(landLoader==null)
					landLoader=new LandLoader();
			}
		}
		return landLoader;
	}
}
