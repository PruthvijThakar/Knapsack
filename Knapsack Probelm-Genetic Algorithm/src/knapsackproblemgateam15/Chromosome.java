/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblemgateam15;

import java.util.ArrayList;
import java.util.Random;


public class Chromosome implements Comparable{
    private ArrayList<Integer> sequence = new ArrayList<>();
    private double weight = 0.0;
    private double value = 0.0;
    private double fitness = 0.0;

    public Chromosome(){
        
    }
    
    public Chromosome(ArrayList<Integer> seq){
        this.sequence = seq;
        this.calculateFitness();
    }
    public ArrayList<Integer> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<Integer> sequence) {
        this.sequence = sequence;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    
    public void generateSequence(){
        Random rand = new Random();
        for(int i=0; i< Main.INIT_ITEMS; i++){
                this.sequence.add(rand.nextInt(2));
        }
        
        this.calculateFitness();
    }
    
    public void calculateFitness(){
        double tempWeight=0.0, tempValue=0.0;
        for(int i=0; i< Main.INIT_ITEMS; i++){
            if(this.sequence.get(i) == 1){
                tempValue = tempValue + Main.ITEMS_POOL[i].getValue();
                tempWeight = tempWeight + Main.ITEMS_POOL[i].getWeight();
            }
        }
        this.weight = tempWeight;
        this.value = tempValue;
        if(this.weight <= Main.KNAPSACK_CAPACITY)
            this.fitness = this.value;
    }   
    
     @Override
    public int compareTo(Object comparestu) {
        double compareFitness=((Chromosome)comparestu).getFitness();
        /* For sorting in descending order do like this */
        return (int)(compareFitness-this.fitness);
    }
}
