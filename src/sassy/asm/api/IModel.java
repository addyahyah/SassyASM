package sassy.asm.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import sassy.asm.pattern.IPattern;
import sassy.asm.pattern.IPatternsFactory;
import sassy.asm.patterndetector.IPatternDetector;
import sassy.asm.visitor.ITraverser;

public interface IModel extends ITraverser{
	public void addClass(IClass c);
	public ArrayList<IClass> getClasses();
	public void addRelation(String name, String s, String string);
	public HashSet<ArrayList<String>> getRelations();
	public HashMap<ArrayList<IClass>, IPatternDetector> getPatterns();
	public void addPattern(ArrayList<IClass> c, IPatternDetector pattern);
	public ArrayList<IPatternsFactory> getPatternDetected();
	public void addPatternDetected(IPatternsFactory p);

}
