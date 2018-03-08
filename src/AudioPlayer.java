import javax.sound.sampled.*;

//import javax.sound.midi.*;
import java.io.*;


public class AudioPlayer implements Runnable{

    private String link;
    private boolean checker = true;

    AudioPlayer(String s){
        this.link = s;
    }

//    public void stopMe(){
//        this.checker = false;
//    }

    @Override
    public void run() {
        while (checker) {
            try {
                File url = new File(link);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000);
                clip.stop();


            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.out.println("uh oh did you type file name right?");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        checker = true;
    }


}

