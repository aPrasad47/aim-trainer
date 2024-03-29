package persistence;

import model.Target;
import org.junit.jupiter.api.Test;
import ui.AimTrainerConsole;
import ui.GamePanel;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
    private static final int MAX_X = 200;
    private static final int MAX_Y = 200;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readAimTrainerConsole();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderFirstQuitSession() {
        JsonReader reader = new JsonReader("./data/testReaderFirstQuitAimTrainingSession.json");
        try {
            AimTrainerConsole atc = reader.readAimTrainerConsole();
            checkAimTrainerConsole(0, 0, 0, 100.0, "",
                    false, atc);
            ArrayList<Target> targets = atc.getTargets().getTargetsArray();
            ArrayList<Target> hitTargets = atc.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargets = atc.getNonHitTargets().getTargetsArray();
            assertEquals(0, targets.size());
            assertEquals(0, hitTargets.size());
            assertEquals(0, nonHitTargets.size());
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    void testReaderGeneralMovingSession() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMovingAimTrainingSession.json");
        try {
            AimTrainerConsole atc = reader.readAimTrainerConsole();
            checkAimTrainerConsole(4, 6, 4, 66.66666666666666, "moving",
                    true, atc);
            ArrayList<Target> targets = atc.getTargets().getTargetsArray();
            ArrayList<Target> hitTargets = atc.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargets = atc.getNonHitTargets().getTargetsArray();
            assertEquals(2, targets.size());
            assertEquals(4, hitTargets.size());
            assertEquals(2, nonHitTargets.size());
            for (Target t : targets) {
                assertTrue(t.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(t.isHit());
                assertTrue(t.isMoving());
                assertEquals(1, t.getNumAttemptsToHit());
                assertEquals(3, t.getLifeSpan());
            }
            for (Target ht : hitTargets) {
                assertTrue(ht.hasValidPosition(MAX_X, MAX_Y));
                assertTrue(ht.isHit());
                assertTrue(ht.isMoving());
                assertNotEquals(0, ht.getNumAttemptsToHit());
                assertNotEquals(0, ht.getLifeSpan());
            }
            for (Target nht : nonHitTargets) {
                assertTrue(nht.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(nht.isHit());
                assertTrue(nht.isMoving());
                assertEquals(0, nht.getNumAttemptsToHit());
                assertEquals(0, nht.getLifeSpan());
            }
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    void testReaderGeneralStationarySession() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStationaryAimTrainingSession.json");
        try {
            AimTrainerConsole atc = reader.readAimTrainerConsole();
            checkAimTrainerConsole(4, 6, 4, 66.66666666666666, "stationary",
                    false, atc);
            ArrayList<Target> targets = atc.getTargets().getTargetsArray();
            ArrayList<Target> hitTargets = atc.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargets = atc.getNonHitTargets().getTargetsArray();
            assertEquals(2, targets.size());
            assertEquals(4, hitTargets.size());
            assertEquals(2, nonHitTargets.size());
            for (Target t : targets) {
                assertTrue(t.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(t.isHit());
                assertFalse(t.isMoving());
                assertEquals(1, t.getNumAttemptsToHit());
                assertEquals(3, t.getLifeSpan());
            }
            for (Target ht : hitTargets) {
                assertTrue(ht.hasValidPosition(MAX_X, MAX_Y));
                assertTrue(ht.isHit());
                assertFalse(ht.isMoving());
                assertNotEquals(0, ht.getNumAttemptsToHit());
                assertNotEquals(0, ht.getLifeSpan());
            }
            for (Target nht : nonHitTargets) {
                assertTrue(nht.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(nht.isHit());
                assertFalse(nht.isMoving());
                assertEquals(0, nht.getNumAttemptsToHit());
                assertEquals(0, nht.getLifeSpan());
            }
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    void testReaderGeneralMovingSessionGUI() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMovingAimTrainingSessionGUI.json");
        try {
            GamePanel gamePanel = reader.readGamePanel();
            checkGamePanel(4, 6, 4, 66.66666666666666, "moving",
                    true, gamePanel);
            ArrayList<Target> targets = gamePanel.getTargets().getTargetsArray();
            ArrayList<Target> hitTargets = gamePanel.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargets = gamePanel.getNonHitTargets().getTargetsArray();
            assertEquals(2, targets.size());
            assertEquals(4, hitTargets.size());
            assertEquals(2, nonHitTargets.size());
            for (Target t : targets) {
                assertTrue(t.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(t.isHit());
                assertTrue(t.isMoving());
                assertEquals(1, t.getNumAttemptsToHit());
                assertEquals(3, t.getLifeSpan());
            }
            for (Target ht : hitTargets) {
                assertTrue(ht.hasValidPosition(MAX_X, MAX_Y));
                assertTrue(ht.isHit());
                assertTrue(ht.isMoving());
                assertNotEquals(0, ht.getNumAttemptsToHit());
                assertNotEquals(0, ht.getLifeSpan());
            }
            for (Target nht : nonHitTargets) {
                assertTrue(nht.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(nht.isHit());
                assertTrue(nht.isMoving());
                assertEquals(0, nht.getNumAttemptsToHit());
                assertEquals(0, nht.getLifeSpan());
            }
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test void testReaderGeneralStationarySessionGUI() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStationaryAimTrainingSessionGUI.json");
        try {
            GamePanel gamePanel = reader.readGamePanel();
            checkGamePanel(4, 6, 4, 66.66666666666666, "stationary",
                    false, gamePanel);
            ArrayList<Target> targets = gamePanel.getTargets().getTargetsArray();
            ArrayList<Target> hitTargets = gamePanel.getHitTargets().getTargetsArray();
            ArrayList<Target> nonHitTargets = gamePanel.getNonHitTargets().getTargetsArray();
            assertEquals(2, targets.size());
            assertEquals(4, hitTargets.size());
            assertEquals(2, nonHitTargets.size());
            for (Target t : targets) {
                assertTrue(t.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(t.isHit());
                assertFalse(t.isMoving());
                assertEquals(1, t.getNumAttemptsToHit());
                assertEquals(3, t.getLifeSpan());
            }
            for (Target ht : hitTargets) {
                assertTrue(ht.hasValidPosition(MAX_X, MAX_Y));
                assertTrue(ht.isHit());
                assertFalse(ht.isMoving());
                assertNotEquals(0, ht.getNumAttemptsToHit());
                assertNotEquals(0, ht.getLifeSpan());
            }
            for (Target nht : nonHitTargets) {
                assertTrue(nht.hasValidPosition(MAX_X, MAX_Y));
                assertFalse(nht.isHit());
                assertFalse(nht.isMoving());
                assertEquals(0, nht.getNumAttemptsToHit());
                assertEquals(0, nht.getLifeSpan());
            }
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }


}
