package com.automationexercises.utils;

public class OSUtils {
    public static OS getCurrentOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) return OS.WINDOWS;
        if (osName.contains("mac")) return OS.MAC;
        if (osName.contains("nix") || osName.contains("nux")) return OS.LINUX;
        return OS.OTHER;
    }

    public enum OS {WINDOWS, MAC, LINUX, OTHER}

}
