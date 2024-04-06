package ui;

/*
TargetMaxSpeedSettings class: represents the target max speed settings of the aim trainer, to alter the max speed of
                             the targets
 */

import model.EventLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TargetMaxSpeedSettings extends JFrame implements ActionListener {
    private JButton increaseTargetMaxSpeedButton;
    private JButton decreaseTargetMaxSpeedButton;
    private JButton currentTargetMaxSpeedButton;
    private JButton backButton;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    // MODIFIES: this
    // EFFECTS: constructs a TargetMaxSizeSettings, and initializes the target max speed settings window, the target max
    //          speed settings window buttons, and adds the target max speed settings window buttons to the target max
    //          speed settings window
    public TargetMaxSpeedSettings() {
        initializeTargetMaxSpeedSettingsWindow();
        initializeTargetMaxSpeedSettingsWindowButtons();
        addButtonsToTargetMaxSpeedSettingsWindow();

        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the target max speed settings window
    public void initializeTargetMaxSpeedSettingsWindow() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int windowHeight = InitializationConstants.WINDOW_HEIGHT;

        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Change Max Target Speed");
        this.addWindowListener(new CloseWindowHandler(EventLog.getInstance()));
    }

    // MODIFIES: increaseTargetMaxSpeedButton, decreaseTargetMaxSpeedButton, currentTargetMaxSpeedButton, backButton
    public void initializeTargetMaxSpeedSettingsWindowButtons() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int buttonWidth = InitializationConstants.BUTTON_WIDTH + 20;
        int buttonHeight = InitializationConstants.BUTTON_HEIGHT;

        increaseTargetMaxSpeedButton = new JButton("Increase Max Target Speed By 1");
        decreaseTargetMaxSpeedButton = new JButton("Decrease Max Target Speed By 1");
        currentTargetMaxSpeedButton = new JButton("View Current Max Target Speed");
        backButton = new JButton("Return to Settings");

        increaseTargetMaxSpeedButton.setBounds((windowWidth - buttonWidth) / 2, 20, buttonWidth, buttonHeight);
        increaseTargetMaxSpeedButton.addActionListener(this);

        decreaseTargetMaxSpeedButton.setBounds((windowWidth - buttonWidth) / 2, 80, buttonWidth, buttonHeight);
        decreaseTargetMaxSpeedButton.addActionListener(this);

        currentTargetMaxSpeedButton.setBounds((windowWidth - buttonWidth) / 2, 140, buttonWidth, buttonHeight);
        currentTargetMaxSpeedButton.addActionListener(this);

        backButton.setBounds((windowWidth - buttonWidth) / 2, 200, buttonWidth, buttonHeight);
        backButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds target max size settings window buttons to target max size settings window
    public void addButtonsToTargetMaxSpeedSettingsWindow() {
        this.add(increaseTargetMaxSpeedButton);
        this.add(decreaseTargetMaxSpeedButton);
        this.add(currentTargetMaxSpeedButton);
        this.add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: if increase or decrease button is pressed, increases or decreases max target speed
    //          if current target max speed button is pressed, shows current target max speed
    //          if backButton is pressed, disposes this, and constructs a new Settings
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increaseTargetMaxSpeedButton) {
            handleIncreaseMaxTargetSpeed();
        } else if (e.getSource() == decreaseTargetMaxSpeedButton) {
            handleDecreaseMaxTargetSpeed();
            if (gameWindowConstants.getMaxTargetSpeedDx() <= 0) {
                JOptionPane.showMessageDialog(this, "Minimum Max Target Speed Reached");
                handleIncreaseMaxTargetSpeed();
            }
        } else if (e.getSource() == currentTargetMaxSpeedButton) {
            JOptionPane.showMessageDialog(this,
                    "Current Max Target Speed: " + gameWindowConstants.getMaxTargetSpeedDx());
        } else if (e.getSource() == backButton) {
            dispose();
            new Settings();
        }
    }

    // MODIFIES: gameWindowConstants
    // EFFECTS: handles the increase of max target speed
    public void handleIncreaseMaxTargetSpeed() {
        gameWindowConstants.setMaxTargetSpeedDx(gameWindowConstants.getMaxTargetSpeedDx() + 1);
        gameWindowConstants.setMinTargetSpeedDx(gameWindowConstants.getMaxTargetSpeedDx() * -1);
        gameWindowConstants.setTargetSpeedDx(gameWindowConstants.getMinTargetSpeedDx(),
                gameWindowConstants.getMaxTargetSpeedDx());

        gameWindowConstants.setMaxTargetSpeedDy(gameWindowConstants.getMaxTargetSpeedDy() + 1);
        gameWindowConstants.setMinTargetSpeedDy(gameWindowConstants.getMaxTargetSpeedDy() * -1);
        gameWindowConstants.setTargetSpeedDy(gameWindowConstants.getMinTargetSpeedDy(),
                gameWindowConstants.getMaxTargetSpeedDy());
    }

    // MODIFIES: gameWindowConstants
    // EFFECTS: handles the decrease of max target speed
    public void handleDecreaseMaxTargetSpeed() {
        gameWindowConstants.setMaxTargetSpeedDx(gameWindowConstants.getMaxTargetSpeedDx() - 1);
        gameWindowConstants.setMinTargetSpeedDx(gameWindowConstants.getMaxTargetSpeedDx() * -1);
        gameWindowConstants.setTargetSpeedDx(gameWindowConstants.getMinTargetSpeedDx(),
                gameWindowConstants.getMaxTargetSpeedDx());

        gameWindowConstants.setMaxTargetSpeedDy(gameWindowConstants.getMaxTargetSpeedDy() - 1);
        gameWindowConstants.setMinTargetSpeedDy(gameWindowConstants.getMaxTargetSpeedDy() * -1);
        gameWindowConstants.setTargetSpeedDy(gameWindowConstants.getMinTargetSpeedDy(),
                gameWindowConstants.getMaxTargetSpeedDy());
    }
}
