/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblemgateam15;

import java.util.ArrayList;
import java.util.Arrays;
import static knapsackproblemgateam15.Main.CHROMOSOME_POOL;
import static knapsackproblemgateam15.Main.INIT_ITEMS;
//import static knapsackproblemgateam15.Main.INIT_TOTAL_CHROMOSOMES;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class MainTest {
    
    public MainTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBestChromosomeOfGeneration method, of class Main.
     */
    @Test
    public void testGetBestChromosomeOfGeneration() {
/* uncomment the below value from here and put comment to INIT_ITEMS in Main file*/        
//Main.INIT_ITEMS = 3;
        Item i1 = new Item(5,5);
        Main.ITEMS_POOL[0] = i1;
        Item i2 = new Item(6,3);
        Main.ITEMS_POOL[1] = i2;
        Item i3 = new Item(4,7);
        Main.ITEMS_POOL[2] = i3;

            Chromosome c = new Chromosome();
            c.getSequence().add(1); c.getSequence().add(0);  c.getSequence().add(1);
            Main.CHROMOSOME_POOL.add(c);            
            Chromosome c2 = new Chromosome();
            c2.getSequence().add(1); c2.getSequence().add(0); c2.getSequence().add(0);
            Main.CHROMOSOME_POOL.add(c2);
            Chromosome c3 = new Chromosome();
            c3.getSequence().add(0); c3.getSequence().add(1); c3.getSequence().add(1);
            Main.CHROMOSOME_POOL.add(c3);

        Chromosome result = Main.getBestChromosomeOfGeneration();
        result.calculateFitness();
        assertEquals(12.0, result.getFitness(),0);
    }    

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Main.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNewGenerations method, of class Main.
     */
    @Test
    public void testCreateNewGenerations() {
        System.out.println("createNewGenerations");
        Main.createNewGenerations();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of culling method, of class Main.
     */
    @Test
    public void testCulling() {
        System.out.println("culling");
        ArrayList<Chromosome> toBeCulled = null;
        ArrayList<Chromosome> expResult = null;
        ArrayList<Chromosome> result = Main.culling(toBeCulled);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of totalPopulationFitness method, of class Main.
     */
    @Test
    public void testTotalPopulationFitness() {
        System.out.println("totalPopulationFitness");
        int expResult = 0;
        int result = Main.totalPopulationFitness();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateItems method, of class Main.
     */
    @Test
    public void testGenerateItems() {
        System.out.println("generateItems");
        Main.generateItems();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
