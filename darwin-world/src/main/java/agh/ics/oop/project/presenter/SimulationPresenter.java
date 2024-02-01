package agh.ics.oop.project.presenter;

import agh.ics.oop.project.interfaces.SimulationListener;
import agh.ics.oop.project.interfaces.WorldElement;
import agh.ics.oop.project.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SimulationPresenter implements SimulationListener {
    private static final double CELL_HEIGHT = 35.0;
    private static final double CELL_WIDTH = 35.0;
    @FXML
    private Label animalInfo;
    private Animal spectatingAnimal = null;
    private Pane spectatingAnimalPane = null;
    @FXML
    private Label movesLabel;

    @FXML
    private GridPane mapGrid;
    private Simulation simulation;

    public void drawMap() {
        clearGrid();
        drawMapTable();
        placeElementsOnMap();
    }

    private void drawMapTable() {
        WorldMap map = simulation.getMap();
        RowConstraints rowConstraints = new RowConstraints(1, CELL_HEIGHT, CELL_HEIGHT);
        rowConstraints.setVgrow(Priority.SOMETIMES);

        ColumnConstraints columnConstraints = new ColumnConstraints(1, CELL_WIDTH, CELL_WIDTH);
        columnConstraints.setHgrow(Priority.SOMETIMES);

        // corner
        Label corner = new Label("y\\x");
        mapGrid.add(corner, 0, 0);
        GridPane.setHalignment(corner, HPos.CENTER);
        mapGrid.getColumnConstraints().add(columnConstraints);
        mapGrid.getRowConstraints().add(rowConstraints);

        // columns
        int column = 1;
        for (int i = map.getBoundary().lower_left().x(); i <= map.getBoundary().upper_right().x(); i++) {
            Label label = new Label("%d".formatted(i));
            mapGrid.add(label, column, 0);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(columnConstraints);
            column++;
        }

        // rows
        int row = 1;
        for (int i = map.getBoundary().upper_right().y(); i >= map.getBoundary().lower_left().y(); i--) {
            Label label = new Label("%d".formatted(i));
            mapGrid.add(label, 0, row);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(rowConstraints);
            row++;
        }
    }

    private void placeElementsOnMap() {
        WorldMap map = simulation.getMap();
        for (WorldElement element : map.getElements()) {
            Label label = new Label("");
            mapGrid.add(label, element.getPosition().x() - map.getBoundary().lower_left().x() + 1, map.getBoundary().upper_right().y() - element.getPosition().y() + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (WaterPool pool : map.getWaterCenters()) {
            Label label = new Label("");
            mapGrid.add(label, pool.getPosition().x() - map.getBoundary().lower_left().x() + 1, map.getBoundary().upper_right().y() - pool.getPosition().y() + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void applyColors() {
        WorldMap map = simulation.getMap();

        Animal max_animal = strongestAnimalOnMap();
        if (max_animal == null) return;

        int max_energy = max_animal.getEnergy();
        for (int x = map.getBoundary().lower_left().x(); x <= map.getBoundary().upper_right().x() + 1; x++) {
            for (int y = map.getBoundary().lower_left().y() - 1; y <= map.getBoundary().upper_right().y(); y++) {
                Pane cellPane = new Pane();
                if (map.getTiles().get(new Vector2d(x - 1, y + 1)) != null) {
                    HashMap<Vector2d, Tile> tile = map.getTiles();
                    Vector2d position = new Vector2d(x - 1, y + 1);
                    Tile t = tile.get(position);
                    // water
                    if (map.getTiles().get(position) != null && map.getTiles().get(position).containsWater()) {
                        cellPane.setStyle("-fx-background-color: rgba(0,217,255,0.62);");
                    }

                    Animal curr_max_animal = strongestAnimalOnTile(t);
                    if (curr_max_animal != null) {

                        // if spectating animal is on this tile, show him instead of the strongest
                        if (t.getAnimals().contains(spectatingAnimal)) {
                            curr_max_animal = spectatingAnimal;
                            spectatingAnimalPane = cellPane;
                        }

                        double curr_energy = (double) curr_max_animal.getEnergy() / max_energy;
                        double opacity = Math.min(Math.max(0.2, curr_energy), 0.7);

                        // different color for spectating animal
                        String cssStyle = curr_max_animal == spectatingAnimal ? String.format(Locale.ROOT, "-fx-background-color: rgba(255, 23, 255, %.2f);", opacity) : String.format(Locale.ROOT, "-fx-background-color: rgba(255, 0, 23, %.2f);", opacity);
                        cellPane.setStyle(cssStyle);

                        Animal finalCurr_max_animal = curr_max_animal;
                        cellPane.setOnMouseClicked(event -> {
                            if (!simulation.isActive()) {
                                if (spectatingAnimalPane != null) {
                                    Animal max_animal2 = strongestAnimalOnMap();
                                    if (max_animal2 == null) return; // shouldn't happen

                                    int max_energy2 = max_animal2.getEnergy();
                                    Animal strongest = strongestAnimalOnTile(map.getTiles().get(spectatingAnimal.getPosition()));
                                    if (strongest != null) { // spectatingAnimal may be dead and the tile empty
                                        double curr_energy2 = (double) strongest.getEnergy() / max_energy2;
                                        double opacity2 = Math.min(Math.max(0.2, curr_energy2), 0.7);
                                        spectatingAnimalPane.setStyle(String.format(Locale.ROOT, "-fx-background-color: rgba(255, 0, 23, %.2f);", opacity2));
                                    }
                                }

                                spectatingAnimal = finalCurr_max_animal;
                                spectatingAnimalPane = cellPane;
                                spectatingAnimalPane.setStyle(String.format(Locale.ROOT, "-fx-background-color: rgba(255, 23, 255, %.2f);", opacity));
                                spectateAnimal();
                            }
                        });

                    }

                    if (map.getPlant(new Vector2d(x - 1, y + 1)) != null && map.getTiles().get(new Vector2d(x - 1, y + 1)).getAnimals().isEmpty()) {
                        cellPane.setStyle("-fx-background-color: rgba(19,193,19,0.43);");
                    }

                    mapGrid.add(cellPane, x - map.getBoundary().lower_left().x(), map.getBoundary().upper_right().y() - y);
                }
            }
        }

    }

    private synchronized Animal strongestAnimalOnTile(Tile t) {
        if (t != null && !t.getAnimals().isEmpty()) {
            return t.getAnimals().stream().sorted(new AnimalComparator()).limit(1).toList().get(0);
        } else return null;
    }

    private synchronized Animal strongestAnimalOnMap() {
        List<Animal> max_animal = simulation.getMap().getAnimals().stream().sorted(new AnimalComparator()).limit(1).toList();
        if (max_animal.isEmpty()) return null;

        return max_animal.get(0);
    }

    @Override
    public void mapChanged(Simulation simulation) {
        Platform.runLater(() -> {
            drawMap();
            applyColors();
            displayStatistics(simulation.getStats());
            spectateAnimal();
        });
    }

    private void spectateAnimal() {
        if (spectatingAnimal != null) {
            String status;
            if (spectatingAnimal.getEnergy() > 0) status = "Status: alive";
            else status = "Status: dead\n" + "Death date: " + spectatingAnimal.getDeathDate() + " day of simulation";
            animalInfo.setText(spectatingAnimal.toString() + status);
        }
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    private void displayStatistics(Statistics stats) {
        movesLabel.setText(stats.toString());
    }

    public void onSimulationPause() {
        simulation.changeStatus();
    }
}
