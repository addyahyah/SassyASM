package sassy.asm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.pattern.IPatternsFactory;
import sassy.asm.patterndetector.IPatternDetector;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class Model implements IModel, ITraverser {
	private ArrayList<IClass> classes;

	private HashSet<ArrayList<String>> relations;

	private HashMap<ArrayList<IClass>, IPatternDetector> patterns;
	private ArrayList<IPatternsFactory> patternDetected;
	private HashMap<String, ArrayList<IPatternsFactory>> patternList;

	public Model() {
		this.classes = new ArrayList<>();
		this.relations = new HashSet<ArrayList<String>>();
		this.patterns = new HashMap<>();
		this.patternDetected = new ArrayList<IPatternsFactory>();
		this.initializePatternList();
	}

	private void initializePatternList() {
		this.patternList = new HashMap<>();
		this.patternList.put("Singleton", new ArrayList<>());
		this.patternList.put("Adapter", new ArrayList<>());
	}

	public void addClass(IClass c) {
		this.classes.add(c);
	}

	public ArrayList<IClass> getClasses() {
		return this.classes;
	}

	public ArrayList<IPatternsFactory> getPatternDetected() {
		return this.patternDetected;
	}

	public void addPatternDetected(IPatternsFactory p) {
		this.patternDetected.add(p);
	}

	public HashMap<ArrayList<IClass>, IPatternDetector> getPatterns() {
		return patterns;
	}

	public void addPattern(ArrayList<IClass> c, IPatternDetector pattern) {
		this.patterns.put(c, pattern);
	}

	@Override
	public void addRelation(String owner, String target, String relation) {
		ArrayList<String> triple = new ArrayList<String>();
		if (target.contains("[")) {
			return;
		}
		if(owner.contains("Object") || target.contains("Object")){
			return;
		}
		owner = owner.substring(owner.lastIndexOf("/") + 1);
		target = target.substring(target.lastIndexOf("/") + 1);

		boolean add = true;
		if (relation.equals("use") && !target.contains("<init>")) {

			for (ArrayList<String> list : this.relations) {
				if (list.get(0).equals(owner) && list.get(1).equals(target)) {
					if (list.get(2).equals("assoc")) {
						add = false;
						break;
					}
				}
			}
			if (add) {
				// Add here
				triple.add(owner);
				triple.add(target);
				triple.add(relation);
				this.relations.add(triple);
			}
		} else if (relation.equals("assoc")) {

//			if(target.contains(">")){
//				target = target.substring(0,target.indexOf(">"));				
//			}
//			System.out.println(owner + " @@ "  +target +  " @@ " + relation);
			
			for (ArrayList<String> list : this.relations) {
				if (list.get(0).equals(owner) && list.get(1).equals(target)) {
					if (list.get(2).equals("use")) {
						this.relations.remove(list);
						break;
					}
				}
			}
			triple.add(owner);
			triple.add(target);
			triple.add(relation);
//			System.out.println(owner + " ## "  +target +  " ## " + relation);
			this.relations.add(triple);
		} else {
//			System.out.println(owner + " && "  +target +  " && " + relation);

			triple.add(owner);
			triple.add(target);
			triple.add(relation);

			this.relations.add(triple);
		}
	}

	@Override
	public HashSet<ArrayList<String>> getRelations() {
		return this.relations;
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		for (IClass c : this.getClasses()) {
			ITraverser t = (ITraverser) c;
			t.accept(v);
		}
		v.postVisit(this);
	}

}
