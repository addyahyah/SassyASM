package sassy.asm.detector;

import sassy.asm.api.IModel;

public class DecoratorDetector implements IDetector{
	private IModel model;
	public DecoratorDetector(IModel model){
		this.model=model;
	}
	@Override
	public void detectPattern() {		
	}
	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return null;
	}


}
