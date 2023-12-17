package agh.ics.oop.project.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }

    public Vector2d add(Vector2d vec) {
        return new Vector2d(this.x + vec.getX(), this.y + vec.getY());
    }

    public Vector2d subtract(Vector2d vec) {
        return new Vector2d(this.x - vec.getX(), this.y - vec.getY());
    }

    public Vector2d opposite() {
        return new Vector2d(this.getY(), this.getX());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
