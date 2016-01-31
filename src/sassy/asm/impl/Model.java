package sassy.asm.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import problem.car.api.ICarPart;
import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.api.IModelPart;
import sassy.asm.detector.IDetector;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class Model implements IModel, ITraverser {
	private ArrayList<IClass> classes;

	private HashMap<ArrayList<String>, String> relations;
	
	private HashMap<ArrayList<IClass>, IDetector> patterns;

	public Model() {
		this.classes = new ArrayList<>();
		this.relations = new HashMap<ArrayList<String>, String>();
		this.patterns = new HashMap<>();
	}

	public void addClass(IClass c) {
		this.classes.add(c);
	}

	public ArrayList<IClass> getClasses() {
		return this.classes;
	}
	

	public HashMap<ArrayList<IClass>, IDetector> getPatterns() {
		return patterns;
	}

	public void addPattern(ArrayList<IClass> c, IDetector pattern) {
		this.patterns.put(c,  pattern);
	}

	@Override
	public void addRelation(String name, String s, String relation) {
		ArrayList<String> couple = new ArrayList<String>();
//		System.out.println("Name: " + name + " : s : " + s + " : relation : "+relation);
		if (relation.equals("use") && !s.contains("<init>")) {
			if (!s.contains("java") && s.length() != 0) {
				// System.out.println("NAme is " + name + " target is " +s);

				name = name.substring(name.lastIndexOf("/") + 1);
				s = s.substring(s.lastIndexOf("/") + 1);

				couple.add(name);
				couple.add(s);
				if (!this.relations.containsKey(couple)) {
					// && !use.equals(this.getName()) && s.length() != 0
					this.relations.put(couple, relation);
				} else {

					if (!this.relations.get(couple).equals("assoc")) {
						this.relations.put(couple, relation);
						return;
					}

				}
			}
		} else if (relation.equals("superClass")) {
			s = s.substring(s.lastIndexOf("/") + 1);
			couple.add(name);
			couple.add(s);
			this.relations.put(couple, relation);
		} else if (relation.equals("interface")) {
			s = s.substring(s.lastIndexOf("/") + 1);
			couple.add(name);
			couple.add(s);
			this.relations.put(couple, relation);
		} else if (relation.equals("assoc")) {
			if (s.contains(";")) {
				String[] map = s.split(";");
				for (String st : map) {
					addAssocHelper(name, st);
				}
			} else {
				addAssocHelper(name, s);
			}
		}
	}

	public void addAssocHelper(String name, String s) {
		if (!s.contains("java") && !s.contains(">")) {
			s = s.substring(s.lastIndexOf("/") + 1);
			
			ArrayList<String> a = new ArrayList<String>();
			a.add(name);
			a.add(s);
//			System.out.println("NAME : " + name + " S: "+ s);
			if (!this.relations.containsKey(a) && s.length() != 0) {

				// && !use.equals(this.getName())
				
				this.relations.put(a, "assoc");

			} else {
				System.out.println(Arrays.toString(a.toArray()));
				System.out.println(this.relations.get(a));
				if (this.relations.get(a).equals("use")) {
					this.relations.remove(a);
					this.relations.put(a, "assoc");
				}else{
					this.relations.put(a, "assoc");
					
				}
			}
		}
	}

	@Override
	public HashMap<ArrayList<String>, String> getRelations() {
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
