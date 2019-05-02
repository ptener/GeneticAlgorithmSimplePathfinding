//Player class

import java.lang.Math;
import java.util.*;

public class Player{

    protected double position[] = new double[2];
    protected double velocity[] = new double[2];
    protected double acceleration[] = new double[2];
    protected DNA playerDNA;
    protected int lifeSpan;
    protected boolean crashed;
    protected double fitness;
    public Player(int givenLife){
        
        Random randNum = new Random();

        lifeSpan = givenLife;

        crashed = false;

        //Setting position
        position[0] = 10;
        position[1] = 10;

        //Setting velocity
        velocity[0] = 0;
        velocity[1] =  0;

        //Setting acceleration
        acceleration[0] = 0;
        acceleration[1] = 0;

        fitness = 0;

        playerDNA = new DNA(lifeSpan);
    }


    public void addForce(int i){
            
            //Used to make force values between -.5 and .5 instead of 0 and 1
            double forceEqualizer = 0.5;
        
            //Velocity limiter
            double velocityLimit = 4;
   

            //Stop Players from going out of bounds
            if(position[0]<0 || position[0]>500){
                crashed = true;
            }
            else if (position[1]<0 || position[1]>500){
                crashed = true;
            }

            if(!crashed)
                //Add force to acceleration
                acceleration[0] += forceEqualizer - playerDNA.getGenes(i,0);
                acceleration[1] += forceEqualizer - playerDNA.getGenes(i,1);
                //Add acceleration to velocity
                velocity[0] += acceleration[0];
                velocity[1] += acceleration[1];
    
                if(velocity[0] > velocityLimit){
                    velocity[0] = velocityLimit;
                }
                if(velocity[0] < (0 - velocityLimit)){
                    velocity[0] = (0 - velocityLimit);
                }
                if(velocity[1] > velocityLimit){
                    velocity[1] = velocityLimit;
                }
                if(velocity[1] < (0 - velocityLimit)){
                    velocity[1] = (0 -  velocityLimit);
                }

                //Add velocity to position
                position[0] += velocity[0];
                position[1] += velocity[1];
            
            //Reset acceleration so it doesn't get out of hand
            acceleration[0] = 0;
            acceleration[1] = 0;
    }

    public void resetPlayer(){

        //Set all movement values to zero
        position[0] = 0;
        position[1] = 0;
        velocity[0] = 0;
        velocity[1] = 0;
        acceleration[0] = 0;
        acceleration[1] = 0;

    }

    public double getPositionX(){
        return position[0];
    }
    public double getPositionY(){
        return position[1];
    }

    public double getFitness(){
        return fitness;
    }
    public void setFitness(double newFitness){
        fitness = newFitness;
    }

    public DNA getDNA(){
        return playerDNA;
    }
}

