package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.SimulationListener;

import java.util.*;

public class Simulation {
    private final SimulationConfiguration config;
    private final List<SimulationListener> listeners = new ArrayList<>();
    private final boolean saveStats;
    private int day = 0;
    private WorldMap map;
    private Statistics stats;
    private Thread thread;
    private boolean isActive;

    public Simulation(SimulationConfiguration config, boolean saveStats) {
        this.config = config;
        this.saveStats = saveStats;
    }

    public void setUp() {
        map = new WorldMap(config.getMapSizeX(), config.getMapSizeY());
        stats = new Statistics();
        if (config.isWater()) map.placeWater(config.getInitialWaterCount());

        spawnPlants(config.getInitialPlantCount());
        spawnAnimals(config.getInitialAnimalCount());

        isActive = true;
    }

    public void addListener(SimulationListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (SimulationListener listener : listeners) {
            listener.mapChanged(this);
        }
    }

    public void run() throws InterruptedException {
        setUp();
        notifyListeners();
        while (true) { // keep this so that thread doesn't stop
            while (!isSimulationOver() && isActive) {
                if (saveStats) stats.saveToCsv();
                increaseDay();
                increaseAge();
                decreaseEnergy();
                removeDeadAnimals();
                map.moveAnimals(config.isBackAndForth());
                map.eatPlants();
                reproduceAnimals();
                spawnPlants(config.getNumberOfPlantsGrowingPerDay());
                stats.updateStats(map.getTiles(), map.getBoundary());
                growWater();
                notifyListeners();
                Thread.sleep(config.getTurnTimeInMs());
            }
        }
    }

    private void growWater() {
        if (getDay() % config.getWaterPoolGrowRate() == 0 && config.isWater()) {
            Random random = new Random();
            int x = random.nextInt(3);
            if (x % 2 == 0) map.growWater();
            else map.shrinkWater();
        }
    }

    private void spawnAnimals(int animalCount) {
        Random random = new Random();
        for (int i = 0; i < animalCount; i++) {
            Vector2d pos = new Vector2d(random.nextInt(map.getWidth()), random.nextInt(map.getHeight()));
            map.placeAnimal(new Animal(pos, config.getInitialAnimalEnergy(), config.getGenomeLength()));
        }
    }

    private void spawnPlants(int plantCount) {
        if (plantCount > 0) {
            List<Vector2d> availablePositions = getPositionsWithoutPlantsAndWater();
            List<Vector2d> centerList = new ArrayList<>();
            List<Vector2d> outsideList = new ArrayList<>();

            int border = (int) (0.2 * config.getMapSizeY());
            int center = config.getMapSizeY() / 2;

            for (Vector2d position : availablePositions) {
                if (position.y() > center - border && position.y() < center + border) {
                    centerList.add(position);
                } else {
                    outsideList.add(position);
                }
            }


            Collections.shuffle(centerList);
            Collections.shuffle(outsideList);
            int how_many;
            if (plantCount == 1) {
                Random random = new Random();
                if (random.nextInt(10) > 6) {
                    how_many = 0;
                } else {
                    how_many = 1;
                }
            } else {
                how_many = Math.max(1, (int) Math.floor((plantCount * 0.8)));
            }


            centerList = centerList.subList(0, Math.min(how_many, centerList.size()));
            outsideList = outsideList.subList(0, Math.min(plantCount - how_many, outsideList.size()));

            for (Vector2d position : centerList) {
                map.placePlant(new Plant(position, config.getInitialPlantEnergy()));
            }

            for (Vector2d position : outsideList) {
                map.placePlant(new Plant(position, config.getInitialPlantEnergy()));
            }
        }
    }

    private void reproduceAnimals() {
        HashMap<Vector2d, Tile> tiles = map.getTiles();

        for (Vector2d position : tiles.keySet()) {
            // two strongest animals
            List<Animal> animalCouple = tiles.get(position).getAnimals().stream().filter(animal -> animal.getEnergy() >= config.getReadyToReproduceEnergy()).sorted(new AnimalComparator()).limit(2).toList();

            if (animalCouple.size() < 2) continue;

            Animal stronger = animalCouple.get(0);
            Animal weaker = animalCouple.get(1);

            Random random = new Random();
            int side = random.nextInt(2);

            List<Integer> genes = new ArrayList<>();
            int stronger_len = (int) Math.ceil(config.getGenomeLength() * ((double) stronger.getEnergy() / (stronger.getEnergy() + weaker.getEnergy())));
            int weaker_len = config.getGenomeLength() - stronger_len;

            switch (side) {
                case 0: // taking left side of stronger genes
                    genes.addAll(stronger.getGenotype().getGenes().subList(0, stronger_len));
                    genes.addAll(weaker.getGenotype().getGenes().subList(config.getGenomeLength() - weaker_len, config.getGenomeLength()));
                    break;
                case 1: // taking left side
                    genes.addAll(stronger.getGenotype().getGenes().subList(config.getGenomeLength() - stronger_len, config.getGenomeLength()));
                    genes.addAll(weaker.getGenotype().getGenes().subList(0, weaker_len));
                    break;
            }

            Genotype genotype = new Genotype(genes);
            genotype.mutate(config.getMinMutation(), config.getMaxMutation());

            Animal newborn = new Animal(position, 2 * config.getReproduceEnergyLoss(), genotype);
            stronger.addChildCount();
            weaker.addChildCount();

            stronger.setEnergy(stronger.getEnergy() - config.getReproduceEnergyLoss());
            weaker.setEnergy(weaker.getEnergy() - config.getReproduceEnergyLoss());

            map.placeAnimal(newborn);
        }
    }

    private void removeDeadAnimals() {
        List<Animal> animals = map.getAnimals();
        int count = 0;
        int sum_age = 0;
        for (Animal animal : animals) {
            if (animal.getEnergy() <= 0) {
                count++;
                map.removeAnimal(animal);
                map.deleteIfEmpty(animal.getPosition());
                sum_age += animal.getAge();
                animal.setDeathDate(day);
            }
        }
        stats.updateDead(sum_age, count);

    }

    private List<Vector2d> getPositionsWithoutPlantsAndWater() {
        List<Vector2d> result = new ArrayList<>();

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (map.getTiles().get(new Vector2d(i, j)) == null || (map.getPlant(new Vector2d(i, j)) == null && !map.containsWater(new Vector2d(i, j)))) {
                    result.add(new Vector2d(i, j));
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

    public void increaseAge() {
        List<Animal> animals = map.getAnimals();

        for (Animal animal : animals) {
            animal.setAge(animal.getAge() + 1);
        }
    }

    public void decreaseEnergy() {
        List<Animal> animals = map.getAnimals();

        for (Animal animal : animals) {
            animal.setEnergy(animal.getEnergy() - config.getEnergyLossEachDay());
        }
    }

    public void increaseDay() {
        day = day + 1;
    }

    public int getDay() {
        return day;
    }

    public void runAsync() {
        thread = new Thread(() -> {
            try {
                run();
            } catch (InterruptedException ignored) {
            }
        });
        thread.start();

    }

    public void stopAsync() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }

    public Statistics getStats() {
        return stats;
    }


    public void changeStatus() {
        isActive = !isActive;
    }

    public boolean isActive() {
        return isActive;
    }
}