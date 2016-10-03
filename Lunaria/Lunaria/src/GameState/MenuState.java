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
	//Constructor loads fonts and gamestatemanager variables
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
	//initiates the menu
	public void init(){}
	//updates the menu(background only)
	public void update(){
		bg.update();
	}
	//draws the menu
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
	//Detects which option has been selects and loads that option
	private void select(){
		
		if(currentChoice == 0){
			
			gsm.setState(GameStateManager.LEVEL1STATE);
			
		}
		if(currentChoice == 1){
			
			//No help feature yet
			
		}
		if(currentChoice == 2){	
			System.exit(0);	
		}
		
	}
	//Detects keypresses and changes the current menu option selected
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
	//Detects key releases
	public void keyReleased(int k){
		
	}
	
}
