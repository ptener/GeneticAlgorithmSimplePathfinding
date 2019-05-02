//Population Class

//import JFrame and Jpannel utility
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.lang.Math.*;
import java.util.*;


public class Population extends JPanel{

    protected int size;
    protected int generation;
    protected int target[] = new int[2];
    protected double maxFitness;
    protected int playTime;
    protected Player players[];

    public Population(){
        //Start generation at 0
        generation = 0;
        
        //Determine amount of players and generate array acordingly
        size = 25;
        players = new Player[size];
        
        //set target location
        target[0] = 500;
        target[1] = 500;

        playTime = 250;

        //populate players array with player objects
        for(int i = 0; i < size; i++){
            players[i] = new Player(playTime);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        //Set up JFrame
        JFrame frame = new JFrame("Game");
        Population pop = new Population();
        frame.add(pop);
        frame.setSize(750, 750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        //Draw scene
        pop.repaint();

        
        while(pop.generation < 1000){

            //AddForce
            for (int i = 0; i < pop.playTime; i++){
                for (int j = 0; j < pop.size; j++){
                    pop.players[j].addForce(i);
                    Thread.sleep(1);
                    pop.repaint();
                    
                }
            }
            for (int i = 0; i < pop.size; i++){
                pop.calculateFitness(pop.players[i]);
               // System.out.println(pop.players[i].getFitness());
            }
            pop.generateNewPopulation();

        }
    }

    //fitness is inverse distance from target
    public double calculateFitness(Player fitPlayer){    

        double distX = Math.abs(target[0] - fitPlayer.getPositionX());
        double distY = Math.abs(target[1] - fitPlayer.getPositionY());

        double distance = Math.hypot(distX, distY);
        
        fitPlayer.setFitness(1/distance); 
        
        return (1/distance);
    }

    public Player chooseBest(){
        maxFitness = 0;
        double sumFitness = 0;
        for(int i = 0; i < size; i++){

            double potentialMax = players[i].getFitness();
            sumFitness += potentialMax; 

            if (potentialMax > maxFitness){
                potentialMax = maxFitness;
            }
        }
        //System.out.println(maxFitness);
        //System.out.println(sumFitness);
        int i = 0;
        Random rand = new Random();
        double randNum = rand.nextDouble();

        while(randNum > 0){ 
            randNum -= (players[i].getFitness() / sumFitness); 
            i++; 
        }
        i--;
        return players[i];

    }

    public void generateNewPopulation(){
        
        for (int i = 0; i < size; i++ ){
            Player parentA = chooseBest();
            Player parentB = chooseBest();
            Player child = breed(parentA, parentB);
            players[i] = child;
            players[i].resetPlayer();
        }
        generation++;
    }

    public Player breed(Player parentA, Player parentB){
    
        Player child = new Player(playTime);

        Random rand = new Random();
        double randNum = rand.nextDouble();

        for(int i = 0; i < playTime; i++){

            if(randNum > 0.5){
                child.getDNA().setGenes(i,0, parentA.getDNA().getGenes(i,0));
                child.getDNA().setGenes(i,1, parentA.getDNA().getGenes(i,1));
            }
            else{
                child.getDNA().setGenes(i,0, parentB.getDNA().getGenes(i,0));
                child.getDNA().setGenes(i,1, parentB.getDNA().getGenes(i,1));
            }
        }
        child.getDNA().mutate();
        return child;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.fillRect(target[0], target[1], 25, 25);
            
        for (int i = 0; i < size; i++){
            g2d.fillOval((int) players[i].getPositionX(), (int) players[i].getPositionY(), 10, 10);
        }
    }

}
