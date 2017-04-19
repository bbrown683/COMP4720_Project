package Simulator;

import Algorithms.*;

import edu.kzoo.grid.gui.EnabledDisabledStates;
import edu.kzoo.grid.gui.GridAppFrame;
import edu.kzoo.grid.gui.ThreadedControlButton;
import edu.kzoo.grid.gui.nuggets.NewBoundedGridButton;

import java.awt.Color;

import java.awt.Color;



public class Controller {
	// Instance variable(s) for each Controller instance.
    private GridAppFrame gui;
    /** Constructs a new Controller object. **/
    public Controller(GridAppFrame gui) {
        this.gui = gui;
    }
       
    public void SimulatedAnnealing() {
    	SimulatedAnnealing sa = new SimulatedAnnealing(gui);
    	System.out.println(sa.Simulate());
    }
    
    public void Genetic() {
    	
    }
}
