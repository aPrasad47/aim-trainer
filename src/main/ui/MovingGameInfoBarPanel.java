package ui;

import javax.swing.*;

public class MovingGameInfoBarPanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel accuracyLabel;
    private JLabel livesLabel;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    public MovingGameInfoBarPanel() {
        this.setSize(gameWindowConstants.getGameWindowWidth(), 50);

        scoreLabel = new JLabel("Score: 0");
        accuracyLabel = new JLabel("Accuracy: 100%");
        livesLabel = new JLabel("Lives: " + gameWindowConstants.getMaxLives());

        this.add(scoreLabel);
        this.add(accuracyLabel);
        this.add(livesLabel);
    }

    public void updateScoreLabel(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateAccuracyLabel(double accuracy) {
        accuracyLabel.setText("Accuracy: " + accuracy);
    }

    public void updateLivesLabel(int lives) {
        livesLabel.setText("Lives: " + lives);
    }

}
