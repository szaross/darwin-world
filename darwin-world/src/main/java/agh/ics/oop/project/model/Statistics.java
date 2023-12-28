package agh.ics.oop.project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private String formattedNumber;
    private int animalCount;
    private int plantCount;
    private int freeTiles;
    private double averageLifespan;
    private double averageEnergy;
    private double averageChildrenCount;
    private int deadCount;
    private HashMap<Genotype, Integer> genotypeCounts = new HashMap<>();

    public void updateStats(HashMap<Vector2d, Tile> tiles, Boundary boundary) {
        genotypeCounts.clear();
        animalCount = 0;
        plantCount = 0;
        freeTiles = 0;
        averageChildrenCount = 0;
        averageEnergy = 0;

        int fullTiles = 0;
        for (Tile tile : tiles.values()) {
            if (tile.getPlant() != null) {
                plantCount += 1;
            }
            if(tile.getPlant() != null || !tile.getAnimals().isEmpty()) {
                fullTiles += 1;
            }
            List<Animal> animals = tile.getAnimals();
            for (Animal animal : animals) {
                averageEnergy += animal.getEnergy();
                averageChildrenCount += animal.getChildrenCount();
                Genotype genotype = animal.getGenotype();
                genotypeCounts.put(genotype, genotypeCounts.getOrDefault(genotype, 0) + 1);
            }
            animalCount += animals.size();
        }
        averageEnergy = averageEnergy / animalCount;
        averageChildrenCount = averageChildrenCount / animalCount;

        int width = boundary.upper_right().getX() - boundary.lower_left().getX() + 1;
        int height = boundary.upper_right().getY() - boundary.lower_left().getY() + 1;
        int area = width * height;
        freeTiles = area - fullTiles;
    }

    public void lifespanStats(double age, int deadCount) {
        averageLifespan += age;
        this.deadCount += deadCount;
    }

    public void printStats(){
        Map.Entry<Genotype, Integer> mostPopularGenotype = null;
        for (Map.Entry<Genotype, Integer> entry : genotypeCounts.entrySet()) {
            if (mostPopularGenotype == null || entry.getValue() > mostPopularGenotype.getValue()) {
                mostPopularGenotype = entry;
            }
        }
        System.out.println("Animal count: " + animalCount);
        System.out.println("Plant count: " + plantCount);
        System.out.println("Free tiles: " + freeTiles);

        if(deadCount != 0) {
            formattedNumber = String.format("%.2f", averageLifespan/deadCount);
        }
        else {
            formattedNumber = String.format("%.2f", 0.0);
        }

        System.out.println("Average lifespan: " + formattedNumber);
        System.out.println("Average energy: " + String.format("%.2f",averageEnergy));
        System.out.println("Average children count: " + String.format("%.2f",averageChildrenCount));
        if (mostPopularGenotype != null) {
            System.out.println(mostPopularGenotype.getValue() +" animals has the same genotype: " + mostPopularGenotype.getKey().toString());
        }
    }

}
