package example;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Player extends Actor{

	// fields unique to Player class
	private int xInit, yInit;
    private int j; // strength of jump
    private int keySpeed; 
    private int termV; // parameter for terminal velocity of player
    private int termVperp; // maximum perpendicular speed
    private boolean hasTraction; // False when in the air, true when standing firmly on something.
    
    /**
     * Player constructor, creates Player object
     * @param xStart
     * 		Initial x-coordinate of Player, must be positive
     * @param yStart
     * 		Initial y-coordinate of Player, must be positive
     */
    public Player(int xStart, int yStart) {
        
    	super(xStart, yStart); // calls Actor constructor with intended starting coordinates for Player
    	xInit = xStart;
    	yInit = yStart;
    	
        initPlayer();
    }
    
    /**
     * performs various actions necessary to initialization of player object
     */
    private void initPlayer() {
          
        ImageIcon ii = new ImageIcon("Musk2.png"); // imports and saves player image
        image = ii.getImage();  
        
        width = image.getWidth(null);
        height = image.getHeight(null);
        
        termV = 21; // initialize terminal velocity parameter
        termVperp = 15;
        j = 18; // initialize jump strength parameter
        hasTraction = false;
        
    }
    
    public void reset() {
    	x = xInit;
    	y = yInit;
    	dx = 0;
    	dy = 0;
    	ddx = 0;
    	ddy = 0;
    	vis = true;
    	setDead(false);
    }
    
    /**
     * move method, changes coordinates of player, changes velocity of player, updates acceleration of player.
     * @param flip
     * 		1 if gravity is upwards, 0 if gravity is downwards
     * @param theta
     * 		angle at which gravity currently acts
     */
    public void move(int flip, double theta) {
    	  
    	if(vis) {
    		
    		ddy = -(2*flip - 1) * (g * Math.cos(theta*(Math.PI/180))); // change in velocity due to gravity in y-direction
    		ddx = (g * Math.sin(theta*(Math.PI/180))); // change in velocity due to gravity in x-direction
    	
    	
    		if (Math.abs(dy) < termV && hasTraction) {  // if |speed| is less than the terminal velocity and hasTraction
    			dy += -(keySpeed*Math.sin(theta*(Math.PI/180))); // change in y-coord due to gravity and key press
    			dy = dy * 0.91; //simulates friction
    			//if(dy<0.0) {
    			//	dy=0.0;
    			//}
    		}
    		else if (Math.abs(dy) < termV) { // if no traction, still y acceleration from gravity
    			dy += ddy;
    		}
    		else if (hasTraction) { // if above terminal velocity, still y acceleration from a-d
    			dy += -(keySpeed*Math.sin(theta*(Math.PI/180)));
    			dy = dy * 0.91; //simulates friction
    			//if(dy <= 0.0) {
    			//	dy=0.0;
    			//}
    		}
    		
    	
    		if (Math.abs(dx) < termVperp && hasTraction) {  // if |speed| is less than the terminal velocity and hasTraction
    			dx += ddx + (keySpeed*Math.cos(theta*(Math.PI/180))); // change in x-coord due to gravity and key press
    			dx = dx * 0.91; //simulates friction
    			//if(dx<0.0) {
    			//	dx=0.0;
    			//}
    		}
    		else if(Math.abs(dx) < termVperp){
    			dx += ddx;
    		}
    		else if (hasTraction) { // if above terminal velocity, still y acceleration from a-d
    			dx += (keySpeed*Math.cos(theta*(Math.PI/180)));
    			dx = dx * 0.91; //simulates friction
    			//if(dx <= 0.0) {
    			//	dx=0.0;
    			//}
    		}
    		
    		// increment coordinates
        	x += dx;
        	y += dy;
    	}
    }

    
    
    
    /**
     * This function is called whenever a key is pressed that is relevent to the Player
     * @param e
     * 		The key event itself
     * @param flip
     * 		1 if gravity is upwards, 0 if downwards
     * @param theta
     * 		The angle at which gravity is currently acting
     */
    public void keyPressed(KeyEvent e, int flip, double theta) {

        int key = e.getKeyCode(); // key tracks what key was pressed

        if (key == KeyEvent.VK_A && hasTraction) {
            keySpeed = -1; // if key is A, and player has traction, adds some velocity left
        }
        
        if (key == KeyEvent.VK_D && hasTraction) {
            keySpeed = 1; // if key is D, and player has traction, adds some velocity right
        }
        
        if (key == KeyEvent.VK_SPACE && hasTraction) {
        	// if key is SPACE, player jumps.
        	dy += (2*flip - 1) * (g * Math.cos(theta*(Math.PI/180)))* j; // sets vertical velocity
        	dx += -(g * Math.sin(theta*(Math.PI/180))) * j; //sets horizontal velocity
        	hasTraction = false;
        }
    }

    public void keyReleased(KeyEvent e, int flip, double theta) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            keySpeed = 0;
        }

        if (key == KeyEvent.VK_D) {
            keySpeed = 0;
        }
    }
    
    public Rectangle getFeet() {
    	return new Rectangle((int)(x+5), (int)(y+119), (width-10), 1);
    }
    
    public Rectangle getLeftSide() {
    	return new Rectangle((int)(x), (int)(y+10), 1, (height-20));
    }
    
    public Rectangle getRightSide() {
    	return new Rectangle((int)(x+width-1), (int)(y+10), 1, (height-20));
    }
    
    public Rectangle getHead() {
    	return new Rectangle((int)(x+5), (int)(y), (width-10), 1);
    }
    
    public void hitBlockVert() {
    	dy = 0;
    	ddy = 0;
    }
    
    public void hitBlockHorz() {
    	dx = 0;
    	ddx = 0;
    }
    
    public void setTraction(boolean a) {
    	hasTraction = a;
    }
    
    // @override
    public void setDead(boolean a) {
    	dead = a;
    	if (dead) {
            ImageIcon ii = new ImageIcon("MuskDead.png");
    		image = ii.getImage();
    	}
    	else {
    		ImageIcon ii = new ImageIcon("Musk2.png");
    		image = ii.getImage();
    	}
    }
    
    public boolean getDead() {
    	return dead;
    }
    
    public boolean getHasTraction() {
    	return hasTraction;
    }
    
    public void setVis(boolean a) {
    	vis = a;
    }
    
    public boolean getVis() {
    	return vis;
    }
    
}