package agh.ics.oop.project;

import agh.ics.oop.project.model.Genotype;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Genotype gen = new Genotype();
        gen.setGenes(new ArrayList<>(List.of(0,1,3,5,6,3,4,2,1,0,0,0,0,3,2,3,4)));

        System.out.println(gen.getGenes());
        gen.mutate();
        System.out.println(gen.getGenes() + "\n\n");
    }
}