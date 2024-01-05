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

    public void grow(HashMap<Vector2d,Tile> tiles){
        List<Vector2d> positions = getGrowPositions();
        for (Vector2d pos : positions){
            if (tiles.get(pos)==null) tiles.put(pos,new Tile());
            tiles.get(pos).addWater();
        }
    }
    private List<Vector2d> getGrowPositions(){
        // codziennie grow/shrink o jedno pole (?)
        return new ArrayList<>(List.of(new Vector2d(1,0)));
    }
    public void shrink(List<Tile> tiles){

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
