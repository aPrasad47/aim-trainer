package ui;

/*
ViewPreviousStats class: represents the window that displays the stats and info of the most recent saved session
 */

import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewPreviousStats extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/savedSessions";
    private JsonReader jsonReader;
    private List<GamePanel> sessions = new ArrayList<>();
    private JPanel previousSessionStatsInfoPanel = new JPanel();
    private JButton backButton = new JButton("Return to Statistics");

    private JLabel hitTargetsSizeLabel;
    private JLabel nonHitTargetsSizeLabel;
    private JLabel scoreLabel;
    private JLabel hitAttemptsLabel;
    private JLabel successfulHitsLabel;
    private JLabel accuracyLabel;
    private JLabel isMovingGameLabel;
    private JLabel nameLabel;


    // MODIFIES: backButton, this
    // EFFECTS: constructs a ViewPreviousStats, and initializes view previous session window, adds all sessions to
    //          sessions list, initializes previous sessions stats and info panel, initializes previous sessions stats
    //          and info labels, adds previous sessions stats and info labels to previous sessions stats and info panel
    public ViewPreviousStats() {
        initializeViewPreviousSessionWindow();
        addAllSessionsToSessionsList();
        initializePreviousSessionStatsInfoPanel();
        initializePreviousSessionStatsInfoLabels();
        addPreviousSessionStatsInfoLabelsToPanel();

        backButton.addActionListener(this);
        backButton.setSize(350, 100);
        previousSessionStatsInfoPanel.add(backButton);

        this.add(previousSessionStatsInfoPanel);

        this.setVisible(true);
    }

    // MODIFIES: previousSessionStatsInfoPanel
    // EFFECTS: initializes previous session stats and info panel
    public void initializePreviousSessionStatsInfoPanel() {
        previousSessionStatsInfoPanel.setSize(new Dimension(400, 300));
        previousSessionStatsInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    // EFFECTS: initializes previous session stats and info labels
    public void initializePreviousSessionStatsInfoLabels() {
        GamePanel previousSession = getPreviousSession();

        hitTargetsSizeLabel = new JLabel("Number of Hit Targets: "
                + previousSession.getHitTargets().getTargetsArray().size() + ",");
        nonHitTargetsSizeLabel = new JLabel("Number of Non-Hit Targets: "
                + previousSession.getNonHitTargets().getTargetsArray().size() + ",");
        scoreLabel = new JLabel("Score: " + previousSession.getScore() + ",");
        hitAttemptsLabel = new JLabel("Total Hit Attempts: " + previousSession.getHitAttempts() + ",");
        successfulHitsLabel = new JLabel("Total Successful Hits: " + previousSession.getSuccessfulHits() + ",");
        accuracyLabel = new JLabel("Accuracy: " + previousSession.getAccuracy() + ",");
        isMovingGameLabel = new JLabel("Moving Game:  " + previousSession.getIsMovingGame());
        nameLabel = new JLabel("Session Name: " + previousSession.getName());
    }

    // MODIFIES: previousSessionStatsInfoPanel
    // EFFECTS:  adds previous session stats and info labels to previous session panel
    public void addPreviousSessionStatsInfoLabelsToPanel() {
        previousSessionStatsInfoPanel.add(hitTargetsSizeLabel);
        previousSessionStatsInfoPanel.add(nonHitTargetsSizeLabel);
        previousSessionStatsInfoPanel.add(scoreLabel);
        previousSessionStatsInfoPanel.add(hitAttemptsLabel);
        previousSessionStatsInfoPanel.add(successfulHitsLabel);
        previousSessionStatsInfoPanel.add(accuracyLabel);
        previousSessionStatsInfoPanel.add(isMovingGameLabel);
        previousSessionStatsInfoPanel.add(nameLabel);
    }

    // EFFECTS: returns most recent game session
    public GamePanel getPreviousSession() {
        GamePanel previousSession = sessions.get(0);
        long latestTimeStamp = Long.parseLong(previousSession.getName());

        for (GamePanel session : sessions) {
            long sessionTimestamp = Long.parseLong(session.getName());
            if (sessionTimestamp > latestTimeStamp) {
                previousSession = session;
                latestTimeStamp = sessionTimestamp;
            }
        }
        return previousSession;
    }

    // MODIFIES: this, backButton
    // EFFECTS: initializes view previous session window
    public void initializeViewPreviousSessionWindow() {
        this.setTitle("Previous Session Stats / Info");
        this.setSize(new Dimension(400, 150));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
    }

    // MODIFIES: sessions
    // EFFECTS: adds all sessions to sessions list
    public void addAllSessionsToSessionsList() {
        File directory = new File(JSON_STORE);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File session : files) {
                if (session.isFile() && session.getName().endsWith(".json")) {
                    try {
                        jsonReader = new JsonReader(session.getPath());
                        GamePanel gamePanel = jsonReader.readGamePanel();
                        sessions.add(gamePanel);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if backButton is pressed, disposes this, and constructs a new Statistics
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
            new Statistics();
        }
    }
}
