package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.MapListener;

public class ConsoleMapDisplay implements MapListener {
    public void mapChanged(WorldMap map) {
        System.out.println(map);
    }
}
