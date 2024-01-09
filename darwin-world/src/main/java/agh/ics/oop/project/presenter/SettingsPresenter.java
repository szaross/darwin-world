package agh.ics.oop.project.presenter;

import agh.ics.oop.project.gui.SimulationApp;
import agh.ics.oop.project.model.SimulationConfiguration;
import agh.ics.oop.project.model.util.SimulationConfigurationLoader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class SettingsPresenter {
    @FXML
    private ListView configurationListView;
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
    private ToggleButton water;

    @FXML
    public void initialize() {
        updateListView();
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
    private void updateSliderValues(SimulationConfiguration configuration) {
        if (configuration != null) {
            sizeX.setValue(configuration.getMapSizeX());
            sizeY.setValue(configuration.getMapSizeY());
            initialPlantCount.setValue(configuration.getInitialPlantCount());
            initialPlantEnergy.setValue(configuration.getInitialPlantEnergy());
            growPerDay.setValue(configuration.getNumberOfPlantsGrowingPerDay());
            animalCount.setValue(configuration.getInitialAnimalCount());
            animalEnergy.setValue(configuration.getInitialAnimalEnergy());
            reproduceEnergy.setValue(configuration.getReadyToReproduceEnergy());
            reproduceLoss.setValue(configuration.getReproduceEnergyLoss());
            energyLoss.setValue(configuration.getEnergyLossEachDay());
            genomeLen.setValue(configuration.getGenomeLength());
            turnTime.setValue(configuration.getTurnTimeInMs());
            initialWaterCount.setValue(configuration.getInitialWaterCount());
            waterPoolSize.setValue(configuration.getWaterPoolSize());
            waterPoolGrowRate.setValue(configuration.getWaterPoolGrowRate());

            backAndForth.setSelected(configuration.isBackAndForth());
            water.setSelected(configuration.isWater());
        }
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
                                                                        backAndForth.isSelected(), water.isSelected());
        SimulationApp app = new SimulationApp(config);

        Platform.runLater(() -> {
            try {
                app.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateListView(){
        // load saved configurations
        List<SimulationConfiguration> configurationList;
        try {
            configurationList = SimulationConfigurationLoader.loadConfigurations();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        configurationListView.getItems().addAll(configurationList);

        configurationListView.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) ->{
            System.out.println(newVal);
            SimulationConfiguration newConfig = configurationList.get((Integer) newVal);
            updateSliderValues(newConfig);
        });
    }

}
