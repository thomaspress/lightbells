package Main;

import javax.sound.midi.*;

public class MainApp {

    public static void main(String[] args) {
        MainApp m = new MainApp();
        m.runApp();
    }

    private void runApp(){
        initMidi();
    }


    private void initMidi() {
        MidiDevice.Info[] md = MidiSystem.getMidiDeviceInfo();
        MidiDevice.Info deviceName = null;

        for (MidiDevice.Info m : md) {
            System.out.println(m);
            if (m.getName().equals("Bus 1")) {
                deviceName = m;
                break;
            }
        }
        try {
            MidiDevice device = MidiSystem.getMidiDevice(deviceName);

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
            Receiver midiReceiver = new MIDIReceiver();
            inTrans.setReceiver(midiReceiver);
            MIDIReceiver mR = (MIDIReceiver)midiReceiver;

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }


}
