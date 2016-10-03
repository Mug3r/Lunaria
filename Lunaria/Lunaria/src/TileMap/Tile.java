package TileMap;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	private int type;
	
	//tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	//Constructor
	public Tile(BufferedImage image, int type){		
		this.image = image;
		this.type  = type;		
	}
	//returns the tile's image
	public BufferedImage getImage(){return image;}
	//returns the type of tile blocked(cannot be moved through) or normal(can be passed through)
	public int getType(){return type;}
	
}
