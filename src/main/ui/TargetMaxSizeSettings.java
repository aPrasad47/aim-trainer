package ui;

/*
TargetMaxSizeSettings class: represents the target max size settings of the aim trainer, to alter the max size of
                             the targets
 */


import model.EventLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TargetMaxSizeSettings extends JFrame implements ActionListener {
    private JButton increaseTargetMaxSizeButton;
    private JButton decreaseTargetMaxSizeButton;
    private JButton currentTargetMaxSizeButton;
    private JButton backButton;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    // MODIFIES: this
    // EFFECTS: constructs a TargetMaxSizeSettings, and initializes the target max size settings window, the target max
    //          size settings window buttons, and adds the target max size settings window buttons to the target max
    //          size settings window
    public TargetMaxSizeSettings() {
        initializeTargetMaxSizeSettingsWindow();
        initializeTargetMaxSizeSettingsWindowButtons();
        addButtonsToTargetMaxSizeSettingsWindow();

        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the target max size settings window
    public void initializeTargetMaxSizeSettingsWindow() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int windowHeight = InitializationConstants.WINDOW_HEIGHT;

        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Change Max Target Size");
        this.addWindowListener(new CloseWindowHandler(EventLog.getInstance()));
    }

    // MODIFIES: increaseTargetMaxSizeButton, decreaseTargetMaxSizeButton, currentTargetMaxSizeButton, backButton
    public void initializeTargetMaxSizeSettingsWindowButtons() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int buttonWidth = InitializationConstants.BUTTON_WIDTH;
        int buttonHeight = InitializationConstants.BUTTON_HEIGHT;

        increaseTargetMaxSizeButton = new JButton("Increase Max Target Size By 1");
        decreaseTargetMaxSizeButton = new JButton("Decrease Max Target Size By 1");
        currentTargetMaxSizeButton = new JButton("View Current Max Target Size");
        backButton = new JButton("Return to Settings");

        increaseTargetMaxSizeButton.setBounds((windowWidth - buttonWidth) / 2, 20, buttonWidth, buttonHeight);
        increaseTargetMaxSizeButton.addActionListener(this);

        decreaseTargetMaxSizeButton.setBounds((windowWidth - buttonWidth) / 2, 80, buttonWidth, buttonHeight);
        decreaseTargetMaxSizeButton.addActionListener(this);

        currentTargetMaxSizeButton.setBounds((windowWidth - buttonWidth) / 2, 140, buttonWidth, buttonHeight);
        currentTargetMaxSizeButton.addActionListener(this);

        backButton.setBounds((windowWidth - buttonWidth) / 2, 200, buttonWidth, buttonHeight);
        backButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds target max size settings window buttons to target max size settings window
    public void addButtonsToTargetMaxSizeSettingsWindow() {
        this.add(increaseTargetMaxSizeButton);
        this.add(decreaseTargetMaxSizeButton);
        this.add(currentTargetMaxSizeButton);
        this.add(backButton);
    }

    // MODIFIES: this, gameWindowConstants
    // EFFECTS: if increase or decrease button is pressed, increases or decreases max target size
    //          if current target max size button is pressed, shows current target max size
    //          if backButton is pressed, disposes this, and constructs a new Settings
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increaseTargetMaxSizeButton) {
            gameWindowConstants.setMaxTargetRadius(gameWindowConstants.getMaxTargetRadius() + 1);
        } else if (e.getSource() == decreaseTargetMaxSizeButton) {
            gameWindowConstants.setMaxTargetRadius(gameWindowConstants.getMaxTargetRadius() - 1);
            if (gameWindowConstants.getMaxTargetRadius() <= gameWindowConstants.getMinTargetRadius()) {
                JOptionPane.showMessageDialog(this, "Minimum Max Target Size Reached");
                gameWindowConstants.setMaxTargetRadius(gameWindowConstants.getMaxTargetRadius() + 1);
            }
        } else if (e.getSource() == currentTargetMaxSizeButton) {
            JOptionPane.showMessageDialog(this,
                    "Current Max Target Size: " + gameWindowConstants.getMaxTargetRadius());
        } else if (e.getSource() == backButton) {
            this.dispose();
            new Settings();
        }
    }
}
