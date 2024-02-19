package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class NonHitTargetsTest {
    private NonHitTargets nonHitTargets;
    private Target t1;
    private Target t2;
    private Target t3;
    private Target t4;
    private Target t5;

    @BeforeEach
    public void setup() {
        nonHitTargets = new NonHitTargets();
        t1 = new Target(false, false, 2);
        t2 = new Target(false, false, 6);
        t3 = new Target(false, false, 1);
        t4 = new Target(false, false, 9);
        t5 = new Target(false, false, 4);
    }

    @Test
    public void testConstructor() {
        assertEquals(new ArrayList<Target>(), nonHitTargets.getTargetsArray());
    }

    // TARGET_SCORE = 1
    @Test
    public void testTotalScoreNotAchievedOneNonHitTarget() {
        assertFalse(t1.isHit());
        nonHitTargets.addTarget(t1);
        assertEquals(1, nonHitTargets.size());
        assertEquals(1, nonHitTargets.totalScoreNotAchieved());
    }

    @Test
    public void testTotalScoreMultipleHitTargets() {
        assertFalse(t1.isHit());
        assertFalse(t2.isHit());
        assertFalse(t3.isHit());
        assertFalse(t4.isHit());
        assertFalse(t5.isHit());
        nonHitTargets.addTarget(t1);
        nonHitTargets.addTarget(t2);
        nonHitTargets.addTarget(t3);
        nonHitTargets.addTarget(t4);
        nonHitTargets.addTarget(t5);
        assertEquals(5, nonHitTargets.size());
        assertEquals(5, nonHitTargets.totalScoreNotAchieved());
    }
}
