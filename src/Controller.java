import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;

import java.awt.Color;

import edu.kzoo.grid.ColorBlock;
import edu.kzoo.grid.Direction;
import edu.kzoo.grid.gui.GridAppFrame;

public class Controller {
	// Instance variable(s) for each Controller instance.
    private GridAppFrame gui;
    /** Constructs a new Controller object. **/
    public Controller(GridAppFrame gui) {
        this.gui = gui;
    }
       
    public void ExecuteLogic() {
    	SimulatedAnnealing sa = new SimulatedAnnealing(gui);
    	sa.Simulate();
    }
}
