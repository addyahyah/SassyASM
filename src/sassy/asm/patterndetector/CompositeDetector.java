package sassy.asm.patterndetector;

import java.util.ArrayList;
import java.util.HashSet;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.pattern.CompositeComponentPattern;
import sassy.asm.pattern.CompositeCompositePattern;
import sassy.asm.pattern.CompositeLeafPattern;
import sassy.asm.pattern.PatternsFactory;

public class CompositeDetector implements IPatternDetector {
	private IModel model;
	private PatternsFactory factory;

	public CompositeDetector(IModel model) {
		this.model = model;

	}

	@Override
	public void detectPattern() {
		HashSet<ArrayList<String>> relations = model.getRelations();
		ArrayList<ArrayList<String>> superClassCandidates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> interfaceCandidates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> assocCandidates = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> key : relations) {
			if (key.get(2).equals("superClass")) {
				if (!superClassCandidates.contains(key)) {
					superClassCandidates.add(key);
				}
			} else if (key.get(2).equals("interface")) {
				if (!interfaceCandidates.contains(key)) {
					interfaceCandidates.add(key);
				}
			} else if (key.get(2).equals("assoc")) {
				if (!assocCandidates.contains(key)) {
					assocCandidates.add(key);
				}
			}
		}

		ArrayList<String> leafsAndComposites = new ArrayList<String>();
		ArrayList<String> components = new ArrayList<String>();
		ArrayList<String> leafs = new ArrayList<String>();
		ArrayList<String> composites = new ArrayList<String>();
		String component = "";
		String componentInterface = "";
		for (ArrayList<String> key1 : superClassCandidates) {
			for (ArrayList<String> key2 : superClassCandidates) {
				if (key1.get(1).equals(key2.get(1))) {
					if (!key1.get(0).equals(key2.get(0))) {
						// both leafs and composites inherit from the abstract
						// class
						component = key1.get(1);
						if (!components.contains(component)) {
							components.add(component);
						}
						// key1.get(0) and key2.get(0) are potential leafs and
						// composites
						if (!leafsAndComposites.contains(key1.get(0))) {
							leafsAndComposites.add(key1.get(0));
						}
						if (!leafsAndComposites.contains(key2.get(0))) {
							leafsAndComposites.add(key2.get(0));
						}

					}
				}
			}
		}

		// the component class might implement an interface
		for (ArrayList<String> key3 : interfaceCandidates) {
			if (key3.get(0).equals(component)) {
				componentInterface = key3.get(1);
				if (!components.contains(componentInterface)) {
					components.add(componentInterface);
				}

			}
		}

		for (ArrayList<String> key : assocCandidates) {
			for (String s : leafsAndComposites) {
				// && components.contains(key.get(1))
				if (s.equals(key.get(0))) {
					if (!composites.contains(s)) {
						composites.add(s);
					}
				} else {
					if (!leafs.contains(s)) {
						leafs.add(s);
					}
				}
			}
		}
		addToCandidate(components, leafs, composites);
	}

	private void addToCandidate(ArrayList<String> components,
			ArrayList<String> leafs, ArrayList<String> composites) {
		
		if (components.size() > 0 && leafs.size() > 0 && composites.size() > 0) {
			factory = new PatternsFactory();
			for (IClass c : this.model.getClasses()) {
				for (String comp : components) {
					if (comp.equals(c.getName()) && !c.isInterface()) {
						this.factory.addClass(c,
								new CompositeComponentPattern());
						c.setDrawable(true);
					}
				}

				for (String leaf : leafs) {
					if (leaf.equals(c.getName())) {
						this.factory.addClass(c, new CompositeLeafPattern());
						c.setDrawable(true);

					}
				}

				for (String comp : composites) {
					if (comp.equals(c.getName())) {
						this.factory.addClass(c,
								new CompositeCompositePattern());
						c.setDrawable(true);

					}
				}

			}
			this.model.addPatternDetected(this.factory);
		}
	}

}
