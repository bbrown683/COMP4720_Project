package Algorithms;

import Blocks.*;

import edu.kzoo.grid.*;
import edu.kzoo.grid.gui.*;

import java.awt.*;
import java.math.*;
import java.util.*;

public class SimulatedAnnealing {
	private GridAppFrame gui;
	private Random random;
	
	private AntibodyBlock antibody;
	private Stack<ColorBlock> cells;

	public SimulatedAnnealing(GridAppFrame gui) {
		this.gui = gui;
		random = new Random(2);
		cells = new Stack<ColorBlock>();
	}
	
    private Location GenerateLocation(Grid grid) {
    	Location location = new Location(0, 0);
    	do {
    		int row = random.nextInt(grid.numRows() + 1);
    		int col = random.nextInt(grid.numCols() + 1);
    		location = new Location(row, col);
    	} while(!grid.isValid(location) || !grid.isEmpty(location)); 
    	return location;
    }
    
    // Returns the euclidean distance between the cell and the target.
    private float Cost(Location current, Location target) {
    	return (float)Math.sqrt(
    		Math.abs((target.row() - current.row()) * (target.row() - current.row())) + 
    		Math.abs((target.col() - current.col()) * (target.col() - current.col())));
    }
        
    // Returns a random neighbor at the specified location.
    private Location Neighbor(Grid grid, Location location) {
    	Location neighbor = new Location(0, 0);
    	do {
    		neighbor = grid.getNeighbor(location, Direction.randomDirection());
    	} while(!grid.isValid(neighbor) && !grid.isEmpty(neighbor));
    	return neighbor;
    }
    
    // Returns the acceptance probability for the new location.
    private double Probability(float current_cost, float next_cost, float temp) {
    	return Math.exp((current_cost - next_cost) / temp);
    }
	
    private float Temperature(int k, int k_max) {
    	if(cells.isEmpty())
    		return 0.0f;
    	else
    		return (float)k / k_max;
    }
    
	public boolean Simulate() {
		Grid grid = gui.getGrid();
    	grid.removeAll();
    	
    	antibody = new AntibodyBlock(gui, GenerateLocation(grid));
    	cells.push(new MutatedCellBlock(gui, GenerateLocation(grid))); // push a mutated cell.
    	cells.push(new NormalCellBlock(gui, GenerateLocation(grid)));
    	cells.push(new NormalCellBlock(gui, GenerateLocation(grid)));
    	cells.push(new NormalCellBlock(gui, GenerateLocation(grid)));
    	cells.push(new NormalCellBlock(gui, GenerateLocation(grid)));
    	gui.showGrid();
		
    	int k = 1;
    	int k_max = Short.MAX_VALUE;
    	
    	while (k < k_max) {
    		// Getting temperature value and ensuring t != 0 
    		// as it would suggest the solution has been found already.
    		float t = Temperature(k, k_max);
    		if(t == 0.0f)
    			return true;
    		
    		// Get the random neighbor to the location.
    		Location next = Neighbor(grid, antibody.location());
    		
    		// Calculate the current cost and the suggested random locations cost.
    		float current_cost = Cost(antibody.location(), cells.peek().location());
    		float next_cost = Cost(next, cells.peek().location());    		
    		
    		// Pass cost into accepted probability function and compare against a random number.
    		if(Probability(current_cost, next_cost, t) > Math.random()) {
    			// Do not consider move if we are next to a neighboring cell.
    			boolean consider = true;
    			for(Location location : antibody.GetNeighbors()) {
    				// Check to see if stack is empty due to empty stack exceptions.
    				if(!cells.isEmpty()) {
    					if(cells.peek().location().equals(location) && 
    							antibody.Inspect((ColorBlock)grid.objectAt(location))) {
    						cells.pop();
    						consider = false;
    					}
    				}
    			}
    			
    			// If move is being considered move to the next location.
    			if(consider)
    				antibody.Move(next);
    		}
    		k++;
    	}
    	
    	return false;
	}
}
