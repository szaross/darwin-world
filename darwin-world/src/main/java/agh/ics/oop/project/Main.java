package agh.ics.oop.project;

import agh.ics.oop.project.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorldMap map = new WorldMap(10,10,1);
        map.placePlant(new Plant(2,2,2));
        map.addListener(new ConsoleMapDisplay());
        List<Animal> animals = new ArrayList<>(List.of(new Animal(new Vector2d(2,7),5,1),new Animal(new Vector2d(5,7),5,1)));

        for (Animal animal : animals){
            System.out.println(animal.getGenotype().getGenes() + " " + animal.getDirection());
            map.placeAnimal(animal);
        }

        for (int i = 0; i < 3; i++) {
            map.updateMap();
        }

    }
}