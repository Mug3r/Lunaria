package Main;

import javax.swing.JFrame;

public class Game {
	//Main method runs on launch creating a new gamepanel object and frame in which it can be found
	public static void main(String[]args){
		
		JFrame window = new JFrame("Lunaria");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}
