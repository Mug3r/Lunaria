package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.*;
import Entity.Enemies.*;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import Audio.AudioPlayer;

/*
NB: 

All Comments here apply to levels 2 and 3's states. As such these comments will not be repeated in thsoe classes.

*/

public class Level1State extends GameState {

	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	private AudioPlayer bgMusic;
	
	private int worldGateX = 1910;
	//Constructor creates a new object setting the gamestatemanager in use and runs the init method
	public Level1State(GameStateManager gsm){
		
		this.gsm = gsm;
		init();
		
	}
	//Initaites the new Level loading all variables, music and resources
	public void init(){ 
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/Tileset.gif");
		tileMap.loadMap("/Maps/Level 1.txt");
		tileMap.setPosition(0, 0);
		
		bg = new Background("/Backgrounds/starsbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100,100);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/Stage 1.mp3");
		bgMusic.play();
		
		
	}
	//Loads enemies onto the map
	private void populateEnemies(){
		
		enemies = new ArrayList<Enemy>();
		Ghoul gh;
		Point[] points = new Point[]{
			new Point(200, 100),
			new Point(860, 200),
		};
		for(int i = 0; i < points.length; i++){
			gh = new Ghoul(tileMap);
			gh.setPosition(points[i].x, points[i].y);
			enemies.add(gh);
		}
		
	}
	//Updates the state and all objects within it
	public void update(){
		
		player.update();
		
		double tileMapShift = GamePanel.WIDTH / 2 - player.getx();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
		tileMapShift -= player.getx();
		if(-tileMapShift >= worldGateX){
			gsm.setState(gsm.currentState + 1);
		}
		//set background
		bg.setPosition(tileMap.getX(), tileMap.getY());	
		
		//attack enemies
		player.checkAttack(enemies);
		
	
		//Update enemies
		for(int i = 0; i < enemies.size(); i++){
		Enemy e = enemies.get(i);
		e.update();
		if(e.isDead()){
			enemies.remove(i);
			i--;
			explosions.add(new Explosion(e.getx(), e.gety()));
		}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()){
				explosions.remove(i);
				i--;
			}
		}
	}
	//draws the current level and all objects on it
	public void draw(Graphics2D g){
		//draw bg
		bg.draw(g);
		
		//draw tilemap
		tileMap.draw(g);
		
		//draw player
		player.draw(g);
		
		//draw enemies
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).setMapPosition((int)tileMap.getX(),(int) tileMap.getY());
			explosions.get(i).draw(g);
		}
		
		//draw hud
		hud.draw(g);
		
		}
	
	
	
	
	//detects key presses
	public void keyPressed(int k){
		//player movement
		if(k == KeyEvent.VK_A){ player.setLeft(true);}
		if(k == KeyEvent.VK_D){ player.setRight(true);}
		if(k == KeyEvent.VK_W){ player.setUp(true);}
		if(k == KeyEvent.VK_S){ player.setDown(true);}
		if(k == KeyEvent.VK_SPACE){ player.setJumping(true);}
		if(k == KeyEvent.VK_SHIFT){ player.setGliding(true);}
		if(k == KeyEvent.VK_V){ player.setFiring();}
	}
	//detects key releases
	public void keyReleased(int k){
		if(k == KeyEvent.VK_A){ player.setLeft(false);}
		if(k == KeyEvent.VK_D){ player.setRight(false);}
		if(k == KeyEvent.VK_W){ player.setUp(false);}
		if(k == KeyEvent.VK_S){ player.setDown(false);}
		if(k == KeyEvent.VK_SPACE){ player.setJumping(false);}
		if(k == KeyEvent.VK_SHIFT){ player.setGliding(false);}
	}
}
