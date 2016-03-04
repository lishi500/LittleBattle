package Map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import Data.DAO.MapSegmentDAO;

public class MapResources {
	HashMap<String, BufferedImage> resourceCenter;
	HashMap<Integer,BufferedImage> segmentPool; 
	private final String parentPath = "img/resources/";
	private static MapResources resources;
	private MapResources(){
		resourceCenter = new HashMap<String, BufferedImage>();
		segmentPool = new HashMap<Integer, BufferedImage>();
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
			}else{
				return null;
			}
		}
		return bimg;
	}
	public BufferedImage getSegmentByID(int id){
		if(segmentPool.containsKey(id))
			return segmentPool.get(id);
		MapSegment mapSegment = new MapSegmentDAO().get(id);
		BufferedImage bimg = getImageSegment(mapSegment);
		segmentPool.put(id, bimg);
		return bimg;
	}
	public BufferedImage getImageSegment(MapSegment mapSegment){
		BufferedImage bimg =  getResources(mapSegment.file_path);
		if(bimg!=null){
			return bimg.getSubimage(mapSegment.xpos, mapSegment.ypos, mapSegment.width, mapSegment.height);
		}
		return null;
	}
}
