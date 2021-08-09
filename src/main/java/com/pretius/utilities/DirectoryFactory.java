package com.pretius.utilities;

import java.io.File;

public class DirectoryFactory {
    public static final String HOME_DIRECTORY_NAME = "HOME";
    public static final String DEV_DIRECTORY_NAME = "DEV";
    public static final String TEST_DIRECTORY_NAME = "TEST";

    public DirectoryFactory() {
    }

    public void createdDesiredDirectories(){
        File homeDirectory = new File(HOME_DIRECTORY_NAME);
        File devDirectory = new File(DEV_DIRECTORY_NAME);
        File testDirectory = new File(TEST_DIRECTORY_NAME);

        boolean isHomeDirectoryCreated = homeDirectory.mkdir();
        boolean isDevDirectoryCreated = devDirectory.mkdir();
        boolean isTestDirectoryCreated = testDirectory.mkdir();

        if(isHomeDirectoryCreated && isDevDirectoryCreated && isTestDirectoryCreated)
            System.out.println("Directories properly created.");
        else
            System.out.println("Something went wrong. Directories were not created.");
    }
}
