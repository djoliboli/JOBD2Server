package com.opel;


import Config.config;
import DatabaseHandler.DatabaseHandler;
import Exeption.Checker;
import Exeption.OBDUnableToConnectExeption;
import GUI.GUI;
import MQTT.MQTThandler;
import SerialCommunication.SerialPortSelector;
import SerialCommunication.StreamGen;
import Threads.DeleteError;
import Threads.ErrorReader;
import Threads.OBDreader;
import Threads.Reader;

import java.io.IOException;

import Commands.OBDreset;

public class Main {
    static Thread activeThread = null;


    public static void main(String[] args) {
    	GUI gui = new GUI();
    	
    	MQTThandler mainmenu = new MQTThandler();
        mainmenu.subscribe("Control");
    }


    public static void newMessage(String Message) throws InterruptedException {


        System.out.println("new Message");
        System.out.println(Message);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(activeThread == null);
        if (SerialPortSelector.AdapterConnected()) {
            SerialPortSelector.rightPort.closePort();
        }
        if (activeThread != null) {
          /*  if (Reader.t1 != null) {
                Reader.t1.stop();
                

                if(Checker.isDBConnected()) {
                    OBDreader.db.disconnect();
                    Checker.setDBConnected(false);
                }
            }*/
            Checker.setAdapterConnected(false);
            activeThread.stop();
            


            Thread.sleep(1000);

        }
        switch (Message) {
            case "Live":
                System.out.println("LIVE");
                Checker.LiveDataRun = true;
                

                activeThread = new Thread(new Reader());
                activeThread.start();
                System.out.println("Live started");
                break;
            case "Error":
                System.out.println("ERROR");
                activeThread = new Thread(new ErrorReader());
                activeThread.start();
                System.out.println("Error started");
                break;
            case "Delete":
                System.out.println("Delete");
                activeThread = new Thread(new DeleteError());
                System.out.println("Deleted");
                activeThread.start();
                break;
            case "Test":
            	System.out.println("TEST");
            	System.out.println(config.DBuser);
            	System.out.println(config.DBpw);
            	DatabaseHandler test = new DatabaseHandler();
            	test.connectToDB("W0L000051T2123456");
            	for (int c= 0; c<10; c++) {
            		test.insertData("kmh", Double.parseDouble(Integer.toString(c)));
            	}
            	break;
            	
            default:
                System.out.println("Keine Gueltige Anweisung");

        }

        System.out.println(Message);

       

    }


}

