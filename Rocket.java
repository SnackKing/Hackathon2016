package example;

import javax.swing.ImageIcon;

public class Rocket extends Actor{
	
	private ImageIcon iLIT;
	private ImageIcon iUNLIT;
	private boolean lit;
	
	private int xInit, yInit;
	
	public Rocket(int xStart, int yStart) {
		super(xStart,yStart);
		xInit = xStart;
		yInit = yStart;
		rocketInit();
	}
	
	public void rocketInit() {
		iLIT = new ImageIcon("lit.png"); // imports and saves lit rocket image
		iUNLIT = new ImageIcon("unlit.png");
        image = iUNLIT.getImage();
        
        dx = 0;
        dy = 0;
        ddx = 0;
        ddy = 0;

        width = image.getWidth(null);
        height = image.getHeight(null);
	}
	
	public void ignition() {
		image = iLIT.getImage();
		ddx = -.1;
		ddy = -.2;
		lit = true;
	}
	
	public void move() {
		dx += ddx;
		dy += ddy;
		x += dx;
		y += dy;
	}
	
	public boolean getLit() {
		return lit;
	}
	
	public void reset() {
		x = xInit;
		y = yInit;
		dx = 0;
		dy = 0;
		ddx = 0;
		ddy = 0;
		dead = false;
		lit = false;
        image = iUNLIT.getImage();
	}
	
}
