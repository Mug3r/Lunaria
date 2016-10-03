package Entity;

import TileMap.TileMap;

public class Enemy extends MapObject {

	protected int health;
	protected int maxHealth;
	protected boolean dead;
	
	protected int damage;
	
	protected boolean flinching;
	protected long flinchTimer;
	//Constructor
	public Enemy(TileMap tm){
		super(tm);
	}
	//Returns the is dead variable
	public boolean isDead(){ return dead;}
	//returns the amount of damage the enemy does
	public int getDamage() {return damage;}
	//Calculates the enemy's Health after having been hit by a player projectile, unless they are flinching or dead, if they go below 0 health the enemy dies
	public void hit(int damage){
		if(dead || flinching){return;}
		health -= damage;
		if(health <= 0){health = 0;}
		if(health == 0) {dead = true;}
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	//Abstract method update
	public void update(){}
	
}
