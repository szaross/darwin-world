package agh.ics.oop.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Vector2dTest {
    private Vector2d vec1;
    private Vector2d vec2;
    private Vector2d vec3;
    private Vector2d vec4;

    @BeforeEach
    public void setUp() {
        vec1 = new Vector2d(2, 3);
        vec2 = new Vector2d(5, 7);
        vec3 = new Vector2d(4, -3);
        vec4 = new Vector2d(-1, -3);
    }

    @Test
    public void toStringTest() {
        assertEquals("(2,3)", vec1.toString());
        assertEquals("(5,7)", vec2.toString());
        assertEquals("(4,-3)", vec3.toString());
        assertEquals("(-1,-3)", vec4.toString());
    }

    @Test
    public void addTest() {
        assertEquals(new Vector2d(7, 10), vec1.add(vec2));
        assertEquals(new Vector2d(3, -6), vec3.add(vec4));
    }

    @Test
    public void subtractTest() {
        assertEquals(new Vector2d(-3, -4), vec1.subtract(vec2));
        assertEquals(new Vector2d(5, 0), vec3.subtract(vec4));
    }

    @Test
    public void equalsTest() {
        assertNotEquals(vec1, vec2);
        assertEquals(vec1, vec1);
        assertEquals(vec1, new Vector2d(2, 3));
    }

    @Test
    public void getTest() {
        assertEquals(2, vec1.x());
        assertEquals(5, vec2.x());
        assertEquals(4, vec3.x());
        assertEquals(-3, vec3.y());
        assertEquals(-3, vec4.y());
    }

    @Test
    public void oppositeTest() {
        assertEquals(new Vector2d(3, 2), vec1.opposite());
        assertEquals(new Vector2d(-3, -1), vec4.opposite());
    }
}
