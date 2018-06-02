package Commands;

import Exeption.CarUnableToConnectExeption;
import Exeption.DataInvalidExeption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class OBDpersistentCommand extends OBDcommand {

    private static Map<String, String> knownValues = new HashMap<>();
    private static Map<String, ArrayList<Integer>> knownBuffers = new HashMap<>();

    /**
     * <p>Constructor for PersistentCommand.</p>
     *
     * @param command a {@link java.lang.String} object.
     */
    public OBDpersistentCommand(String command) {
        super(command);
    }




    /**
     * <p>reset.</p>
     */
    public static void reset() {
        knownValues = new HashMap<>();
        knownBuffers = new HashMap<>();
    }

    /**
     * <p>knows.</p>
     *
     * @param cmd a {@link java.lang.Class} object.
     * @return a boolean.
     */
    public static boolean knows(Class cmd) {
        String key = cmd.getSimpleName();
        return knownValues.containsKey(key);
    }

    /** {@inheritDoc} */
    @Override
    protected void readRawData(InputStream in) throws IOException {
        super.readRawData(in);
        String key = getClass().getSimpleName();
        knownValues.put(key, rawData);
        knownBuffers.put(key, new ArrayList<>(buffer));
    }

    /** {@inheritDoc} */
    @Override
    public void run(InputStream in, OutputStream out) throws IOException, InterruptedException {
        String key = getClass().getSimpleName();
        if (knownValues.containsKey(key)) {
            rawData = knownValues.get(key);
            buffer = knownBuffers.get(key);

        } else {
            super.run(in, out);
        }
    }
}