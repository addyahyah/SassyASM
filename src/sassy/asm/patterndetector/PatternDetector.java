package sassy.asm.patterndetector;

import java.util.ArrayList;

import sassy.asm.api.IModel;

public class PatternDetector{
	private IModel model;
	private ArrayList<IPatternDetector> detectors;
	public PatternDetector(IModel model) {
		this.model = model;
		this.detectors = new ArrayList<>();
		this.initializeDetectors();
	}
	
	private void initializeDetectors(){
//		this.detectors.add(new DecoratorDetector(model));
//		this.detectors.add(new AdapterDetector(model));
//		this.detectors.add(new SingletonDetector(model));
		this.detectors.add(new CompositeDetector(model));

	}

	public void detectPatterns() {	
		for(IPatternDetector d : this.detectors){
			d.detectPattern();
		}
	}
}
