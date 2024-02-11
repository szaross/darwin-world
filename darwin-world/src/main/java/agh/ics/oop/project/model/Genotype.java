package agh.ics.oop.project.model;

import java.util.*;
import java.util.stream.IntStream;

public class Genotype {
    private List<Integer> Genes;

    public Genotype(List<Integer> Genes) {
        this.Genes = Genes;
    }

    public void mutate(int min, int max) {
        Random random = new Random();
        int n = Genes.size();
        int how_many = random.nextInt(max + 1 - min) + min;

        List<Integer> positions = new ArrayList<>(IntStream.range(0, n).boxed().toList());
        Collections.shuffle(positions);
        positions = positions.subList(0, how_many);

        int new_gene;

        for (Integer position : positions) {
            do new_gene = random.nextInt(8);
            while (new_gene == Genes.get(position));
            Genes.set(position, new_gene);
        }
    }

    public List<Integer> getGenes() {
        return Genes;
    }

    public void setGenes(List<Integer> Genes) {
        this.Genes = Genes;
    }

    public void reverse() {
        Collections.reverse(Genes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Objects.equals(Genes, genotype.Genes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Genes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int num : Genes) {
            sb.append(num);
        }

        return sb.toString();
    }
}
