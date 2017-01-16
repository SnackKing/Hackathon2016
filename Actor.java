package example;

import java.awt.Image;
import java.awt.Rectangle;

	/**
	 * General game thing, anything that will be affected by gravity. Other classes will extend this.
	 * If an object is not subject to gravity, dx, dy, ddx, and ddy will simply be all constant at zero.
	 * @author plank and others
	 */
public class Actor {
	
	// fields to be used in classes that extend Actor
	protected double ddx; // x acceleration
	protected double ddy; // y acceleration
    protected double dx; // x velocity
    protected double dy; // y velocity
    protected double x; // x coordinate
    protected double y; // y coordinate
    protected Image image; // image of sprite
    protected int width; // width of sprite
    protected int height; // height of sprite
    
    protected boolean dead;
    
    protected double g; // acceleration of gravity
	protected boolean vis; // visibility of object (not yet implemented)
    
    /**
     * Constructor for Actor class.
     * 
     * @param xStart
     * 		Starting x-coordinate of Actor, must be positive.
     * @param yStart
     * 		Starting y-coordinate of Actor, must be positive.
     */
    public Actor(int xStart, int yStart) {
    	
    	this.x = xStart; // sets initial coords
    	this.y = yStart;
    	
    	vis = true;
    	g = .7; // acceleration due to gravity, adjustable parameter.
    	
    }
    
    
 // rounds coordinates to nearest whole pixel for display on screen
    public int getX() {
        return (int)(x+0.5);
    }

    public int getY() {
        return (int)(y+0.5);
    }
    
    /**
     * returns the bounding rectangle of the Actor's image.
     * bounds are needed in collision detection.
     * @return
     * 		returns a Rectangle object
     */
    public Rectangle getBounds() {
    	return new Rectangle((int)x,(int)y,width,height);
    }
    
    public Image getImage() {
        return image;
    }
	
    public void setDead(boolean a) {
    	dead = a;
    }
    public boolean getDead() {
    	return dead;
    }
}
