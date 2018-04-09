package Levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Game.Enemy;
import Game.GamePanel;
import Game.Player;
import Projectiles.Bullets;

public class GameStateManager {
	
	private final int MENUSTATE = 0, GAMESTATE = 1, PAUSED = 2, VICTORY = 3, DEFEAT = 4;

	private final int[] STATES = {
			MENUSTATE,GAMESTATE,PAUSED, VICTORY, DEFEAT
	}; 
	private int state;
	private int level;
	private ArrayList<Enemy> e;
	private ArrayList<Bullets> bullets;
	private Player p;
	
	private int choice = 0;
	
	public GameStateManager() {
		
		state = STATES[0];
		level = 0;
		e = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullets>();
		p = new Player();
	}
	
	public void loadLevel(int l) {
		
		state = GAMESTATE;
		level = l;
		
		switch(level) {
		
			case 0:
				e.add(new Enemy(200, 30, 1));
				e.add(new Enemy(250, 30, 1));
				e.add(new Enemy(300, 30, 1));
				
				e.add(new Enemy(15, 30, 0));
				e.add(new Enemy(65, 30, 0));
				e.add(new Enemy(115, 30, 0));
				break;
		
			case 1:
				

				break;
			
			case 2:
				break;
			
			case 3:
				break;
			
			case 4:
				break;
			
			case 5:
				break;
			
			case 6:
				state = STATES[3];
				level = 0;
				break;
		
		}
		
	}
	
	public void update() {
		
		if(state == MENUSTATE) {
			level = 0;
		}
		
		if(state == GAMESTATE) {
			
			if(e.size() <= 0) {
				loadLevel(level++);
			} else {
				
				for(int i = 0; i < e.size(); i++) {
					e.get(i).update();
				}
				p.update();
				
				
			}
			
			
			
		}
		
	}
	public void draw(Graphics2D g) {
		
		switch(state) {
		
			case 0:
				
				
				Font stringFont = new Font( "SansSerif", Font.PLAIN, 26 );
				
				g.setColor(new Color(0,12,128));
				g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
				
				
				g.setFont(stringFont);
				
				switch(choice) {
				
					case 0:
						g.setColor(new Color(255,255,0));
						g.drawString("Play", 280, 480);
						g.fillRect(230, 460, 20, 20);
						g.drawRect(240, 440, 120, 60);
						g.setColor(new Color(255,255,255));
						g.drawString("Quit", 280, 540);
							break;
					
					case 1:
						g.setColor(new Color(255,255,255));
						g.drawString("Play", 280, 480);
						g.setColor(new Color(255,255,0));
						g.drawString("Quit", 280, 540);
						g.fillRect(230, 520, 20, 20);
						g.drawRect(240, 500, 120, 60);
							break;
							
				}
				
				
				break;
				
			case 1:
				g.setColor(new Color(0,0,0));
				g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
				g.setColor(new Color(255,255,0));
				g.drawLine(0, 730, 600, 730);
				for(int i = 0; i < e.size(); i++) {
					e.get(i).draw(g);
				}
				p.draw(g);
				
				break;
				
			case 2:
				break;
			
		}
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		switch(state) {
		
		case 0:
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				choice--;
				if(choice < 0) {
					choice = 1;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				choice++;
				if(choice > 1) {
					choice = 0;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(choice == 0) {
					
					state = GAMESTATE;
					
				} else if(choice == 1) {
					System.exit(2);
				}
			}
			break;
		
		case 1:
			
			if(e.getKeyCode() == KeyEvent.VK_A) {p.setLt(true);}
			if(e.getKeyCode() == KeyEvent.VK_D) {p.setRt(true);}
			
			break;
		
		
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		
		if(state == 1) {
			if(e.getKeyCode() == KeyEvent.VK_A) {p.setLt(false);}
			if(e.getKeyCode() == KeyEvent.VK_D) {p.setRt(false);}
		}
		
	}
	
}
