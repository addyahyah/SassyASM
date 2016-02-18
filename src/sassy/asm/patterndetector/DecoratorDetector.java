package sassy.asm.patterndetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.pattern.DecoratorComponentPattern;
import sassy.asm.pattern.DecoratorPattern;
import sassy.asm.pattern.PatternsFactory;

public class DecoratorDetector implements IPatternDetector {
	private IModel model;
	private PatternsFactory adapter;

	public DecoratorDetector(IModel model) {
		this.model = model;
	}

	@Override
	public void detectPattern() {
		HashSet<ArrayList<String>> relations = model.getRelations();
		ArrayList<ArrayList<String>> assocCandidates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> inheritCandidates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> superClassCandidates = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> key : relations) {
			if (key.get(2).equals("assoc")) {
				if (!assocCandidates.contains(key)) {
					assocCandidates.add(key);
				}
			} else if (key.get(2).equals("interface")) {
				if (!inheritCandidates.contains(key)) {
					inheritCandidates.add(key);
				}
			} else if (key.get(2).equals("superClass")) {
				if (!superClassCandidates.contains(key)) {
					inheritCandidates.add(key);
					superClassCandidates.add(key);
				}
			}
		}

		ArrayList<String> decorators = new ArrayList<String>();
		for (ArrayList<String> assocCandidate : assocCandidates) {
			for (ArrayList<String> inheritCandidate : inheritCandidates) {
				for (ArrayList<String> superClassCandidate : superClassCandidates) {
					if (superClassCandidate.get(1).equals(
							inheritCandidate.get(0))) {

						decorators.add(superClassCandidate.get(0));

						if (assocCandidate.get(0).equals(
								inheritCandidate.get(0))) {

							if (assocCandidate.get(1).equals(
									inheritCandidate.get(1))) {
								String component = inheritCandidate.get(1);
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
		if (component != "" && decorators.size() > 0) {
			PatternsFactory decorator = new PatternsFactory();
			for (IClass c : this.model.getClasses()) {
				if (component.equals(c.getName())) {

					decorator.addClass(c, new DecoratorComponentPattern());
					c.setDrawable(true);
				}
				for (String s : decorators) {
					if (s.equals(c.getName())) {
						decorator.addClass(c, new DecoratorPattern());
						c.setDrawable(true);
					}
				}
			}
			this.model.addPatternDetected(decorator);
		}
	}
}
