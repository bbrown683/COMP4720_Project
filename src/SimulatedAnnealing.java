import edu.kzoo.grid.*;
import edu.kzoo.grid.gui.*;

import java.awt.Color;
import java.math.*;

public class SimulatedAnnealing {
	private GridAppFrame gui;
	
	public SimulatedAnnealing(GridAppFrame gui) {
		this.gui = gui;
	}
	
    // Returns the euclidean distance between the cell and the virus (energy).
    private float e(Location location) {
    	return (float)Math.sqrt((8 * 8 - (location.row() * location.row())) + 
    		(7 * 7 - (location.col() * location.col())));
    }

    // Returns a random neighbor at the specified location.
    private Location neighbor(Grid grid, Location location) {
    	Location neighbor = new Location(0, 0);
    	do {
    		neighbor = grid.getNeighbor(location, Direction.randomDirection());
    	} while(!grid.isValid(neighbor) || !grid.isEmpty(neighbor));
    	return neighbor;
    }
    
    // Returns the acceptance probability for the new location.
    private float p(float old_cost, float new_cost, float temp) {
    	return (float)Math.exp((old_cost - new_cost) / temp);
    }
	
    private float temperature(float k, float k_max) {
    	return k / k_max;
    }
    
	public void Simulate() {
    	Grid grid = gui.getGrid();
    	grid.removeAll();
		Location s = new Location(1, 0);
		gui.showGrid();
		
    	grid.add(new ColorBlock(Color.BLACK), s);    	
    	grid.add(new ColorBlock(Color.RED), new Location(8,7));
    	grid.add(new ColorBlock(Color.RED), new Location(2,4));
    	grid.add(new ColorBlock(Color.RED), new Location(5,2));
    	grid.add(new ColorBlock(Color.YELLOW), new Location(3,6));
    	
    	// cost of current location.
    	float k = 1.0f;
    	float k_max = 100.0f;
    	
    	while (k < k_max) {
    		float t = temperature(k, k_max);
    		Location s_new = neighbor(grid, s);
    		if(p(e(s), e(s_new), t) > Math.random()) {
    			// remove antibody at old location.
    			grid.remove(s);
    			
    			// add it to the new location.
    			s = s_new;
    			grid.add(new ColorBlock(Color.BLACK), s);
    			gui.showGrid();
    		}
    		k++;
    	}
	}
}
