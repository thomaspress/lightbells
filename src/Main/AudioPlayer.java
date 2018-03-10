package Main;

import javax.sound.sampled.*;
import java.io.*;


public class AudioPlayer implements Runnable {

    private String link;

    AudioPlayer(String s) {
        this.link = s;
    }

    @Override
    public void run() {

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
    }


