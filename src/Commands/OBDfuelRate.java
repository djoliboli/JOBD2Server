package Commands;

import Exeption.Checker;

public class OBDfuelRate extends OBDcommand {
    float fuelRate=0;
    public OBDfuelRate() {
        super("01 5E");
    }

    @Override
    protected void calculateResult() {
        if (available&& Checker.isCarConnected()) {
            fuelRate = (256 * buffer.get(4) + buffer.get(5)) / 20;
        }
    }
    @Override
    public String getResult(){
        if(available&&Checker.isCarConnected()){
            return Float.toString(fuelRate);
        }
        else if(!Checker.isCarConnected()) {
            return "Car not connected";
        }
        else {
            return "NODATA";
        }
    }
}
