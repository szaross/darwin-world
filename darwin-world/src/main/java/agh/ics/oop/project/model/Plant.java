package agh.ics.oop.project.model;
import agh.ics.oop.project.interfaces.WorldElement;

public class Plant implements WorldElement{
    private int x;
    private int y;
    private int energy;

    public Plant(int x, int y, int energy) {
        this.x = x;
        this.y = y;
        this.energy = energy;
    }
    public Vector2d getPosition(){
        return new Vector2d(x,y);
    }
    public int getEnergy(){
        return energy;
    }
    public void setEnergy(int energy){
        this.energy = energy;
    }
}
