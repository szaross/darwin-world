package agh.ics.oop.project.model;

import agh.ics.oop.project.interfaces.Movable;
import agh.ics.oop.project.interfaces.MoveValidator;
import agh.ics.oop.project.interfaces.WorldElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Animal implements Movable, WorldElement {
    private final Genotype genotype;
    private Vector2d position;
    private Direction direction;
    private int activeGene;
    private int energy;
    private int childrenCount = 0;
    private int age = 0;
    private int deathDate = 0;
    private int plantEaten = 0;

    public Animal(Vector2d position, int energy, int genotypeSize) {
        this.position = position;
        this.energy = energy;

        // random direction and active gene
        Random random = new Random();
        this.direction = Direction.randomDirection();
        this.activeGene = random.nextInt(genotypeSize);

        // random genes
        List<Integer> genes = new ArrayList<>();
        for (int i = 0; i < genotypeSize; i++) {
            genes.add(random.nextInt(8));
        }
        this.genotype = new Genotype(genes);
    }

    public Animal(Vector2d position, int energy, Genotype genotype) {
        this.position = position;
        this.energy = energy;

        // random direction and active gene
        Random random = new Random();
        this.direction = Direction.randomDirection();
        this.activeGene = random.nextInt(genotype.getGenes().size());

        this.genotype = genotype;
    }

    //testy
    public Animal(Vector2d position, int energy, Genotype genotype, int activeGene, Direction direction) {
        this.position = position;
        this.energy = energy;
        this.genotype = genotype;
        this.direction = direction;
        this.activeGene = activeGene;
    }

    @Override
    public void move(MoveValidator validator) {
        direction = direction.rotate(genotype.getGenes().get(activeGene));

        int genotypeSize = genotype.getGenes().size();
        activeGene = (activeGene + 1) % genotypeSize;

        // calculate new pos
        Vector2d new_pos = position.add(direction.toUnitVector());
        Boundary boundary = validator.getBoundary();

        if (new_pos.y() > boundary.upper_right().y() || new_pos.y() < boundary.lower_left().y()) {
            new_pos = new_pos.subtract(new Vector2d(0, direction.toUnitVector().y()));
            direction = direction.rotate(4);
        }
        // animal is beyond right wall
        if (new_pos.x() > boundary.upper_right().x()) {
            new_pos = new_pos.subtract(new Vector2d(boundary.upper_right().x() + 1, 0));
        }
        // animal is beyond left wall
        else if (new_pos.x() < boundary.lower_left().x()) {
            new_pos = new_pos.add(new Vector2d(boundary.upper_right().x() + 1, 0));
        }

        if (validator.canMoveTo(new_pos)) {
            position = new_pos;
        }
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(int deathDate) {
        this.deathDate = deathDate;
    }

    public void addChildCount() {
        childrenCount += 1;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    @Override
    public String toString() {
        return "Age: %d\n".formatted(getAge()) + "Position: (%d,%d)\n".formatted(getPosition().x(), getPosition().y()) + "Direction: %s\n".formatted(getDirection()) + "Energy: %d\n".formatted(getEnergy()) + "Genotype: %s\n".formatted(getGenotype()) + "Children count: %d\n".formatted(getChildrenCount()) + "Active gene: %d\n".formatted(getActiveGene()) + "Eaten plants: %d\n".formatted(getPlantEaten());
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getActiveGene() {
        return activeGene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return activeGene == animal.activeGene && energy == animal.energy && childrenCount == animal.childrenCount && age == animal.age && Objects.equals(position, animal.position) && direction == animal.direction && Objects.equals(genotype, animal.genotype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction, genotype, activeGene, energy, childrenCount, age);
    }

    public int getPlantEaten() {
        return plantEaten;
    }

    public void setPlantEaten() {
        this.plantEaten += 1;
    }
}
