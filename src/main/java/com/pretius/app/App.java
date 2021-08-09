package com.pretius.app;

import com.pretius.utilities.CountFileManager;
import com.pretius.utilities.DirectoryFactory;
import com.pretius.utilities.DirectoryObservator;

import java.util.Timer;
import java.util.TimerTask;

public class App
{
    public static void main( String[] args )
    {
        new DirectoryFactory().createdDesiredDirectories();

        new CountFileManager().creatCountFile();
        final DirectoryObservator directoryObservator = new DirectoryObservator();

        TimerTask directoryObservatiorTask = new TimerTask() {
            @Override
            public void run() {
                directoryObservator.observeHomeDirectory();
            }
        };

        Timer directoryObservatorTimer = new Timer();
        directoryObservatorTimer.schedule(directoryObservatiorTask, 0L, 1000L);
    }
}
