package agh.ics.oop.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {
    @Test
    public void toStringTest(){
        assertEquals("N", Direction.NORTH.toString());
        assertEquals("S", Direction.SOUTH.toString());
        assertEquals("E", Direction.EAST.toString());
        assertEquals("W", Direction.WEST.toString());
        assertEquals("NE", Direction.NORTHEAST.toString());
        assertEquals("NW", Direction.NORTHWEST.toString());
        assertEquals("SE", Direction.SOUTHEAST.toString());
        assertEquals("SW", Direction.SOUTHWEST.toString());
    }

    @Test
    public void toUnitVectorTest() {
        assertEquals(new Vector2d(0,1), Direction.NORTH.toUnitVector());
        assertEquals(new Vector2d(0, -1), Direction.SOUTH.toUnitVector());
        assertEquals(new Vector2d(1, 0), Direction.EAST.toUnitVector());
        assertEquals(new Vector2d(-1, 0), Direction.WEST.toUnitVector());
        assertEquals(new Vector2d(-1, 1), Direction.NORTHWEST.toUnitVector());
        assertEquals(new Vector2d(1, -1), Direction.SOUTHEAST.toUnitVector());
        assertEquals(new Vector2d(-1, -1), Direction.SOUTHWEST.toUnitVector());
        assertEquals(new Vector2d(1, 1), Direction.NORTHEAST.toUnitVector());
    }
}
