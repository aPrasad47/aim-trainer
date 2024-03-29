package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TargetMaxSizeSettings extends JFrame implements ActionListener {
    private JButton increaseTargetMaxSizeButton;
    private JButton decreaseTargetMaxSizeButton;
    private JButton currentTargetMaxSizeButton;
    private JButton backButton;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    public TargetMaxSizeSettings() {
        initializeTargetMaxSizeSettingsWindow();
        initializeTargetMaxSizeSettingsWindowButtons();
        addButtonsToTargetMaxSizeSettingsWindow();

        this.setVisible(true);
    }

    public void initializeTargetMaxSizeSettingsWindow() {
        int windowWidth = InitializationConstants.WINDOW_WIDTH;
        int windowHeight = InitializationConstants.WINDOW_HEIGHT;

        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Change Max Target Size");
    }

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

    public void addButtonsToTargetMaxSizeSettingsWindow() {
        this.add(increaseTargetMaxSizeButton);
        this.add(decreaseTargetMaxSizeButton);
        this.add(currentTargetMaxSizeButton);
        this.add(backButton);
    }

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
            dispose();
            new Settings();
        }
    }
}
