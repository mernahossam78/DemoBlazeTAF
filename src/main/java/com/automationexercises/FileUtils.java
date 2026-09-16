package com.automationexercises;


import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;

import java.io.File;

// if i want to handle anything in a file i will do it here
public class FileUtils {

    private static final String USER_DIR = PropertyReader.getProperty("user.dir") + File.separator;

    private FileUtils() {
        //Prevent instantiation
    }

    //Renaming
    public static void renameFile(String oldPath, String newPath) {
        try {
            File oldFile = new File(USER_DIR + oldPath);
            File newFile = new File(USER_DIR + newPath);
            if (oldFile.exists()) {
                boolean success = oldFile.renameTo(newFile);
                if (success) {
                    LogsManager.info("File renamed from " + oldPath + " to " + newPath);
                } else {
                    LogsManager.error("Failed to rename file from " + oldPath + " to " + newPath, null);
                }
            } else {
                LogsManager.error("File not found: " + oldPath, null);
            }
        } catch (Exception e) {
            LogsManager.error("Failed to rename file from " + oldPath + " to " + newPath, e.getMessage());
        }
    }

    //Creating directory
    public static void createDirectory(String path) {
        try {
            File file = new File(USER_DIR + path);
            if (!file.exists()) {
                file.mkdir();
                LogsManager.info("Directory created: " + path);
            }
        } catch (Exception e) {
            LogsManager.error("Failed to create directory: " + path, e.getMessage());
        }
    }

    //Cleaning directory
    public static void cleanDirectory(File file) {
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(file);

        } catch (Exception e) {
            LogsManager.error("Failed to clean directory: " + file.getAbsolutePath(), e.getMessage());
        }
    }
}
