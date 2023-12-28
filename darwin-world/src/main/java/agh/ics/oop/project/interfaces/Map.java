package agh.ics.oop.project.interfaces;

import agh.ics.oop.project.model.*;

import java.util.HashMap;
import java.util.List;

public interface Map extends MoveValidator {
    boolean placeAnimal(Animal animal);
    boolean placePlant(Plant plant);

    List<WorldElement> objectsAt(Vector2d position);

    boolean isOccupied(Vector2d position);

    List<WorldElement> getElements();

    int getHeight();

    int getWidth();

    HashMap<Vector2d,Tile> getTiles();

    Boundary getBoundary();
}
