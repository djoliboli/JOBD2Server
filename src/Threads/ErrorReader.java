package Threads;

import Commands.OBDDTCcount;
import Commands.OBDreadTroubleCode;
import Exeption.Checker;
import Exeption.OBDUnableToConnectExeption;
import GSON.ErrorData;
import MQTT.MQTThandler;
import SerialCommunication.StreamGen;
import com.google.gson.Gson;
import com.opel.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorReader implements Runnable {
    private static Gson gson = new Gson();
    private static OBDreadTroubleCode dtc = new OBDreadTroubleCode();
    private MQTThandler errorMQTT;
    private static OBDDTCcount count = new OBDDTCcount();
    private static ErrorData error = new ErrorData();
    private static InputStream in;
    private static OutputStream out;
    private static List<String> codes;


    public ErrorReader() {
        this.errorMQTT = new MQTThandler();

        codes = new ArrayList<>();

    }


    @Override
    public void run() {
        try {
            StreamGen gen = new StreamGen();
            out = gen.out();
            in = gen.in();
            Checker.setAdapterConnected(true);
            count.run(in, out);
            dtc.run(in, out);
            if(Checker.isMQTTConnected()){
            	if(count.getResult().equals("0")){
            		sendMessage("0","");
            		System.out.println("zero dtc");
            	}
            	else {
                sendMessage(count.getResult(),dtc.getCalculatedResult());}
                System.out.println("YES");
            }
            System.out.println(Checker.isMQTTConnected());

        } catch (OBDUnableToConnectExeption e) {
            System.out.println("adapter nicht verbunden");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }

    private void sendMessage(String anzahl, String errorString) {


        /*if (!Checker.isCarConnected()){
            errorMQTT.sendMessage("Fehler", "Car not connected");
            System.out.println("Error1");
            return;
        }
        if (!Checker.isAdapterConnected()){
            errorMQTT.sendMessage("Fehler", "Adapter not connected");
            System.out.println("Error2");
            return;

        }*/
        //Integer.parseInt(anzahl);
        int x = 0;
        int y = 5;
        while (y < errorString.length()) {
            codes.add(errorString.substring(x, y));
            x += 4;
            y += 4;
        }
        error.fehlerArray = codes.toArray(new String[codes.size()]);
        errorMQTT.sendMessage("Fehler", gson.toJson(error, ErrorData.class));
        System.out.println("send");
    }
}
