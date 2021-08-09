package com.pretius.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CountFileManager {
    public static final String FILE_NAME = "count.txt";
    private static FileWriter fileWriter;
    private static String filePath;
    private File countFile;

    public CountFileManager() {
        filePath = DirectoryFactory.HOME_DIRECTORY_NAME +
                DirectoryObservator.SLASH +
                FILE_NAME;
    }

    public void creatCountFile(){
        countFile = new File(filePath);
        boolean isFileCreated = false;
        try {
            isFileCreated = countFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isFileCreated)
            System.out.println("File count.txt properly created.\n");
        else
            System.out.println("Something went wrong. File was not created.");

    }

    public static void appendCountFile(String line){
        try {
            fileWriter = new FileWriter(filePath, true);
            fileWriter.append(line);
            fileWriter.append("\n");
            fileWriter.close();
            System.out.println("Added line to countFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
