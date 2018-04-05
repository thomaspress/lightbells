package Main;

import javax.sound.midi.*;

public class MainApp {

    public static void main(String[] args) {
        MainApp m = new MainApp();
        m.runApp();
        Thread thread = new Thread(new KeyListener());
        thread.start();
        while (!thread.isInterrupted()){
//            System.out.println("running fine");
            continue;
        }
        System.out.println("quitting");
        return;
    }

    private void runApp(){
        initMidi();
    }


    private void initMidi() {
        MidiDevice.Info[] md = MidiSystem.getMidiDeviceInfo();
        MidiDevice.Info deviceName = null;

        for (MidiDevice.Info m : md) {
            System.out.println(m.getDescription());
            if (m.getDescription().equals("Teensy MIDI, USB MIDI, Teensy MIDI")) {
                deviceName = m;
                break;
            }
        }
        try {
            MidiDevice device = MidiSystem.getMidiDevice(deviceName);

            if (!(device.isOpen())) {
                try {
                    device.open();
                    System.out.println("Opening midi in device");
                } catch (MidiUnavailableException e) {
                    System.out.println("Midi in use by another application");
                }
            } else {
                device.close();
                System.out.println("closing midi device");
                return;
            }
            System.out.println("Successfully loaded Midi Input Device");

            Transmitter inTrans = device.getTransmitter();
            Receiver midiReceiver = new MIDIReceiver();
            inTrans.setReceiver(midiReceiver);
            MIDIReceiver mR = (MIDIReceiver)midiReceiver;

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private String checkUserInput() {
        String s = Keyboard.readInput();
        if (s.equals("q")){
            return "q";
        } return null;
    }


}
