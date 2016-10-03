package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import GameState.GameStateManager;
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	//dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	//game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	//image
	private BufferedImage image;
	private Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;
	
	//constructor
	public GamePanel(){
		
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	//Starts new threads and runs the addnotify method to make this Component displayable by connecting it to a native screen resource.
	public void addNotify(){
		
		super.addNotify();
		if(thread == null){
			
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
			
		}
	}
	//Initates the new game object.
	private void init(){
				
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	//Game Loop runs the entire game and holds the main clock for updates and rendering
	public void run(){
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		//Game Loop
		while(running){
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0){wait = 5;}
			
			try{
				Thread.sleep(wait);
			}
			catch(Exception e){
				e.printStackTrace();				
			}
		}
		
	}
	//Updates the game(through the gamestatemanager)
	private void update(){
		gsm.update();
	}
	//Draws the game(through gamestatemanager)
	private void draw(){
		gsm.draw(g);
	}
	//Compiles graphics and then draws them to the current bottom most buffer to be displayed once it reaches the top(repeats as new frames are added by the draw method below)
	private void drawToScreen(){
		
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
		
	}
	//Keypress detection methods
	public void keyTyped(KeyEvent key){}
	public void keyPressed(KeyEvent key){gsm.keyPressed(key.getKeyCode());}
	public void keyReleased(KeyEvent key){gsm.keyReleased(key.getKeyCode());}
}
