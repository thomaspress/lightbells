
import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {

    public Window(){
        setTitle("Audio Player");
        setBounds(0,0,20,20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel frameContent = new Panel();
        frameContent.setPreferredSize(new Dimension(20,20));
        Container visibleArea = getContentPane();
        visibleArea.add(frameContent);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window frame = new Window();
                frame.setVisible(true);
            }
        });

//        new Panel();
//        Thread t = new Thread(new AudioPlayer());
//        t.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Thread b = new Thread(new AudioPlayer());
//        b.start();

    }


}
