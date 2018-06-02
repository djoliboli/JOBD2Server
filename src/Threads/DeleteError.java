package Threads;

import Commands.OBDdelete;
import Exeption.Checker;
import SerialCommunication.StreamGen;

import java.io.InputStream;
import java.io.OutputStream;

public class DeleteError implements Runnable {

    private static InputStream in;
    private static OutputStream out;
    private static OBDdelete deleter = new OBDdelete();

    public DeleteError() {
    }

    @Override
    public void run() {
        try {
            StreamGen gen = new StreamGen();
            out = gen.out();
            in = gen.in();
            Checker.setAdapterConnected(true);
            deleter.run(in,out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
