package agh.ics.oop.project.model;

import java.util.Random;

public enum Direction {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

    private static final Random random = new Random();

    public static Direction randomDirection() {
        return values()[random.nextInt(values().length)];
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
            case NORTHEAST -> "NE";
            case NORTHWEST -> "NW";
            case SOUTHEAST -> "SE";
            case SOUTHWEST -> "SW";
        };
    }

    public Direction rotate(int rotation) {
        return Direction.values()[(this.ordinal() + rotation) % Direction.values().length];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHEAST -> new Vector2d(1, 1);
            case NORTHWEST -> new Vector2d(-1, 1);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
        };

    }
}
