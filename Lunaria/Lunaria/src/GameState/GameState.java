package GameState;

public abstract class GameState {

	protected GameStateManager gsm;
	//Abstract methods for the controllign of gamestates and their interactions with keypresses.
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
}
