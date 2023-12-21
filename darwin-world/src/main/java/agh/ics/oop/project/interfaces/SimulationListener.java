package agh.ics.oop.project.interfaces;

import agh.ics.oop.project.model.Simulation;
import agh.ics.oop.project.model.WorldMap;

public interface SimulationListener {
    void mapChanged(Simulation simulation);
}
