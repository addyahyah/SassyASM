package sassy.asm.detector;

import java.util.ArrayList;

import sassy.asm.api.IModel;

public class PatternDetector{
	private IModel model;
	private ArrayList<IDetector> detectors;
	public PatternDetector(IModel model) {
		this.model = model;
		this.detectors = new ArrayList<>();
		this.initializeDetectors();
	}
	
	private void initializeDetectors(){
		this.detectors.add(new AdapterDetector(model));
		this.detectors.add(new SingletonDetector(model));
		this.detectors.add(new DecoratorDetector(model));

	}

	public void detectPatterns() {	
		for(IDetector d : this.detectors){
			d.detectPattern();
		}
	}
}
