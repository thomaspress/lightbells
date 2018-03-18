package Main;

import javax.sound.midi.*;

public class MIDIReceiver implements Receiver {
    private Receiver rcvr;
    private DMXEngine lights;

    public MIDIReceiver() {

        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            this.rcvr = synth.getReceiver();
        } catch (MidiUnavailableException mue) {
            mue.printStackTrace();
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
//            String output = "";
//            for (byte bt : b) {
//                output += "|" + bt;
//            }
//            System.out.println(output);
            switch (b[1]) {
                case 67:
                    System.out.println("G3 pressed");
                    Thread t1 = new Thread(new AudioPlayer("clock.wav"));
                    t1.start();
                    lights.controlChannel(1);
                    break;
                case 68:
                    System.out.println("G#3 pressed");
                    Thread t2 = new Thread(new AudioPlayer("bell.wav"));
                    t2.start();
                    lights.controlChannel(2);
                    break;
                case 69:
                    System.out.println("A3 pressed");
                    Thread t3 = new Thread(new AudioPlayer("clock.wav"));
                    t3.start();
                    lights.controlChannel(3);
                    break;
                case 70:
                    System.out.println("A#3 pressed");
                    Thread t4 = new Thread(new AudioPlayer("bell.wav"));
                    t4.start();
                    lights.controlChannel(4);
                    break;
                case 71:
                    System.out.println("B3 pressed");
                    Thread t5 = new Thread(new AudioPlayer("clock.wav"));
                    t5.start();
                    lights.controlChannel(5);
                    break;
                case 72:
                    System.out.println("C4 pressed");
                    Thread t6 = new Thread(new AudioPlayer("bell.wav"));
                    t6.start();
                    lights.controlChannel(6);
                    break;
                case 73:
                    System.out.println("C#4 pressed");
                    Thread t7 = new Thread(new AudioPlayer("clock.wav"));
                    t7.start();
                    lights.controlChannel(7);
                    break;
                case 74:
                    System.out.println("D4 pressed");
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