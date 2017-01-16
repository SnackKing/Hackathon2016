package example;

import javax.swing.ImageIcon;

public class Spike extends Actor {

	public Spike(int xStart, int yStart) {
		super(xStart, yStart);
		initSpike();
	}
	
	public void initSpike(){
		 ImageIcon ii = new ImageIcon("spike.png"); // imports and saves player image
		 image = ii.getImage();  

	     width = image.getWidth(null);
	     height = image.getHeight(null);
	     System.out.println(width + " " + height);

	}

}
