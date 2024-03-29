package ui;

/*
ViewProgress class: represents the window that displays the progress of the user over the saved sessions
 */

import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewProgress extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/savedSessions";
    private JsonReader jsonReader;
    private List<GamePanel> sessions = new ArrayList<>();
    private JButton backButton = new JButton("Return to Statistics");

    // MODIFIES: this
    // EFFECTS: constructs a ViewProgress, and initializes the view progress window, adds all sessions to sessions list,
    //          constructs a new ProgressBarGraphPanel and adds it to this, and paints ProgressBarGraphPanel
    public ViewProgress() {
        initializeViewProgressWindow();
        addAllSessionsToSessionsList();
        ProgressBarGraphPanel progressBarGraphPanel = new ProgressBarGraphPanel(sessions);
        this.add(progressBarGraphPanel);

        this.setVisible(true);

        progressBarGraphPanel.repaint();
    }

    // MODIFIES: this, backButton
    // EFFECTS: initializes view progress window
    public void initializeViewProgressWindow() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int windowHeight = InitializationConstants.WINDOW_HEIGHT;

        this.setTitle("Progress Over Last 10 Sessions");
        this.setSize(windowWidth, windowHeight + 70);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        backButton.setBounds(25, windowHeight + 10, 450, 20);
        backButton.addActionListener(this);

        this.add(backButton);
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
