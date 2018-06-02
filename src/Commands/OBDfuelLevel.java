package Commands;

import Exeption.Checker;

public class OBDfuelLevel extends OBDcommand {
    float percentage=0;
    public OBDfuelLevel() {
        super("01 2F");
    }

    @Override
    protected void calculateResult() {
        if(available&& Checker.isCarConnected()) {
            percentage = buffer.get(4) * 100 / 255;
        }
    }

    @Override
    public String getResult(){
        if (available&&Checker.isCarConnected()) {
            return Float.toString(percentage);
        }
        else if(!Checker.isCarConnected()){
            return "Car not connected";

        }
        else{
            return "NODATA";
        }
    }
}
