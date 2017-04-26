package Algorithms;

import Blocks.*;

import edu.kzoo.grid.*;
import edu.kzoo.grid.gui.*;

import java.util.*;

public class Genetic {
    private GridAppFrame gui;
    private Random random;
    
    private AntibodyBlock antibody;
    private Stack<ColorBlock> cells;
    private ArrayList<String> population;
    
    public Genetic(GridAppFrame gui) {
        this.gui = gui;
        random = new Random();
        cells = new Stack<ColorBlock>();
        population = new ArrayList<String>();
    }
    
    private boolean IsValidAndEmpty(Location location) {
    	if(gui.getGrid().isValid(location) && gui.getGrid().isEmpty(location))
    		return true;
    	return false;
    }
    
    private String Crossover(String x, String y) {
    	int bitstring_size = x.length();
    	int crossover_point = random.nextInt(bitstring_size);
    	return x.substring(0, crossover_point) + y.substring(crossover_point);
    }
    
    // Returns the euclidean distance between the cell and the target.
    private float Cost(Location current, Location target) {
    	return (float)Math.sqrt(
    		Math.abs((target.row() - current.row()) * (target.row() - current.row())) + 
    		Math.abs((target.col() - current.col()) * (target.col() - current.col())));
    }
    
    private float MaximumCost(Location current, Location target) {
    	return Cost(current, target) + (0.35f * Cost(current, target));
    }
    
    private Location SingleMoveLocation(String move, Location new_location) {
    	Location location = new_location;
    	switch (move) {
        case "00":
           	if(IsValidAndEmpty(new Location(location.row() - 1, location.col())))
            	location = new Location(location.row() - 1, location.col());
            break;
        case "01":
            if(IsValidAndEmpty(new Location(location.row() + 1, location.col())))
            	location = new Location(location.row() + 1, location.col());
            break;
        case "10":
        	if(IsValidAndEmpty(new Location(location.row(), location.col() - 1)))
           		location = new Location(location.row(), location.col() - 1);
            break;
        case "11":
            if(IsValidAndEmpty(new Location(location.row(), location.col() + 1)))
                location = new Location(location.row(), location.col() + 1);
            break;
    	}
    	
    	return location;
    }
    
    private Location TotalMoveLocation(String chromosome, boolean moveAntibody) {
    	Location initial = antibody.location();
    	String current = "";
    	String overall = chromosome;
    	for(int i = 0; i < Math.floor(MaximumCost(antibody.location(), cells.peek().location())); i++) {
    		current = overall.substring(0, 2);
    		overall = overall.substring(2);
    		initial = SingleMoveLocation(current, initial);
    		if(moveAntibody)
    			antibody.Move(initial);
    	}
    	return initial;
    }

    private float Fitness(String chromosome) { 
    	return MaximumCost(antibody.location(), cells.peek().location())
    			- Cost(TotalMoveLocation(chromosome, false), cells.peek().location());
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
    
    private String Mutate(String chromosome) {
    	int mutate_pos = random.nextInt(chromosome.length());
    	int mutate_val = random.nextInt(2);
    	return chromosome.substring(0, mutate_pos) + mutate_val + chromosome.substring(mutate_pos + 1);
    }
    
    // Performs random selection.
    private String Selection(ArrayList<String> population) {
    	return population.get(random.nextInt(population.size()));
    }
    
    private float SumPopulation(ArrayList<String> population) {
    	float sum = 0;
    	for(int i = 0; i < population.size(); i++) {
    		sum += Fitness(population.get(i));
    	}
    	return sum / population.size();
    }
    
    private void Populate(Location initial, Location target) {
    	population.clear();
    	    	
    	// up down left right in bitstring notation.
    	String[] moves = {"00", "01", "10", "11"};
    	
    	for(int i = 0; i < Byte.MAX_VALUE; i++) {
    		String chromosome = "";
	    	for(int j = 0; j < Math.floor(MaximumCost(initial, target)); j++)
	    		chromosome += moves[random.nextInt(3)];
	    	population.add(chromosome);	    	
    	}
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
    	int k_max = Byte.MAX_VALUE;
    	while(k < k_max) {
    		if(cells.isEmpty())
    			break;
    		
    		// Initialize our population.
	    	Populate(antibody.location(), cells.peek().location());
	    	for(int i = 0; i < Byte.MAX_VALUE; i++) {
				ArrayList<String> new_population = new ArrayList<String>();
				for(int j = 0; j < population.size(); j++) {	
					String x = Selection(population);
					String y = Selection(population);
					String child = Crossover(x, y);
					if(random.nextFloat() < 0.1f)
						child = Mutate(child);
					new_population.add(child);
				}
				population = new_population;
				System.out.println(i + "\t" + SumPopulation(population));
	    	}
	    	
	    	// Find fittest of the population.
	    	String fittest = "";
	    	for(int i = 0; i < population.size(); i++) {
	    		if(i == 0) 
	    			fittest = population.get(0);
	    		else if(Fitness(population.get(i)) > Fitness(fittest))
	    			fittest = population.get(i);
	    	}
	    	
	    	TotalMoveLocation(fittest, true);
			for(Location location : antibody.GetNeighbors()) {
				// Check to see if stack is empty due to empty stack exceptions.
				if(!cells.isEmpty()) {
					if(cells.peek().location().equals(location) && 
							antibody.Inspect((ColorBlock)grid.objectAt(location)))
						cells.pop();	
				}
			}
			k++;
    	}
    	
    	if(cells.isEmpty())
    		return true;
    	else
    		return false;
	}
}
