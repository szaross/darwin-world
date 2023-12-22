package agh.ics.oop.project.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalComparatorTest {
    @Test
    public void animalCompareTest(){

        List<Animal> animals = new ArrayList<>(List.of(new Animal(new Vector2d(2,3), 4, 4),
                                                        new Animal(new Vector2d(4,3), 17, 4),
                                                        new Animal(new Vector2d(1,3), 8, 4),
                                                        new Animal(new Vector2d(3,2), 9, 2)));
        Animal max_animal = animals.get(0);
        AnimalComparator comparator = new AnimalComparator();
        for(Animal animal : animals){
            if (comparator.compare(max_animal, animal) > 0){
                max_animal = animal;
            }
        }

        Animal animal = new Animal(new Vector2d(4,3), 17,4);
        assertTrue(animal.equals(max_animal));
    }
}
