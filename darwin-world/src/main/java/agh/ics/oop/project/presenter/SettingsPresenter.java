package agh.ics.oop.project.presenter;

import agh.ics.oop.project.gui.SimulationApp;
import agh.ics.oop.project.interfaces.SimulationListener;
import agh.ics.oop.project.model.Simulation;
import agh.ics.oop.project.model.SimulationConfiguration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsPresenter {
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label movesLabel;
    @FXML
    private TextField movesField;
    public void onSimulationStartClicked() {
        // pobieranie danych
        SimulationConfiguration config = new SimulationConfiguration(30,30,50,5,6,20,30,6, 5, 1, 5,10);
        SimulationApp app = new SimulationApp(config);

        Platform.runLater(() -> {
            try {
                app.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
