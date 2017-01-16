package example;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Jpanel that contains information for the whole game.
 *
 * @author Jack Plank
 * @author Zach Allegretti
 * @author Bennett Deshotels
 * @author Sean Vactor
 */
public class Board extends JPanel implements ActionListener {

    /*
     * Private fields
     */

    private int xStart, yStart;

    /**
     * The game timer.
     */
    private Timer timer;

    /**
     * Character controlled by the user.
     */
    private Player player;

    /**
     * The delay for the program timer. Longer delay means the whole program
     * runs slower.
     */
    private final int DELAY = 15;

    /**
     * The angle at which gravity acts. 90 degree theta applies gravitational
     * force to the right.
     */
    private double theta;

    /**
     * False indicates gravity faces to bottom half of screen, true indicates
     * gravity faces toward top half of screen.
     */
    private int flip;

    /**
     * The background image.
     */
    private Image background;

    /**
     * An array list storing all platforms in the current level.
     */
    private ArrayList<Platform> platforms;
    /**
     * ArrayList of spikes on current level.
     */
    private ArrayList<Spike> spikes;

    private ArrayList<LongPlatform> longPlatforms;

    private Rocket rocket;

    /*
     * Instance methods
     */

    /**
     * No-argument constructor for Board.
     */
    public Board() {
        this.initBoard();
    }

    /**
     * Performs various tasks necessary to the creation of the Board object
     */
    private void initBoard() {
        // adding event listeners
        this.addKeyListener(new TAdapter());
        this.addMouseWheelListener(new WheelAdapter());
        this.addMouseListener(new WheelClick());

        this.setFocusable(true); // gives focus to this JPanel
        ImageIcon ii = new ImageIcon("Background.png"); // imports and saves player image
        this.background = ii.getImage();

        this.initPlatforms();
        this.initSpikes();
        this.initLongPlatforms();

        this.xStart = 64;
        this.yStart = 600;

        this.player = new Player(this.xStart, this.yStart); // creates Player object at initial coords x=64 and y=600

        int xRocket = 64;
        int yRocket = 200;

        this.rocket = new Rocket(xRocket, yRocket);

        this.flip = 0; // initializes gravity to downward
        this.theta = 0; // initializes gravity to straight vertical

        this.timer = new Timer(this.DELAY, this); // creates timer to regulate how quickly the program runs, creates actionEvent once every DELAY seconds
        this.timer.start();
        this.setVisible(true);
        this.requestFocusInWindow();
    }

    public void initSpikes() {
        this.spikes = new ArrayList<>();

        /*
         * Array of x,y coordinates for each spike
         */
        int[][] pos = { { 372, 450 }, { 404, 450 }, { 436, 450 }, { 468, 450 },
                { 500, 450 }, { 372, 761 }, { 404, 761 }, { 436, 761 },
                { 468, 761 }, { 718, 761 }, { 750, 761 }, { 782, 761 },
                { 814, 761 } };
        /*
         * Add each platform coordinate to the array list of platforms
         */
        for (int[] p : pos) {
            this.spikes.add(new Spike(p[0], p[1]));
            System.out.println("one");
        }
    }

    /**
     * Initializes {@code platforms} with the x,y coords of each platform in the
     * level.
     */
    public void initPlatforms() {
        this.platforms = new ArrayList<>();

        /*
         * Array with all x,y pairs for each platform in the level
         */
        int[][] pos = { { 0, 505 }, { 0, 473 }, { 0, 729 }, { 0, 697 },
                { 0, 665 }, { 0, 633 }, { 0, 601 }, { 0, 569 }, { 0, 537 },
                { 718, 441 }, { 718, 409 }, { 718, 377 }, { 718, 345 },
                { 718, 313 }, { 718, 281 }, { 718, 249 }, { 750, 249 },
                { 1032, 697 }, { 1032, 665 }, { 1032, 633 }, { 1032, 601 },
                { 1032, 569 }, { 1032, 537 }, { 1032, 505 }, { 1032, 473 },
                { 1364, 473 }, { 1364, 441 }, { 1364, 409 }, { 1364, 377 },
                { 1364, 345 }, { 1364, 313 }, { 1364, 281 }, { 1364, 249 },
                { 1364, 217 }, { 1364, 185 }, { 1364, 153 }, { 1364, 121 },
                { 1364, 89 }, { 1364, 57 }, { 1364, 25 }

        };

        /*
         * Add each platform coordinate to the array list of platforms
         */
        for (int[] p : pos) {
            this.platforms.add(new Platform(p[0], p[1]));
        }
    }

