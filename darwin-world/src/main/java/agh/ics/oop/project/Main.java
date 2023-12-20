package agh.ics.oop.project;

import agh.ics.oop.project.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SimulationConfiguration config = new SimulationConfiguration(10,10,9,1,5,5,0,2,3);
        Simulation sim = new Simulation(config);
        try {
            sim.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}