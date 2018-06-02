package SerialCommunication;

import Exeption.OBDUnableToConnectExeption;
import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public class StreamGen {

    SerialPort port;

    public StreamGen() throws OBDUnableToConnectExeption {
        SerialPortSelector selector = new SerialPortSelector();
    this.port = selector.findPort();
    }

    public OutputStream out(){
        return port.getOutputStream();
    }
    public InputStream in(){
        return port.getInputStream();

    }
}
