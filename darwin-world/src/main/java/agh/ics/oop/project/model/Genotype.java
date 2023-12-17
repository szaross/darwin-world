package agh.ics.oop.project.model;

import java.util.*;

public class Genotype {
    private List<Integer> Genes = new ArrayList<>();

    public Genotype(List<Integer> Genes) {
        this.Genes = Genes;
    }

    public void mutate() {
        Random random = new Random();
        int n = Genes.size();
        int how_many = random.nextInt(n) + 1;
        Set<Integer> positions = new HashSet<>();

        while(positions.size() != how_many)
            positions.add(random.nextInt(n));

        int new_gene;
        for(Integer position : positions) {
            do new_gene = random.nextInt(8);
                while (new_gene == Genes.get(position));
            Genes.set(position, new_gene);
        }
    }

    public void setGenes(List<Integer> Genes) {
        this.Genes = Genes;
    }

    public List<Integer> getGenes(){
        return Genes;
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
}
