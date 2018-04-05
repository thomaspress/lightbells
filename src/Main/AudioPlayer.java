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
        Clip clip = null;
        Mixer deviceMixer;
        Mixer.Info deviceInfo = null;

        Mixer.Info[] ad = AudioSystem.getMixerInfo();

        //        These lines print out available devices
        for (Mixer.Info m : ad) {
            System.out.println("Audio Device is " + m.getName() + " description is " + m.getDescription());

            //This is where we choose which output device to use and lock to this one"
            if (m.getName().equals("ALSA [default]")) {
                deviceInfo = m;
                break;
            } else deviceInfo = ad[0];
        }

        deviceMixer = AudioSystem.getMixer(deviceInfo);
        Line.Info[] linesSource = deviceMixer.getSourceLineInfo();


        try {
            clip = (Clip) deviceMixer.getLine(linesSource[1]);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }


        try {
//                File url = new File(link);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource("/" + link));

//                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
//                Clip clip = AudioSystem.getClip();

            clip.open(audioIn);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.stop();
            Thread.currentThread().interrupt();
            deviceMixer.close();


        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("uh oh did you type file name right?");
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("io error" + e);
        }
    }
}


