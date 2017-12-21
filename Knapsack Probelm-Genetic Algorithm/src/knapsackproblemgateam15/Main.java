/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblemgateam15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static final int INIT_TOTAL_CHROMOSOMES = 100;           //initial population size
    public static ArrayList<Chromosome> CHROMOSOME_POOL = new ArrayList<Chromosome>();      //final population pool at any given generation
    public static final int INIT_ITEMS = 20;                    //total number of items available to consider for knapsacking
    public static Item[] ITEMS_POOL = new Item[INIT_ITEMS];     //Array of available items
    public static final int KNAPSACK_CAPACITY = 30;             //total capacity of knapsack           
    public static int POP_GENERATIONS = 100;                    //number of generations this program will run for
    public static double CROSSOVER_PROB = 0.5;                  //probability of deciding whether crossover will take place between two chromosomes
    public static double CULLING_PERCENTAGE = 0.90;             //culling percentage to for each generation
    public static double MUTATION_PROB = 0.03;                  //mutation probability whether mutation will happen in certain generation
    public static int generation_counter = 0;                   //counter to keep track of current number generation
    public static ArrayList<Chromosome> breed_population = new ArrayList<Chromosome>(); //ArrayList to hold newly bred population
    public static Population current_population;                //Population object to hold attributes of pupolation of current generation
    public static ArrayList<Population> all_populations = new ArrayList<Population>();      //ArrayList which holds attributes of all popuations generated till now

    public static void main(String[] args) {

        //generate and show available items
        generateItems();
        System.out.println(INIT_ITEMS + " Items have been generated as:");
        System.out.println("Item No.    Weight      Value");
        for (int i = 0; i < INIT_ITEMS; i++) {
            System.out.println(i + "            " + ITEMS_POOL[i].getWeight() + "           " + ITEMS_POOL[i].getValue());
        }

        //generate and show initial pool of chromosomes
        generateChromosomes();
        System.out.println(INIT_TOTAL_CHROMOSOMES + " Initial chromosomes have been generated as:");
        System.out.println("Chromosome      Weight      Value       Fitness");
        for (int i = 0; i < INIT_TOTAL_CHROMOSOMES; i++) {
            for (int z : CHROMOSOME_POOL.get(i).getSequence()) {
                System.out.print(z);
            }

            System.out.println("        " + CHROMOSOME_POOL.get(i).getWeight() + "          " + CHROMOSOME_POOL.get(i).getValue()
                    + "           " + CHROMOSOME_POOL.get(i).getFitness());
        }

        current_population = new Population();
        //get total fitness of current population
        current_population.setTotalFitness(totalPopulationFitness());
        //get best chromosome from current population
        current_population.setBestSolution(getBestChromosomeOfGeneration());
        //get mean fitness of current population
        current_population.setMeanFitness(getMeanFitnessOfPopulation());
        //add current population to the pool of populations
        all_populations.add(current_population);

        if (POP_GENERATIONS > 1) {
            //create next generation
            createNewGenerations();
        }
    }

    public static void createNewGenerations() {
        for (int i = 1; i < POP_GENERATIONS; i++) {
            breed_population = new ArrayList<>();
            System.out.println("===========================================");
            
            //check if we have met the exit criteria
            //Once we have gone through sufficiently large generations(POP_GENERATIONS * 0.8),
            //if last 5 generations have same best solution then stop
            if ((POP_GENERATIONS > 6) && (i > POP_GENERATIONS * 0.8)) {
                double a = all_populations.get(i - 1).getBestSolution().getFitness();
                double b = all_populations.get(i - 2).getBestSolution().getFitness();
                double c = all_populations.get(i - 3).getBestSolution().getFitness();
                double d = all_populations.get(i - 4).getBestSolution().getFitness();
                double e = all_populations.get(i - 5).getBestSolution().getFitness();

                if (a == b && b == c && c == d && d == e) {
                    System.out.println("\nStop criterion met at generation:" + (i-1));
                    Chromosome solution = all_populations.get(i-1).getBestSolution();
                    System.out.println("Ideal Solution has fitness:" + solution.getFitness() + " weight:" + solution.getWeight() + " value:" + solution.getValue());
                    System.out.println("And the sequence is:");
                    for (int z : solution.getSequence()) {
                        System.out.print(z);
                    }
                    System.out.println("\n");
                    POP_GENERATIONS = i;
                    break;
                }
            }
            //else continue breeding next generation
            System.out.println("Generation:" + i);
            for (int j = 0; j < POP_GENERATIONS / 2; j++) {
                breedPopulation();
            }
            
            //Once new population is generated, Check if mutation is to be performed
            mutateGene();
            
            //evaluate fitness of each chromosome of newly generated population
            evalBreedPopulation();
            
            //add this newly generated population members to the original pool of chromosomes
            for (int k = 0; k < breed_population.size(); k++) {
                CHROMOSOME_POOL.add(k, breed_population.get(k));
            }
            
            //display updated chromosome pool
            System.out.println("New Chromosome pool | total:" + CHROMOSOME_POOL.size());
            System.out.println("Chromosome      Weight      Value       Fitness");
            for (int j = 0; j < CHROMOSOME_POOL.size(); j++) {
                for (int z : CHROMOSOME_POOL.get(j).getSequence()) {
                    System.out.print(z);
                }
                System.out.println("        " + CHROMOSOME_POOL.get(j).getWeight() + "          " + CHROMOSOME_POOL.get(j).getValue()
                        + "           " + CHROMOSOME_POOL.get(j).getFitness());
            }
            
            //perform culling on chromosome pool
            CHROMOSOME_POOL = culling(CHROMOSOME_POOL);
            current_population = new Population();
            //calculate attributes of this updated chromosome pool
            current_population.setTotalFitness(totalPopulationFitness());
            current_population.setBestSolution(getBestChromosomeOfGeneration());
            current_population.setMeanFitness(getMeanFitnessOfPopulation());
            //add it to the population pool to track
            all_populations.add(current_population);

        }
    }

    public static ArrayList<Chromosome> culling(ArrayList<Chromosome> toBeCulled) {

        double check = Math.random();
        if (check > 0.5) {      //perform culling
            System.out.println("Culling the population");
            //sort it first
            Collections.sort(toBeCulled);
            
            //decide how much percentage of population is to be retained
            int cutoff = (int) (CULLING_PERCENTAGE * toBeCulled.size());
            ArrayList<Chromosome> populationAfterCulling = new ArrayList<>();
            for (int i = 0; i <= cutoff; i++) {
                populationAfterCulling.add(toBeCulled.get(i));
            }

            System.out.println("Population after culling | total:" + populationAfterCulling.size());
            System.out.println("Chromosome      Weight      Value       Fitness");
            for (int j = 0; j < populationAfterCulling.size(); j++) {
                for (int z : populationAfterCulling.get(j).getSequence()) {
                    System.out.print(z);
                }
                System.out.println("        " + populationAfterCulling.get(j).getWeight() + "          " + populationAfterCulling.get(j).getValue()
                        + "           " + populationAfterCulling.get(j).getFitness());
            }

            return populationAfterCulling;
        } else {        //dont perform culling
            return toBeCulled;
        }
    }

    private static void breedPopulation() {
        // 2 genes for breeding
        int gene_1;
        int gene_2;
        
        // Increase generation_counter
        generation_counter = generation_counter + 1;

        // If population_size is odd #, add best solution of previous generation
        if (POP_GENERATIONS % 2 == 1) {
            breed_population.add(all_populations.get(generation_counter - 1).getBestSolution());
        }

        // Get positions of pair of genes for breeding
        gene_1 = selectGene();
        gene_2 = selectGene();

        // Crossover or cloning
        crossoverGenes(gene_1, gene_2);
    }

    private static int selectGene() {
        // Generate random number between 0 and total_fitness_of_generation
        double rand = Math.random() * current_population.getTotalFitness();

        // Use random number to select gene based on fitness level
        for (int i = 0; i < CHROMOSOME_POOL.size(); i++) {
            double tempFitness = CHROMOSOME_POOL.get(i).getFitness();
            if (rand <= tempFitness) {
                return i;
            }
            rand = rand - tempFitness;
        }
        //default select 0th chromosome
        return 0;
    }

    private static void crossoverGenes(int gene_1, int gene_2) {
        // Arrays to hold new genes
        ArrayList<Integer> new_gene_1 = new ArrayList<>();
        ArrayList<Integer> new_gene_2 = new ArrayList<>();

        // Decide if crossover is to be used
        double rand_crossover = Math.random();
        if (rand_crossover <= CROSSOVER_PROB) {
            // Perform crossover at random point in chromosome
            Random generator = new Random();
            int cross_point = generator.nextInt(INIT_ITEMS) + 1;

            // Cross genes at random spot in strings
            new_gene_1.addAll(CHROMOSOME_POOL.get(gene_1).getSequence().subList(0, cross_point));
            new_gene_1.addAll(CHROMOSOME_POOL.get(gene_2).getSequence().subList(cross_point, CHROMOSOME_POOL.get(gene_2).getSequence().size()));

            new_gene_2.addAll(CHROMOSOME_POOL.get(gene_2).getSequence().subList(0, cross_point));
            new_gene_2.addAll(CHROMOSOME_POOL.get(gene_1).getSequence().subList(cross_point, CHROMOSOME_POOL.get(gene_2).getSequence().size()));

            // Add new genes to breed_population
            breed_population.add(new Chromosome(new_gene_1));
            breed_population.add(new Chromosome(new_gene_2));
        } else {
            // Otherwise, perform cloning
            breed_population.add(CHROMOSOME_POOL.get(gene_1));
            breed_population.add(CHROMOSOME_POOL.get(gene_2));
        }
    }
    
    private static void mutateGene(){
        double randomMutRate = Math.random();
        if(randomMutRate >= MUTATION_PROB){
            System.out.println("Performing Mutation:");
            int mutPosition = (int)(Math.random() * INIT_ITEMS);        //at what gene position mutation should happen
            int mutChromosome = (int)(Math.random() * breed_population.size()); //in which chromosome in a population mutation should happen
            
            //if gene at that position is 0, flip it to 1 or vice versa
            if(breed_population.get(mutChromosome).getSequence().get(mutPosition) == 1)
                breed_population.get(mutChromosome).getSequence().set(mutPosition, 0);
            else
                breed_population.get(mutChromosome).getSequence().set(mutPosition, 1);
        }
    }

    private static void evalBreedPopulation() {
        for (int i = 0; i < breed_population.size(); i++) {
            breed_population.get(i).calculateFitness();
        }
    }

    public static double getMeanFitnessOfPopulation() {
        double total_fitness = 0;
        double mean_fitness = 0;
        for (int i = 0; i < CHROMOSOME_POOL.size(); i++) {
            total_fitness += CHROMOSOME_POOL.get(i).getFitness();
        }
        mean_fitness = total_fitness / INIT_TOTAL_CHROMOSOMES;
        return mean_fitness;
    }

    public static Chromosome getBestChromosomeOfGeneration() {
        int best_position = 0;
        double this_fitness = 0;
        double best_fitness = 0;
        for (int i = 0; i < CHROMOSOME_POOL.size(); i++) {
            this_fitness = CHROMOSOME_POOL.get(i).getFitness();
            if (this_fitness > best_fitness) {
                best_fitness = this_fitness;
                best_position = i;
            }
        }
        return CHROMOSOME_POOL.get(best_position);
    }

    public static int totalPopulationFitness() {
        int tempTotalFitness = 0;
        for (int i = 0; i < CHROMOSOME_POOL.size(); i++) {
            if (CHROMOSOME_POOL.get(i).getWeight() <= KNAPSACK_CAPACITY) {
                tempTotalFitness += CHROMOSOME_POOL.get(i).getFitness();
            }
        }
        return tempTotalFitness;
    }

    public static void generateItems() {
        //generates sample array of available items with wieghts and values
        Random rand = new Random();
        for (int i = 0; i < INIT_ITEMS; i++) {
            Item item = new Item();
            //weight and value should be within certain limits
            item.setWeight(rand.nextInt(10) + 1);
            item.setValue(rand.nextInt(10) + 1);
            ITEMS_POOL[i] = item;
        }
    }

    public static void generateChromosomes() {
        for (int i = 0; i < INIT_TOTAL_CHROMOSOMES; i++) {
            Chromosome chromosome = new Chromosome();
            chromosome.generateSequence();
            CHROMOSOME_POOL.add(chromosome);
        }
    }

}