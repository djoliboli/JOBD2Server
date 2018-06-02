package Commands;

import Exeption.Checker;

import java.io.IOException;
import java.io.InputStream;

public class OBDreadTroubleCode extends OBDcommand{

    protected final static char[] dtcLetters = {'P', 'C', 'B', 'U'};
    protected final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    protected StringBuilder codes = null;


    public OBDreadTroubleCode() {
        super("03");
        codes = new StringBuilder();
    }




    @Override
    protected void fillBuffer() {
    }

    @Override
    protected void calculateResult() {
        final String result = getResult();
        codes = new StringBuilder();


        String workingData;

        int startIndex = 0;//Header size.
        String cof = result.substring(2);
        String canOneFrame = cof.replaceAll("[\r\n]", "");
        System.out.println("Can One Frame "+canOneFrame);
        if (UNABLE_TO_CONNECT.matcher(canOneFrame).matches()|UNABLE_TO_CONNECT2.matcher(canOneFrame).matches()) {
            Checker.setCarConnected(false);

            return;
        }
        int canOneFrameLength = canOneFrame.length();
        if (canOneFrameLength <= 16 && canOneFrameLength % 4 == 0) {//CAN(ISO-15765) protocol one frame.
            workingData = canOneFrame;//43yy{codes}
            startIndex = 4;//Header is 43yy, yy showing the number of data items.
        } else if (result.contains(":")) {//CAN(ISO-15765) protocol two and more frames.
            workingData = result.replaceAll("[\r\n].:", "");//xxx43yy{codes}
            startIndex = 7;//Header is xxx43yy, xxx is bytes of information to follow, yy showing the number of data items.
        } else {//ISO9141-2, KWP2000 Fast and KWP2000 5Kbps (ISO15031) protocols.
            workingData = result.replaceAll("43|[\r\n]43|[\r\n]", "");
        }
        for (int begin = startIndex; begin < workingData.length(); begin += 4) {
            String dtc = "";
            byte b1 = hexStringToByteArray(workingData.charAt(begin));
            int ch1 = ((b1 & 0xC0) >> 6);
            int ch2 = ((b1 & 0x30) >> 4);
            dtc += dtcLetters[ch1];
            dtc += hexArray[ch2];
            dtc += workingData.substring(begin+1, begin + 4);
            if (dtc.equals("P0000")) {
                return;
            }
            codes.append(dtc);
            codes.append('\n');
        }
    }

    private byte hexStringToByteArray(char s) {
        return (byte) ((Character.digit(s, 16) << 4));
    }


    public String formatResult() {
        return codes.toString();
    }


    public String getCalculatedResult() {
        return String.valueOf(codes);
    }



    @Override
    protected void readRawData(InputStream in) throws IOException {
        byte b;
        StringBuilder res = new StringBuilder();

        // read until '>' arrives OR end of stream reached (and skip ' ')
        char c;
        while (true) {
            b = (byte) in.read();
            if (b == -1) // -1 if the end of the stream is reached
            {
                break;
            }
            c = (char) b;
            if (c == '>') // read until '>' arrives
            {
                break;
            }
            if (c != ' ') // skip ' '
            {
                res.append(c);
            }
        }

        rawData = res.toString().trim();

    }





}
