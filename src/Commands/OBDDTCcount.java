package Commands;

import Exeption.Checker;

public class OBDDTCcount extends OBDcommand {
    protected int count = 0;
    boolean engineLampOn = false;
    public OBDDTCcount() {
        super("01 01");
    }

    @Override
    protected void calculateResult() {
        if (available&& Checker.isCarConnected()) {
            count = buffer.get(4);
            if (count > 128) {
                engineLampOn = true;
                count = count - 128;
            }
        }
    }

    @Override
    public String getResult(){
        if(available&&Checker.isCarConnected()) {
            return Integer.toString(count);
        }
        else if(!Checker.isCarConnected()) {
            return "Car not connected";
        }
        else{
            return "NODATA";
        }
    }

}
