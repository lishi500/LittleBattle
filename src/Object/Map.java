package Object;

import java.awt.image.BufferedImage;

public abstract class Map {
	public BufferedImage mapImage;
	public final int EMPTY = 0;
	public final int BORDER = -1;
	public final int CONTENT = -2;

	public Map(int width, int height){
		mapImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	}
}
