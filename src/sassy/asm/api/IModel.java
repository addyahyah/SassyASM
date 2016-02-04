package sassy.asm.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import sassy.asm.detector.IDetector;
import sassy.asm.pattern.IPattern;
import sassy.asm.pattern.IPatterns;
import sassy.asm.visitor.ITraverser;

public interface IModel extends ITraverser{
	public void addClass(IClass c);
	public ArrayList<IClass> getClasses();
	public void addRelation(String name, String s, String string);
	public HashSet<ArrayList<String>> getRelations();
	public HashMap<ArrayList<IClass>, IDetector> getPatterns();
	public void addPattern(ArrayList<IClass> c, IDetector pattern);
	public ArrayList<IPatterns> getPatternDetected();
	public void addPatternDetected(IPatterns p);
}
