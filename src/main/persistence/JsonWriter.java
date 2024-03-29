package persistence;

import org.json.JSONObject;

import ui.AimTrainerConsole;
import ui.GameOverScreen;


import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;


// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final String JSON_STORE = "./data/savedSessions";
    private static final int MAX_SESSIONS = 10;
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String directory;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String directory) {
        this.directory = directory;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(directory);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(GameOverScreen gameOverScreen) {
        JSONObject json = gameOverScreen.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(AimTrainerConsole atc) {
        JSONObject json = atc.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }

    // EFFECTS: manages sessions in savedSessions folder
    public void manageSessions() {
        File directory = new File(JSON_STORE);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            handleAddingNewSession(files);
        } else {
            System.err.println("Invalid directory path or directory does not exist.");
        }
    }

    // EFFECTS: sorts all JSON files in savedSessions folder from oldest to newest
    public void sortFilesOldestToNewest(File[] files) {
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
    }

    // EFFECTS: handles adding new sessions to savedSessions folder
    public void handleAddingNewSession(File[] files) {
        if (files != null) {
            sortFilesOldestToNewest(files);

            if (files.length >= MAX_SESSIONS) {
                File oldestFile = files[0];
                try {
                    Files.deleteIfExists(oldestFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("No files in the directory.");
        }
    }


}
