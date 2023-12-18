package agh.ics.oop.project.interfaces;

import agh.ics.oop.project.model.Vector2d;

import java.util.List;

public interface Map extends MoveValidator {
    boolean place(WorldElement element);

    List<WorldElement> objectsAt(Vector2d position);

    boolean isOccupied(Vector2d position);

    List<WorldElement> getElements();

    int getHeight();

    int getWidth();
}
