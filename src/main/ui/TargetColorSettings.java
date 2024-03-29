package ui;

/*
TargetColorSettings class: represents the target color settings of the aim trainer, to alter the target colors
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TargetColorSettings extends JFrame implements ActionListener {
    private JButton redButton;
    private JButton greenButton;
    private JButton blueButton;
    private JButton pinkButton;
    private JButton backButton;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    // MODIFIES: this
    // EFFECTS: constructs a TargetColorSettings, and initializes the target color settings window, the target color
    //          settings window buttons, and adds the target color settings window buttons to the target color settings
    //          window
    public TargetColorSettings() {
        initializeTargetColorSettingsWindow();
        initializeTargetColorSettingsWindowButtons();
        addButtonsToTargetColorSettingsWindow();

        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the target color settings window
    public void initializeTargetColorSettingsWindow() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int windowHeight = InitializationConstants.WINDOW_HEIGHT;

        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Change Target Color");
    }

    // MODIFIES: redButton, greenButton, blueButton, pinkButton, backButton
    public void initializeTargetColorSettingsWindowButtons() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int buttonWidth = InitializationConstants.BUTTON_WIDTH;
        int buttonHeight = InitializationConstants.BUTTON_HEIGHT;

        redButton = new JButton("Red Targets");
        greenButton = new JButton("Green Targets");
        blueButton = new JButton("Blue Targets");
        pinkButton = new JButton("Pink Targets");
        backButton = new JButton("Return to Settings");

        redButton.setBounds((windowWidth - buttonWidth) / 2, 5, buttonWidth, buttonHeight);
        redButton.addActionListener(this);

        greenButton.setBounds((windowWidth - buttonWidth) / 2, 55, buttonWidth, buttonHeight);
        greenButton.addActionListener(this);

        blueButton.setBounds((windowWidth - buttonWidth) / 2, 105, buttonWidth, buttonHeight);
        blueButton.addActionListener(this);

        pinkButton.setBounds((windowWidth - buttonWidth) / 2, 155, buttonWidth, buttonHeight);
        pinkButton.addActionListener(this);

        backButton.setBounds((windowWidth - buttonWidth) / 2, 205, buttonWidth, buttonHeight);
        backButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds target color settings window buttons to target color settings window
    public void addButtonsToTargetColorSettingsWindow() {
        this.add(redButton);
        this.add(greenButton);
        this.add(blueButton);
        this.add(pinkButton);
        this.add(backButton);
    }

    // EFFECTS: if any colorButton is pressed, sets target color to that color,
    //          if backButton is pressed, disposes this, and constructs a new Settings
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == redButton) {
            gameWindowConstants.setTargetColor(Color.RED);
            JOptionPane.showMessageDialog(this, "Set to Red");
        } else if (e.getSource() == greenButton) {
            gameWindowConstants.setTargetColor(Color.GREEN);
            JOptionPane.showMessageDialog(this, "Set to Green");
        } else if (e.getSource() == blueButton) {
            gameWindowConstants.setTargetColor(Color.BLUE);
            JOptionPane.showMessageDialog(this, "Set to Blue");
        } else if (e.getSource() == pinkButton) {
            gameWindowConstants.setTargetColor(Color.PINK);
            JOptionPane.showMessageDialog(this, "Set to Pink");
        } else if (e.getSource() == backButton) {
            this.dispose();
            new Settings();
        }

    }
}
