package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.SimulationListener;

public class ConsoleSimulationDisplay implements SimulationListener {
    public void mapChanged(Simulation simulation) {
        System.out.println(simulation.getMap());
    }
}
