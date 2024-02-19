package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {
    private Position pos1;

    @BeforeEach
    public void setup() {
        pos1 = new Position(12,92);
    }

    @Test
    public void testConstructor() {
        assertEquals(12, pos1.getX());
        assertEquals(92, pos1.getY());
    }

}
