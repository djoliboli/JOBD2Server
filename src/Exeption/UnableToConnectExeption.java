package Exeption;

public class UnableToConnectExeption extends Exception {
    public UnableToConnectExeption(String target){
        super("Unable to Connect to " + target);
    }
}
