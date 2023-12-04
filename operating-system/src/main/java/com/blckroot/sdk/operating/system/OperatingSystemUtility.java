package com.blckroot.sdk.operating.system;

import java.util.Properties;

class OperatingSystemUtility implements OperatingSystemContract {
    private Properties operatingSystemProperties;

    public OperatingSystemUtility() {
        this.operatingSystemProperties = System.getProperties();
    }

    public Properties getOperatingSystemProperties() {
        return operatingSystemProperties;
    }

    public void setOperatingSystemProperties(Properties operatingSystemProperties) {
        this.operatingSystemProperties = operatingSystemProperties;
    }
}
