package Blocks;

import java.awt.Color;
import java.util.ArrayList;

import edu.kzoo.grid.*;
import edu.kzoo.grid.display.*;
import edu.kzoo.grid.gui.GridAppFrame;

public class MutatedCellBlock extends ColorBlock {
	private GridAppFrame gui;
	public MutatedCellBlock(GridAppFrame gui, Location location) {
		super(Color.GREEN, gui.getGrid(), location);
		this.gui = gui;
	}
		
	public void Move(Location location) {
		if(gui.getGrid().isValid(location) && gui.getGrid().isEmpty(location)) {
			super.changeLocation(location);
			gui.showGrid();
		}
	}
	
	public void Infect(ColorBlock colorBlock) {
		if(colorBlock instanceof NormalCellBlock) {
		
		}
	}
	
	public ArrayList<Location> GetNeighbors() {
		return super.grid().neighborsOf(super.location());
	}
	
	public void Die() {
		super.removeFromGrid();
	}
}
