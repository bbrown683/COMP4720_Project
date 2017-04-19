package Blocks;

import java.awt.Color;
import java.util.*;
import edu.kzoo.grid.*;
import edu.kzoo.grid.display.*;
import edu.kzoo.grid.gui.GridAppFrame;

public class NormalCellBlock extends ColorBlock {
	private GridAppFrame gui;
	public NormalCellBlock(GridAppFrame gui, Location location) {
		super(Color.RED, gui.getGrid(), location);
		this.gui = gui;
	}
	
	public void Move(Location location) {
		if(gui.getGrid().isValid(location) && gui.getGrid().isEmpty(location)) {
			super.changeLocation(location);
			gui.showGrid();
		}
	}
	
	public ArrayList<Location> GetNeighbors() {
		return super.grid().neighborsOf(super.location());
	}
}