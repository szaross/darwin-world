package agh.ics.oop.project.model;

import java.util.ArrayList;
import java.util.List;

public class Tile {
//    private int x;
//    private int y;
    private Plant plant;
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
    public Plant getPlant(){
        return plant;
    }
    public void removePlant(){
        plant = null;
    }
    public List<Animal> getAnimals(){
        return animals;
    }

}
