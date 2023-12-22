package agh.ics.oop.project;

import agh.ics.oop.project.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SimulationConfiguration config = new SimulationConfiguration(20,20,5,
                                                        1,3,20,
                                                        1,2, 5,5,500);
        Simulation sim = new Simulation(config);
        try {
            sim.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}