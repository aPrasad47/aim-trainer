package persistence;

import ui.AimTrainerConsole;
import ui.GamePanel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAimTrainerConsole(int score, int hitAttempts, int successfulHits,
                                          double accuracy, String name, boolean isMovingGame, AimTrainerConsole atc) {
        assertEquals(score, atc.getScore());
        assertEquals(hitAttempts, atc.getHitAttempts());
        assertEquals(successfulHits, atc.getSuccessfulHits());
        assertEquals(accuracy, atc.getAccuracy());
        assertEquals(name, atc.getName());
        assertEquals(isMovingGame, atc.getIsMovingGame());
    }

    protected void checkGamePanel(int score, int hitAttempts, int successfulHits,
                                          double accuracy, String name, boolean isMovingGame, GamePanel gamePanel) {
        assertEquals(score, gamePanel.getScore());
        assertEquals(hitAttempts, gamePanel.getHitAttempts());
        assertEquals(successfulHits, gamePanel.getSuccessfulHits());
        assertEquals(accuracy, gamePanel.getAccuracy());
        assertEquals(name, gamePanel.getName());
        assertEquals(isMovingGame, gamePanel.getIsMovingGame());
    }
}

