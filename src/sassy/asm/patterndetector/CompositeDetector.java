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

		ArrayList<String> components = new ArrayList<String>();
		ArrayList<String> leafs = new ArrayList<String>();
		ArrayList<String> composites = new ArrayList<String>();
		String component = "";
		String componentInterface = "";
		String subClass = "";
		for (ArrayList<String> key : superClassCandidates) {
			component = key.get(1);
			components = new ArrayList<String>();
			leafs = new ArrayList<String>();
			composites = new ArrayList<String>();
			for (ArrayList<String> key1 : superClassCandidates) {
				if (key1.get(1).equals(component)) {
					subClass = key1.get(0);
//					 System.out.println(subClass);
					for (ArrayList<String> key2 : interfaceCandidates) {
						if (key2.get(0).equals(component)) {
							componentInterface = key2.get(1);
						}

						if (!leafs.contains(subClass)) {
							leafs.add(subClass);
						}
						for (ArrayList<String> key3 : assocCandidates) {
							if (subClass.equals(key3.get(0))) {
								if (key3.get(1).equals(component)
										|| key3.get(1).equals(
												componentInterface)) {
									
									if (!composites.contains(subClass)) {
										composites.add(subClass);
									}
								}
							} 

						}
//						 System.out.println(component);
//						 System.out.println("COMP : " + composites);
//						 System.out.println("LEAV: " + leafs);
//						 System.out.println();
						if (leafs.size() > 0 && composites.size() > 0) {
							if (!components.contains(component)) {
								components.add(component);
							}
							addToCandidate(components, leafs, composites);
						}
					}

				}

			}
		}

	}

	private void addToCandidate(ArrayList<String> components,
			ArrayList<String> leafs, ArrayList<String> composites) {
		if (components.size() > 0 && leafs.size() > 0 && composites.size() > 0) {
			factory = new PatternsFactory();
			for (IClass c : this.model.getClasses()) {
				for (String comp : components) {
					if (comp.equals(c.getName()) && !c.isInterface()) {
						// System.out.println(comp);
						this.factory.addClass(c,
								new CompositeComponentPattern());
						c.setDrawable(true);
					}
				}

				for (String leaf : leafs) {
					if (leaf.equals(c.getName())) {
						// System.out.println(leaf);
						this.factory.addClass(c, new CompositeLeafPattern());
						c.setDrawable(true);

					}
				}

				for (String comp : composites) {
					if (comp.equals(c.getName())) {
						// System.out.println(comp);
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
