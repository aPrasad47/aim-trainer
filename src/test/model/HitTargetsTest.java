package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HitTargetsTest {
    private HitTargets hitTargets;
    private Target t1;
    private Target t2;
    private Target t3;
    private Target t4;
    private Target t5;

    @BeforeEach
    public void setup() {
        hitTargets = new HitTargets();
        t1 = new Target(true, true, 2);
        t2 = new Target(true, true, 6);
        t3 = new Target(true, true, 1);
        t4 = new Target(true, true, 9);
        t5 = new Target(true, true, 4);
    }

    @Test
    public void testConstructor() {
        assertEquals(new ArrayList<Target>(), hitTargets.getTargetsArray());
    }

    @Test
    public void testAverageNumAttemptsToHitTargetOneHitTarget() {
        assertTrue(t1.isHit());
        hitTargets.addTarget(t1);
        assertEquals(1, hitTargets.size());
        assertEquals(2.0, hitTargets.averageNumAttemptsToHitTarget());
    }

    @Test
    public void testAverageNumAttemptsToHitTargetMultipleHitTargets() {
        assertTrue(t1.isHit());
        assertTrue(t2.isHit());
        assertTrue(t3.isHit());
        assertTrue(t4.isHit());
        assertTrue(t5.isHit());
        hitTargets.addTarget(t1);
        hitTargets.addTarget(t2);
        hitTargets.addTarget(t3);
        hitTargets.addTarget(t4);
        hitTargets.addTarget(t5);
        assertEquals(5, hitTargets.size());
        assertEquals(4.4, hitTargets.averageNumAttemptsToHitTarget());
    }

    @Test
    public void testTotalScoreOneHitTarget() {
        assertTrue(t1.isHit());
        hitTargets.addTarget(t1);
        assertEquals(1, hitTargets.size());
        assertEquals(1, hitTargets.totalScore());
    }

    @Test
    public void testTotalScoreMultipleHitTargets() {
        assertTrue(t1.isHit());
        assertTrue(t2.isHit());
        assertTrue(t3.isHit());
        assertTrue(t4.isHit());
        assertTrue(t5.isHit());
        hitTargets.addTarget(t1);
        hitTargets.addTarget(t2);
        hitTargets.addTarget(t3);
        hitTargets.addTarget(t4);
        hitTargets.addTarget(t5);
        assertEquals(5, hitTargets.size());
        assertEquals(5, hitTargets.totalScore());
    }
}
