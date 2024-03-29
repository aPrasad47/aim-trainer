package ui;

import javax.swing.*;
import static javax.swing.WindowConstants.*;

public class AimTrainerGUI {
    private boolean isMovingGame;

    private JFrame movingGameWindow;
    private JFrame stationaryGameWindow;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    public AimTrainerGUI(boolean isMovingGame) {
        this.isMovingGame = isMovingGame;
        startGame();
    }

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
