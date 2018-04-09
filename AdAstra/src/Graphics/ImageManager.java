package Graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

// this class loads resources on boot.
// spritesheets are taken from here.

public class ImageManager {
	
	public static BufferedImage[] playerSprites = load("/Sprites/Player/Ship.gif", 30, 30);
	public static BufferedImage[] alienSprites = load("/Sprites/Enemies/Aliens.gif", 30, 60);

	public static BufferedImage[] load(String s, int w, int h) {
		BufferedImage[] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(ImageManager.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			ret = new BufferedImage[width];
			
				for(int i = 0; i < width; i++) {
					ret[i] = spritesheet.getSubimage(i * w, 0, w, h);
				}
			
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
	
}