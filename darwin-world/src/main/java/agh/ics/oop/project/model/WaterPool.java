package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.WorldElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaterPool implements WorldElement {
    private final Vector2d center;
    private final List<Vector2d> WaterTiles = new ArrayList<>();

    public WaterPool(Vector2d center_position) {
        this.center = center_position;
        this.WaterTiles.add(center_position);
    }

    public void grow(HashMap<Vector2d, Tile> tiles, Boundary borders) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Vector2d> temp_water = new ArrayList<>();

        for (int[] direction : directions) {
            int x = direction[0];
            int y = direction[1];

            for (Vector2d pos : WaterTiles) {
                Vector2d new_pos = new Vector2d(pos.x() + x, pos.y() + y);
                if (new_pos.x() >= borders.lower_left().x() && new_pos.x() <= borders.upper_right().x() && new_pos.y() >= borders.lower_left().y() && new_pos.y() <= borders.upper_right().y()) {
                    if (tiles.get(new_pos) == null) {
                        tiles.put(new_pos, new Tile());
                    }
                    if (!tiles.get(new_pos).containsWater() && tiles.get(new_pos).getPlant() == null && tiles.get(new_pos).getAnimals().isEmpty()) {
                        tiles.get(new_pos).addWater();
                        temp_water.add(new_pos);
                    }
                }
            }
        }

        WaterTiles.addAll(temp_water);
    }

    public void shrink(HashMap<Vector2d, Tile> tiles, Boundary borders) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Vector2d> temp_water = new ArrayList<>();

        for (int[] direction : directions) {
            int x = direction[0];
            int y = direction[1];

            for (Vector2d pos : WaterTiles) {
                Vector2d new_pos = new Vector2d(pos.x() + x, pos.y() + y);
                if (new_pos.x() >= borders.lower_left().x() && new_pos.x() <= borders.upper_right().x() && new_pos.y() >= borders.lower_left().y() && new_pos.y() <= borders.upper_right().y()) {
                    if (tiles.get(new_pos) == null) {
                        tiles.put(new_pos, new Tile());
                    }
                    if (!tiles.get(new_pos).containsWater() && !temp_water.contains(new_pos)) {
                        temp_water.add(pos);
                    }
                }
            }
        }

        for (Vector2d pos : temp_water) {
            if (pos != center) {
                tiles.get(pos).removeWater();
                WaterTiles.remove(pos);
            }

        }
    }

    @Override
    public Vector2d getPosition() {
        return center;
    }

    @Override
    public String toString() {
        return "W";
    }
}
