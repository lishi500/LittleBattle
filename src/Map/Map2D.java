package Map;

import java.awt.image.BufferedImage;

import Object.Map;

public class Map2D extends Map{
	public Map2D(int width, int height) {
		super(width, height);
	}
	int[][] imageMatrix;
	int[][] obstacleMatrix;
	
	
	public void addImageToMap(int xpos,int ypos,MapSegment mapSegment){
		if(imageMatrix[ypos][xpos]==0){
			for(int i=ypos;i<ypos+mapSegment.getImage().getWidth();i++){
				for(int k=xpos;k<xpos+mapSegment.getImage().getHeight();k++){
					imageMatrix[i][k] = mapSegment.id;
					mapImage.setRGB(k, i, mapSegment.getImage().getRGB(k-mapSegment.getImage().getWidth(), i-mapSegment.getImage().getHeight()));
				}
			}
		}
		
	}
	
	public void cleanImage(int x_index, int y_index){
		if(imageMatrix[x_index][y_index]>EMPTY){
			int id = imageMatrix[x_index][y_index];
			BufferedImage bimg = MapResources.getInstance().getSegmentByID(id);
			for(int i=x_index;i<bimg.getWidth();i++){
				for(int j=y_index;j<bimg.getHeight();j++){
					imageMatrix[i][j]=0;
				}
			}
		}
		else if(imageMatrix[x_index][y_index]==EMPTY){
			return;
		}
		else if(imageMatrix[x_index][y_index]==BORDER){
			
		}
	}
	public void clearArea(int x_index,int y_index,int w,int h){
		
	}
}
