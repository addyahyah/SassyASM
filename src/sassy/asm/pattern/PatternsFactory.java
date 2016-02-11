package sassy.asm.pattern;

import java.util.HashMap;
import java.util.List;

import sassy.asm.api.IClass;

public class PatternsFactory implements IPatternsFactory{
	private HashMap<IClass, IPattern> patternList;
	
	public PatternsFactory(){
		this.patternList = new HashMap<>();
	}
	
	@Override
	public HashMap<IClass, IPattern> getPatternList() {
		return this.patternList;
	}

	@Override
	public void addClass(IClass c, IPattern p) {
		this.patternList.put(c, p);
	}
	
	
}
