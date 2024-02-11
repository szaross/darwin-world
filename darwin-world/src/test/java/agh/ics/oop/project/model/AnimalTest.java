package agh.ics.oop.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    private WorldMap map;
    private Genotype gen1;
    private Animal animal;
    private Vector2d start;

    @BeforeEach
    public void setUp() {
        map = new WorldMap(10, 10);
        gen1 = new Genotype(new ArrayList<>(List.of(0)));
    }

    @Test
    void moveWallTest() {
        // upper wall
        start = new Vector2d(5, 9);
        animal = new Animal(start, 0, gen1, 0, Direction.NORTH);
        animal.move(map);
        assertEquals(new Vector2d(5, 9), animal.getPosition());
        assertEquals(Direction.SOUTH, animal.getDirection());

        // lower wall
        start = new Vector2d(5, 0);
        animal = new Animal(start, 0, gen1, 0, Direction.SOUTH);
        animal.move(map);
        assertEquals(new Vector2d(5, 0), animal.getPosition());
        assertEquals(Direction.NORTH, animal.getDirection());

        // right wall
        start = new Vector2d(9, 5);
        animal = new Animal(start, 0, gen1, 0, Direction.EAST);
        animal.move(map);
        assertEquals(new Vector2d(0, 5), animal.getPosition());
        assertEquals(Direction.EAST, animal.getDirection());

        // left wall
        start = new Vector2d(0, 5);
        animal = new Animal(start, 0, gen1, 0, Direction.WEST);
        animal.move(map);
        assertEquals(new Vector2d(9, 5), animal.getPosition());
        assertEquals(Direction.WEST, animal.getDirection());
    }

    @Test
    void moveCornerTest() {
        // upper right corner
        start = new Vector2d(9, 9);
        animal = new Animal(start, 0, gen1, 0, Direction.NORTHEAST);
        animal.move(map);
        assertEquals(new Vector2d(0, 9), animal.getPosition());
        assertEquals(Direction.SOUTHWEST, animal.getDirection());

        // upper left corner
        start = new Vector2d(0, 9);
        animal = new Animal(start, 0, gen1, 0, Direction.NORTHWEST);
        animal.move(map);
        assertEquals(new Vector2d(9, 9), animal.getPosition());
        assertEquals(Direction.SOUTHEAST, animal.getDirection());

        // lower right corner
        start = new Vector2d(9, 0);
        animal = new Animal(start, 0, gen1, 0, Direction.SOUTHEAST);
        animal.move(map);
        assertEquals(new Vector2d(0, 0), animal.getPosition());
        assertEquals(Direction.NORTHWEST, animal.getDirection());

        // lower left corner
        start = new Vector2d(0, 0);
        animal = new Animal(start, 0, gen1, 0, Direction.SOUTHWEST);
        animal.move(map);
        assertEquals(new Vector2d(9, 0), animal.getPosition());
        assertEquals(Direction.NORTHEAST, animal.getDirection());
    }
}
