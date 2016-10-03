package Audio;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;

public class AudioPlayer {

	private Clip clip;
	
	//Constructor
	public AudioPlayer(String s){
		
		try{
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	//Starts playing the selected clip from the start(if no clip goes to the stop method)
	public void play(){
		if(clip == null){return;}
		stop();
		
		clip.setFramePosition(0);
		clip.start();
	}
	//Ends/Pauses the current clip
	public void stop(){
		if(clip.isRunning()) {clip.stop();}
	}
	//Termiantes the clip making it unresumable.
	public void close(){
		stop();
		clip.close();
	}
	
}
