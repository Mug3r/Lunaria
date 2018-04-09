package Game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy {

	private int type, r, health;
	private double speed;
	private double x, y;
	private boolean lt = false;
	
	public Enemy(int x, int y, int t) {
	
		this.x = x;
		this.y = y;
		this.type = t;
		
		switch(t) {
			
		case 0:
			r = 15;
			health = 1;
			speed = 1;
			lt = true;
			break;
		
		case 1:
			r = 30;
			health = 2;
			speed = 3;
			lt = false;
			break;
		
		}
		
	}
	
	public void update() {
		
		if((x+r) >= GamePanel.WIDTH) {
			
			lt = true; 
			y+=r;
			
		} else if((x-r) <= 0) {
			
			lt = false; 
			y+=r;
			
		}
		
		if(!lt) {x = (x+speed);} else {x = (x-speed);} 
		
	}
	
	public void draw(Graphics2D g) {
		switch(type) {
		
			case 0:
				g.setColor(new Color(120,100,0));
				g.fillOval((int)x, (int)y, r, r);
				break;
				
			case 1:
				g.setColor(new Color(220,0,120));
				g.fillOval((int)x, (int)y, r, r);
				break;
				
		}
		
		
	}
	public boolean getLt() {return lt;}
	public void setLt(boolean b) {lt = b;}
	
	public double getX() {return x;}
	public double getY() {return y;}
	public int getR() {return r;}

	
	
}
