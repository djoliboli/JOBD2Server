package Config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class config {
    static Properties props = new Properties();
    File configFile = new File(System.getProperty("user.dir") + "//Config//config.cfg");
    static config conf = new config();
    // Serial Settings
    public static String SerialPort = props.getProperty("SerialPortName");
    public static Integer Baudrate = Integer.parseInt(props.getProperty("baudrate"));
    public static Integer Databits = Integer.parseInt(props.getProperty("databits"));
    public static Integer Stopbit = Integer.parseInt(props.getProperty("stopbit"));

    //MQTT Settings
    public static String MQTTIP = props.getProperty("MQTTIP");
    public static String MQTTport = props.getProperty("MQTTport");
    public static Integer MQTTTimeout = Integer.parseInt(props.getProperty("MQTTTimeout"));

    public static String DBIP = props.getProperty("DBIP");
    public static String DBname = props.getProperty("DBname");
    public static String DBuser = props.getProperty("DBuser");
    public static String DBpw = props.getProperty("DBpw");

    public config() {
        try {
            readConfig();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    public void readConfig() throws IOException {
        try {
            FileReader reader = new FileReader(configFile);
            props.load(reader);
            reader.close();
            if (props.isEmpty()) {
                createDefaultConfig();
            }
        } catch (IOException e) {
            try {
                configFile.createNewFile();
                createDefaultConfig();
            } catch (Exception e1) {
                File folder = new File(System.getProperty("user.dir") + "//Config");
                folder.mkdir();
                createDefaultConfig();
            }
        }
    }

    protected void createDefaultConfig() throws IOException {
        //Serial
        props.setProperty("baudrate", "115200");
        props.setProperty("COMport", "1");
        props.setProperty("SerialPortName", "USB-to-Serial Port");
        props.setProperty("databits", "8");
        props.setProperty("stopbit", "1");
        //MQTT
        props.setProperty("MQTTIP", "10.3.141.1");
        props.setProperty("MQTTport", "1883");
        props.setProperty("MQTTTimeout","1000");
        //DB
        props.setProperty("DBIP","localhost");
        props.setProperty("DBname","obddb");
        props.setProperty("DBuser","servclient");
        props.setProperty("DBpw","servclient1");
        FileWriter writer = new FileWriter(configFile);
        props.store(writer, null);
        writer.flush();
        writer.close();
    }


}





