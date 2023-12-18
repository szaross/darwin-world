package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.Map;
import agh.ics.oop.project.interfaces.WorldElement;

import java.util.*;

public class WorldMap implements Map {
    private final Boundary boundary;
    private final int height;
    private final int width;
    private HashMap<Vector2d, Tile> tiles;
    private final int id;

    public WorldMap(int width, int height, int id) {
        this.height = height;
        this.width = width;
        this.boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
        this.id=id;

    }

    @Override
    public boolean place(WorldElement element) {
        Vector2d pos = element.getPosition();
        if (canMoveTo(pos)) {
            if (!tiles.containsKey(pos)) tiles.put(pos, new Tile());
            if (element instanceof Animal) tiles.get(pos).addAnimal((Animal) element);
            else if (element instanceof Plant) tiles.get(pos).setPlant((Plant) element);
            return true;
        }
        return false;
    }

    public void updateMap(){
        Set<Animal> moved = new HashSet<>();
        for (Tile t : tiles.values()){
            for (Animal animal : t.getAnimals()){
                if (!moved.contains(animal)){
                    // move the animal
                    t.removeAnimal(animal);
                    animal.move(this);

                    // create Tile on Vector2d if doesnt exist
                    if (!tiles.containsKey(animal.getPosition())){
                        tiles.put(animal.getPosition(),new Tile());
                    }

                    // put animal on the new tile
                    tiles.get(animal.getPosition()).addAnimal(animal);
                    moved.add(animal);
                }
            }
        }

    }


    @Override
    public List<WorldElement> objectsAt(Vector2d position) {
        Tile t = tiles.get(position);
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
    public boolean isOccupied(Vector2d position) {
        return tiles.get(position) != null;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> result = new ArrayList<>();
        for (Tile t : tiles.values()) {
            result.addAll(t.getAnimals());
            if (t.getPlant() != null) result.add(t.getPlant());
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
}
