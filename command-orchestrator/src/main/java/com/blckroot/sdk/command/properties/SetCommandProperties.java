package com.blckroot.sdk.command.properties;

import com.blckroot.sdk.command.Command;

import static com.blckroot.sdk.command.properties.SetCommandPropertiesState.*;

public class SetCommandProperties {
    public static Integer setProperties(Command command) {
        int exitCode = 0;
        SetCommandPropertiesState setCommandPropertiesState = INITIAL;
        while (setCommandPropertiesState != FINISHED && exitCode == 0) {
            exitCode = setCommandPropertiesState.run(command);
            setCommandPropertiesState = setCommandPropertiesState.transitionToNextState();
        }
        return exitCode;
    }
}
