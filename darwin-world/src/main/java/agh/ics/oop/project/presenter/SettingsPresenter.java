package agh.ics.oop.project.presenter;

import agh.ics.oop.project.gui.SimulationApp;
import agh.ics.oop.project.interfaces.SimulationListener;
import agh.ics.oop.project.model.Simulation;
import agh.ics.oop.project.model.SimulationConfiguration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsPresenter {
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label movesLabel;
    @FXML
    private TextField movesField;
    @FXML
    private Slider sizeX;
    @FXML
    private Slider sizeY;
    @FXML
    private Slider initialPlantCount;
    @FXML
    private Slider initialPlantEnergy;
    @FXML
    private Slider growPerDay;
    @FXML
    private Slider animalCount;
    @FXML
    private Slider animalEnergy;
    @FXML
    private Slider reproduceEnergy;
    @FXML
    private Slider reproduceLoss;
    @FXML
    private Slider energyLoss;
    @FXML
    private Slider genomeLen;
    @FXML
    private Slider turnTime;
    @FXML
    private Text valueText;

    public void onSimulationStartClicked() {
        //Test print
        valueText.setText((int) sizeX.getValue() + " " + (int) sizeY.getValue() + " " + (int) initialPlantCount.getValue() + " " + (int) initialPlantEnergy.getValue());
        // pobieranie danych
        SimulationConfiguration config = new SimulationConfiguration((int) sizeX.getValue(),
                                                                        (int) sizeY.getValue(),
                                                                        (int) initialPlantCount.getValue(),
                                                                        (int) initialPlantEnergy.getValue(),
                                                                        (int) growPerDay.getValue(),
                                                                        (int) animalCount.getValue(),
                                                                        (int) animalEnergy.getValue(),
                                                                        (int) reproduceEnergy.getValue(),
                                                                        (int) reproduceLoss.getValue(),
                                                                        (int) energyLoss.getValue(),
                                                                        (int) genomeLen.getValue(),
                                                                        (int) turnTime.getValue());

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
