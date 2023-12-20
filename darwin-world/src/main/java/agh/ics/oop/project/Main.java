package agh.ics.oop.project;

import agh.ics.oop.project.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorldMap map = new WorldMap(10,10,1);
        map.placePlant(new Plant(2,3,1));
        map.placePlant(new Plant(8,3,1));
        map.placePlant(new Plant(5,4,1));
        map.placePlant(new Plant(1,6,1));
        map.placePlant(new Plant(3,2,1));
        map.placePlant(new Plant(7,4,1));
        map.placePlant(new Plant(1,2,1));
        map.placePlant(new Plant(5,5,1));
        map.placePlant(new Plant(9,4,1));
        map.placePlant(new Plant(7,9,1));
        map.placePlant(new Plant(3,6,1));
        map.addListener(new ConsoleMapDisplay());
        List<Animal> animals = new ArrayList<>(List.of(new Animal(new Vector2d(2,7),5,1),new Animal(new Vector2d(1,3),5,1),new Animal(new Vector2d(6,6),5,1)));

        for (Animal animal : animals){
            System.out.println(animal.getGenotype().getGenes() + " " + animal.getDirection());
            map.placeAnimal(animal);
        }

        for (int i = 0; i < 100; i++) {
            map.updateMap();
        }

    }
}