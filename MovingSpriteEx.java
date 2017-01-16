package example;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class MovingSpriteEx extends JFrame {

    public MovingSpriteEx() {

        this.initUI();
    }

    private void initUI() {

        MainMenu mm = new MainMenu(this);
        this.add(mm);

        this.setSize(1500, 825);
        this.setResizable(false);
        this.setTitle("Moving sprite");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

// f
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                MovingSpriteEx ex = new MovingSpriteEx();
                ex.setVisible(true);
            }
        });
    }
}