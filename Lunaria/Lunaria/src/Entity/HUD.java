package Entity;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;

public class HUD {

	private Player player;
	
	private BufferedImage image;
	private Font font;
	
	public HUD(Player p){
		player = p;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			font = new Font("Arial", Font.PLAIN, 9);
		}catch(Exception e){
			e.printStackTrace();
			}
	}
	
	public void draw(Graphics2D g){
		
		g.drawImage(image, 0, 3, null);
		g.setFont(font);
		g.setColor(new Color(255, 255, 255));
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 25, 35);
		g.drawString(player.getSoul() / 100 + "/" + player.getMaxSoul() / 100, 25, 48);
		
	}
	
}
