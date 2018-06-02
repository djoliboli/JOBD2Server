package Commands;

import Exeption.Checker;

public class OBDtemperature extends OBDcommand {
    int temperature=0;
    public OBDtemperature(String command) {
        super(command);
    }

    @Override
    protected void calculateResult() {
        if (available&& Checker.isCarConnected()) {
            temperature = buffer.get(4) - 40;
        }
    }

    @Override
    public String getResult(){
        if(available&&Checker.isCarConnected()) {
            return Integer.toString(temperature);
        }
        else if(!Checker.isCarConnected()) {
            return "Car not connected";
        }
         else {
            return "NODATA";
        }
    }

}
