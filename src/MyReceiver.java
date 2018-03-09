import javax.sound.midi.*;

public class MyReceiver implements Receiver {
    private Receiver rcvr;

    public MyReceiver() {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            this.rcvr = synth.getReceiver();
        } catch (MidiUnavailableException mue) {
            mue.printStackTrace();
        }
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        byte[] b = message.getMessage();
        if ((b[0] != (byte) 254) && (b[2] > 64)) {
//            String output = "";
//            for (byte bt: b){
//                output += "|" + bt;
//            }
//            System.out.println(output);
            switch (b[1]) {
                case 67:
                    System.out.println("G3 pressed");
                    Thread t1 = new Thread(new AudioPlayer("clock.wav"));
                    t1.start();
                    break;
                case 68:
                    System.out.println("G#3 pressed");
                    Thread t2 = new Thread(new AudioPlayer("bell.wav"));
                    t2.start();
                    break;
            }
        }
        rcvr.send(message, timeStamp);
    }

    @Override
    public void close() {
        rcvr.close();
    }

}