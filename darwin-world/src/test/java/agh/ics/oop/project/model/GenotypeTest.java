package agh.ics.oop.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenotypeTest {
    private Genotype gen1;
    private Genotype gen2;
    private Genotype gen3;
    private Genotype gen4;

    @BeforeEach
    public void setUp() {
        gen1 = new Genotype(new ArrayList<>(List.of(0,1,3,5,6)));
        gen2 = new Genotype(new ArrayList<>(List.of(0,1,3,5,6)));
        gen3 = new Genotype(new ArrayList<>(List.of(3,4,1,2,4,5,3,1,7,7,7,0,0,0)));
        gen4 = new Genotype(new ArrayList<>(List.of(3,4,1,2,4,5,3,1,7,7,7,0,0,0)));
    }
    @Test
    public void setTest(){
        gen1.setGenes(new ArrayList<>(List.of(3,4,5,3,1)));
        assertEquals(gen1.getGenes(), new ArrayList<>(List.of(3,4,5,3,1)));

        gen2.setGenes(new ArrayList<>(List.of(1,2,5,5,0)));
        assertEquals(gen2.getGenes(), new ArrayList<>(List.of(1,2,5,5,0)));
    }
}
