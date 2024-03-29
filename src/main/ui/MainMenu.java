package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu implements ActionListener {
    private JFrame mainMenuWindow;

    private JButton playButton;
    private JButton statisticsButton;
    private JButton settingsButton;

    private JLabel welcomeLabel;

    public MainMenu() {
        initializeMainMenuWindow();
        initializeMainMenuButtons();
        addMainMenuButtonsToMainMenuWindow();

        mainMenuWindow.setVisible(true);
    }

    public void initializeMainMenuWindow() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int windowHeight = InitializationConstants.WINDOW_HEIGHT;
        int buttonWidth = InitializationConstants.BUTTON_WIDTH;
        int buttonHeight = InitializationConstants.BUTTON_HEIGHT;

        mainMenuWindow = new JFrame("Aim Trainer - Main Menu");
        mainMenuWindow.setSize(windowWidth, windowHeight);
        mainMenuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuWindow.setLocationRelativeTo(null);
        mainMenuWindow.setLayout(null);
        mainMenuWindow.setResizable(false);

        welcomeLabel = new JLabel("Welcome to the Aim Trainer!");
        welcomeLabel.setBounds((windowWidth - buttonWidth) / 2 + 10, 10, buttonWidth, buttonHeight);
        mainMenuWindow.add(welcomeLabel);
    }

    public void initializeMainMenuButtons() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int buttonWidth = InitializationConstants.BUTTON_WIDTH;
        int buttonHeight = InitializationConstants.BUTTON_HEIGHT;

        playButton = new JButton("Play");
        playButton.setBounds((windowWidth - buttonWidth) / 2, 50, buttonWidth, buttonHeight);
        playButton.addActionListener(this);

        statisticsButton = new JButton("Statistics");
        statisticsButton.setBounds((windowWidth - buttonWidth) / 2, 120, buttonWidth, buttonHeight);
        statisticsButton.addActionListener(this);

        settingsButton = new JButton("Settings");
        settingsButton.setBounds((windowWidth - buttonWidth) / 2, 190, buttonWidth, buttonHeight);
        settingsButton.addActionListener(this);
    }

    public void addMainMenuButtonsToMainMenuWindow() {
        mainMenuWindow.add(playButton);
        mainMenuWindow.add(statisticsButton);
        mainMenuWindow.add(settingsButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            mainMenuWindow.dispose();
            new GameModeSelector();
        } else if (e.getSource() == statisticsButton) {
            mainMenuWindow.dispose();
            new Statistics();
        } else if (e.getSource() == settingsButton) {
            mainMenuWindow.dispose();
            new Settings();
        }
    }

    public JFrame getMainMenuWindow() {
        return mainMenuWindow;
    }
}