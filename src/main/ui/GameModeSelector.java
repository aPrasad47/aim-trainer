package ui;

import javax.swing.*;
import static javax.swing.WindowConstants.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// this window is displayed when a user selects "play" in the main menu

public class GameModeSelector implements ActionListener {

    private JFrame gameModeSelectorWindow;

    private JButton stationaryGameModeButton;
    private JButton movingGameModeButton;
    private JButton returnToMainMenuButton;

    private JLabel selectYourGameModeLabel;

    public GameModeSelector() {
        initializeGameModeSelectorWindow();
        initializeGameModeSelectorButtons();
        addGameModeSelectorButtonsToGameModeSelectorWindow();

        gameModeSelectorWindow.setVisible(true);

    }

    public void initializeGameModeSelectorWindow() {
        gameModeSelectorWindow = new JFrame("Aim Trainer - Game Mode Selection");
        gameModeSelectorWindow.setSize(InitializationConstants.WINDOW_WIDTH, InitializationConstants.WINDOW_HEIGHT);
        gameModeSelectorWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameModeSelectorWindow.setLocationRelativeTo(null);
        gameModeSelectorWindow.setLayout(null);
        gameModeSelectorWindow.setResizable(false);

        selectYourGameModeLabel = new JLabel("Select Your Gamemode!");
        selectYourGameModeLabel.setBounds((InitializationConstants.WINDOW_WIDTH
                        - InitializationConstants.BUTTON_WIDTH) / 2 + 25,
                10,
                InitializationConstants.BUTTON_WIDTH,
                InitializationConstants.BUTTON_HEIGHT);
        gameModeSelectorWindow.add(selectYourGameModeLabel);
    }

    public void initializeGameModeSelectorButtons() {
        stationaryGameModeButton = new JButton("Stationary Targets");
        stationaryGameModeButton.setBounds((InitializationConstants.WINDOW_WIDTH
                - InitializationConstants.BUTTON_WIDTH) / 2,
                50,
                InitializationConstants.BUTTON_WIDTH,
                InitializationConstants.BUTTON_HEIGHT);
        stationaryGameModeButton.addActionListener(this);

        movingGameModeButton = new JButton("Moving Targets");
        movingGameModeButton.setBounds((InitializationConstants.WINDOW_WIDTH
                - InitializationConstants.BUTTON_WIDTH) / 2,
                120, InitializationConstants.BUTTON_WIDTH,
                InitializationConstants.BUTTON_HEIGHT);
        movingGameModeButton.addActionListener(this);

        returnToMainMenuButton = new JButton("Return to Main Menu");
        returnToMainMenuButton.setBounds((InitializationConstants.WINDOW_WIDTH
                        - InitializationConstants.BUTTON_WIDTH) / 2,
                190, InitializationConstants.BUTTON_WIDTH,
                InitializationConstants.BUTTON_HEIGHT);
        returnToMainMenuButton.addActionListener(this);
    }

    public void addGameModeSelectorButtonsToGameModeSelectorWindow() {
        gameModeSelectorWindow.add(stationaryGameModeButton);
        gameModeSelectorWindow.add(movingGameModeButton);
        gameModeSelectorWindow.add(returnToMainMenuButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stationaryGameModeButton) {
            gameModeSelectorWindow.dispose();
            new AimTrainerGUI(false);
        } else if (e.getSource() == movingGameModeButton) {
            gameModeSelectorWindow.dispose();
            new AimTrainerGUI(true);
        } else if (e.getSource() == returnToMainMenuButton) {
            gameModeSelectorWindow.dispose();
            new MainMenu();
        }
    }
}
