package command;



import java.io.Serializable;


import adapter.HexagonAdapter;



public class UpdateHexagonCmd implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HexagonAdapter original;
	private HexagonAdapter oldHexagon;
	private HexagonAdapter newHexagon; 
	
	
	
	
	public UpdateHexagonCmd(HexagonAdapter oldHexagon, HexagonAdapter newHexagon) {
		this.oldHexagon = oldHexagon;
		this.newHexagon = newHexagon;
		
		
	}

	@Override
	public void execute() {

		original = oldHexagon.clone();
		
		oldHexagon.setX(newHexagon.getX());
		oldHexagon.setY(newHexagon.getY());
		oldHexagon.setEdgeColor(newHexagon.getEdgeColor());
		oldHexagon.setInnerColor(newHexagon.getInnerColor());
		try {
			oldHexagon.setRadius(newHexagon.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unexecute() {
		
		oldHexagon.setX(original.getX());
		oldHexagon.setY(original.getY());
		oldHexagon.setEdgeColor(original.getEdgeColor());
		oldHexagon.setInnerColor(original.getInnerColor());
		try {
			oldHexagon.setRadius(original.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//oldHexagon.setHexagon(original.getHexagon());
		
		
	}

	

	@Override
	public String toString() {
		if(original == null) {
			// U slucaju Load loga
			return "ModifyHexagon; " + oldHexagon.toString()+ "; TO ;" + newHexagon.toString();
		} else 
			return "ModifyHexagon; " + original.toString()+ "; TO ;" + newHexagon.toString();
	}

	

	
	

	
	

}
