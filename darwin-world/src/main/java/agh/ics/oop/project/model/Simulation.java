package agh.ics.oop.project.model;

import java.util.Random;

public class Simulation  {

    private SimulationConfiguration config;

    public Simulation(SimulationConfiguration config) {
        this.config = config;
    }

    public void run() throws InterruptedException {
        WorldMap map = new WorldMap(config.getMapSizeX(), config.getMapSizeY(), 1);
        Random rand = new Random();
        for(int i = 0; i < config.getInitialPlantCount(); i++) {
            Plant plant = new Plant(rand.nextInt(config.getMapSizeX()), rand.nextInt(config.getMapSizeY()), config.getInitialPlantEnergy());
            map.placePlant(plant);
        }

        for(int i = 0; i < config.getInitialAnimalCount(); i++) {
            Vector2d position = new Vector2d(rand.nextInt(config.getMapSizeX()), rand.nextInt(config.getMapSizeY()));
            Animal animal = new Animal(position, config.getInitialAnimalEnergy(), config.getGenomeLength());
            map.placeAnimal(animal);
        }
        map.addListener(new ConsoleMapDisplay());
        while(true){
            map.updateMap();
            Thread.sleep(2000);
        }
    }
}
