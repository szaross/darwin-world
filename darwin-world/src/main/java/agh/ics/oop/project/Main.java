package agh.ics.oop.project;

import agh.ics.oop.project.gui.SettingsApp;
import agh.ics.oop.project.gui.SimulationApp;
import agh.ics.oop.project.model.*;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        SimulationConfiguration config = new SimulationConfiguration(20,20,50,3,7,60,60,5, 10, 1, 3,100);
//
//        Simulation sim = new Simulation(config);
//        try {
//            sim.run();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Application.launch(SettingsApp.class, args);
    }
}