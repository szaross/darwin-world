package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.WorldElement;

public class Plant implements WorldElement {
    private final Vector2d position;
    private int energy;

    public Plant(Vector2d position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return "*";
    }
}
