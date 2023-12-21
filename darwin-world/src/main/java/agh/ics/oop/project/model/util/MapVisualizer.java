package agh.ics.oop.project.model.util;

import agh.ics.oop.project.model.Animal;
import agh.ics.oop.project.model.Plant;
import agh.ics.oop.project.model.Vector2d;
import agh.ics.oop.project.interfaces.WorldElement;
import agh.ics.oop.project.model.WorldMap;

public class MapVisualizer {
    private static final String EMPTY_CELL = " ";
    private static final String FRAME_SEGMENT = "-";
    private static final String CELL_SEGMENT = " | "; //tu spacje
    private final WorldMap map;

    public MapVisualizer(WorldMap map) {
        this.map = map;
    }

    public String draw(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.getY() + 1; i >= lowerLeft.getY() - 1; i--) {
            if (i == upperRight.getY() + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.getX(); j <= upperRight.getX() + 1; j++) {
                if (i < lowerLeft.getY() || i > upperRight.getY()) {
                    builder.append(drawFrame(j <= upperRight.getX()));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.getX()) {
                        builder.append(drawObject(new Vector2d(j, i)));
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.getX(); j < upperRight.getX() + 1; j++) {
            builder.append(String.format(" %2d ", j)); //tu osie
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    //tu trzeba gigantyczną poprawke napisać w draw Object
    // narazie jak jest jeden obiekt to sie on wyswietla, jak jest wiecej niz jeden to wyswietla sie ilosc obiektow na tym polu
    private String drawObject(Vector2d currentPosition) {
        if (this.map.isOccupied(currentPosition)) {
            //dla testów
            int len = this.map.objectsAt(currentPosition).size();
            Object object;
            if (len==1){
                object = this.map.objectsAt(currentPosition).get(0);
            }
            else{
                object = len;
            }


            if (object != null) {
                return object.toString();
            }
        }
        return EMPTY_CELL;
    }
}