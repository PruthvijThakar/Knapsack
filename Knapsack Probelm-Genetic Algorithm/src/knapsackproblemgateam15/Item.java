/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblemgateam15;


public class Item {
    private int weight;
    private int value;

    
    public Item(){
        
    }
    
    public Item(int weight, int value){
        this.value = value;
        this.weight = weight;
    }
    
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
