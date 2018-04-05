package Main;

import javax.sound.midi.*;

public class MIDIReceiver implements Receiver {
    private Receiver rcvr;
    private DMXEngine lights;

    public MIDIReceiver() {

        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            this.rcvr = synth.getReceiver();
        } catch (MidiUnavailableException mue) {
            System.out.println("Error opening midi receiver " + mue);
        }

        try {
            Thread t = new Thread(lights = new DMXEngine());
            t.start();
        } catch (Exception e) {
            System.out.println("Error loading DMXEngine" + e);
        }

    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        byte[] b = message.getMessage();
        if ((b[0] != (byte) 254) && (b[2] > 64)) {
            String output = "";
            for (byte bt : b) {
                output += "|" + bt;
            }
            System.out.println(output);
            switch (b[1]) {
                case 1:
                    System.out.println("C#2 pressed");
                    Thread t1 = new Thread(new AudioPlayer("clock.wav"));
                    t1.start();
                    lights.controlChannel(1);
                    break;
                case 2:
                    System.out.println("D2 pressed");
                    Thread t2 = new Thread(new AudioPlayer("bell.wav"));
                    t2.start();
                    lights.controlChannel(2);
                    break;
                case 3:
                    System.out.println("D#2 pressed");
                    Thread t3 = new Thread(new AudioPlayer("clock.wav"));
                    t3.start();
                    lights.controlChannel(3);
                    break;
                case 4:
                    System.out.println("E2 pressed");
                    Thread t4 = new Thread(new AudioPlayer("bell.wav"));
                    t4.start();
                    lights.controlChannel(4);
                    break;
                case 5:
                    System.out.println("F2 pressed");
                    Thread t5 = new Thread(new AudioPlayer("clock.wav"));
                    t5.start();
                    lights.controlChannel(5);
                    break;
                case 6:
                    System.out.println("F#2 pressed");
                    Thread t6 = new Thread(new AudioPlayer("bell.wav"));
                    t6.start();
                    lights.controlChannel(6);
                    break;
                case 7:
                    System.out.println("G2 pressed");
                    Thread t7 = new Thread(new AudioPlayer("clock.wav"));
                    t7.start();
                    lights.controlChannel(7);
                    break;
                case 8:
                    System.out.println("G#2 pressed");
                    Thread t8 = new Thread(new AudioPlayer("bell.wav"));
                    t8.start();
                    lights.controlChannel(8);
            }
        }
        rcvr.send(message, timeStamp);
    }

    @Override
    public void close() {
        rcvr.close();
    }

}