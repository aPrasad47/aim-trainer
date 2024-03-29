package ui;

/*
GameOverScreen class: represent a game over screen, where a user can save their session
 */

import model.Target;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriter;
import persistence.Writable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class GameOverScreen extends JFrame implements ActionListener, Writable {
    private static final String JSON_STORE = "./data/savedSessions";

    private JLabel scoreLabel;
    private JLabel accuracyLabel;
    private JButton saveButton;
    private JButton displayAllHitTargetsOnScreenButton;
    private JButton returnToMainMenuButton;

    private int score;
    private double accuracy;

    private JFrame displayAllHitTargetsWindow;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    private GamePanel gamePanel;

    private JsonWriter jsonWriter;

    // MODIFIES: this
    // EFFECTS: constructs a GameOverScreen with a score, accuracy, and a GamePanel
    public GameOverScreen(int score, double accuracy, GamePanel gamePanel) {
        this.score = score;
        this.accuracy = accuracy;
        this.gamePanel = gamePanel;

        this.setTitle("Game Over!");
        this.setSize(InitializationConstants.WINDOW_WIDTH, InitializationConstants.WINDOW_HEIGHT + 70);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        initializeGameOverScreenComponents(score, accuracy);

        setVisible(true);
    }

    // MODIFIES: scoreLabel, accuracyLabel, saveButton, displayAllHitTargetsOnScreenButton, returnToMainMenuButton, this
    // EFFECTS: sets up GameOverScreen components and adds them to this
    public void initializeGameOverScreenComponents(int score, double accuracy) {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int buttonWidth = InitializationConstants.BUTTON_WIDTH;
        int buttonHeight = InitializationConstants.BUTTON_HEIGHT;

        scoreLabel = new JLabel("Score: " + score);
        accuracyLabel = new JLabel("Accuracy: " + accuracy + "%");

        saveButton = new JButton("Save Session");
        displayAllHitTargetsOnScreenButton = new JButton("Display All Hit Targets");
        returnToMainMenuButton = new JButton("Return to Main Menu");

        scoreLabel.setBounds((windowWidth - buttonWidth) / 2 + 75, 50, buttonWidth, buttonHeight);
        accuracyLabel.setBounds((windowWidth - buttonWidth) / 2 + 55, 100, buttonWidth, buttonHeight);

        saveButton.setBounds((windowWidth - buttonWidth) / 2, 150, buttonWidth, buttonHeight);
        displayAllHitTargetsOnScreenButton.setBounds((windowWidth - buttonWidth) / 2, 205, buttonWidth, buttonHeight);
        returnToMainMenuButton.setBounds((windowWidth - buttonWidth) / 2, 260, buttonWidth, buttonHeight);

        saveButton.addActionListener(this);
        displayAllHitTargetsOnScreenButton.addActionListener(this);
        returnToMainMenuButton.addActionListener(this);

        this.add(scoreLabel);
        this.add(accuracyLabel);
        this.add(saveButton);
        this.add(displayAllHitTargetsOnScreenButton);
        this.add(returnToMainMenuButton);
    }

    // MODIFIES: this
    // EFFECTS: if saveButton is pressed, disposes this, saves sessions, and constructs a new MainMenu,
    //          if displayAllHitTargetsOnScreenButton is pressed, disposes this, and displays all hit targets (filtered)
    //          on a new frame,
    //          if returnToMainMenuButton is pressed, disposes this, and constructs a new MainMenu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            this.dispose();
            saveSession();
            new MainMenu();
        } else if (e.getSource() == displayAllHitTargetsOnScreenButton) {
            this.dispose();
            int maxTargetSize = promptForRadius();
            displayAllHitTargetsOnScreen(maxTargetSize);
        } else if (e.getSource() == returnToMainMenuButton) {
            this.dispose();
            new MainMenu();
        }
    }

    // EFFECTS: returns an integer specified by the user
    public int promptForRadius() {
        String input = JOptionPane.showInputDialog(this,
                "Enter Max Target Size To Filter By, 0 For All Targets to be Displayed:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.");
            return -1;
        }
    }

    // MODIFIES: displayAllHitTargetsWindow
    // EFFECTS: displays all hit targets on a new window, filtered by maxTargetSize
    public void displayAllHitTargetsOnScreen(int maxTargetSize) {
        initializeDisplayAllHitTargetsWindow();

        DisplayHitTargetsPanel displayHitTargetsPanel = new DisplayHitTargetsPanel(gamePanel, maxTargetSize);

        displayAllHitTargetsWindow.add(displayHitTargetsPanel);
        displayAllHitTargetsWindow.setVisible(true);

        displayHitTargetsPanel.repaint();
    }

    // MODIFIES: displayAllHitTargetsWindow
    // EFFECTS: sets up displayAllHitTargetsWindow
    public void initializeDisplayAllHitTargetsWindow() {
        int windowWidth = GameWindowConstants.getGameWindowWidth();
        int windowHeight = GameWindowConstants.getGameWindowHeight();

        displayAllHitTargetsWindow = new JFrame();

        displayAllHitTargetsWindow.setTitle("All Hit Targets");
        displayAllHitTargetsWindow.setSize(windowWidth, windowHeight);
        displayAllHitTargetsWindow.setResizable(false);
        displayAllHitTargetsWindow.setLayout(null);
        displayAllHitTargetsWindow.setLocationRelativeTo(null);
        displayAllHitTargetsWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // MODIFIES: gamePanel, jsonWriter
    // EFFECTS: saves session to file
    public void saveSession() {
        try {
            String sessionName = nameSession();
            String jsonStorePath = JSON_STORE + "/" + sessionName;

            String sessionNameInTime = sessionName.replaceAll("[^\\d]", "");

            gamePanel.setName(sessionNameInTime);

            jsonWriter = new JsonWriter(jsonStorePath);

            jsonWriter.manageSessions();

            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();

            JOptionPane.showMessageDialog(this, "Session Saved");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: Session Could Not Be Saved");
        }
    }

    // EFFECTS: returns a session name specified by System.currentTimeMillis()
    public String nameSession() {
        return "session_" + System.currentTimeMillis() + ".json";
    }

    // MODIFIES: json
    // EFFECTS: maps gamePanel fields to json keys and values, then returns json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("targets", targetsToJson());
        json.put("hit-targets", hitTargetsToJson());
        json.put("non-hit-targets", nonHitTargetsToJson());
        json.put("score", gamePanel.getScore());
        json.put("hit-attempts", gamePanel.getHitAttempts());
        json.put("successful-hits", gamePanel.getSuccessfulHits());
        json.put("accuracy", gamePanel.getAccuracy());
        json.put("is-moving-game", gamePanel.getIsMovingGame());
        json.put("name", gamePanel.getName());
        return json;
    }

    // EFFECTS: returns a jsonArray by converting a target array
    private JSONArray targetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target t : gamePanel.getTargets().getTargetsArray()) {
            jsonArray.put(t.targetToJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns a jsonArray by converting a hit target array
    private JSONArray hitTargetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target ht : gamePanel.getHitTargets().getTargetsArray()) {
            jsonArray.put(ht.targetToJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns a jsonArray by converting a non-hit target array
    private JSONArray nonHitTargetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target ht : gamePanel.getNonHitTargets().getTargetsArray()) {
            jsonArray.put(ht.targetToJson());
        }
        return jsonArray;
    }
}
