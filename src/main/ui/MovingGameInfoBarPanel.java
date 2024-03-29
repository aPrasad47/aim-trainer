package ui;

/*
MovingGameInfoBarPanel class: represents the info bar at the top of the moving game that displays score, accuracy, and
                              lives
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingGameInfoBarPanel extends JPanel implements ActionListener {
    private JLabel scoreLabel;
    private JLabel accuracyLabel;
    private JLabel livesLabel;

    private JButton quitButton = new JButton("Quit");

    private MovingGamePanel movingGamePanel;

    private GameWindowConstants gameWindowConstants = new GameWindowConstants();

    // MODIFIES: this
    // EFFECTS: constructs a MovingGameInfoBarPanel with a MovingGamePanel, and initializes and adds all buttons to this
    public MovingGameInfoBarPanel(MovingGamePanel movingGamePanel) {
        this.movingGamePanel = movingGamePanel;

        this.setSize(gameWindowConstants.getGameWindowWidth(), 50);

        scoreLabel = new JLabel("Score: 0");
        accuracyLabel = new JLabel("Accuracy: 100%");
        livesLabel = new JLabel("Lives: " + gameWindowConstants.getMaxLives());

        this.add(scoreLabel);
        this.add(accuracyLabel);
        this.add(livesLabel);

        initializeQuitButton();

        this.add(quitButton);
    }

    // MODIFIES: quitButton
    // EFFECTS: initializes and adds quitButton to this
    public void initializeQuitButton() {
        quitButton.addActionListener(this);
        quitButton.setBounds(10, 0, 10, 50);

        this.add(quitButton);
    }

    // MODIFIES: scoreLabel
    // EFFECTS: updates scoreLabel
    public void updateScoreLabel(int score) {
        scoreLabel.setText("Score: " + score);
    }

    // MODIFIES: accuracyLabel
    // EFFECTS: updates accuracyLabel
    public void updateAccuracyLabel(double accuracy) {
        accuracyLabel.setText("Accuracy: " + accuracy);
    }

    // MODIFIES: livesLabel
    // EFFECTS: updates livesLabel
    public void updateLivesLabel(int lives) {
        livesLabel.setText("Lives: " + lives);
    }

    // MODIFIES: movingGamePanel
    // EFFECTS: if quitButton is pressed, displays a game over screen
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitButton) {
            movingGamePanel.displayGameOverScreen();
        }
    }

}
