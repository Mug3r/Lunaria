package GameState;

import java.awt.*;


public class GameStateManager {
	
	
	//Stores number of 'states' the game can be in
	private GameState[] gameStates;
	//Index of the current state
	public int currentState;
	
	public static final int NUMGAMESTATES = 3;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int LEVEL2STATE = 2;
	
	//Stops the game checking for key presses/releases while changing levels
	private boolean listen = false;
	
	//Constructor
	public GameStateManager(){
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
		listen = true;
		
	}
	
	private void loadState(int state){
		switch (state){
		
		case MENUSTATE:
			gameStates[state] = new MenuState(this);
			break;
		case LEVEL1STATE:
			gameStates[state] = new Level1State(this);
			break;
		case LEVEL2STATE:
			gameStates[state] = new Level2State(this);
			break;
		
		}
	}
	
	private void unloadState(int state){
		
		listen = false;
		gameStates[state] = null;
		
	}
	
	public void setState(int state){
		
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		listen = true;
		//gameStates[currentState].init();
				
	}
	
	public void update(){
		try{
		gameStates[currentState].update();}
		catch(Exception e){}
		
	}
	
	public void draw(Graphics2D g){
		try{
		gameStates[currentState].draw(g);}
		catch(Exception e){}
		
	}
	
	public void keyPressed(int k){	
		if(listen){gameStates[currentState].keyPressed(k);}
	}
	
	public void keyReleased(int k){
		if(listen){gameStates[currentState].keyReleased(k);}
	}
	
	
}
