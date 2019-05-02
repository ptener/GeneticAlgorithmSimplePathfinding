//DNA Class

import java.lang.Math;
import java.util.*;

public class DNA {
    
    //The var that stores all of the "force" vectors
    protected double genes[][];

    //How many force vectors will be stored
    protected int lifeSpan;

    public DNA(int givenLife){

        lifeSpan = givenLife;
        
        //Setting up array dimentions
        genes = new double[lifeSpan][2];

        Random randNum = new Random();

        for(int i = 0; i < lifeSpan; i++){
            
            //setting x and y forces to random numbers
            genes[i][0] = randNum.nextDouble();
            genes[i][1] = randNum.nextDouble();

        }

    }
        
    public void mutate(){

    Random randNum = new Random();

    for(int i = 0; i < lifeSpan; i++){
        if(randNum.nextDouble() < 0.01){
            genes[i][0] = randNum.nextDouble();
            genes[i][1] = randNum.nextDouble();
        }
        randNum.nextDouble();
    }

    }
    
    public double getGenes(int orderNum, int coordinate){
        return genes[orderNum][coordinate];
    }

    public void setGenes(int orderNum, int coordinates, double value){
        genes[orderNum][coordinates] = value;
    }
}
