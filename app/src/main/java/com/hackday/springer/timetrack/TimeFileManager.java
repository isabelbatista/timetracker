package com.hackday.springer.timetrack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by iso3097 on 29.09.17.
 */

public class TimeFileManager {

    private File file;

    public TimeFileManager(String path) {
        file = new File(path, "timetrack.csv");
    }

    public void writeFile(String textToWrite) throws IOException {

        FileOutputStream stream = new FileOutputStream(file);
        try {
            System.out.println("The file " + file.getAbsolutePath() + " will be written with text: " + textToWrite);
            stream.write(textToWrite.getBytes());
        } finally {
            stream.close();
        }
    }

    public String readFile() throws IOException {
        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(file);
        try {
            in.read(bytes);
        } finally {
            in.close();
        }

        String contents = new String(bytes);
        return contents;
    }

    private String generateTitleLine() {
        return null;
    }

}
