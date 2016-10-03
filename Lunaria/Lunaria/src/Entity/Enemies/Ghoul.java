package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import Entity.*;
import Graphics.ImageManager;
import TileMap.TileMap;

public class Ghoul extends Enemy {

	private BufferedImage[] sprites;
	//Constructor sets the tilemap and the ghoul's variables
	public Ghoul(TileMap tm){
		
		super(tm);
		
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		sprites = ImageManager.ghoulSprites;
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
	}
	//Gets the ghoul's next position based on its vector
	private void getNextPosition(){
		
		//movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		
		if(falling){
			dy += fallSpeed;
		}
	}
	//Updates the ghoul object
	public void update(){
		
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		// check flinching
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer)/1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
		
		// if it hits wall, turn around
		if(right && dx == 0){
			right  = false;
			left = true;
			facingRight = false;
		} else if(left && dx == 0){
			right = true;
			left = false;
			facingRight = true;
		}
		animation.update();
	}
	//Draws the ghoul object
	public void draw(Graphics2D g){
		
		//if(offScreen()){return;}
		
		setMapPosition();
		
		super.draw(g);
		
	}
	
}
