package Commands;

import Exeption.Checker;

public class OBDthrottlePosition extends OBDcommand {
    float percentage = 0;

    public OBDthrottlePosition() {
        super("01 11");
    }

    @Override
    protected void calculateResult() {
        if (available && Checker.isCarConnected()) {
            percentage = buffer.get(4) * 100 / 255;
        }
    }

    @Override
    public String getResult() {
        if (available && Checker.isCarConnected()) {
            return Float.toString(percentage);
        } else if (!Checker.isCarConnected()) {
            return "Car not Connected";
        } else {

            return "NODATA";
        }
    }
}
