package agh.ics.oop.project.model;
import agh.ics.oop.project.interfaces.Map;
import agh.ics.oop.project.interfaces.WorldElement;
import agh.ics.oop.project.model.util.MapVisualizer;

import java.util.*;

public class WorldMap implements Map{
    private final Boundary boundary;
    private final int height;
    private final int width;
    private HashMap<Vector2d, Tile> tiles;
    private final int id;

    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    public WorldMap(int width, int height, int id) {
        this.height = height;
        this.width = width;
        this.boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
        this.id=id;
        this.tiles = new HashMap<>();
    }

    @Override
    public boolean placeAnimal(Animal animal) {
        Vector2d pos = animal.getPosition();
        if (canMoveTo(pos)) {
            if (!getTiles().containsKey(pos)) getTiles().put(pos, new Tile());
            getTiles().get(pos).addAnimal(animal);
            return true;
        }
        return false;
    }

    public void removeAnimal(Animal animal){
        getTiles().get(animal.getPosition()).removeAnimal(animal);
    }

    @Override
    public boolean placePlant(Plant plant) {
        Vector2d pos = plant.getPosition();
        if (!getTiles().containsKey(pos) || getTiles().get(pos).getPlant()==null) {
            if (!getTiles().containsKey(pos)) getTiles().put(pos, new Tile());
            getTiles().get(pos).setPlant(plant);
            return true;
        }
        return false;
    }

    public void moveAnimals(boolean backAndForth){
        Set<Animal> moved = new HashSet<>();

        for (Animal animal : getAnimals()){
            if (!moved.contains(animal)) {
                Vector2d old_pos=animal.getPosition();
                Tile t = getTiles().get(old_pos);

                // move the animal
                t.removeAnimal(animal);
                animal.move(this);

                // back and forth configuration
                if (animal.getActiveGene()==0 && backAndForth){
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
        for(Vector2d position : getTiles().keySet()) {
            Tile t = getTiles().get(position);
            if(t.getPlant() != null && !t.getAnimals().isEmpty()) {
                List<Animal> animals = t.getAnimals();

                Animal max_animal = animals.get(0);
                AnimalComparator comparator = new AnimalComparator();

                for(Animal animal : animals){
                    if (comparator.compare(max_animal, animal) > 0){
                        max_animal = animal;
                    }
                }

                int max_energy = max_animal.getEnergy();

                t.removeAnimal(max_animal);
                max_animal.setEnergy(max_energy + t.getPlant().getEnergy());
                t.addAnimal(max_animal);
                t.removePlant();

                deleteIfEmpty(position);
            }
        }
    }


    public void deleteIfEmpty(Vector2d position){
        if (getTiles().containsKey(position)){
            Tile t = getTiles().get(position);
            if (t.isEmpty()) {
                getTiles().remove(position);
            }
        }
    }
    @Override
    public synchronized List<WorldElement> objectsAt(Vector2d position) {
        Tile t = getTiles().get(position);
        if (t == null) {
            return new ArrayList<>();
        }
        List<WorldElement> result = new ArrayList<>(t.getAnimals());
        if (t.getPlant() != null) {
            result.add(t.getPlant());
        }
        return result;
    }

    @Override
    public synchronized boolean isOccupied(Vector2d position) {
        return getTiles().get(position) != null;
    }

    public synchronized Plant getPlant(Vector2d pos){
        if (isOccupied(pos)){
            return getTiles().get(pos).getPlant();
        }
        return null;
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

    public synchronized List<Animal> getAnimals(){
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
        // domyslnie true - dla wody bedzie false
        return true;
    }

    @Override
    public Boundary getBoundary() {
        return boundary;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(boundary.lower_left(), boundary.upper_right());
    }


}
