package strategy;

public class StrategyManager implements SaveStrategy{
	
	private SaveStrategy saveLoadStrategy;
	
	public StrategyManager (SaveStrategy saveLoadStrategy) {
		this.saveLoadStrategy = saveLoadStrategy;
	}

	@Override
	public void save() {
		saveLoadStrategy.save();
		
	}

	

}
