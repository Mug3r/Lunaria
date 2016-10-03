package Entity;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	//Prepares the new animation by reseting the hasplayed once variable 
	public Animation() {
		playedOnce = false;
	}
	//Sets which frame the animation will begin with after recieving an array of images containing frames
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	//Sets the delay between frames
	public void setDelay(long d) { delay = d; }
	//Sets the current frame
	public void setFrame(int i) { currentFrame = i; }
	//Runs the animation based on how much time has passed vs  the delay required.
	public void update() {
		
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
		
	}
	//Returns the index of the current frame
	public int getFrame() { return currentFrame; }
	//returns the specific image(frame)
	public BufferedImage getImage() { return frames[currentFrame]; }
	//Returns the hasplayedonce variable
	public boolean hasPlayedOnce() { return playedOnce; }
	
}
















