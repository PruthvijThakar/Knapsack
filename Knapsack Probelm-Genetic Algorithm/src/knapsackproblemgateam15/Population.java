/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblemgateam15;

import java.util.ArrayList;


public class Population {
    private double totalFitness;
    private double meanFitness;
    private Chromosome bestSolution = new Chromosome();

    public double getTotalFitness() {
        return totalFitness;
    }

    public void setTotalFitness(double totalFitness) {
        this.totalFitness = totalFitness;
    }

    public double getMeanFitness() {
        return meanFitness;
    }

    public void setMeanFitness(double meanFitness) {
        this.meanFitness = meanFitness;
    }

    public Chromosome getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(Chromosome bestSolution) {
        this.bestSolution = bestSolution;
    }
}
