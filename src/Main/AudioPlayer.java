package Main;

import javax.sound.sampled.*;
import java.io.*;


public class AudioPlayer implements Runnable {
    private Mixer deviceMixer;
    private String link;
    Mixer.Info deviceInfo = null;
    private Line thisLine;
    Clip clip;


    AudioPlayer(String s) {
        Mixer.Info[] ad = AudioSystem.getMixerInfo();

        for (Mixer.Info m : ad) {
            System.out.println("Audio Device " + m.getName());

            //This is where we choose which output device to use and lock to this one"
            if (m.getName().equals("Built-in Output")) {
                deviceInfo = m;
                break;
            }
        }

        deviceMixer = AudioSystem.getMixer(deviceInfo);

        Line.Info[] linesSource = deviceMixer.getSourceLineInfo();

//        for (Line.Info l: linesSource){
//            try {
//                thisLine = deviceMixer.getLine(l);
//                System.out.println(thisLine.toString());
//                thisLine.open();
//            } catch (LineUnavailableException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            clip = (Clip)deviceMixer.getLine(linesSource[1]);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        this.link = s;
    }

    @Override
    public void run() {

            try {
                File url = new File(link);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource("/" + link));

//                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
//                Clip clip = AudioSystem.getClip();

                clip.open(audioIn);
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000);
                clip.stop();

            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.out.println("uh oh did you type file name right?");
            } catch (IOException e) {
                System.out.println("io error" + e);
            } catch (LineUnavailableException e) {
                e.printStackTrace();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


