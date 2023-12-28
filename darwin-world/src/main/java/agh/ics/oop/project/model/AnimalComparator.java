package agh.ics.oop.project.model;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        int energyComparison = Integer.compare(animal2.getEnergy(), animal1.getEnergy());
        if (energyComparison != 0) {
            return energyComparison;
        }

        int ageComparison = Integer.compare(animal1.getAge(), animal2.getAge());
        if (ageComparison != 0) {
            return ageComparison;
        }

        int childrenComparison = Integer.compare(animal2.getChildrenCount(), animal1.getChildrenCount());
        if (childrenComparison != 0) {
            return childrenComparison;
        }

        return Math.random() < 0.5 ? -1 : 1;
    }
}