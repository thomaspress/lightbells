import javax.sound.sampled.*;

import javax.sound.midi.*;
import java.io.*;


public class AudioPlayer implements Runnable {

    private String link;
    private boolean checker = true;

    AudioPlayer(String s) {
        this.link = s;
        initMidi();
    }

    private void initMidi() {
        MidiDevice.Info[] md = MidiSystem.getMidiDeviceInfo();
        MidiDevice.Info deviceName = null;
        MidiDevice device = null;
        for (MidiDevice.Info m : md) {
            System.out.println(m);
            if (m.getName().equals("Bus 1")) {
                deviceName = m;
                break;
            }
        }
        try {
            device = MidiSystem.getMidiDevice(deviceName);

            if (!(device.isOpen())) {
                try {
                    device.open();
                } catch (MidiUnavailableException e) {
                    System.out.println("Midi in use by another application");
                }
            } else {
                device.close();
                System.out.println("closing midi device");
                return;
            }
            System.out.println("Successfully loaded Bus 1");

            Transmitter inTrans = device.getTransmitter();

            Receiver midiReceiver = new MyReceiver();
            inTrans.setReceiver(midiReceiver);

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }

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

