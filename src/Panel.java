import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

public class Panel extends JPanel implements KeyListener {

    public Panel(){
        setBackground(Color.white);
        this.addKeyListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
        // Sets focus outside of actionPerformed so key presses work without pressing the button
        requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        switch(key){
            case 49:
                AudioPlayer p = new AudioPlayer("clock.wav");
//                p.stopMe();
                Thread a = new Thread(p,"a");
                a.start();

                break;
            case 50:

                Thread b = new Thread(new AudioPlayer("clock.wav"));
                b.start();

                break;
            case 51:
                Thread c = new Thread(new AudioPlayer("clock.wav"));
                c.start();
                break;
            case 52:
                Thread d = new Thread(new AudioPlayer("clock.wav"));
                d.start();
                break;
            case 53:
                Thread f = new Thread(new AudioPlayer("HIP HOP SNIPPET_3.wav"));
                f.start();
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
}
