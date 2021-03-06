package Simulator;

import Algorithms.*;
import Blocks.*;

import edu.kzoo.grid.*;
import edu.kzoo.grid.gui.*;

import java.awt.*;
import java.util.*;

public class Controller {
    private GridAppFrame gui;
    
    public Controller(GridAppFrame gui) {
        this.gui = gui;
    }
        
    public void SimulatedAnnealing() {
    	// Algorithm Analysis
    	// Iterations: 87 average, Best: 66
    	// Runtime: 13.134 +- 1ms
    	int success = 0;
    	for(int i = 0; i < 10; i++) {
    		SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(gui);
    		if(simulatedAnnealing.Simulate())
    			success++;
    	}
    	/*
    	// Time calculations.
    	long start = System.currentTimeMillis();
    	System.out.println(simulatedAnnealing.Simulate());
    	System.out.println((System.currentTimeMillis() - start) / 1000.0); 
    	*/
    	
    	//System.out.println("Success %:" + (success / 1000.0f) * 100);
    }
    
    public void Genetic() {
    	// Algorithm Analysis
    	// Iterations: 87 average, Best: 58
    	// Runtime: 0.25ms +- 0.01
    	int success = 0;
    	for(int i = 0; i < 100; i++) {
    		Genetic genetic = new Genetic(gui);
    		try {
    		if(genetic.Simulate())
    			success++;
    		}
    		catch(Exception e) {
    			
    		}
    	}
    	/*
    	// Time calculations.
       	long start = System.currentTimeMillis();
    	System.out.println(genetic.Simulate());
    	System.out.println((System.currentTimeMillis() - start) / 1000.0);
    	*/
        //System.out.println("Success %:" + (success / 10.0f) * 100);
    }
}
