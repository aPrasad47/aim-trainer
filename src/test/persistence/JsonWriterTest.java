package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import ui.AimTrainerConsole;
import ui.GameOverScreen;
import ui.GamePanel;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest extends JsonTest {
    private static final int MAX_X = 200;
    private static final int MAX_Y = 200;

    @Test
    void testWriterInvalidFile() {
        try {
            AimTrainerConsole atc = new AimTrainerConsole(new Targets(), new HitTargets(), new NonHitTargets(),
                    0, 0, 0,100.0, "", true);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterFirstQuitAimTrainingSession() {
        try {
            AimTrainerConsole atc = new AimTrainerConsole(new Targets(), new HitTargets(), new NonHitTargets(),
                    0, 0, 0, 100.0, "First Quit", false);

            JsonWriter writer = new JsonWriter("./data/testWriterFirstQuitAimTrainingSession.json");
            writer.open();
            writer.write(atc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFirstQuitAimTrainingSession.json");
            atc = reader.readAimTrainerConsole();
            checkAimTrainerConsole(0, 0, 0, 100.0, "First Quit",
                    false, atc);
            ArrayList<Target> targets = atc.getTargets().getTargetsArray();
            ArrayList<Target> hitTargets = atc.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargets = atc.getNonHitTargets().getTargetsArray();
            assertEquals(0, targets.size());
            assertEquals(0, hitTargets.size());
            assertEquals(0, nonHitTargets.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMovingSession() {
        try {
            Target target1 = new Target(new Position(1, 3), true);

            Target target2 = new Target(new Position(5, 9), true);

            Targets targets = new Targets();
            targets.addTarget(target1);
            targets.addTarget(target2);

            Target hitTarget1 = new Target(new Position(100, 99), true);
            hitTarget1.targetHit();
            hitTarget1.setNumAttemptsToHit(2);
            hitTarget1.setLifeSpan(2);

            Target hitTarget2 = new Target(new Position(14, 122), true);
            hitTarget2.targetHit();
            hitTarget2.setNumAttemptsToHit(2);
            hitTarget2.setLifeSpan(1);

            HitTargets hitTargets = new HitTargets();
            hitTargets.addTarget(hitTarget1);
            hitTargets.addTarget(hitTarget2);

            Target nonHitTarget1 = new Target(new Position(10, 22), true);
            nonHitTarget1.setNumAttemptsToHit(0);
            nonHitTarget1.setLifeSpan(0);

            Target nonHitTarget2 = new Target(new Position(43, 0), true);
            nonHitTarget2.setNumAttemptsToHit(0);
            nonHitTarget2.setLifeSpan(0);

            NonHitTargets nonHitTargets = new NonHitTargets();
            nonHitTargets.addTarget(nonHitTarget1);
            nonHitTargets.addTarget(nonHitTarget2);

            AimTrainerConsole atc = new AimTrainerConsole(targets, hitTargets, nonHitTargets,
                    2, 4, 2, 50.0, "moving", true);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMovingAimTrainingSession.json");
            writer.open();
            writer.write(atc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMovingAimTrainingSession.json");
            atc = reader.readAimTrainerConsole();
            checkAimTrainerConsole(2, 4, 2, 50.0, "moving",
                    true, atc);
            ArrayList<Target> targetsRead = atc.getTargets().getTargetsArray();
            ArrayList<Target> hitTargetsRead = atc.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargetsRead = atc.getNonHitTargets().getTargetsArray();
            assertEquals(2, targetsRead.size());
            assertEquals(2, hitTargetsRead.size());
            assertEquals(2, nonHitTargetsRead.size());
            for (Target t : targetsRead) {
                assertTrue(t.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(t.isHit());
                assertTrue(t.isMoving());
                assertEquals(1, t.getNumAttemptsToHit());
                assertEquals(3, t.getLifeSpan());
            }
            for (Target ht : hitTargetsRead) {
                assertTrue(ht.hasValidPosition(MAX_X, MAX_Y));
                assertTrue(ht.isHit());
                assertTrue(ht.isMoving());
                assertNotEquals(0, ht.getNumAttemptsToHit());
                assertNotEquals(0, ht.getLifeSpan());
            }
            for (Target nht : nonHitTargetsRead) {
                assertTrue(nht.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(nht.isHit());
                assertTrue(nht.isMoving());
                assertEquals(0, nht.getNumAttemptsToHit());
                assertEquals(0, nht.getLifeSpan());
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStationarySession() {
        try {
            Target target1 = new Target(new Position(1, 3), false);

            Target target2 = new Target(new Position(5, 9), false);

            Targets targets = new Targets();
            targets.addTarget(target1);
            targets.addTarget(target2);

            Target hitTarget1 = new Target(new Position(100, 99), false);
            hitTarget1.targetHit();
            hitTarget1.setNumAttemptsToHit(2);
            hitTarget1.setLifeSpan(2);

            Target hitTarget2 = new Target(new Position(14, 122), false);
            hitTarget2.targetHit();
            hitTarget2.setNumAttemptsToHit(2);
            hitTarget2.setLifeSpan(1);

            HitTargets hitTargets = new HitTargets();
            hitTargets.addTarget(hitTarget1);
            hitTargets.addTarget(hitTarget2);

            Target nonHitTarget1 = new Target(new Position(10, 22), false);
            nonHitTarget1.setNumAttemptsToHit(0);
            nonHitTarget1.setLifeSpan(0);

            Target nonHitTarget2 = new Target(new Position(43, 0), false);
            nonHitTarget2.setNumAttemptsToHit(0);
            nonHitTarget2.setLifeSpan(0);

            NonHitTargets nonHitTargets = new NonHitTargets();
            nonHitTargets.addTarget(nonHitTarget1);
            nonHitTargets.addTarget(nonHitTarget2);

            AimTrainerConsole atc = new AimTrainerConsole(targets, hitTargets, nonHitTargets,
                    2, 4, 2, 50.0, "stationary", false);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStationaryAimTrainingSession.json");
            writer.open();
            writer.write(atc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStationaryAimTrainingSession.json");
            atc = reader.readAimTrainerConsole();
            checkAimTrainerConsole(2, 4, 2, 50.0, "stationary",
                    false, atc);
            ArrayList<Target> targetsRead = atc.getTargets().getTargetsArray();
            ArrayList<Target> hitTargetsRead = atc.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargetsRead = atc.getNonHitTargets().getTargetsArray();
            assertEquals(2, targetsRead.size());
            assertEquals(2, hitTargetsRead.size());
            assertEquals(2, nonHitTargetsRead.size());
            for (Target t : targetsRead) {
                assertTrue(t.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(t.isHit());
                assertFalse(t.isMoving());
                assertEquals(1, t.getNumAttemptsToHit());
                assertEquals(3, t.getLifeSpan());
            }
            for (Target ht : hitTargetsRead) {
                assertTrue(ht.hasValidPosition(MAX_X, MAX_Y));
                assertTrue(ht.isHit());
                assertFalse(ht.isMoving());
                assertNotEquals(0, ht.getNumAttemptsToHit());
                assertNotEquals(0, ht.getLifeSpan());
            }
            for (Target nht : nonHitTargetsRead) {
                assertTrue(nht.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(nht.isHit());
                assertFalse(nht.isMoving());
                assertEquals(0, nht.getNumAttemptsToHit());
                assertEquals(0, nht.getLifeSpan());
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStationarySessionGUI() {
        try {
            Target target1 = new Target(new Position(1, 3), false);

            Target target2 = new Target(new Position(5, 9), false);

            Targets targets = new Targets();
            targets.addTarget(target1);
            targets.addTarget(target2);

            Target hitTarget1 = new Target(new Position(100, 99), false);
            hitTarget1.targetHit();
            hitTarget1.setNumAttemptsToHit(2);
            hitTarget1.setLifeSpan(2);

            Target hitTarget2 = new Target(new Position(14, 122), false);
            hitTarget2.targetHit();
            hitTarget2.setNumAttemptsToHit(2);
            hitTarget2.setLifeSpan(1);

            HitTargets hitTargets = new HitTargets();
            hitTargets.addTarget(hitTarget1);
            hitTargets.addTarget(hitTarget2);

            Target nonHitTarget1 = new Target(new Position(10, 22), false);
            nonHitTarget1.setNumAttemptsToHit(0);
            nonHitTarget1.setLifeSpan(0);

            Target nonHitTarget2 = new Target(new Position(43, 0), false);
            nonHitTarget2.setNumAttemptsToHit(0);
            nonHitTarget2.setLifeSpan(0);

            NonHitTargets nonHitTargets = new NonHitTargets();
            nonHitTargets.addTarget(nonHitTarget1);
            nonHitTargets.addTarget(nonHitTarget2);

            GamePanel gamePanel = new GamePanel(targets, hitTargets, nonHitTargets,
                                2, 4, 2, 50.0, false, "stationary") {
                @Override
                public void startGameThread() {

                }

                @Override
                public void displayGameOverScreen() {

                }
            };

            GameOverScreen gameOverScreen = new GameOverScreen(2, 50.0, gamePanel);

            JsonWriter writer = new JsonWriter("./data/savedSessions/testWriterGeneralStationaryAimTrainingSessionGUI.json");
            writer.open();
            writer.write(gameOverScreen);
            writer.close();

            JsonReader reader = new JsonReader("./data/savedSessions/testWriterGeneralStationaryAimTrainingSessionGUI.json");
            gamePanel = reader.readGamePanel();
            checkGamePanel(2, 4, 2, 50.0, "stationary",
                    false, gamePanel);
            ArrayList<Target> targetsRead = gamePanel.getTargets().getTargetsArray();
            ArrayList<Target> hitTargetsRead = gamePanel.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargetsRead = gamePanel.getNonHitTargets().getTargetsArray();
            assertEquals(2, targetsRead.size());
            assertEquals(2, hitTargetsRead.size());
            assertEquals(2, nonHitTargetsRead.size());
            for (Target t : targetsRead) {
                assertTrue(t.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(t.isHit());
                assertFalse(t.isMoving());
                assertEquals(1, t.getNumAttemptsToHit());
                assertEquals(3, t.getLifeSpan());
            }
            for (Target ht : hitTargetsRead) {
                assertTrue(ht.hasValidPosition(MAX_X, MAX_Y));
                assertTrue(ht.isHit());
                assertFalse(ht.isMoving());
                assertNotEquals(0, ht.getNumAttemptsToHit());
                assertNotEquals(0, ht.getLifeSpan());
            }
            for (Target nht : nonHitTargetsRead) {
                assertTrue(nht.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(nht.isHit());
                assertFalse(nht.isMoving());
                assertEquals(0, nht.getNumAttemptsToHit());
                assertEquals(0, nht.getLifeSpan());
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMovingSessionGUI() {
        try {
            Target target1 = new Target(new Position(1, 3), true);

            Target target2 = new Target(new Position(5, 9), true);

            Targets targets = new Targets();
            targets.addTarget(target1);
            targets.addTarget(target2);

            Target hitTarget1 = new Target(new Position(100, 99), true);
            hitTarget1.targetHit();
            hitTarget1.setNumAttemptsToHit(2);
            hitTarget1.setLifeSpan(2);

            Target hitTarget2 = new Target(new Position(14, 122), true);
            hitTarget2.targetHit();
            hitTarget2.setNumAttemptsToHit(2);
            hitTarget2.setLifeSpan(1);

            HitTargets hitTargets = new HitTargets();
            hitTargets.addTarget(hitTarget1);
            hitTargets.addTarget(hitTarget2);

            Target nonHitTarget1 = new Target(new Position(10, 22), true);
            nonHitTarget1.setNumAttemptsToHit(0);
            nonHitTarget1.setLifeSpan(0);

            Target nonHitTarget2 = new Target(new Position(43, 0), true);
            nonHitTarget2.setNumAttemptsToHit(0);
            nonHitTarget2.setLifeSpan(0);

            NonHitTargets nonHitTargets = new NonHitTargets();
            nonHitTargets.addTarget(nonHitTarget1);
            nonHitTargets.addTarget(nonHitTarget2);

            GamePanel gamePanel = new GamePanel(targets, hitTargets, nonHitTargets,
                                2, 4, 2, 50.0, true, "moving") {
                @Override
                public void startGameThread() {

                }

                @Override
                public void displayGameOverScreen() {

                }
            };

            GameOverScreen gameOverScreen = new GameOverScreen(2, 50.0, gamePanel);

            JsonWriter writer = new JsonWriter("./data/savedSessions/testWriterGeneralMovingAimTrainingSessionGUI.json");
            writer.open();
            writer.write(gameOverScreen);
            writer.close();

            JsonReader reader = new JsonReader("./data/savedSessions/testWriterGeneralMovingAimTrainingSessionGUI.json");
            gamePanel = reader.readGamePanel();
            checkGamePanel(2, 4, 2, 50.0, "moving",
                    true, gamePanel);
            ArrayList<Target> targetsRead = gamePanel.getTargets().getTargetsArray();
            ArrayList<Target> hitTargetsRead = gamePanel.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargetsRead = gamePanel.getNonHitTargets().getTargetsArray();
            assertEquals(2, targetsRead.size());
            assertEquals(2, hitTargetsRead.size());
            assertEquals(2, nonHitTargetsRead.size());
            for (Target t : targetsRead) {
                assertTrue(t.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(t.isHit());
                assertTrue(t.isMoving());
                assertEquals(1, t.getNumAttemptsToHit());
                assertEquals(3, t.getLifeSpan());
            }
            for (Target ht : hitTargetsRead) {
                assertTrue(ht.hasValidPosition(MAX_X, MAX_Y));
                assertTrue(ht.isHit());
                assertTrue(ht.isMoving());
                assertNotEquals(0, ht.getNumAttemptsToHit());
                assertNotEquals(0, ht.getLifeSpan());
            }
            for (Target nht : nonHitTargetsRead) {
                assertTrue(nht.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(nht.isHit());
                assertTrue(nht.isMoving());
                assertEquals(0, nht.getNumAttemptsToHit());
                assertEquals(0, nht.getLifeSpan());
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
