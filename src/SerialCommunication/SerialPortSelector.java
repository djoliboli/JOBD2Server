package SerialCommunication;

import Config.config;
import Exeption.Checker;
import Exeption.OBDUnableToConnectExeption;
import com.fazecast.jSerialComm.SerialPort;

import java.util.regex.Pattern;

import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED;
import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED;

public class SerialPortSelector {

    private static Pattern portname = Pattern.compile(".*" + config.SerialPort + ".*"); //aus conf prop
    public static SerialPort rightPort = null;

    public  SerialPort findPort() throws OBDUnableToConnectExeption {

        SerialPort[] liste = SerialPort.getCommPorts();
        for (SerialPort port : liste) {

            if (portname.matcher(port.getDescriptivePortName()).matches()) {
                rightPort = port;
                System.out.println("gefunden");
                
                break;
            } else {
                System.out.println(port.getDescriptivePortName());
                rightPort=null;
            }

        }

        if (rightPort != null) {
            rightPort.closePort();
            rightPort.setBaudRate(config.Baudrate);
            rightPort.setNumStopBits(config.Stopbit);
            rightPort.setNumDataBits(config.Databits);
            rightPort.setFlowControl(FLOW_CONTROL_XONXOFF_IN_ENABLED);
            rightPort.setFlowControl(FLOW_CONTROL_XONXOFF_OUT_ENABLED);
            rightPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
            rightPort.openPort();
            return rightPort;
        } else {
            throw new OBDUnableToConnectExeption();        }
    }

    public static void close() {
        rightPort.closePort();


    }


    public static boolean AdapterConnected() {
        if(rightPort!=null){
            return rightPort.isOpen();
        }
        else{
            return false;
        }
    }


}

