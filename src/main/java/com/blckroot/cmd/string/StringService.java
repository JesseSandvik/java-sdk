package com.blckroot.cmd.string;

public class StringService implements StringServiceContract {
    private final StringServiceUtility stringServiceUtility;

    public StringService() {
        this.stringServiceUtility = new StringServiceUtility();
    }

    @Override
    public String getShortestStringInArray(String[] strings) {
        return stringServiceUtility.getShortestStringInArray(strings);
    }

    @Override
    public String getLongestStringInArray(String[] strings) {
        return stringServiceUtility.getLongestStringInArray(strings);
    }
}
