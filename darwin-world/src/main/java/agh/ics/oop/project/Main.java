package agh.ics.oop.project;

import agh.ics.oop.project.model.Direction;
import agh.ics.oop.project.model.Genotype;
import agh.ics.oop.project.model.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorldMap map = new WorldMap(10,10,1);
        System.out.println(map);
    }
}