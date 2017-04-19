package Blocks;

import java.awt.Color;
import java.util.ArrayList;

import edu.kzoo.grid.*;
import edu.kzoo.grid.display.*;
import edu.kzoo.grid.gui.GridAppFrame;

public class AntibodyBlock extends ColorBlock {
	private GridAppFrame gui;
	public AntibodyBlock(GridAppFrame gui, Location location) {
		super(Color.BLUE, gui.getGrid(), location);
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
	
	public boolean Inspect(ColorBlock colorBlock) {
		if(colorBlock instanceof MutatedCellBlock) {
			((MutatedCellBlock)colorBlock).Die();
			System.out.println("Mutated.");
			return true;
		}
		else if(colorBlock instanceof NormalCellBlock) {
			System.out.println("Normal.");
			return true;
		}
		return false;
	}
}
