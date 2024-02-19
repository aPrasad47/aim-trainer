package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TargetsTest {
    private Targets targets;
    private Target t1;
    private Target t2;
    private Target t3;
    private Target t4;
    private Target t5;

    @BeforeEach
    public void setup() {
        targets = new Targets();
        t1 = new Target();
        t2 = new Target();
        t3 = new Target();
        t4 = new Target();
        t5 = new Target();
    }

    @Test
    public void testConstructor() {
        assertEquals(new ArrayList<Target>(), targets.getTargetsArray());
    }

    @Test
    public void testAddTargetOneTarget() {
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
        targets.addTarget(t1);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertTrue(targets.contains(t1));
    }

    @Test
    public void testAddTargetMultipleTargets() {
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
        targets.addTarget(t1);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertTrue(targets.contains(t1));
        targets.addTarget(t2);
        assertFalse(targets.isEmpty());
        assertEquals(2, targets.size());
        assertTrue(targets.contains(t2));
        targets.addTarget(t3);
        assertFalse(targets.isEmpty());
        assertEquals(3, targets.size());
        assertTrue(targets.contains(t3));
        targets.addTarget(t4);
        assertFalse(targets.isEmpty());
        assertEquals(4, targets.size());
        assertTrue(targets.contains(t4));
        targets.addTarget(t5);
        assertFalse(targets.isEmpty());
        assertEquals(5, targets.size());
        assertTrue(targets.contains(t5));
    }

    @Test
    public void testRemoveTargetAtIndexOneTarget() {
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
        targets.addTarget(t1);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertTrue(targets.contains(t1));
        targets.removeTargetAtIndex(0);
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
        assertFalse(targets.contains(t1));
    }

    @Test
    public void testRemoveTargetAtIndexMultipleTargetsUntilEmpty() {
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
        targets.addTarget(t1);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertTrue(targets.contains(t1));
        targets.addTarget(t2);
        assertFalse(targets.isEmpty());
        assertEquals(2, targets.size());
        assertTrue(targets.contains(t2));
        targets.addTarget(t3);
        assertFalse(targets.isEmpty());
        assertEquals(3, targets.size());
        assertTrue(targets.contains(t3));
        targets.addTarget(t4);
        assertFalse(targets.isEmpty());
        assertEquals(4, targets.size());
        assertTrue(targets.contains(t4));
        targets.addTarget(t5);
        assertFalse(targets.isEmpty());
        assertEquals(5, targets.size());
        assertTrue(targets.contains(t5));
        targets.removeTargetAtIndex(0);
        assertFalse(targets.isEmpty());
        assertEquals(4, targets.size());
        assertFalse(targets.contains(t1));
        targets.removeTargetAtIndex(0);
        assertFalse(targets.isEmpty());
        assertEquals(3, targets.size());
        assertFalse(targets.contains(t2));
        targets.removeTargetAtIndex(0);
        assertFalse(targets.isEmpty());
        assertEquals(2, targets.size());
        assertFalse(targets.contains(t3));
        targets.removeTargetAtIndex(0);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertFalse(targets.contains(t4));
        targets.removeTargetAtIndex(0);
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
        assertFalse(targets.contains(t5));
    }

    @Test
    public void testRemoveTargetAtIndexMultipleTargetsNotUntilEmpty() {
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
        targets.addTarget(t1);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertTrue(targets.contains(t1));
        targets.addTarget(t2);
        assertFalse(targets.isEmpty());
        assertEquals(2, targets.size());
        assertTrue(targets.contains(t2));
        targets.addTarget(t3);
        assertFalse(targets.isEmpty());
        assertEquals(3, targets.size());
        assertTrue(targets.contains(t3));
        targets.addTarget(t4);
        assertFalse(targets.isEmpty());
        assertEquals(4, targets.size());
        assertTrue(targets.contains(t4));
        targets.addTarget(t5);
        assertFalse(targets.isEmpty());
        assertEquals(5, targets.size());
        assertTrue(targets.contains(t5));
        targets.removeTargetAtIndex(0);
        assertFalse(targets.isEmpty());
        assertEquals(4, targets.size());
        assertFalse(targets.contains(t1));
        targets.removeTargetAtIndex(0);
        assertFalse(targets.isEmpty());
        assertEquals(3, targets.size());
        assertFalse(targets.contains(t2));
    }

    @Test
    public void testIsEmptyEmpty() {
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
    }

    @Test
    public void testIsEmptyNotEmptyOneTarget() {
        targets.addTarget(t1);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertTrue(targets.contains(t1));
    }

    @Test
    public void testIsEmptyNotEmptyMultipleTargets() {
        targets.addTarget(t1);
        targets.addTarget(t2);
        targets.addTarget(t3);
        assertFalse(targets.isEmpty());
        assertEquals(3, targets.size());
        assertTrue(targets.contains(t1));
        assertTrue(targets.contains(t2));
        assertTrue(targets.contains(t3));
    }

    @Test
    public void testContainsContains() {
        targets.addTarget(t1);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertTrue(targets.contains(t1));
    }

    @Test
    public void testContainsNotContainsEmpty() {
        assertTrue(targets.isEmpty());
        assertEquals(0, targets.size());
        assertFalse(targets.contains(t1));
    }

    @Test
    public void testContainsNotContainsNotEmpty() {
        targets.addTarget(t1);
        assertFalse(targets.isEmpty());
        assertEquals(1, targets.size());
        assertFalse(targets.contains(t2));
    }

    @Test
    public void testSizeEmpty() {
        assertEquals(0, targets.size());
        assertTrue(targets.isEmpty());
    }

    @Test
    public void testSizeNotEmptyOneTarget() {
        targets.addTarget(t1);
        assertEquals(1, targets.size());
        assertFalse(targets.isEmpty());
    }

    @Test
    public void testSizeNotEmptyMultipleTargets() {
        targets.addTarget(t1);
        assertEquals(1, targets.size());
        assertFalse(targets.isEmpty());
        targets.addTarget(t2);
        assertEquals(2, targets.size());
        assertFalse(targets.isEmpty());
        targets.addTarget(t3);
        assertEquals(3, targets.size());
        assertFalse(targets.isEmpty());
    }
}
