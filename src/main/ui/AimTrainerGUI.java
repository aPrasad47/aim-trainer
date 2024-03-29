package ui;

/*
AimTrainerGUI Target: represents a GUI with an isMovingGame movingGameWindow, stationaryGameWindow,
                      and gameWindowConstants
 */

import javax.swing.*;
import static javax.swing.WindowConstants.*;

public class AimTrainerGUI {
    private boolean isMovingGame;

    private JFrame movingGameWindow;
    private JFrame stationaryGameWindow;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    // EFFECTS: constructs and AimTrainerGUI with isMovingGame to denote the type of game,
    //          then starts the game
    public AimTrainerGUI(boolean isMovingGame) {
        this.isMovingGame = isMovingGame;
        startGame();
    }

    // MODIFIES: movingGameWindow, stationaryGameWindow
    // EFFECTS: if (isMovingGame), then sets up and plays moving game, otherwise
    //          sets up and plays stationary game
    public void startGame() {
        if (isMovingGame) {
            movingGameWindowSetup();
            MovingGamePanel movingGamePanel = new MovingGamePanel(movingGameWindow);
            movingGameWindow.add(movingGamePanel);
            movingGameWindow.setVisible(true);
        } else {
            stationaryGameWindowSetup();
            StationaryGamePanel stationaryGamePanel = new StationaryGamePanel(stationaryGameWindow);
            stationaryGameWindow.add(stationaryGamePanel);
            stationaryGameWindow.setVisible(true);
        }
    }

    // MODIFIES: movingGameWindow
    // EFFECTS: sets up movingGameWindow frame
    public void movingGameWindowSetup() {
        int gameWindowWidth = gameWindowConstants.getGameWindowWidth();
        int gameWindowHeight = gameWindowConstants.getGameWindowHeight();

        movingGameWindow = new JFrame("Aim Trainer - Moving Targets");
        movingGameWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        movingGameWindow.setResizable(false);
        movingGameWindow.setSize(gameWindowWidth, gameWindowHeight);
        movingGameWindow.setLocationRelativeTo(null);
        movingGameWindow.setLayout(null);

    }

    // MODIFIES: stationaryGameWindow
    // EFFECTS: sets up stationaryGameWindow frame
    public void stationaryGameWindowSetup() {
        int gameWindowWidth = gameWindowConstants.getGameWindowWidth();
        int gameWindowHeight = gameWindowConstants.getGameWindowHeight();

        stationaryGameWindow = new JFrame("Aim Trainer - Stationary Targets");
        stationaryGameWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        stationaryGameWindow.setResizable(false);
        stationaryGameWindow.setSize(gameWindowWidth, gameWindowHeight);
        stationaryGameWindow.setLocationRelativeTo(null);
        stationaryGameWindow.setLayout(null);
    }
}
