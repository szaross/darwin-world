package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.Map;
import agh.ics.oop.project.interfaces.WorldElement;

import java.util.*;

public class WorldMap implements Map {
    private final Boundary boundary;
    private final int height;
    private final int width;
    private final HashMap<Vector2d, Tile> tiles;
    private final List<WaterPool> waterCenters;


    public WorldMap(int width, int height) {
        this.height = height;
        this.width = width;
        this.boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
        this.tiles = new HashMap<>();
        this.waterCenters = new ArrayList<>();
    }

    public void placeWater(int initialWaterCount) {
        for (int i = 0; i < initialWaterCount; i++) {
            Random random = new Random();
            int x = random.nextInt(getBoundary().upper_right().x() - getBoundary().lower_left().x());
            int y = random.nextInt(getBoundary().upper_right().y() - getBoundary().lower_left().y());
            Vector2d pos = new Vector2d(x, y);
            waterCenters.add(new WaterPool(pos));
            if (getTiles().get(pos) == null) getTiles().put(pos, new Tile());
            getTiles().get(pos).addWater();
        }
    }

    public void growWater() {
        for (WaterPool pool : waterCenters) {
            pool.grow(getTiles(), getBoundary());
        }
    }

    public void shrinkWater() {
        for (WaterPool pool : waterCenters) {
            pool.shrink(getTiles(), getBoundary());
        }
    }

    @Override
    public void placeAnimal(Animal animal) {
        Vector2d pos = animal.getPosition();
        if (canMoveTo(pos)) {
            if (!getTiles().containsKey(pos)) getTiles().put(pos, new Tile());
            getTiles().get(pos).addAnimal(animal);
        }
    }

    public void removeAnimal(Animal animal) {
        getTiles().get(animal.getPosition()).removeAnimal(animal);
    }

    @Override
    public void placePlant(Plant plant) {
        Vector2d pos = plant.getPosition();
        if (!getTiles().containsKey(pos) || getTiles().get(pos).getPlant() == null) {
            if (!getTiles().containsKey(pos)) getTiles().put(pos, new Tile());
            getTiles().get(pos).setPlant(plant);
        }
    }

    public void moveAnimals(boolean backAndForth) {
        Set<Animal> moved = new HashSet<>();

        for (Animal animal : getAnimals()) {
            if (!moved.contains(animal)) {
                Vector2d old_pos = animal.getPosition();
                Tile t = getTiles().get(old_pos);

                // move the animal
                t.removeAnimal(animal);
                animal.move(this);

                // back and forth configuration
                if (animal.getActiveGene() == 0 && backAndForth) {
                    animal.getGenotype().reverse();
                }

                // create Tile on Vector2d if doesnt exist
                if (!getTiles().containsKey(animal.getPosition())) {
                    getTiles().put(animal.getPosition(), new Tile());
                }

                // put animal on the new tile
                getTiles().get(animal.getPosition()).addAnimal(animal);
                moved.add(animal);

                deleteIfEmpty(old_pos);
            }
        }
    }

    public void eatPlants() {
        for (Vector2d position : getTiles().keySet()) {
            Tile t = getTiles().get(position);
            if (t.getPlant() != null && !t.getAnimals().isEmpty()) {
                List<Animal> animals = t.getAnimals();

                Animal max_animal = animals.get(0);
                AnimalComparator comparator = new AnimalComparator();

                for (Animal animal : animals) {
                    if (comparator.compare(max_animal, animal) > 0) {
                        max_animal = animal;
                    }
                }

                int max_energy = max_animal.getEnergy();

                t.removeAnimal(max_animal);
                max_animal.setEnergy(max_energy + t.getPlant().getEnergy());
                max_animal.setPlantEaten();
                t.addAnimal(max_animal);
                t.removePlant();

                deleteIfEmpty(position);
            }
        }
    }

    public void deleteIfEmpty(Vector2d position) {
        if (getTiles().containsKey(position)) {
            Tile t = getTiles().get(position);
            if (t.isEmpty()) {
                getTiles().remove(position);
            }
        }
    }


    @Override
    public synchronized boolean isOccupied(Vector2d position) {
        return getTiles().get(position) != null;
    }

    public synchronized Plant getPlant(Vector2d pos) {
        if (isOccupied(pos)) {
            return getTiles().get(pos).getPlant();
        }
        return null;
    }

    public boolean containsWater(Vector2d pos) {
        return (!isOccupied(pos) || getTiles().get(pos).containsWater());
    }

    @Override
    public synchronized List<WorldElement> getElements() {
        List<WorldElement> result = new ArrayList<>();
        for (Tile t : getTiles().values()) {
            result.addAll(t.getAnimals());
            if (t.getPlant() != null) result.add(t.getPlant());
        }
        return result;
    }

    public synchronized List<Animal> getAnimals() {
        List<Animal> result = new ArrayList<>();
        for (Tile t : getTiles().values()) {
            result.addAll(t.getAnimals());
        }
        return result;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public synchronized HashMap<Vector2d, Tile> getTiles() {
        return tiles;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (!isOccupied(position) || !getTiles().get(position).containsWater());
    }

    @Override
    public Boundary getBoundary() {
        return boundary;
    }


    public List<WaterPool> getWaterCenters() {
        return waterCenters;
    }


}
