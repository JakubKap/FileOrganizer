package com.pretius.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DirectoryObservator {
    private static final String DOT = ".";
    static final String SLASH = "\\";
    private static final String JAR_EXTENSION = ".jar";
    private static final String XML_EXTENSION = ".xml";


    private final Path homeDirectoryPath;

    public DirectoryObservator() {
        homeDirectoryPath = Paths.get(new File(DirectoryFactory.HOME_DIRECTORY_NAME).getAbsolutePath());
    }

    public void observeHomeDirectory() {
        try {
            WatchService watcher = homeDirectoryPath.getFileSystem().newWatchService();
            homeDirectoryPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            System.out.println("\nMonitoring directory for changes...");

            WatchKey watchKey = watcher.take();
            List<WatchEvent<?>> events = watchKey.pollEvents();

            for (WatchEvent event : events) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    String fileName = event.context().toString();
                    System.out.println("\nCreated file: " + fileName);

                    printLastModificationDate(fileName);
                }
            }
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void printLastModificationDate(String fileName) throws IOException, ParseException {
        Path filePath = Paths.get(DirectoryFactory.HOME_DIRECTORY_NAME+ "\\" + fileName);
        System.out.println("filePath: " + filePath.toString());

        BasicFileAttributes basicFileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


        Date fileDate = simpleDateFormat
                .parse(simpleDateFormat.format(basicFileAttributes.creationTime().toMillis()));

        int fileCreationHour = fileDate.getHours();
        System.out.println("File creation hour: " + fileDate.getHours());

        String fileExtension =  getFileExtension(fileName);
        System.out.println("File extension: " + fileExtension);

        moveFile(fileName, fileExtension, fileCreationHour);
    }

    public String getFileExtension(String fileName){
        int lastIndexOf = fileName.lastIndexOf(DOT);

        if(lastIndexOf == -1){
            return "";
        }
        return fileName.substring(lastIndexOf);
    }

    public void moveFile(String fileName, String fileExtension, int fileCreationHour) throws IOException {
        Path fileDestinationPath = null;

        if(fileExtension.equals(JAR_EXTENSION)){
            if(fileCreationHour % 2 == 0){
                fileDestinationPath = Paths.get(prepareFilePath(DirectoryFactory.DEV_DIRECTORY_NAME,fileName));

                Files.move(Paths.get(prepareFilePath(DirectoryFactory.HOME_DIRECTORY_NAME,fileName)),
                        fileDestinationPath,
                        StandardCopyOption.REPLACE_EXISTING);

            } else{
                fileDestinationPath = Paths.get(prepareFilePath(DirectoryFactory.TEST_DIRECTORY_NAME,fileName));

                Files.move(Paths.get(prepareFilePath(DirectoryFactory.HOME_DIRECTORY_NAME,fileName)),
                        fileDestinationPath,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        else if(fileExtension.equals(XML_EXTENSION)){
            fileDestinationPath =  Paths.get(prepareFilePath(DirectoryFactory.DEV_DIRECTORY_NAME,fileName));

            Files.move(Paths.get(prepareFilePath(DirectoryFactory.HOME_DIRECTORY_NAME,fileName)),
                    fileDestinationPath,
                    StandardCopyOption.REPLACE_EXISTING);
        }

        if(fileDestinationPath != null) {
            String exportMessage = "File " + fileName + " has been moved to " + fileDestinationPath.toString();
            System.out.println(exportMessage);
            appendCountFile(exportMessage);
        }
    }

    private String prepareFilePath(String directoryName, String fileName){
        return directoryName + SLASH + fileName;
    }

    private void appendCountFile(String line){
        CountFileManager.appendCountFile(line);
    }
}
