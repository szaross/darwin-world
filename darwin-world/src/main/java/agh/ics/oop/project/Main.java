package agh.ics.oop.project;

import agh.ics.oop.project.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorldMap map = new WorldMap(10,10,1);
        map.placePlant(new Plant(2,2,2));
        List<Animal> animals = new ArrayList<>(List.of(new Animal(new Vector2d(2,7),5,1),new Animal(new Vector2d(3,5),5,1)));
        for (Animal animal : animals){
            System.out.println(animal.getGenotype().getGenes());
            map.placeAnimal(animal);
        }
        System.out.println(map);
        for (int i = 0; i < 5; i++) {
            map.updateMap();
            System.out.println(map);
        }

    }
}