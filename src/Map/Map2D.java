package Map;

import java.awt.image.BufferedImage;

import Object.Map;

public class Map2D extends Map{
	final int EMPTY = 0;
	final int BORDER = -1;
	final int CONTENT = -2;
	int[][] imageMatrix;
	int[][] obstacleMatrix;
	
	public void addImageToMap(int xpos,int ypos,BufferedImage img){
		if(imageMatrix[ypos][xpos]!=0){
			
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
