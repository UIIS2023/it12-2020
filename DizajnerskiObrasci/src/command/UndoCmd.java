package command;

import java.io.Serializable;





public class UndoCmd implements Command,Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Command cmd;


	public UndoCmd(Command cmd) {
		this.cmd = cmd;
	}

	@Override
	public void execute() {
		cmd.unexecute();
		
	}

	@Override
	public void unexecute() {
		cmd.execute();
		
	}

	

	/**
	 * @return the cmd
	 */
	public Command getCmd() {
		return cmd;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Undo; " + cmd.toString();
	}

}
