package command;

import java.io.Serializable;



import shapes.Line;




public class UpdateLineCmd implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Line oldLine;
	private Line newLine;
	private Line original;
	
	

	public UpdateLineCmd(Line oldLine, Line newLine) {
		this.oldLine = oldLine;
		this.newLine = newLine;
		
	}
	
	
	@Override
	public void execute() {
		
		original = oldLine.clone();

		oldLine.getStartPoint().setX(newLine.getStartPoint().getX());
		oldLine.getStartPoint().setY(newLine.getStartPoint().getY());
		oldLine.getEndPoint().setX(newLine.getEndPoint().getX());
		oldLine.getEndPoint().setY(newLine.getEndPoint().getY());
		oldLine.setEdgeColor(newLine.getEdgeColor());


	}

	@Override
	public void unexecute() {
		
		oldLine.getStartPoint().setX(original.getStartPoint().getX());
		oldLine.getStartPoint().setY(original.getStartPoint().getY());
		oldLine.getEndPoint().setX(original.getEndPoint().getX());
		oldLine.getEndPoint().setY(original.getEndPoint().getY());
		oldLine.setEdgeColor(original.getEdgeColor());

	}





	@Override
	public String toString() {
		if(original == null) {
			return "ModifyLine; " + oldLine.toString() + "; TO ;"+ newLine.toString();
		}else {
			return "ModifyLine; " + original.toString() + "; TO ;"+ newLine.toString();
		}
		
	}






	

	
	
	

}
