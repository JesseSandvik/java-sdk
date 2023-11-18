package com.blckroot;

import static java.lang.System.Logger.Level.*;

public class Main {
    public static void main(String[] args) {
        System.Logger logger = System.getLogger(Main.class.getName());
        logger.log(ERROR, "Hello, World!");
    }
}