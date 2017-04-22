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
    	SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(gui);
    	
    	long start = System.currentTimeMillis();
    	System.out.println(simulatedAnnealing.Simulate());
    	System.out.println((System.currentTimeMillis() - start) / 1000.0);
    }
    
    public void Genetic() {
    	// Algorithm Analysis
    	// Iterations: 87 average, Best: 58
    	// Runtime: 0.25ms +- 0.01
    	Genetic genetic = new Genetic(gui);
    	
       	long start = System.currentTimeMillis();
    	System.out.println(genetic.Simulate());
    	System.out.println((System.currentTimeMillis() - start) / 1000.0);
    }
}
