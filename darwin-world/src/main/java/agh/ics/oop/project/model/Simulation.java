package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.SimulationListener;
import java.util.*;

public class Simulation  {

    private final SimulationConfiguration config;
    private WorldMap map;
    private SimulationListener listener;

    public Simulation(SimulationConfiguration config) {
        this.config = config;
    }

    public void setUp() {
        map = new WorldMap(config.getMapSizeX(), config.getMapSizeY(), 1);
        this.addListener(new ConsoleSimulationDisplay()); // TODO

        spawnPlants(config.getInitialPlantCount());
        spawnAnimals(config.getInitialAnimalCount());
    }

    private void addListener(SimulationListener listener) {
        this.listener=listener;
    }


    public void run() throws InterruptedException {
        setUp();
        listener.mapChanged(this);
        while(!isSimulationOver()){
            removeDeadAnimals();
            map.moveAnimals();
            map.eatPlants();
            //rozmnazanie
//            spawnPlants(config.getNumberOfPlantsGrowingPerDay());
            listener.mapChanged(this);
            Thread.sleep(config.getTurnTimeInMs());
        }
    }
    private void spawnAnimals(int animalCount){
        Random random = new Random();
        for (int i = 0; i < animalCount; i++) {
            Vector2d pos = new Vector2d(random.nextInt(map.getWidth()), random.nextInt(map.getHeight()));
            map.placeAnimal(new Animal(pos,config.getInitialAnimalEnergy(),config.getGenomeLength()));
        }
    }

    private void spawnPlants(int plantCount){
        List<Vector2d> availablePositions = getPositionsWithoutPlants();
        Collections.shuffle(availablePositions);
        availablePositions = availablePositions.subList(0,plantCount);

        for (Vector2d position : availablePositions){
            map.placePlant(new Plant(position, config.getInitialPlantEnergy()));
        }
    }

    private void removeDeadAnimals(){
        List<Animal> animals = map.getAnimals();
        for(Animal animal : animals){
            if (animal.getEnergy()==0){
                map.removeAnimal(animal);
                map.deleteIfEmpty(animal.getPosition());
            }
        }
    }

    private List<Vector2d> getPositionsWithoutPlants(){
        List<Vector2d> result = new ArrayList<>();

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (!map.isOccupied(new Vector2d(i,j)) || map.getPlant(new Vector2d(i,j))==null){
                    result.add(new Vector2d(i,j));
                }
            }
        }
        return result;
    }

    private boolean isSimulationOver() {
        List<Animal> animals = map.getAnimals();
        return animals.isEmpty();
    }
    public WorldMap getMap() {
        return map;
    }
}
