
package Entity;


import TileMap.*;

import java.util.ArrayList;
import java.util.HashMap;

import Audio.AudioPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {

	//Player Variables
	private int health;
	private int maxHealth;
	private int soul;
	private int maxSoul;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	// Soul Blast
	private boolean firing;
	private int soulCost;
	private int soulBlastDamage;
	private ArrayList<SoulBlast> soulBlasts;
	
	// Melee Attack, Removed to enrich gameplay(for now :) )
	private boolean clawing;
	private int clawDamage;
	private int clawRange;
	
	//Gliding
	private boolean gliding;
	
	// Animations
	private ArrayList<BufferedImage[]> sprites;
	public final int[] numFrames = {
			2, 8, 1, 2, 4, 2, 5
	};
	// Animation Actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int SOULBLAST = 5;
	private static final int SCRATCHING = 6;
	
		private final int[] FRAMEWIDTHS = {
			30, 30, 30, 30, 30, 30, 60
		};
		private final int[] FRAMEHEIGHTS = {
			30, 30, 30, 30, 30, 30, 30
		};
		private final int[] SPRITEDELAYS = {
			400, 40, 2, 6, 50, 150, 2
		};
	
	private HashMap<String , AudioPlayer> sfx;
	//Constructor, Sets all the player's variables loads sprites and preps animations
	public Player(TileMap tm){
		
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 5;
		soul = maxSoul = 2500;
		
		soulCost = 200;
		soulBlastDamage = 5;
		soulBlasts = new ArrayList<SoulBlast>();
		
		clawDamage = 8;
		clawRange = 40;
		
		//load sprites
try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Player/playersprites.gif"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					
					if(i != SCRATCHING) {
						bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					}
					else {
						bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
					}
					
				}
				
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("jump", new AudioPlayer("/SFX/jump.mp3"));
		sfx.put("claw", new AudioPlayer("/SFX/scratch.mp3"));
		
	}
	//Returns the players current health
	public int getHealth(){return health;}
	//Returns the player's maximum possible health
	public int getMaxHealth(){return maxHealth;}
	//Returns the current amount of soul energy
	public int getSoul(){return soul;}
	//Returns the maximum amount of soul energy
	public int getMaxSoul(){return maxSoul;}
	//Sets if the player is firing attacks
	public void setFiring(){
		firing = true;		
	}
	//Sets if the player is using their Melee attack
	public void setClawing(){
		clawing = true;
	}
	//Sets if the player is gliding
	public void setGliding(boolean b){
		gliding = b;
	}
	//Checks the players current attacks, if they have hit any enemies.
	public void checkAttack(ArrayList<Enemy> enemies){
		// loop through enemies
		for(int i = 0; i < enemies.size(); i ++){
			
			Enemy e = enemies.get(i);
			
			// claw attack
			
			if(clawing){
				if(facingRight){
					if(
						e.getx() > x &&
						e.getx() < x + clawRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2){
							e.hit(clawDamage);
					}
				}
				else {
					if(
						e.getx() < x &&
						e.getx() > x - clawRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2){
							e.hit(clawDamage);
					}
				}
			}
			
			//soulBlasts
			for(int j = 0; j < soulBlasts.size(); j++){
				if(soulBlasts.get(j).intersects(e)){
					e.hit(soulBlastDamage);
					soulBlasts.get(j).setHit();
					break;
				}
			}
			//check enemy collison
			if(intersects(e)){
				hit(e.getDamage());
			}
		}
	
		//SoulBlasts
		for(int i = 0; i < soulBlasts.size(); i++){
			for(int j = 0; j < enemies.size(); j++){
				
			}
		}
		
		
		
	}
	//if the player is hit and not flinching they take damage, equal to the amount passed on by other objects
	public void hit(int damage){
		if(flinching){return;}
		health -= damage;
		if(health <= 0){
			health = 0;
		}
		if(health == 0){dead = true;}
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	//Gets the players next amp position using their current vextor
	private void getNextPosition(){
		// movement
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
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}		
		
		// cannot attack while moving, except in air
		if((currentAction == SCRATCHING || currentAction == SOULBLAST) && !(jumping || falling)){
			dx = 0;
		}
		
		//jumping
		if(jumping && !falling){
			sfx.get("jump").play();
			dy = jumpStart;
			falling = true;
		}
		
		//falling 
		if(falling){
			
			if(dy > 0 && gliding){dy += fallSpeed * 0.1;}
			else dy += fallSpeed;
			
			if(dy > 0) {jumping = false;
			if(dy < 0 && !jumping){dy += stopJumpSpeed;}
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	}
	//Updates the player and all their variables.
	public void update(){
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		// check attack stopped
		/*if(currentAction == SCRATCHING){
			if(animation.hasPlayedOnce()) {scratching = false;}
		}*/
		if(currentAction == SOULBLAST){
			if(animation.hasPlayedOnce()) {firing = false;}
		}
		
		//Soul Blast Attack
		soul += 1;
		if(soul > maxSoul){
			soul = maxSoul;
		}
		if(firing && currentAction != SOULBLAST){
			if(soul > soulCost){
				soul -= soulCost;
				SoulBlast sb = new SoulBlast(tileMap, facingRight);
				sb.setPosition(x, y);
				soulBlasts.add(sb);
			}
		}
		//update Soul Blasts
		for(int i = 0; i < soulBlasts.size(); i++){
			soulBlasts.get(i).update();
			if(soulBlasts.get(i).shouldRemove()){
				soulBlasts.remove(i);
				i--;
			}
		}
		
		// Check done flinching
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed >= 1000){
				flinching = false;
			}
		}
		
		//set animations
		/*if(scratching){
			
			if(currentAction != SCRATCHING){
				sfx.get("scratch").play();
				setAnimation(SCRATCHING);		
			}
		} else */if(firing){
			if(currentAction != SOULBLAST){
				setAnimation(SOULBLAST);		
			}
		} else if(dy > 0){
			if(gliding){
				if(currentAction != GLIDING){
					setAnimation(GLIDING);
				}			
			}else if(currentAction != FALLING){
				setAnimation(FALLING);
			} 
		}
			
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				setAnimation(JUMPING);
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				setAnimation(FALLING);
			}
		}
		
		else if(left || right) {
			if(currentAction != WALKING) {
				setAnimation(WALKING);
			}
		}
		else if(currentAction != IDLE) {
			setAnimation(IDLE);
		}
		
		animation.update();
		
		
		//set direction
		if(currentAction != SCRATCHING && currentAction != SOULBLAST){
			if(right){facingRight = true;}
			if(left) {facingRight = false;}
		}
	}
	//Draw the player
	public void draw(Graphics2D g){
		
		setMapPosition();
		
		//draw Soul Blasts
		for(int i = 0; i < soulBlasts.size(); i++){
			soulBlasts.get(i).draw(g);
		}
		//draw player
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0){return;}
		}
		
		super.draw(g);
	}
	//sets the player's animation to the parsed frame
	private void setAnimation(int i) {
		currentAction = i;
		animation.setFrames(sprites.get(currentAction));
		animation.setDelay(SPRITEDELAYS[currentAction]);
		width = FRAMEWIDTHS[currentAction];
		height = FRAMEHEIGHTS[currentAction];
	}
}
