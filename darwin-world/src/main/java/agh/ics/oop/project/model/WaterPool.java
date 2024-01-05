package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.WorldElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaterPool implements WorldElement {
    private final Vector2d center;
    private List<Vector2d> WaterTiles = new ArrayList<>();
    private final int max_size;

    public WaterPool(Vector2d center_position, int max_size){
        this.center=center_position;
        this.WaterTiles.add(center_position);
        this.max_size=max_size;
    }

    public void grow(HashMap<Vector2d,Tile> tiles, Boundary borders){
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Vector2d> temp_water = new ArrayList<>();

        for (int i = 0; i < directions.length; i++) {
            int x = directions[i][0];
            int y = directions[i][1];

            for(Vector2d pos : WaterTiles){
                Vector2d new_pos = new Vector2d(pos.getX() + x, pos.getY() + y);
                if (new_pos.getX() >= borders.lower_left().getX() && new_pos.getX() <= borders.upper_right().getX()
                    && new_pos.getY() >= borders.lower_left().getY() && new_pos.getY() <= borders.upper_right().getY()) {
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
    public void shrink(HashMap<Vector2d,Tile> tiles, Boundary borders){
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Vector2d> temp_water = new ArrayList<>();

        for (int i = 0; i < directions.length; i++) {
            int x = directions[i][0];
            int y = directions[i][1];

            for(Vector2d pos : WaterTiles){
                Vector2d new_pos = new Vector2d(pos.getX() + x, pos.getY() + y);
                if (new_pos.getX() >= borders.lower_left().getX() && new_pos.getX() <= borders.upper_right().getX()
                        && new_pos.getY() >= borders.lower_left().getY() && new_pos.getY() <= borders.upper_right().getY()) {
                        if (tiles.get(new_pos) == null) {
                            tiles.put(new_pos, new Tile());
                        }
                        if (!tiles.get(new_pos).containsWater() && !temp_water.contains(new_pos)) {
                            temp_water.add(pos);
                        }
                }
            }
        }

        for(Vector2d pos : temp_water) {
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

    public List<Vector2d> getWaterTiles() {
        return WaterTiles;
    }

    @Override
    public String toString(){
        return "W";
    }
}
