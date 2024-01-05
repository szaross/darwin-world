package agh.ics.oop.project.model;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private Plant plant;
    private boolean containsWater = false;

    private List<Animal> animals = new ArrayList<>();

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }
    public boolean removeAnimal(Animal animal){
        if(animals.contains(animal)){
            animals.remove(animal);
            return true;
        }
        return false;

    }
    public void setPlant(Plant plant) {
        this.plant = plant;
    }
    public synchronized Plant getPlant(){
        return plant;
    }
    public void removePlant(){
        plant = null;
    }
    public synchronized List<Animal> getAnimals(){
        return animals;
    }
    public boolean isEmpty() {
        return plant == null && animals.isEmpty() && !containsWater;
    }

    public boolean containsWater() {
        return containsWater;
    }
    public void addWater(){
        containsWater=true;
    }
}
