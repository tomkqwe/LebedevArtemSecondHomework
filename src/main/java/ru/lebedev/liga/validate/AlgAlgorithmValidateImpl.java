package ru.lebedev.liga.validate;

public class AlgAlgorithmValidateImpl implements Validate {
    private final static String MOON = "moon";
    private final static String ACTUAL = "actual";
    private final static String REGRESS = "regress";

    private final static int FIFTH_ELEMENT = 4;
    private final static int SIXTH_ELEMENT = 5;

    @Override
    public boolean checkCommand(String[] command) {
        boolean _alg = command[FIFTH_ELEMENT].equals(_ALG);
        boolean chooseAlgo = chooseAlgo(command[SIXTH_ELEMENT]);
        return _alg && chooseAlgo;
    }

    private boolean chooseAlgo(String s) {
        return s.equals(MOON) || s.equals(ACTUAL) || s.equals(REGRESS);
    }
}
