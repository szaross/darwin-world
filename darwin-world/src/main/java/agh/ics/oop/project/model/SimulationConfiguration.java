package agh.ics.oop.project.model;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class SimulationConfiguration {
    @CsvBindByName
    private int mapSizeX;
    @CsvBindByName
    private int mapSizeY;
    @CsvBindByName
    private int initialPlantCount;
    @CsvBindByName
    private int initialPlantEnergy;
    @CsvBindByName
    private int numberOfPlantsGrowingPerDay;
    @CsvBindByName
    private int initialAnimalCount;
    @CsvBindByName
    private int initialAnimalEnergy;
    @CsvBindByName
    private int readyToReproduceEnergy;
    @CsvBindByName
    private int reproduceEnergyLoss;
    @CsvBindByName
    private int energyLossEachDay;
    @CsvBindByName
    private int genomeLength;
    @CsvBindByName
    private int turnTimeInMs;
    @CsvBindByName
    private boolean backAndForth;
    @CsvBindByName
    private int initialWaterCount;
    @CsvBindByName
    private int waterPoolSize;
    @CsvBindByName
    private int waterPoolGrowRate;
    @CsvBindByName
    private boolean water;

    @CsvBindByName
    private int minMutation;

    @CsvBindByName
    private int maxMutation;

    public SimulationConfiguration(int mapSizeX, int mapSizeY, int initialPlantCount, int initialPlantEnergy, int numberOfPlantsGrowingPerDay, int initialAnimalCount, int initialAnimalEnergy, int readyToReproduceEnergy, int reproduceEnergyLoss, int energyLossEachDay, int genomeLength, int turnTimeInMs, int initialWaterCount, int waterPoolSize, int waterPoolGrowRate, boolean backAndForth, boolean water, int minMutation, int maxMutation) {
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        this.initialPlantCount = initialPlantCount;
        this.initialPlantEnergy = initialPlantEnergy;
        this.numberOfPlantsGrowingPerDay = numberOfPlantsGrowingPerDay;
        this.initialAnimalCount = initialAnimalCount;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.readyToReproduceEnergy = readyToReproduceEnergy;
        this.reproduceEnergyLoss = reproduceEnergyLoss;
        this.energyLossEachDay = energyLossEachDay;
        this.genomeLength = genomeLength;
        this.turnTimeInMs = turnTimeInMs;
        this.backAndForth = backAndForth;
        this.initialWaterCount = initialWaterCount;
        this.waterPoolSize = waterPoolSize;
        this.waterPoolGrowRate = waterPoolGrowRate;
        this.water = water;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
    }

    public SimulationConfiguration() { // needed for opencsv beans

    }

    public int getMapSizeX() {
        return mapSizeX;
    }

    public int getMapSizeY() {
        return mapSizeY;
    }

    public int getInitialPlantCount() {
        return initialPlantCount;
    }

    public int getNumberOfPlantsGrowingPerDay() {
        return numberOfPlantsGrowingPerDay;
    }

    public int getInitialAnimalCount() {
        return initialAnimalCount;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }

    public int getReadyToReproduceEnergy() {
        return readyToReproduceEnergy;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getInitialPlantEnergy() {
        return initialPlantEnergy;
    }

    public int getTurnTimeInMs() {
        return turnTimeInMs;
    }

    public int getEnergyLossEachDay() {
        return energyLossEachDay;
    }

    public int getReproduceEnergyLoss() {
        return reproduceEnergyLoss;
    }

    public int getInitialWaterCount() {
        return initialWaterCount;
    }

    public int getWaterPoolSize() {
        return waterPoolSize;
    }

    public int getWaterPoolGrowRate() {
        return waterPoolGrowRate;
    }

    public boolean isBackAndForth() {
        return backAndForth;
    }

    public boolean isWater() {
        return water;
    }

    public int getMaxMutation() {
        return maxMutation;
    }

    public int getMinMutation() {
        return minMutation;
    }

    @Override
    public String toString() {
        return "SimulationConfiguration{" + " mapSizeX=" + mapSizeX + ", mapSizeY=" + mapSizeY + ", \ninitialPlantCount=" + initialPlantCount + ", initialPlantEnergy=" + initialPlantEnergy + ", \nnumberOfPlantsGrowingPerDay=" + numberOfPlantsGrowingPerDay + ", initialAnimalCount=" + initialAnimalCount + ", \ninitialAnimalEnergy=" + initialAnimalEnergy + ", readyToReproduceEnergy=" + readyToReproduceEnergy + ", \nreproduceEnergyLoss=" + reproduceEnergyLoss + ", energyLossEachDay=" + energyLossEachDay + ", \ngenomeLength=" + genomeLength + ", turnTimeInMs=" + turnTimeInMs + ", \ninitialWaterCount=" + initialWaterCount + ", waterPoolSize=" + waterPoolSize + ", \nwaterPoolGrowRate=" + waterPoolGrowRate + ", water=" + water + ", \nbackAndForth=" + backAndForth + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationConfiguration that = (SimulationConfiguration) o;
        return getMapSizeX() == that.getMapSizeX() && getMapSizeY() == that.getMapSizeY() && getInitialPlantCount() == that.getInitialPlantCount() && getInitialPlantEnergy() == that.getInitialPlantEnergy() && getNumberOfPlantsGrowingPerDay() == that.getNumberOfPlantsGrowingPerDay() && getInitialAnimalCount() == that.getInitialAnimalCount() && getInitialAnimalEnergy() == that.getInitialAnimalEnergy() && getReadyToReproduceEnergy() == that.getReadyToReproduceEnergy() && getReproduceEnergyLoss() == that.getReproduceEnergyLoss() && getEnergyLossEachDay() == that.getEnergyLossEachDay() && getGenomeLength() == that.getGenomeLength() && getTurnTimeInMs() == that.getTurnTimeInMs() && isBackAndForth() == that.isBackAndForth() && getInitialWaterCount() == that.getInitialWaterCount() && getWaterPoolSize() == that.getWaterPoolSize() && getWaterPoolGrowRate() == that.getWaterPoolGrowRate() && isWater() == that.isWater();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMapSizeX(), getMapSizeY(), getInitialPlantCount(), getInitialPlantEnergy(), getNumberOfPlantsGrowingPerDay(), getInitialAnimalCount(), getInitialAnimalEnergy(), getReadyToReproduceEnergy(), getReproduceEnergyLoss(), getEnergyLossEachDay(), getGenomeLength(), getTurnTimeInMs(), isBackAndForth(), getInitialWaterCount(), getWaterPoolSize(), getWaterPoolGrowRate(), isWater());
    }
}
