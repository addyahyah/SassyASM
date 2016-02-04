package sassy.asm.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import problem.car.api.ICarPart;
import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.api.IModelPart;
import sassy.asm.detector.IDetector;
import sassy.asm.pattern.AdapterPattern;
import sassy.asm.pattern.IPattern;
import sassy.asm.pattern.IPatterns;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class Model implements IModel, ITraverser {
	private ArrayList<IClass> classes;

	// private HashMap<ArrayList<String>, String> relations;

	private HashSet<ArrayList<String>> relations;

	private HashMap<ArrayList<IClass>, IDetector> patterns;
	private ArrayList<IPatterns> patternDetected;
	private HashMap<String, ArrayList<IPatterns>> patternList;

	public Model() {
		this.classes = new ArrayList<>();
		this.relations = new HashSet<ArrayList<String>>();
		this.patterns = new HashMap<>();
		this.patternDetected = new ArrayList<IPatterns>();
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

	public ArrayList<IPatterns> getPatternDetected() {
		return this.patternDetected;
	}

	public void addPatternDetected(IPatterns p) {
		this.patternDetected.add(p);
	}

	public HashMap<ArrayList<IClass>, IDetector> getPatterns() {
		return patterns;
	}

	public void addPattern(ArrayList<IClass> c, IDetector pattern) {
		this.patterns.put(c, pattern);
	}

	@Override
	public void addRelation(String owner, String target, String relation) {
		ArrayList<String> triple = new ArrayList<String>();
		if (target.contains("[")) {
			return;
		}
		owner = owner.substring(owner.lastIndexOf("/") + 1);
		target = target.substring(target.lastIndexOf("/") + 1);
		triple.add(owner);
		triple.add(target);
		triple.add(relation);

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
			if(add) { 
				//Add here
				this.relations.add(triple);
			}
		} else if (relation.equals("assoc")) {
			for (ArrayList<String> list : this.relations) {
				if (list.get(0).equals(owner) && list.get(1).equals(target)) {
					if (list.get(2).equals("use")) {
						this.relations.remove(list);
						break;
					} 
				}
			}
			this.relations.add(triple);
		} else {
//			if (!this.relations.contains(triple)) {
				this.relations.add(triple);
//			}
		}
	}

	// public void addAssocHelper(String owner, String target) {
	// if (target.contains("<") || target.contains(">")) {
	// String inside = target.substring(target.lastIndexOf("<") + 1,
	// target.lastIndexOf(">"));
	// for (IClass c : this.getClasses()) {
	// if (c.getName().equals(inside)) {
	// target = inside;
	// } else {
	// if (target.contains("<")) {
	// target = target.substring(0, target.indexOf("<"));
	// } else if (target.contains(">")) {
	// target = target.substring(0, target.indexOf(">") - 1);
	// }
	// break;
	// }
	//
	// }
	// }
	// ArrayList<String> couple = new ArrayList<String>();
	// couple.add(owner);
	// couple.add(target);
	// if (!this.relations.containsKey(couple)) {
	// this.relations.put(couple, "assoc");
	// } else {
	// if (this.relations.get(couple).equals("use")) {
	// this.relations.remove(couple);
	// this.relations.put(couple, "assoc");
	// } else {
	// this.relations.put(couple, "assoc");
	// }
	// }
	// }

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
