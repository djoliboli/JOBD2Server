package Exeption;

import GUI.GUI;
import SerialCommunication.SerialPortSelector;

public class Checker {

    public static boolean isAdapterConnected() {
        return AdapterConnected;
    }

    public static void setAdapterConnected(boolean adapterConnected) {
        AdapterConnected = adapterConnected;
        if(adapterConnected){
            GUI.updateAdapterChecker(adapterConnected);
        }
        if(!SerialPortSelector.AdapterConnected()){
            GUI.updateAdapterChecker(false);
        }

    }

    public static boolean isCarConnected() {
        return CarConnected;
    }

    public static void setCarConnected(boolean carConnected) {
        CarConnected = carConnected;
        GUI.updateCarChecker(carConnected);
    }

    public static boolean isMQTTConnected() {
        return MQTTConnected;
    }

    public static void setMQTTConnected(boolean MQTTConnected) {
        Checker.MQTTConnected = MQTTConnected;
        if(MQTTConnected) {
            GUI.updateMQTTChecker(MQTTConnected);
        }
    }

    public static boolean isDBConnected() {
        return DBConnected;
    }

    public static void setDBConnected(boolean DBConnected) {
        Checker.DBConnected = DBConnected;
        GUI.updateDBChecker(DBConnected);
    }

    private static boolean AdapterConnected = false;
    private static boolean CarConnected = false;
    private static boolean MQTTConnected = false;
    private static boolean DBConnected = false;
    public static boolean LiveDataRun = false;

}
