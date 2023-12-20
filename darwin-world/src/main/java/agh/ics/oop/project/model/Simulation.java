package agh.ics.oop.project.model;

import java.util.*;

public class Simulation  {

    private SimulationConfiguration config;
    private static WorldMap map;

    public Simulation(SimulationConfiguration config) {
        this.config = config;
    }

    public void setUp() {
        map = new WorldMap(config.getMapSizeX(), config.getMapSizeY(), 1);
        map.addListener(new ConsoleMapDisplay());
        Random rand = new Random();

        //Place plants
        for(int i = 0; i < config.getInitialPlantCount(); i++) {
            Vector2d position = new Vector2d(rand.nextInt(config.getMapSizeX()), rand.nextInt(config.getMapSizeY()));
            Plant plant = new Plant(position, config.getInitialPlantEnergy());

            map.placePlant(plant);
        }

        //Place animals
        for(int i = 0; i < config.getInitialAnimalCount(); i++) {
            Vector2d position = new Vector2d(rand.nextInt(config.getMapSizeX()), rand.nextInt(config.getMapSizeY()));
            Animal animal = new Animal(position, config.getInitialAnimalEnergy(), config.getGenomeLength());
            map.placeAnimal(animal);
        }
    }

    public void run() throws InterruptedException {
        setUp();

        while(true){
            map.updateMap();
            Thread.sleep(2000);
        }
    }
}
