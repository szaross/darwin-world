package agh.ics.oop.project.presenter;

import agh.ics.oop.project.gui.SimulationApp;
import agh.ics.oop.project.model.SimulationConfiguration;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsPresenter {
    @FXML
    private ToggleButton backAndForth;
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
    private Label sizeXLabel;
    @FXML
    private Label sizeYLabel;
    @FXML
    private Label initialPlantCountLabel;
    @FXML
    private Label initialPlantEnergyLabel;
    @FXML
    private Label growPerDayLabel;
    @FXML
    private Label animalCountLabel;
    @FXML
    private Label animalEnergyLabel;
    @FXML
    private Label reproduceEnergyLabel;
    @FXML
    private Label reproduceLossLabel;
    @FXML
    private Label energyLossLabel;
    @FXML
    private Label genomeLenLabel;
    @FXML
    private Label turnTimeLabel;
    @FXML
    private Slider initialWaterCount;
    @FXML
    private Slider waterPoolSize;
    @FXML
    private Slider waterPoolGrowRate;
    @FXML
    private Label initialWaterCountLabel;
    @FXML
    private Label waterPoolSizeLabel;
    @FXML
    private Label waterPoolGrowRateLabel;

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

        initialWaterCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        waterPoolSize.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });

        waterPoolGrowRate.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateValueText();
        });
    }

    public void updateValueText(){
        sizeXLabel.setText(String.valueOf((int) sizeX.getValue()));
        sizeYLabel.setText(String.valueOf((int) sizeY.getValue()));
        initialPlantCountLabel.setText(String.valueOf((int) initialPlantCount.getValue()));
        initialPlantEnergyLabel.setText(String.valueOf((int) initialPlantEnergy.getValue()));
        growPerDayLabel.setText(String.valueOf((int) growPerDay.getValue()));
        animalCountLabel.setText(String.valueOf((int) animalCount.getValue()));
        animalEnergyLabel.setText(String.valueOf((int) animalEnergy.getValue()));
        reproduceEnergyLabel.setText(String.valueOf((int) reproduceEnergy.getValue()));
        reproduceLossLabel.setText(String.valueOf((int) reproduceLoss.getValue()));
        energyLossLabel.setText(String.valueOf((int) energyLoss.getValue()));
        genomeLenLabel.setText(String.valueOf((int) genomeLen.getValue()));
        turnTimeLabel.setText(String.valueOf((int) turnTime.getValue()));
        initialWaterCountLabel.setText(String.valueOf((int) initialWaterCount.getValue()));
        waterPoolSizeLabel.setText(String.valueOf((int) waterPoolSize.getValue()));
        waterPoolGrowRateLabel.setText(String.valueOf((int) waterPoolGrowRate.getValue()));
    }

    public void onSimulationStartClicked() {
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
                                                                        (int) turnTime.getValue(),
                                                                        (int) initialWaterCount.getValue(),
                                                                        (int) waterPoolSize.getValue(),
                                                                        (int) waterPoolGrowRate.getValue(),
                                                                        backAndForth.isSelected());
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
