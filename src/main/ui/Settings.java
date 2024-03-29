package ui;

/*
Settings class: represents the settings of the aim trainer, to alter the game
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings implements ActionListener {
    private JFrame settingsWindow;

    private JButton changeTargetColorButton;
    private JButton changeTargetSizeButton;
    private JButton changeTargetSpeedButton;
    private JButton returnToMainMenuButton;

    // MODIFIES: settingsWindow
    // EFFECTS: constructs a Settings, and initializes the settings window, the settings window buttons, and adds
    //          the settings window buttons to the settings window
    public Settings() {
        initializeSettingsWindow();
        initializeSettingsButtons();
        addSettingsButtonsToSettingsWindow();

        settingsWindow.setVisible(true);
    }

    // MODIFIES: settingsWindow
    // EFFECTS: initializes the settings window
    public void initializeSettingsWindow() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int windowHeight = InitializationConstants.WINDOW_HEIGHT;

        settingsWindow = new JFrame("Aim Trainer - Settings");
        settingsWindow.setSize(windowWidth, windowHeight);
        settingsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingsWindow.setLocationRelativeTo(null);
        settingsWindow.setLayout(null);
        settingsWindow.setResizable(false);
    }

    // MODIFIES: changeTargetColorButton, changeTargetSizeButton, changeTargetSpeedButton, returnToMainMenuButton
    public void initializeSettingsButtons() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int buttonWidth = InitializationConstants.BUTTON_WIDTH;
        int buttonHeight = InitializationConstants.BUTTON_HEIGHT;

        changeTargetColorButton = new JButton("Change Target Color");
        changeTargetSizeButton = new JButton("Change Max Target Size");
        changeTargetSpeedButton = new JButton("Change Max Target Speed");
        returnToMainMenuButton = new JButton("Return to Main Menu");

        changeTargetColorButton.setBounds((windowWidth - buttonWidth) / 2, 20, buttonWidth, buttonHeight);
        changeTargetColorButton.addActionListener(this);

        changeTargetSizeButton.setBounds((windowWidth - buttonWidth) / 2, 80, buttonWidth, buttonHeight);
        changeTargetSizeButton.addActionListener(this);

        changeTargetSpeedButton.setBounds((windowWidth - buttonWidth) / 2, 140, buttonWidth, buttonHeight);
        changeTargetSpeedButton.addActionListener(this);

        returnToMainMenuButton.setBounds((windowWidth - buttonWidth) / 2, 200, buttonWidth, buttonHeight);
        returnToMainMenuButton.addActionListener(this);
    }

    // MODIFIES: settingsWindow
    // EFFECTS: adds settings window buttons to settings window
    public void addSettingsButtonsToSettingsWindow() {
        settingsWindow.add(changeTargetColorButton);
        settingsWindow.add(changeTargetSizeButton);
        settingsWindow.add(changeTargetSpeedButton);
        settingsWindow.add(returnToMainMenuButton);
    }

    // MODIFIES: settingsWindow
    // EFFECTS: if changeTargetColorButton is pressed, disposes settingsWindow, and constructs a new
    //          TargetColorSettings,
    //          if changeTargetSizeButton is pressed, disposes settingsWindow, and constructs a new
    //          TargetMaxSizeSettings,
    //          if changeTargetSpeedButton is pressed, disposes settingsWindow, and constructs a new
    //          TargetMaxSpeedSettings,
    //          if returnToMainMenuButton is pressed, disposes settingsWindow, and constructs a new
    //          MainMenu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeTargetColorButton) {
            settingsWindow.dispose();
            new TargetColorSettings();
        } else if (e.getSource() == changeTargetSizeButton) {
            settingsWindow.dispose();
            new TargetMaxSizeSettings();
        } else if (e.getSource() == changeTargetSpeedButton) {
            settingsWindow.dispose();
            new TargetMaxSpeedSettings();
        } else if (e.getSource() == returnToMainMenuButton) {
            settingsWindow.dispose();
            new MainMenu();
        }
    }
}
