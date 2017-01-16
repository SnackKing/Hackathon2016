package example;

import javax.swing.ImageIcon;

public class LongPlatform extends Actor{
	
	public LongPlatform(int xStart, int yStart) {
		
		super(xStart,yStart);
		
		initPlatform();
	}
	
	public void initPlatform() {
		
		ImageIcon ii = new ImageIcon("PlatformLong.png"); // imports and saves player image
        image = ii.getImage();  

        width = image.getWidth(null);
        height = image.getHeight(null);
	}
	

}
