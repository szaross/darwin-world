package agh.ics.oop.project.gui;

import agh.ics.oop.project.model.Simulation;
import agh.ics.oop.project.model.SimulationConfiguration;
import agh.ics.oop.project.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimulationApp extends Application {
    private final SimulationConfiguration configuration;

    public SimulationApp(SimulationConfiguration configuration){
        this.configuration=configuration;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        HBox viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        configureStage(primaryStage,viewRoot);

        // setup simulation
        Simulation sim = new Simulation(configuration);
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
