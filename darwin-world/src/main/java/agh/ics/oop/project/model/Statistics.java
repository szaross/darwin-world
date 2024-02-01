package agh.ics.oop.project.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private static final DateFormat dateformat = new SimpleDateFormat("-yyyy-MM-dd-HH-mm-ss");
    private final File CONFIG_FILE_PATH;
    private final HashMap<Genotype, Integer> genotypeCounts = new HashMap<>();
    private int day = 0;
    private int animalCount;
    private int plantCount;
    private int freeTiles;
    private double currentAge = 0;
    private double deadAge = 0;
    private double averageEnergy;
    private double averageChildrenCount;
    private int deadCount = 0;

    public Statistics() {
        Date now = new Date();
        CONFIG_FILE_PATH = new File("src/main/resources/stats" + dateformat.format(now) + ".csv");
    }

    public void updateStats(HashMap<Vector2d, Tile> tiles, Boundary boundary) {
        day += 1;
        genotypeCounts.clear();
        animalCount = 0;
        plantCount = 0;
        freeTiles = 0;
        averageChildrenCount = 0;
        averageEnergy = 0;
        currentAge = 0;

        int fullTiles = 0;
        for (Tile tile : tiles.values()) {
            if (tile.getPlant() != null) {
                plantCount += 1;
            }
            if (tile.getPlant() != null || !tile.getAnimals().isEmpty()) {
                fullTiles += 1;
            }
            List<Animal> animals = tile.getAnimals();
            for (Animal animal : animals) {
                averageEnergy += animal.getEnergy();
                currentAge += animal.getAge();
                averageChildrenCount += animal.getChildrenCount();
                Genotype genotype = animal.getGenotype();
                genotypeCounts.put(genotype, genotypeCounts.getOrDefault(genotype, 0) + 1);
            }
            animalCount += animals.size();
        }
        averageEnergy = averageEnergy / animalCount;
        averageChildrenCount = averageChildrenCount / animalCount;

        int width = boundary.upper_right().x() - boundary.lower_left().x() + 1;
        int height = boundary.upper_right().y() - boundary.lower_left().y() + 1;
        int area = width * height;
        freeTiles = area - fullTiles;


    }

    public void updateDead(int age, int count) {
        deadAge += age;
        deadCount += count;
    }


    public synchronized String getGenotype() {
        Map.Entry<Genotype, Integer> mostPopularGenotype = null;
        for (Map.Entry<Genotype, Integer> entry : genotypeCounts.entrySet()) {
            if (mostPopularGenotype == null || entry.getValue() > mostPopularGenotype.getValue()) {
                mostPopularGenotype = entry;
            }
        }

        if (mostPopularGenotype != null) {
            if (mostPopularGenotype.getValue() == 1) {
                return mostPopularGenotype.getValue() + " animal has the same genotype: " + mostPopularGenotype.getKey().toString();
            } else {
                return mostPopularGenotype.getValue() + " animals have the same genotype: " + mostPopularGenotype.getKey().toString();
            }

        }

        return " ";

    }

    public void saveToCsv() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH, true)) {
            if (day == 0) {
                writer.append("day;animalCount;plantCount;freeTiles;averageLifespan;averageEnergy;averageChildrenCount\n");
            }
            String result = "%d;%d;%d;%d;%.2f;%.2f;%.2f\n".formatted(day, animalCount, plantCount, freeTiles, ((currentAge + deadAge) / (animalCount + deadCount)), averageEnergy, averageChildrenCount);

            writer.append(result);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        String result = "";
        result += "Day: " + day + "\n";
        result += "Animal count: " + animalCount + "\n";
        result += "Plant count: " + plantCount + "\n";
        result += "Free tiles: " + freeTiles + "\n";
        result += "Average lifespan: " + String.format("%.2f", ((currentAge + deadAge) / (animalCount + deadCount))) + "\n";
        result += "Average energy: " + String.format("%.2f", averageEnergy) + "\n";
        result += "Average children count: " + String.format("%.2f", averageChildrenCount) + "\n";

        result += this.getGenotype() + "\n";
        return result;
    }
}
