package agh.ics.oop.project.presenter;

import agh.ics.oop.project.interfaces.Map;
import agh.ics.oop.project.interfaces.SimulationListener;
import agh.ics.oop.project.interfaces.WorldElement;
import agh.ics.oop.project.model.*;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SimulationPresenter implements SimulationListener {
    private static final double CELL_HEIGHT = 35.0/2;
    private static final double CELL_WIDTH = 35.0/2;
    @FXML
    private Label movesLabel;

    @FXML
    private GridPane mapGrid;
    private Simulation simulation;

    @FXML
    private Label animalCountLabel;
    @FXML
    private Label plantCountLabel;
    @FXML
    private Label averageEnergyLabel;
    @FXML
    private Label averageLifespanLabel;
    @FXML
    private Label freeTiles;
    @FXML

    private Label averageChildrenCount;
    @FXML
    private Label popularGenotype;

    public void drawMap(){
        WorldMap map = simulation.getMap();

        clearGrid();
        RowConstraints rowConstraints = new RowConstraints(1,CELL_HEIGHT,Integer.MAX_VALUE);
        rowConstraints.setVgrow(Priority.SOMETIMES);

        ColumnConstraints columnConstraints = new ColumnConstraints(1,CELL_WIDTH,Integer.MAX_VALUE);
        columnConstraints.setHgrow(Priority.SOMETIMES);

        // corner
        Label corner = new Label("y\\x");
        mapGrid.add(corner,0,0);
        GridPane.setHalignment(corner, HPos.CENTER);
        mapGrid.getColumnConstraints().add(columnConstraints);
        mapGrid.getRowConstraints().add(rowConstraints);

        // columns
        int column=1;
        for (int i = map.getBoundary().lower_left().getX(); i <= map.getBoundary().upper_right().getX(); i++) {
            Label label = new Label("%d".formatted(i));
            mapGrid.add(label,column,0);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(columnConstraints);
            column++;
        }

        // rows
        int row=1;
        for (int i = map.getBoundary().upper_right().getY(); i >= map.getBoundary().lower_left().getY(); i--) {
            Label label = new Label("%d".formatted(i));
            mapGrid.add(label,0,row);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(rowConstraints);
            row++;
        }

        // place elements on map
        for(WorldElement element : map.getElements()){
            Label label = new Label(" ");
            mapGrid.add(label, element.getPosition().getX() - map.getBoundary().lower_left().getX() + 1, map.getBoundary().upper_right().getY() - element.getPosition().getY() + 1);
            GridPane.setHalignment(label, HPos.CENTER);

        }


        // adding colors
        List<Animal> max_animal = map.getAnimals().stream()
                .sorted(new AnimalComparator())
                .limit(1)
                .toList();
        if (max_animal.isEmpty()) return;

        int max_energy = max_animal.get(0).getEnergy();
        for (int x = map.getBoundary().lower_left().getX(); x <= map.getBoundary().upper_right().getX()+1 ; x++) {
            for (int y = map.getBoundary().lower_left().getY() -1; y <= map.getBoundary().upper_right().getY(); y++) {
                Pane cellPane = new Pane();
//                cellPane.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
                if(map.getTiles() != null){
                    HashMap<Vector2d, Tile> tile = map.getTiles();
                    Vector2d position = new Vector2d(x-1, y+1);
                    Tile t = tile.get(position);
                    if(t != null && t.getAnimals().size() > 0) {
                        synchronized (this){
                        List<Animal> curr_max_animal = t.getAnimals().stream()
                                .sorted(new AnimalComparator())
                                .limit(1)
                                .toList();

                        double curr_energy = (double) curr_max_animal.get(0).getEnergy() / max_energy;
                        double opacity = Math.min(Math.max(0.2,curr_energy), 0.7);
                        String cssStyle = String.format(Locale.ROOT, "-fx-background-color: rgba(255, 0, 23, %.2f);", opacity);
                        cellPane.setStyle(cssStyle);
                    }
                    }
                }
                if(map.getPlant(new Vector2d(x-1,y+1)) != null && map.getTiles().get(new Vector2d(x-1,y+1)).getAnimals().size() == 0) {
                    cellPane.setStyle("-fx-background-color: rgba(19,193,19,0.43);");
                }
                mapGrid.add(cellPane, x - map.getBoundary().lower_left().getX(), map.getBoundary().upper_right().getY() - y);
            }
        }

    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }


    @Override
    public void mapChanged(Simulation simulation) {
        Platform.runLater(()->{
            drawMap();
            displayStatistics(simulation.getStats());
        });
    }

    public void setSimulation(Simulation simulation) {

        this.simulation = simulation;
    }

    private void displayStatistics(Statistics stats) {
        movesLabel.setText(stats.toString());
    }
}
