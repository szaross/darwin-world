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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SimulationPresenter implements SimulationListener {
    private static final double CELL_HEIGHT = 35.0;
    private static final double CELL_WIDTH = 35.0;
    @FXML
    private Label movesLabel;

    @FXML
    private GridPane mapGrid;
    private int counter=0;
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
        counter++;
//        movesLabel.setText("licznik dni na testy: "+ counter);




        // corner
        Label corner = new Label("y\\x");
        mapGrid.add(corner,0,0);
        GridPane.setHalignment(corner, HPos.CENTER);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));

        // columns
        int column=1;
        for (int i = map.getBoundary().lower_left().getX(); i <= map.getBoundary().upper_right().getX(); i++) {
            Label label = new Label("%d".formatted(i));
            mapGrid.add(label,column,0);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            column++;
        }

        // rows
        int row=1;
        for (int i = map.getBoundary().upper_right().getY(); i >= map.getBoundary().lower_left().getY(); i--) {
            Label label = new Label("%d".formatted(i));
            mapGrid.add(label,0,row);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            row++;
        }

        // place elements on map
        for(WorldElement element : map.getElements()){
            Label label = new Label("");
            mapGrid.add(label, element.getPosition().getX() - map.getBoundary().lower_left().getX() + 1, map.getBoundary().upper_right().getY() - element.getPosition().getY() + 1);
            GridPane.setHalignment(label, HPos.CENTER);

        }


        // adding colors
        List<Animal> max_animal = map.getAnimals().stream()
                .sorted(new AnimalComparator())
                .limit(1)
                .toList();

        int max_energy = max_animal.get(0).getEnergy();
        for (int x = map.getBoundary().lower_left().getX(); x <= map.getBoundary().upper_right().getX(); x++) {
            for (int y = map.getBoundary().lower_left().getY(); y <= map.getBoundary().upper_right().getY(); y++) {
                Pane cellPane = new Pane();
                cellPane.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
                if(map.getPlant(new Vector2d(x-1,y+1)) != null) {
                    cellPane.setStyle("-fx-background-color: rgba(19,193,19,0.43);");
                }
                else {
                    if(map.getTiles() != null){
                        HashMap<Vector2d, Tile> tile = map.getTiles();
                        Vector2d position = new Vector2d(x-1, y+1);
                        Tile t = tile.get(position);
                        if(t != null && t.getAnimals().size() > 0) {
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
//        statistics.updateStats(simulation.getMap().getTiles(), simulation.getMap().getBoundary());
        movesLabel.setText(stats.toString());
//        animalCountLabel.setText("Liczba zwierzat: " + statistics.getAnimalCount());
//        plantCountLabel.setText("Liczba roslin: " + statistics.getPlantCount());
//        averageEnergyLabel.setText("Srednia energia: " + String.format("%.2f", statistics.getAverageEnergy()));
//        averageLifespanLabel.setText("Srednia dlugosc zycia: " + statistics.getAverageLifespan());
//        freeTiles.setText("Wolne pola: " + statistics.getFreeTiles());
//        averageChildrenCount.setText("Srednia liczba dzieci: " + String.format("%.2f", statistics.getAverageChildrenCount()));
//        popularGenotype.setText(statistics.getGenotype());

    }
}
