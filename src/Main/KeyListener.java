package Main;

public class KeyListener implements Runnable {
    String s;

    KeyListener (){
        s = "";
    }

    @Override
    public void run() {
        s = Keyboard.readInput();
        if (s.equals("q")){
            Thread.interrupted();
        }
    }
}
