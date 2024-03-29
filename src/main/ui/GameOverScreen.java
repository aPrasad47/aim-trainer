package ui;

import model.Target;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
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

    private JFrame displayAllHitTargetsWindow;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    private GamePanel gamePanel;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public GameOverScreen(int score, double accuracy, GamePanel gamePanel) {
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

    private void initializeGameOverScreenComponents(int score, double accuracy) {
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

    public void displayAllHitTargetsOnScreen(int maxTargetSize) {
        initializeDisplayAllHitTargetsWindow();

        DisplayHitTargetsPanel displayHitTargetsPanel = new DisplayHitTargetsPanel(gamePanel, maxTargetSize);

        displayAllHitTargetsWindow.add(displayHitTargetsPanel);
        displayAllHitTargetsWindow.setVisible(true);

        displayHitTargetsPanel.repaint();
    }

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


    public void saveSession() {
        try {
            String sessionName = nameSession();
            String jsonStorePath = JSON_STORE + "/" + sessionName;

            jsonWriter = new JsonWriter(jsonStorePath);

            jsonWriter.manageSessions();

            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();

            System.out.println("Saved " + sessionName + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public String nameSession() {
        return "session_" + System.currentTimeMillis() + ".json";
    }

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
        return json;
    }

    private JSONArray targetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target t : gamePanel.getTargets().getTargetsArray()) {
            jsonArray.put(t.targetToJson());
        }
        return jsonArray;
    }

    private JSONArray hitTargetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target ht : gamePanel.getHitTargets().getTargetsArray()) {
            jsonArray.put(ht.targetToJson());
        }
        return jsonArray;
    }

    private JSONArray nonHitTargetsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Target ht : gamePanel.getNonHitTargets().getTargetsArray()) {
            jsonArray.put(ht.targetToJson());
        }
        return jsonArray;
    }



}
