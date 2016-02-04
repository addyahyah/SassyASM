package sassy.asm.detector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.pattern.ComponentPattern;
import sassy.asm.pattern.DecoratorPattern;
import sassy.asm.pattern.Pattern;

public class DecoratorDetector implements IDetector {
	private IModel model;
	private Pattern adapter;

	public DecoratorDetector(IModel model) {
		this.model = model;
	}

	@Override
	public void detectPattern() {
		HashSet<ArrayList<String>> relations = model.getRelations();
		ArrayList<ArrayList<String>> assocCandidates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> interfaceCandidates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> superClassCandidates = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> key : relations) {
			if (key.get(2).equals("assoc")) {
				if (!assocCandidates.contains(key)) {
					//System.out.println("Assoc" + key);
					assocCandidates.add(key);
				}
			} else if (key.get(2).equals("interface")) {
				if (!interfaceCandidates.contains(key)) {
					///System.out.println("Inter" + key);
					interfaceCandidates.add(key);
				}
			} else if (key.get(2).equals("superClass")) {
				if (!superClassCandidates.contains(key)) {
					//System.out.println("Super" + key);
					superClassCandidates.add(key);
				}
			}
		}

		ArrayList<String> decorators = new ArrayList<String>();
		for (ArrayList<String> assocCandidate : assocCandidates) {
			for (ArrayList<String> interfaceCandidate : interfaceCandidates) {
				for (ArrayList<String> superClassCandidate : superClassCandidates) {
					if (superClassCandidate.get(1).equals(
							interfaceCandidate.get(0))) {
						decorators.add(superClassCandidate.get(0));
						if (assocCandidate.get(0).equals(
								interfaceCandidate.get(0))) {
							if (assocCandidate.get(1).equals(
									interfaceCandidate.get(1))) {
								String component = interfaceCandidate.get(1);
								decorators.add(assocCandidate.get(0));
								this.addToCandidate(component, decorators);

							}
						}
					}
				}
			}
		}
	}

	public void addToCandidate(String component, ArrayList<String> decorators) {
		Pattern decorator = new Pattern();
		for (IClass c : this.model.getClasses()) {
			if (component.equals(c.getName())) {
				decorator.addClass(c, new ComponentPattern());
				c.setDrawable(true);
			}
			for (String s : decorators) {
				if (s.equals(c.getName())) {
					decorator.addClass(c, new DecoratorPattern());
					c.setDrawable(true);
				}
			}
		}
	}
}
