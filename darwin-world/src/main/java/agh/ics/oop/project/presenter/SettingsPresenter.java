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

    @FXML
    public void initialize() {
        updateValueText();

        sizeX.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        sizeY.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        initialPlantCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        initialPlantEnergy.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        growPerDay.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        animalCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        animalEnergy.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        reproduceEnergy.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        reproduceLoss.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        energyLoss.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        genomeLen.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        turnTime.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });
    }
    private void updateValueText() {
        String textBuilder = "Szerokosc mapy: " + (int) sizeX.getValue() + "\n" +
                "Wysokosc mapy: " + (int) sizeY.getValue() + "\n" +
                "Startowa ilosc roslin: " + (int) initialPlantCount.getValue() + "\n" +
                "Energia roslin: " + (int) initialPlantEnergy.getValue() + "\n" +
                "Codzienny wzrost roslin: " + (int) growPerDay.getValue() + "\n" +
                "Ilosc zwierzat: " + (int) animalCount.getValue() + "\n" +
                "Poczatkowa energia zwierzat: " + (int) animalEnergy.getValue() + "\n" +
                "Energia potrzebna do rozmnazania: " + (int) reproduceEnergy.getValue() + "\n" +
                "Strata energii podczas rozmnazania: " + (int) reproduceLoss.getValue() + "\n" +
                "Codzienna strata energii: " + (int) energyLoss.getValue() + "\n" +
                "Dlugosc genomu: " + (int) genomeLen.getValue() + "\n" +
                "Czas dnia w milisekundach: " + (int) turnTime.getValue() + "\n";

        valueText.setText(textBuilder);
    }

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
