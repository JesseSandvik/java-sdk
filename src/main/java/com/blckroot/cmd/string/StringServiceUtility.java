package com.blckroot.cmd.string;

class StringServiceUtility implements StringServiceContract {
    public String getShortestStringInArray(String[] strings) {
        String shortestString = null;

        for (String string : strings) {
            if (shortestString == null) {
                shortestString = string;
            } else {
                if (string.length() < shortestString.length()) {
                    shortestString = string;
                }
            }
        }
        return shortestString;
    }

    public String getLongestStringInArray(String[] strings) {
        String longestString = null;

        for (String string : strings) {
            if (longestString == null) {
                longestString = string;
            } else {
                if (string.length() > longestString.length()) {
                    longestString = string;
                }
            }
        }
        return longestString;
    }
}
