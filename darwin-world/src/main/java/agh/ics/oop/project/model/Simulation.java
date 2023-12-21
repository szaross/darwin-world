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

//        while(!isSimulationOver()){
//            removeDeadAnimals();
//            map.moveAnimals();
//            map.eatPlants();
//            //rozmnazanie
//            spawnPlants(config.getNumberOfPlantsGrowingPerDay());
//            listener.mapChanged(this);
//            Thread.sleep(config.getTurnTimeInMs());
//        }

        for(int i = 0; i < 200; i++) {
            removeDeadAnimals();
            map.moveAnimals();
            map.eatPlants();
            spawnPlants(config.getNumberOfPlantsGrowingPerDay());
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
        List<Vector2d> centerList = new ArrayList<>();
        List<Vector2d> outsideList = new ArrayList<>();

        int border = (int) (0.2*config.getMapSizeY());
        int center = config.getMapSizeY() / 2;

        for(Vector2d position: availablePositions){
            if(position.getY() > center - border && position.getY() < center + border) {
                centerList.add(position);
            } else {
                outsideList.add(position);
            }
        }


        Collections.shuffle(centerList);
        Collections.shuffle(outsideList);

        int how_many = (int) Math.floor((plantCount * 0.8));

        centerList = centerList.subList(0, Math.min(how_many, centerList.size()));
        outsideList = outsideList.subList(0, Math.min(plantCount - how_many, outsideList.size()));

        for (Vector2d position : centerList){
            map.placePlant(new Plant(position, config.getInitialPlantEnergy()));
        }

        for (Vector2d position : outsideList){
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