    public void initLongPlatforms() {
        this.longPlatforms = new ArrayList<>();

        /*
         * Array with all x,y pairs for each platform in the level
         */
        int[][] pos = { { 32, 729 }, { 32, 473 }, { 154, 729 }, { 154, 473 },
                { 500, 729 }, { 846, 729 }, { 532, 473 }, { 782, 249 },
                { 1000, 249 } };

        /*
         * Add each platform coordinate to the array list of platforms
         */
        for (int[] p : pos) {
            this.longPlatforms.add(new LongPlatform(p[0], p[1]));
        }
    }

    /**
     * Weird Graphics stuff, not fully understood. We'll need to address this
     * and figure out how to draw objects in more complicated ways later.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Draws background, player, and platform images to the game world.
     *
     * @param g
     *            the graphics object for drawing images
     */
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.background, 0, 0, this);
        g2d.drawImage(this.rocket.getImage(), this.rocket.getX(),
                this.rocket.getY(), this);

        if (this.player.getVis()) {
            g2d.drawImage(this.player.getImage(), this.player.getX(),
                    this.player.getY(), this);
        } else {
            g2d.drawImage(null, this.player.getX(), this.player.getY(), this);
        }

        for (Platform p : this.platforms) {
            g.drawImage(p.getImage(), p.getX(), p.getY(), this);
        }
        for (Spike s : this.spikes) {
            g.drawImage(s.getImage(), s.getX(), s.getY(), this);
        }
        for (LongPlatform l : this.longPlatforms) {
            g.drawImage(l.getImage(), l.getX(), l.getY(), this);
        }
    }

    /**
     * actionPerformed This method executes every DELAY seconds, moves Actors
     * and repaints graphics.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        if (this.player.getDead()) {
            this.timer.stop();
            int reply = JOptionPane.showConfirmDialog(null, "CONTINUE?",
                    "GAME OVER", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                this.reset();
            } else {
                System.exit(0);
            }
        }

        else {
            this.rocket.move();
            this.checkOutside(this.rocket);
            if (this.rocket.getDead() == true) {
                this.wonLevel();
            }
            this.player.move(this.flip, this.theta); //moves player an "infinitesimal" amount
            this.checkOutside(this.player);
            this.checkCollisions();
            //System.out.println(player.getHasTraction());
            this.repaint(); //updates graphics
        }
    }

    public void reset() {
        this.player.reset();
        this.rocket.reset();
        this.theta = 0;
        this.flip = 0;
        this.timer.start();
    }

    /**
     * WheelAdapter listens for the scroll-wheel being scrolled. Each click that
     * the wheel is scrolled will change theta by 1 degree.
     *
     * @author plank
     */
    private class WheelAdapter implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getWheelRotation() < 0 && Board.this.theta > -90) { // if wheel is scrolled towards user, increments theta.
                Board.this.theta = Board.this.theta - 2;
            } else if (Board.this.theta < 90) { // if wheel is scrolled away from user, decrements theta.
                Board.this.theta = Board.this.theta + 2;
            }
            System.out.println(Board.this.theta); // used for debugging to display current theta.

        }
    }

    /**
     * WheelClick used to listen for clicking the mouse wheel. When mouse wheel
     * is clicked, theta is reset to zero.
     *
     * @author plank
     */
    private class WheelClick implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        } // other mouse events remain unused, currently.

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 2) { // checks if mouse event is the scroll wheel click button
                Board.this.theta = 0;
            } else if (e.getButton() == 3) {
                Board.this.flip();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }

    /**
     * listens for keys being pressed
     *
     * @author plank
     */
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            Board.this.player.keyReleased(e, Board.this.flip, Board.this.theta); // passes release event to player class along with flip and theta
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_F) { // if key pressed is f, flips gravity.
                Board.this.flip();
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                Board.this.theta = 0;
            } else {
                Board.this.player.keyPressed(e, Board.this.flip,
                        Board.this.theta); // otherwise, passes event to player class along with flip and theta
            }
        }
    }

    /**
     * Check if the player is colliding with an object on any of its sides, and
     * updates the player's traction accordingly.
     *
     * @updates this.player
     * @requires this method requires that you know what the fuck you're doing
     *           because its complicated af
     * @ensures basically nothing at this point, check back later when we figure
     *          out how to make Elon Musk stop glitching through walls and
     *          sticking his head to the ceiling.
     */
    private void checkCollisions() {
        Rectangle rPlay = this.player.getBounds();

        boolean flag = true;

        for (Spike spike : this.spikes) {
            Rectangle rSpike = spike.getBounds();

            if (rSpike.intersects(rPlay)) { //checks if Musk collided with a spike
                this.player.setDead(true); //sets dead to true
                return; //exits collision check immediately, game will end on next timer click
            }
        }

        Rectangle rectRock = this.rocket.getBounds();
        if (rectRock.intersects(rPlay)) {
            this.rocket.ignition();
            this.player.setVis(false);
        }

        for (LongPlatform lplatform : this.longPlatforms) {
            Rectangle rLPlat = lplatform.getBounds();

            flag = this.checkRectangle(rLPlat, flag);
        }
        for (Platform platform : this.platforms) {
            Rectangle rPlat = platform.getBounds();

            flag = this.checkRectangle(rPlat, flag);

        }

    }

    public boolean checkRectangle(Rectangle rect, boolean flag) {
        Rectangle rPlayFeet = this.player.getFeet();
        Rectangle rPlayLeft = this.player.getLeftSide();
        Rectangle rPlayRight = this.player.getRightSide();
        Rectangle rPlayHead = this.player.getHead();
        if (rect.intersects(rPlayFeet)) {
            this.player.hitBlockVert();
            //System.out.println("hit feet");
            if (this.flip == 0 && Math.abs(this.theta) < 90) {
                this.player.setTraction(true);
                flag = false;
            }
            /*
             * Fixes the quicksand platform bug
             */
            if (this.player.y > rect.y - this.player.height + 1) {
                this.player.y = rect.y - this.player.height + 1;
            }
        }
        if (rect.intersects(rPlayLeft)) {
            this.player.hitBlockHorz();
            //System.out.println("hit left");
            if (this.theta < 0) {
                this.player.setTraction(true);
                flag = false;
            }
            if (this.player.x < rect.x + rect.width) {
                this.player.x = rect.x + rect.width;
                //System.out.println("test left");
            }
        }
        if (rect.intersects(rPlayRight)) {
            this.player.hitBlockHorz();
            //System.out.println("hit right");
            if (this.theta > 0) {
                this.player.setTraction(true);
                flag = false;
            }
            if (this.player.x > rect.x - this.player.width + 1) {
                this.player.x = rect.x - this.player.width + 1;
                //System.out.println("test right");
            }
        }
        if (rect.intersects(rPlayHead)) {
            this.player.hitBlockVert();
            //System.out.println("hit head");
            if (this.flip == 1 && Math.abs(this.theta) < 90) {
                this.player.setTraction(true);
                flag = false;
            }
            if (this.player.y < rect.y + rect.height - 1) {
                this.player.y = rect.y + rect.height - 1;
            }
        }
        if (flag) { // if nothing else has set traction to true, set it to false here
            this.player.setTraction(false);
        }
        return flag;
    }

    private void checkOutside(Actor thing) {
        int x = (int) thing.x;
        int y = (int) thing.y;

        if (x < -192 || x > 1628 || y > 825 || y < -250) {
            thing.setDead(true);
        }

    }

    /**
     * Changes the value of flip to the opposite of its current value,
     * representing a switch in gravitational direction.
     *
     * @updates this.flip
     * @ensures if #this.flip = 0 then this.flip = 1 else this.flip = 0
     */
    private void flip() {
        if (this.flip == 0) {
            this.flip++;
        } else {
            this.flip--;
        }
    }

    private void wonLevel() {
        this.timer.stop();
        int reply = JOptionPane.showConfirmDialog(null, "Play Again?",
                "WINNER!!!!", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            this.reset();
        } else {
            System.exit(0);
        }
    }

}