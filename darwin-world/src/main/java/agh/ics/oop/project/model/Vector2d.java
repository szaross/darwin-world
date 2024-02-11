package agh.ics.oop.project.model;

public record Vector2d(int x, int y) {

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Vector2d add(Vector2d vec) {
        return new Vector2d(this.x + vec.x(), this.y + vec.y());
    }

    public Vector2d subtract(Vector2d vec) {
        return new Vector2d(this.x - vec.x(), this.y - vec.y());
    }

    public Vector2d opposite() {
        return new Vector2d(this.y(), this.x());
    }

}
