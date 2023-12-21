package agh.ics.oop.project;

import agh.ics.oop.project.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SimulationConfiguration config = new SimulationConfiguration(10,10,30,
                                                        1,5,8,
                                                        1,2,5,500);
        Simulation sim = new Simulation(config);
        try {
            sim.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}