package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import TileMap.Background;

public class MenuState extends GameState {

	private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {
			"Begin",
			"Help",
			"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	private Font bodyFont;
	
	public MenuState(GameStateManager gsm){
		
		this.gsm = gsm;
		try {
			File font_file = new File("Resources/Fonts/FairyDustB.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, font_file);
			titleFont = font.deriveFont(60f);
			bodyFont = font.deriveFont(20f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
		
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1,  0);
			
			titleColor = new Color(255, 249, 200);
			
			
			
		}
		catch(Exception e){
			e.printStackTrace();}
		
	}
	
	public void init(){}
	public void update(){
		bg.update();
	}
	public void draw(Graphics2D g){
		//draw backgorund
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Lunaria", 75, 70);
		
		// draw menu options
		g.setFont(bodyFont);
		for(int i = 0; i < options. length; i++){
			if(i == currentChoice){
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 145, 140 + i * 30);			
			g.drawString(">", 120, 140 + currentChoice * 30);
			
		}
	}
	
	private void select(){
		
		if(currentChoice == 0){
			
			gsm.setState(GameStateManager.LEVEL1STATE);
			
		}
		if(currentChoice == 1){
			
			//
			
		}
		if(currentChoice == 2){	
			System.exit(0);	
		}
		
	}
	public void keyPressed(int k){
		if( k == KeyEvent.VK_ENTER){
			select();			
		}
		if( k == KeyEvent.VK_UP){
			currentChoice--;
			if(currentChoice <= -1){
				currentChoice = options.length - 1;
			}
		}
		if( k == KeyEvent.VK_DOWN){
			currentChoice++;
			if(currentChoice >= options.length){
				currentChoice = 0;
			}		
		}
	}
	public void keyReleased(int k){
		
	}
	
}
