package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TargetTest {
    private static final int MAX_X = 101;
    private static final int MAX_Y = 81;

    private Position topLeftScreen;
    private Position topRightScreen;
    private Position topMidScreen;
    private Position midLeftScreen;
    private Position midRightScreen;
    private Position bottomLeftScreen;
    private Position bottomRightScreen;
    private Position bottomMidScreen;
    private Position arbitraryInBounds;
    private Position arbitraryInBoundsNotCollided;
    private Position arbitraryInBoundsCollided;
    private Position coordXGreaterThanMax;
    private Position coordYGreaterThanMax;
    private Position coordXLessThanZero;
    private Position coordYLessThanZero;
    private Position allOutOfBounds;
    private Position diffYThanArbitraryInBounds;
    private Position sameYThanArbitraryInBounds;
    private Target targetTopLeftMoving;
    private Target targetTopRightMoving;
    private Target targetTopMidMoving;
    private Target targetMidLeftMoving;
    private Target targetMidRightMoving;
    private Target targetBottomLeftMoving;
    private Target targetBottomRightMoving;
    private Target targetBottomMidMoving;
    private Target targetArbitraryInBoundsMoving;
    private Target targetArbitraryInBoundsNotCollided;
    private Target targetArbitraryInBoundsCollided;
    private Target targetCoordXGreaterThanMax;
    private Target targetCoordYGreaterThanMax;
    private Target targetCoordXLessThanZero;
    private Target targetCoordYLessThanZero;
    private Target targetAllOutOfBounds;
    private Target targetDiffYThanArbitraryInBounds;
    private Target targetSameYThanArbitraryInBounds;

    @BeforeEach
    public void setup() {
        topLeftScreen = new Position(0, 0);
        topRightScreen = new Position(MAX_X - 1, 0);
        topMidScreen = new Position((MAX_X - 1) / 2, 0);
        midLeftScreen = new Position(0, (MAX_Y - 1) / 2);
        midRightScreen = new Position(MAX_X - 1, (MAX_Y - 1) / 2);
        bottomLeftScreen = new Position(0, MAX_Y - 1);
        bottomRightScreen = new Position(MAX_X - 1, MAX_Y - 1);
        bottomMidScreen = new Position((MAX_X - 1) / 2, MAX_Y - 1);
        arbitraryInBounds = new Position(3, 12);
        arbitraryInBoundsNotCollided = new Position(14, 7);
        arbitraryInBoundsCollided = new Position(3, 12);
        coordXGreaterThanMax = new Position(MAX_X + 1, 5);
        coordYGreaterThanMax = new Position(7, MAX_Y + 1);
        coordXLessThanZero = new Position(-1, 5);
        coordYLessThanZero = new Position(4, -1);
        allOutOfBounds = new Position(-1, -1);
        diffYThanArbitraryInBounds = new Position(3, 14);
        sameYThanArbitraryInBounds = new Position(5, 12);
        targetTopLeftMoving = new Target(topLeftScreen, true);
        targetTopRightMoving = new Target(topRightScreen, true);
        targetTopMidMoving = new Target(topMidScreen, true);
        targetMidLeftMoving = new Target(midLeftScreen, true);
        targetMidRightMoving = new Target(midRightScreen, true);
        targetBottomLeftMoving = new Target(bottomLeftScreen, true);
        targetBottomRightMoving = new Target(bottomRightScreen, true);
        targetBottomMidMoving = new Target(bottomMidScreen, true);
        targetArbitraryInBoundsMoving = new Target(arbitraryInBounds, true);
        targetArbitraryInBoundsNotCollided = new Target(arbitraryInBoundsNotCollided, false);
        targetArbitraryInBoundsCollided = new Target(arbitraryInBoundsCollided, false);
        targetCoordXGreaterThanMax = new Target(coordXGreaterThanMax, true);
        targetCoordYGreaterThanMax = new Target(coordYGreaterThanMax, true);
        targetCoordXLessThanZero = new Target(coordXLessThanZero, true);
        targetCoordYLessThanZero = new Target(coordYLessThanZero, true);
        targetAllOutOfBounds = new Target(allOutOfBounds, true);
        targetDiffYThanArbitraryInBounds = new Target(diffYThanArbitraryInBounds, true);
        targetSameYThanArbitraryInBounds = new Target(sameYThanArbitraryInBounds, true);
    }

    @Test
    public void testConstructorArbitraryMoving() {
        assertEquals(arbitraryInBounds, targetArbitraryInBoundsMoving.getPosition());
        assertFalse(targetArbitraryInBoundsMoving.isHit());
        assertTrue(targetArbitraryInBoundsMoving.isMoving());
        assertEquals(1, targetArbitraryInBoundsMoving.getNumAttemptsToHit());
        assertEquals(3, targetArbitraryInBoundsMoving.getLifeSpan());
    }

    @Test
    public void testMoveTopLeft() {
        int initialX = targetTopLeftMoving.getPosition().getX();
        int initialY = targetTopLeftMoving.getPosition().getY();
        assertEquals(topLeftScreen, targetTopLeftMoving.getPosition());
        targetTopLeftMoving.move(MAX_X, MAX_Y);
        assertTrue(targetTopLeftMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetTopLeftMoving.getPosition().getX());
        assertNotEquals(initialY, targetTopLeftMoving.getPosition().getY());
    }

    @Test
    public void testMoveTopRight() {
        int initialX = targetTopRightMoving.getPosition().getX();
        int initialY = targetTopRightMoving.getPosition().getY();
        assertEquals(topRightScreen, targetTopRightMoving.getPosition());
        targetTopRightMoving.move(MAX_X, MAX_Y);
        assertTrue(targetTopRightMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetTopRightMoving.getPosition().getX());
        assertNotEquals(initialY, targetTopRightMoving.getPosition().getY());
    }

    @Test
    public void testMoveTopMid() {
        int initialX = targetTopMidMoving.getPosition().getX();
        int initialY = targetTopMidMoving.getPosition().getY();
        assertEquals(topMidScreen, targetTopMidMoving.getPosition());
        targetTopMidMoving.move(MAX_X, MAX_Y);
        assertTrue(targetTopMidMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetTopMidMoving.getPosition().getX());
        assertNotEquals(initialY, targetTopMidMoving.getPosition().getY());
    }

    @Test
    public void testMoveMidLeft() {
        int initialX = targetMidLeftMoving.getPosition().getX();
        int initialY = targetMidLeftMoving.getPosition().getY();
        assertEquals(midLeftScreen, targetMidLeftMoving.getPosition());
        targetMidLeftMoving.move(MAX_X, MAX_Y);
        assertTrue(targetMidLeftMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetMidLeftMoving.getPosition().getX());
        assertNotEquals(initialY, targetMidLeftMoving.getPosition().getY());
    }

    @Test
    public void testMoveMidRight() {
        int initialX = targetMidRightMoving.getPosition().getX();
        int initialY = targetMidRightMoving.getPosition().getY();
        assertEquals(midRightScreen, targetMidRightMoving.getPosition());
        targetMidRightMoving.move(MAX_X, MAX_Y);
        assertTrue(targetMidRightMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetMidRightMoving.getPosition().getX());
        assertNotEquals(initialY, targetMidRightMoving.getPosition().getY());
    }

    @Test
    public void testMoveBottomLeft() {
        int initialX = targetBottomLeftMoving.getPosition().getX();
        int initialY = targetBottomLeftMoving.getPosition().getY();
        assertEquals(bottomLeftScreen, targetBottomLeftMoving.getPosition());
        targetBottomLeftMoving.move(MAX_X, MAX_Y);
        assertTrue(targetBottomLeftMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetBottomLeftMoving.getPosition().getX());
        assertNotEquals(initialY, targetBottomLeftMoving.getPosition().getY());
    }

    @Test
    public void testMoveBottomRight() {
        int initialX = targetBottomRightMoving.getPosition().getX();
        int initialY = targetBottomRightMoving.getPosition().getY();
        assertEquals(bottomRightScreen, targetBottomRightMoving.getPosition());
        targetBottomRightMoving.move(MAX_X, MAX_Y);
        assertTrue(targetBottomRightMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetBottomRightMoving.getPosition().getX());
        assertNotEquals(initialY, targetBottomRightMoving.getPosition().getY());
    }

    @Test
    public void testMoveBottomMid() {
        int initialX = targetBottomMidMoving.getPosition().getX();
        int initialY = targetBottomMidMoving.getPosition().getY();
        assertEquals(bottomMidScreen, targetBottomMidMoving.getPosition());
        targetBottomMidMoving.move(MAX_X, MAX_Y);
        assertTrue(targetBottomMidMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetBottomMidMoving.getPosition().getX());
        assertNotEquals(initialY, targetBottomMidMoving.getPosition().getY());
    }

    @Test
    public void testMoveArbitraryInBounds() {
        int initialX = targetArbitraryInBoundsMoving.getPosition().getX();
        int initialY = targetArbitraryInBoundsMoving.getPosition().getY();
        assertEquals(arbitraryInBounds, targetArbitraryInBoundsMoving.getPosition());
        targetArbitraryInBoundsMoving.move(MAX_X, MAX_Y);
        assertTrue(targetArbitraryInBoundsMoving.hasValidPosition(MAX_X, MAX_Y));
        assertNotEquals(initialX, targetArbitraryInBoundsMoving.getPosition().getX());
        assertNotEquals(initialY, targetArbitraryInBoundsMoving.getPosition().getY());
    }

    @Test
    public void testHasCollidedWithTargetNotCollidedWithArbitraryTarget() {
        assertNotEquals(3, targetArbitraryInBoundsNotCollided.getPosition().getX());
        assertNotEquals(12, targetArbitraryInBoundsNotCollided.getPosition().getY());
        assertNotEquals(14, targetArbitraryInBoundsMoving.getPosition().getX());
        assertNotEquals(7, targetArbitraryInBoundsMoving.getPosition().getY());
        assertFalse(targetArbitraryInBoundsMoving.hasCollidedWithTarget(targetArbitraryInBoundsNotCollided));
    }

    @Test
    public void testHasCollidedWithTargetNotCollidedWithArbitraryTargetDiffYSameX() {
        assertEquals(3, targetDiffYThanArbitraryInBounds.getPosition().getX());
        assertNotEquals(12, targetDiffYThanArbitraryInBounds.getPosition().getY());
        assertEquals(3, targetArbitraryInBoundsMoving.getPosition().getX());
        assertNotEquals(14, targetArbitraryInBoundsMoving.getPosition().getY());
        assertFalse(targetArbitraryInBoundsMoving.hasCollidedWithTarget(targetDiffYThanArbitraryInBounds));
    }

    @Test
    public void testHasCollidedWithTargetNotCollidedWithArbitraryTargetSameYDiffX() {
        assertNotEquals(3, targetSameYThanArbitraryInBounds.getPosition().getX());
        assertEquals(12, targetSameYThanArbitraryInBounds.getPosition().getY());
        assertNotEquals(5, targetArbitraryInBoundsMoving.getPosition().getX());
        assertEquals(12, targetArbitraryInBoundsMoving.getPosition().getY());
        assertFalse(targetArbitraryInBoundsMoving.hasCollidedWithTarget(targetDiffYThanArbitraryInBounds));
    }

    @Test
    public void testHasCollidedWithTargetCollidedWithArbitraryTarget() {
        assertEquals(3, targetArbitraryInBoundsCollided.getPosition().getX());
        assertEquals(12, targetArbitraryInBoundsCollided.getPosition().getY());
        assertEquals(3, targetArbitraryInBoundsMoving.getPosition().getX());
        assertEquals(12, targetArbitraryInBoundsMoving.getPosition().getY());
        assertTrue(targetArbitraryInBoundsMoving.hasCollidedWithTarget(targetArbitraryInBoundsCollided));
    }

    @Test
    public void testHasValidPositionTopLeft() {
        assertTrue(targetTopLeftMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionTopRight() {
        assertTrue(targetTopRightMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionTopMid() {
        assertTrue(targetTopMidMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionMidLeft() {
        assertTrue(targetMidLeftMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionMidRight() {
        assertTrue(targetMidRightMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionBottomLeft() {
        assertTrue(targetBottomLeftMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionBottomRight() {
        assertTrue(targetBottomRightMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionBottomMid() {
        assertTrue(targetBottomMidMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionArbitraryInBounds() {
        assertTrue(targetArbitraryInBoundsMoving.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionCoordXGreaterThanMax() {
        assertFalse(targetCoordXGreaterThanMax.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionCoordYGreaterThanMax() {
        assertFalse(targetCoordYGreaterThanMax.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionCoordXLessThanZero() {
        assertFalse(targetCoordXLessThanZero.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionCoordYLessThanZero() {
        assertFalse(targetCoordYLessThanZero.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testHasValidPositionAllOutOfBounds() {
        assertFalse(targetAllOutOfBounds.hasValidPosition(MAX_X, MAX_Y));
    }

    @Test
    public void testTargetHitTargetNotYetHit() {
        assertFalse(targetArbitraryInBoundsCollided.isHit());
        targetArbitraryInBoundsCollided.targetHit();
        assertTrue(targetArbitraryInBoundsCollided.isHit());
    }

    @Test
    public void testIncrementNumAttemptToHitOnce() {
        assertEquals(1, targetBottomLeftMoving.getNumAttemptsToHit());
        targetBottomLeftMoving.incrementNumAttemptsToHit();
        assertEquals(2, targetBottomLeftMoving.getNumAttemptsToHit());
    }

    @Test
    public void testIncrementNumAttemptToHitMultipleTimes() {
        assertEquals(1, targetBottomLeftMoving.getNumAttemptsToHit());
        targetBottomLeftMoving.incrementNumAttemptsToHit();
        assertEquals(2, targetBottomLeftMoving.getNumAttemptsToHit());
        targetBottomLeftMoving.incrementNumAttemptsToHit();
        assertEquals(3, targetBottomLeftMoving.getNumAttemptsToHit());
        targetBottomLeftMoving.incrementNumAttemptsToHit();
        assertEquals(4, targetBottomLeftMoving.getNumAttemptsToHit());
    }

    @Test
    public void testDecrementLifeSpanOnceNotDead() {
        assertEquals(3, targetBottomLeftMoving.getLifeSpan());
        targetBottomLeftMoving.decrementLifeSpan();
        assertEquals(2, targetBottomLeftMoving.getLifeSpan());
    }

    @Test
    public void testDecrementLifeSpanMultipleTimesNotDead() {
        assertEquals(3, targetBottomLeftMoving.getLifeSpan());
        targetBottomLeftMoving.decrementLifeSpan();
        assertEquals(2, targetBottomLeftMoving.getLifeSpan());
        targetBottomLeftMoving.decrementLifeSpan();
        assertEquals(1, targetBottomLeftMoving.getLifeSpan());
    }

    @Test
    public void testDecrementLifeSpanMultipleTimesDead() {
        assertEquals(3, targetBottomLeftMoving.getLifeSpan());
        targetBottomLeftMoving.decrementLifeSpan();
        assertEquals(2, targetBottomLeftMoving.getLifeSpan());
        targetBottomLeftMoving.decrementLifeSpan();
        assertEquals(1, targetBottomLeftMoving.getLifeSpan());
        targetBottomLeftMoving.decrementLifeSpan();
        assertEquals(0, targetBottomLeftMoving.getLifeSpan());
    }

    @Test
    public void testIncrementTargetSize() {

    }

}
