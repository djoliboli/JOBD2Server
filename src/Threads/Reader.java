package Threads;

import Exeption.Checker;
import SerialCommunication.SerialPortSelector;

public class Reader implements Runnable {

    public static Thread t1;

    static int x=0;
    public static void runReader() throws InterruptedException {

        t1 = new Thread(new OBDreader());
        Thread.sleep(1000);
        t1.start();
        while ((!Checker.isAdapterConnected())) {
            System.out.println("nicht verbunden main");
            Thread.sleep(2000);

        }
        while (SerialPortSelector.AdapterConnected()) {
            System.out.println("verbunden2 ");

            Thread.sleep(1000);


        }
        if (SerialPortSelector.AdapterConnected()) {
            SerialPortSelector.rightPort.closePort();
        }
        t1.stop();

        Checker.setAdapterConnected(false);
        

      

    }

    @Override
    public void run() {
        try {
            runReader();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
