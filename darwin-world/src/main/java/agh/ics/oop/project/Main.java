package agh.ics.oop.project;

import agh.ics.oop.project.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SimulationConfiguration config = new SimulationConfiguration(20,20,50,3,8,60,9,5, 10, 1, 2,10);

        Simulation sim = new Simulation(config);
        try {
            sim.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}