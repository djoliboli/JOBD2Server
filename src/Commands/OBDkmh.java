package Commands;

import Exeption.Checker;

public class OBDkmh extends OBDcommand
{
    int kmh = -1;

    public OBDkmh() {
        super("01 0D");
    }

    @Override
    protected void calculateResult() {
        if(available&&Checker.isCarConnected()) {
            kmh = buffer.get(4);
        }
    }
    @Override
    public String getResult(){
        if(available&&Checker.isCarConnected()) {
            return Integer.toString(kmh);
        }
        else if(!Checker.isCarConnected()) {
            return "Car not connected";
        }
        else {
            return "NODATA";
        }
    }
}
