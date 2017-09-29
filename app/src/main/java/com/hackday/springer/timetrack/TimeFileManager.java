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
    private static final String TOKEN_SEPARATOR = ";";
    private static final String LINE_BREAK = "\n";

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
        StringBuilder titleLine = new StringBuilder();
        titleLine.append("Date" + TOKEN_SEPARATOR);
        titleLine.append("Started Working" + TOKEN_SEPARATOR);
        titleLine.append("Break Start" + TOKEN_SEPARATOR);
        titleLine.append("Break End" + TOKEN_SEPARATOR);
        titleLine.append("Break Time Sum" + TOKEN_SEPARATOR);
        titleLine.append("Stopped Working" + TOKEN_SEPARATOR);
        titleLine.append("Working Time Sum (Day)" + TOKEN_SEPARATOR);
        titleLine.append("Working Time Sum (Week)" + TOKEN_SEPARATOR);
        titleLine.append("Working Time Sum (Month)" + TOKEN_SEPARATOR);
        titleLine.append("+/-" + TOKEN_SEPARATOR);

        return titleLine.toString();
    }

    public String generateLineToWrite(long timeInMilliseconds) {
        // TODO: move this line to initialization so it is just printed once
        String titleLine = generateTitleLine();

        String date = TimeCalculator.calcDateFromMilliseconds(timeInMilliseconds);
        String time = TimeCalculator.calcTimeFromMilliseconds(timeInMilliseconds);
        StringBuilder textToWriteToFile = new StringBuilder(titleLine + LINE_BREAK);
        textToWriteToFile.append(date + TOKEN_SEPARATOR);
        textToWriteToFile.append(time + TOKEN_SEPARATOR);

        return textToWriteToFile.toString();
    }
}
