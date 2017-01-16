package example;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements ActionListener {
    /**
     * The background image.
     */
    private Image background;
    private JButton startButton;
    private MovingSpriteEx mainFrame;

    public MainMenu(MovingSpriteEx frame) {

        this.initMenu(frame);
    }

    public void initMenu(MovingSpriteEx frame) {
        this.mainFrame = frame;
        ImageIcon ii = new ImageIcon("mainMenu.png");
        this.background = ii.getImage();
        JPanel buttonPanel = new JPanel();
        this.startButton = new JButton();
        this.startButton.setText("Start");
        this.startButton.setPreferredSize(new Dimension(120, 80));
        this.startButton.addActionListener(this);

        buttonPanel.add(this.startButton);
        this.add(buttonPanel);
        this.setVisible(true);

    }

    /**
     * Weird Graphics stuff, not fully understood. We'll need to address this
     * and figure out how to draw objects in more complicated ways later.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.background, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == this.startButton) {

            this.mainFrame.remove(this);
            this.mainFrame.add(new Board());
            this.mainFrame.validate();
            this.repaint();

        }

    }

}
