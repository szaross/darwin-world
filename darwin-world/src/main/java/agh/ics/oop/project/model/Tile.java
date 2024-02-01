package agh.ics.oop.project.model;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private final List<Animal> animals = new ArrayList<>();
    private Plant plant;
    private boolean containsWater = false;

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public synchronized Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public void removePlant() {
        plant = null;
    }

    public synchronized List<Animal> getAnimals() {
        return animals;
    }

    public boolean isEmpty() {
        return plant == null && animals.isEmpty() && !containsWater;
    }

    public boolean containsWater() {
        return containsWater;
    }

    public void addWater() {
        containsWater = true;
    }

    public void removeWater() {
        containsWater = false;
    }
}
