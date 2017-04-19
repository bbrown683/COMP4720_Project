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
	private HashMap<Location, ColorBlock> cells2;
	
	public SimulatedAnnealing(GridAppFrame gui) {
		this.gui = gui;
		random = new Random(2);
		cells = new Stack<ColorBlock>();
		cells2 = new HashMap<Location, ColorBlock>();
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
    
    // Returns the euclidean distance between the cell and the virus (energy).
    private float cost(Location antibody, Location virus) {
    	return (float)Math.sqrt(Math.abs(Math.abs(virus.row() - antibody.row()) * Math.abs(virus.row()) - antibody.row())) + 
    		(Math.abs(virus.col() - antibody.col()) * Math.abs(virus.col() - antibody.col()));
    }
        
    // Returns a random neighbor at the specified location.
    private Location neighbor(Grid grid, Location location) {
    	Location neighbor = new Location(0, 0);
    	do {
    		neighbor = grid.getNeighbor(location, Direction.randomDirection());
    	} while(!grid.isValid(neighbor) && !grid.isEmpty(neighbor));
    	return neighbor;
    }
    
    // Returns the acceptance probability for the new location.
    private float probability(float current_cost, float next_cost, float temp) {
    	return (float)Math.exp((current_cost - next_cost) / temp);
    }
	
    private float temperature(float k, float k_max) {
    	if(cells.isEmpty())
    		return 0.0f;
    	else
    		return k / k_max;
    }
    
	public boolean Simulate() {
    	Grid grid = gui.getGrid();
    	grid.removeAll();
    	
    	antibody = new AntibodyBlock(gui, GenerateLocation(grid));
    	Location mutatedLocation = GenerateLocation(grid);
    	cells2.put(mutatedLocation, new MutatedCellBlock(gui, mutatedLocation));
    	cells.push(new MutatedCellBlock(gui, GenerateLocation(grid))); // push a mutated cell.
    	for(int i = 0; i < 5; i++) {
    		Location normalLocation = GenerateLocation(grid);
    		cells2.put(normalLocation, new NormalCellBlock(gui, normalLocation));
    	}
    	cells.push(new NormalCellBlock(gui, GenerateLocation(grid)));
    	cells.push(new NormalCellBlock(gui, GenerateLocation(grid)));
    	cells.push(new NormalCellBlock(gui, GenerateLocation(grid)));
    	cells.push(new NormalCellBlock(gui, GenerateLocation(grid)));
    	gui.showGrid();
		
    	float k = 1.0f;
    	float k_max = Short.MAX_VALUE;
    	
    	while (k < k_max) {
    		float t = temperature(k, k_max);
    		System.out.println(t);
    		if(t == 0.0f)
    			return true;
    		
    		Location next = neighbor(grid, antibody.location());
    		float current_cost = cost(antibody.location(), cells.peek().location());
    		float next_cost = cost(next, cells.peek().location());
    		
    		if(probability(current_cost, next_cost, t) > Math.random()) {
    			for(Location location : antibody.GetNeighbors()) {
    				if(antibody.Inspect((ColorBlock)grid.objectAt(location))) {
    					cells.pop();
    				}
    			}
    			antibody.Move(next);
    		}
    		k++;
    	}
    	return false;
	}
}
