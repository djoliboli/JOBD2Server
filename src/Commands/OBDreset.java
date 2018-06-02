package Commands;

import Exeption.Checker;

public class OBDreset extends OBDcommand {

    public OBDreset() {
        super("AT Z");
    }

    @Override
    protected void calculateResult() {

    }
    @Override
     protected void fillBuffer(){
    	
    }
}
