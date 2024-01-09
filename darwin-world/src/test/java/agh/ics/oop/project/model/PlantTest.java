package agh.ics.oop.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlantTest {
    @Test
    public void getPositionTest()
    {
        Plant plant1 = new Plant(new Vector2d(4,5),40);
        Plant plant2 = new Plant(new Vector2d(7,3),40);
        assertEquals(new Vector2d(4,5), plant1.getPosition());
        assertEquals(new Vector2d(7,3), plant2.getPosition());
    }

    @Test
    public void getEnergyTest()
    {
        Plant plant1 = new Plant(new Vector2d(4,5),40);
        Plant plant2 = new Plant(new Vector2d(7,-3),60);
        assertEquals(40, plant1.getEnergy());
        assertEquals(60, plant2.getEnergy());
    }

    @Test
    public void setEnergyTest()
    {
        Plant plant1 = new Plant(new Vector2d(4,5),40);
        Plant plant2 = new Plant(new Vector2d(7,-3),40);
        plant1.setEnergy(70);
        plant2.setEnergy(20);
        assertEquals(70, plant1.getEnergy());
        assertEquals(20, plant2.getEnergy());
    }

}
