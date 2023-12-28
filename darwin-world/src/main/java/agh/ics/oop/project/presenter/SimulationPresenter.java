package agh.ics.oop.project.presenter;

import agh.ics.oop.project.interfaces.Map;
import agh.ics.oop.project.interfaces.SimulationListener;
import agh.ics.oop.project.interfaces.WorldElement;
import agh.ics.oop.project.model.Simulation;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements SimulationListener {
    private static final double CELL_HEIGHT = 35.0;
    private static final double CELL_WIDTH = 35.0;
    @FXML
    private Label movesLabel;

    @FXML
    private GridPane mapGrid;
    private int counter=0;
    private Map map;
    public void setWorldMap(Map map) {
        this.map = map;
    }

    public void drawMap(){
        clearGrid();
        counter++;
//        movesLabel.setText("licznik dni na testy: "+ counter);
        movesLabel.setText(String.valueOf(map.getElements()));

//        if (counter%2==0){
//            return;
//        }
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
        for(WorldElement element :map.getElements()){
            Label label = new Label(element.toString());
            mapGrid.add(label,element.getPosition().getX()-map.getBoundary().lower_left().getX()+1,map.getBoundary().upper_right().getY() - element.getPosition().getY()+1);
            GridPane.setHalignment(label, HPos.CENTER);
        }

    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }


    @Override
    public void mapChanged(Simulation simulation) {
        Platform.runLater(this::drawMap);
    }
}
