package agh.ics.oop.project.gui;

import agh.ics.oop.project.model.Simulation;
import agh.ics.oop.project.model.SimulationConfiguration;
import agh.ics.oop.project.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SimulationApp extends Application {
    private final SimulationConfiguration configuration;
    private final boolean saveStats;

    public SimulationApp(SimulationConfiguration configuration, boolean saveStats) {
        this.configuration = configuration;
        this.saveStats = saveStats;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        HBox viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        configureStage(primaryStage, viewRoot);

        // setup simulation
        Simulation sim = new Simulation(configuration, saveStats);
        sim.setUp();
        presenter.setSimulation(sim);
        sim.addListener(presenter);

        primaryStage.show();
        primaryStage.setOnCloseRequest((event -> sim.stopAsync()));
        sim.runAsync();


    }

    private void configureStage(Stage primaryStage, HBox viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
