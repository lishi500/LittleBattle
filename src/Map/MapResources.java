package Map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class MapResources {
	HashMap<String, BufferedImage> resourceCenter;
	private final String parentPath = "img/resources/";
	private static MapResources resources;
	private MapResources(){
		resourceCenter = new HashMap<String, BufferedImage>();
	}
	public static MapResources getInstance(){
		if(resources==null){
			synchronized (MapResources.class) {
				if(resources==null){
					resources = new MapResources();
				}
			}
		}
		return resources;
	}
	
	public final BufferedImage getResources(String file_path){
		BufferedImage bimg = resourceCenter.get(file_path);
		if(bimg!=null)
			return bimg;
		return addResources(file_path);
	}
	
	public BufferedImage addResources(String file_path){
		BufferedImage bimg = null;
		synchronized (resourceCenter) {
			if(resourceCenter.containsKey(file_path))
				return resourceCenter.get(file_path);
			File imgFile = new File(parentPath+file_path);
			if(imgFile.exists()){
				try {
					bimg = ImageIO.read(imgFile);
					resourceCenter.put(file_path, bimg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return bimg;
	}
}
