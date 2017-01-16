package example;

import javax.swing.ImageIcon;

public class Platform extends Actor{
	
	
	public Platform(int xStart, int yStart) {
		
		super(xStart,yStart);
		
		initPlatform();
	}
	
	public void initPlatform() {
		
		ImageIcon ii = new ImageIcon("platform.png"); // imports and saves player image
        image = ii.getImage();  

        width = image.getWidth(null);
        height = image.getHeight(null);
        
	}
	
}
