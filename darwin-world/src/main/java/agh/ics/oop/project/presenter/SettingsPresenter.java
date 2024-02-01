package agh.ics.oop.project.presenter;

import agh.ics.oop.project.gui.SimulationApp;
import agh.ics.oop.project.model.SimulationConfiguration;
import agh.ics.oop.project.model.util.SimulationConfigurationLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SettingsPresenter {
    private final static int NUMBER_OF_PREDEFINED_CONFIGURATIONS = 3;
    @FXML
    private CheckBox saveStatsToFile;
    @FXML
    private ListView<SimulationConfiguration> configurationListView;
    @FXML
    private ToggleButton backAndForth;
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
    private TextField minMutation;
    @FXML
    private TextField maxMutation;
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
    private List<SimulationConfiguration> configurationList = new ArrayList<>();

    @FXML
    public void initialize() {
        initListView();
        updateListView();
        updateValueText();
        sizeX.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        sizeY.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        initialPlantCount.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        initialPlantEnergy.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        growPerDay.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        animalCount.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        animalEnergy.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        reproduceEnergy.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        reproduceLoss.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        energyLoss.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        genomeLen.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        turnTime.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        initialWaterCount.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        waterPoolSize.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
        waterPoolGrowRate.valueProperty().addListener((observable, oldValue, newValue) -> updateValueText());
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

    public void updateValueText() {
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
        SimulationConfiguration config = getSelectedConfiguration();
        if (config != null) {
            boolean saveStats = saveStatsToFile.isSelected();
            SimulationApp app = new SimulationApp(config, saveStats);

            Platform.runLater(() -> {
                try {
                    app.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private void updateListView() {
        // load saved configurations
        try {
            configurationList = SimulationConfigurationLoader.loadConfigurations();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        configurationListView.getItems().clear();
        configurationListView.getItems().addAll(configurationList);
    }

    private void initListView() {
        configurationListView.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            if ((Integer) newVal == -1) return;
            SimulationConfiguration newConfig = configurationList.get((Integer) newVal);
            updateSliderValues(newConfig);
        });
    }

    public void onConfigurationSaveClicked() {
        SimulationConfiguration config = getSelectedConfiguration();
        configurationList.add(config);
        SimulationConfigurationLoader.saveConfigurations(configurationList);
        updateListView();

    }

    public boolean validateInput() {
        //Min and max mutation validation
        String minText = minMutation.getText();
        String maxText = maxMutation.getText();

        if (minText.isEmpty() || maxText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Pola mutacji nie moga byc puste!", ButtonType.OK);
            alert.show();
            return false;
        }


        for (int i = 0; i < minText.length(); i++) {
            if ((int) minText.charAt(i) < 48 || (int) minText.charAt(i) > 57) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Wpisano niepoprawne dane w minimalnej mutacji!", ButtonType.OK);
                alert.show();
                return false;
            }
        }

        for (int i = 0; i < maxText.length(); i++) {
            if ((int) maxText.charAt(i) < 48 || (int) maxText.charAt(i) > 57) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Wpisano niepoprawne dane w maksymalnej mutacji!", ButtonType.OK);
                alert.show();
                return false;
            }
        }

        int minVal = Integer.parseInt(minMutation.getText());
        int maxVal = Integer.parseInt(maxMutation.getText());

        if (minVal > maxVal) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Minimalna liczba mutacji jest wieksza niz maksymalna!", ButtonType.OK);
            alert.show();
            return false;
        }
        if (maxVal > (int) genomeLen.getValue()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Ilosc mutacji jest wieksza niz dlugosc genu!", ButtonType.OK);
            alert.show();
            return false;
        }

        // Reproduction energy validation

        if (reproduceEnergy.getValue() < reproduceLoss.getValue()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Strata energii przy rozmnazaniu powinna byc mniejsza niz energia gotowosci do rozmnazania!", ButtonType.OK);
            alert.show();
            return false;
        }
        return true;
    }

    private SimulationConfiguration getSelectedConfiguration() {
        if (validateInput()) {
            return new SimulationConfiguration((int) sizeX.getValue(),
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
                    backAndForth.isSelected(),
                    water.isSelected(),
                    Integer.parseInt(minMutation.getText()),
                    Integer.parseInt(maxMutation.getText()));
        }
        return null;
    }


    public void onConfigurationDeleteClicked() {
        int selectedIndex = configurationListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) return;
        if (selectedIndex < NUMBER_OF_PREDEFINED_CONFIGURATIONS) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Nie mozna usunac predefinowanych konfiguracji!", ButtonType.OK);
            alert.show();
            return;
        }
        configurationList.remove(selectedIndex);
        SimulationConfigurationLoader.saveConfigurations(configurationList);
        updateListView();
    }
}
