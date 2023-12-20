package agh.ics.oop.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileTest {
    private Animal animal;
    private Tile tile;

    @BeforeEach
    public void setUp(){
        animal = new Animal(new Vector2d(2,5), 40, 20);
        tile = new Tile();
    }
    @Test
    public void addTest(){
        tile.addAnimal(animal);
        assertTrue(tile.getAnimals().contains(animal));
    }

    @Test
    public void removeTest(){
        tile.addAnimal(animal);
        tile.removeAnimal(animal);
        assertFalse(tile.getAnimals().contains(animal));
    }

}
