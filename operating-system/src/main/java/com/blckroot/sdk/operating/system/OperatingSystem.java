package com.blckroot.sdk.operating.system;

import java.util.Properties;

public class OperatingSystem implements OperatingSystemContract {
    private final OperatingSystemUtility operatingSystemUtility;

    public OperatingSystem() {
        this.operatingSystemUtility = new OperatingSystemUtility();
    }

    @Override
    public Properties getOperatingSystemProperties() {
        return operatingSystemUtility.getOperatingSystemProperties();
    }

    @Override
    public void setOperatingSystemProperties(Properties operatingSystemProperties) {
        operatingSystemUtility.setOperatingSystemProperties(operatingSystemProperties);
    }
}
