import edu.kzoo.grid.*;
import java.awt.Color;

public class CellObject extends ColorBlock {

	public CellObject(Color colorValue) {
		super(colorValue);
	}

	public CellObject(Color colorValue, Grid gridValue, Location locationValue) {
		super(colorValue, gridValue, locationValue);
	}
	
	@Override 
	public void act() {

	}
}
