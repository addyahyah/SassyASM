package sassy.asm.patterndetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.pattern.AdapteePattern;
import sassy.asm.pattern.PatternsFactory;
import sassy.asm.pattern.AdapterPattern;
import sassy.asm.pattern.TargetPattern;

public class AdapterDetector implements IPatternDetector {
	private IModel model;
	private PatternsFactory adapter;

	public AdapterDetector(IModel model) {
		this.model = model;

	}

	@Override
	public void detectPattern() {
		HashSet<ArrayList<String>> relations = model.getRelations();
		ArrayList<ArrayList<String>> assocCandidates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> inheritCandidates = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> key : relations) {
			if (key.get(2).equals("assoc")) {
				if (!assocCandidates.contains(key)) {
					assocCandidates.add(key);
				}
			} else if (key.get(2).equals("interface")
					|| key.get(2).equals("superClass")) {
				if (!inheritCandidates.contains(key)) {
					inheritCandidates.add(key);
				}
			}

		}

		for (ArrayList<String> assocCandidate : assocCandidates) {
			for (ArrayList<String> inheritCandidate : inheritCandidates) {
				if ((assocCandidate.get(0)).equals(inheritCandidate.get(0))) {
					String adapter = inheritCandidate.get(0);
					String adaptee = assocCandidate.get(1);
					String target = inheritCandidate.get(1);
//					System.out.println(adapter +  " " + adaptee + " " + target);
					this.addToCandidates(adapter, adaptee, target);

				}
			}
		}
	}

	private void addToCandidates(String adapter, String adaptee, String target) {
		IClass adapterClass = null;
		IClass adapteeClass = null;
		IClass targetClass = null;
		this.adapter = new PatternsFactory();
		// Check for Adapter/Adaptee relationship

		boolean checkAdapter = false;
		boolean checkAdaptee = false;
		boolean checkTarget = false;
		for (IClass c : this.model.getClasses()) {
			if (c.getName().equals(adapter)) {
				adapterClass = c;
				checkAdapter = true;
			} else if (c.getName().equals(adaptee)) {
				adapteeClass = c;
				checkAdaptee = true;
			} else if (c.getName().equals(target)) {
				targetClass = c;
				checkTarget = true;
			}
			if (checkAdapter && checkAdaptee && checkTarget) {
				break;
			}
		}

		List<String> methodNames = new ArrayList<>();
		int methodsCalled = 0;
		for (IMethod m : adapterClass.getMethods()) {
			Queue<List<String>> callStack = m.getStack();
			for (List<String> list : callStack) {
				// System.out.println(list);
				if (list.get(1).equals(adaptee)) {
					if (!methodNames.contains(list.get(2))) {
						methodNames.add(list.get(2));
						methodsCalled++;
					}
				} else {
					methodsCalled = 0;
					break;
				}
			}
		}
		if (methodsCalled > 1 && checkAdapter && checkAdaptee &&checkTarget) {
			// Set Adapter, adaptee, and target class here
			adapterClass.setDrawable(true);
			adapteeClass.setDrawable(true);
			targetClass.setDrawable(true);
			this.adapter.addClass(adapteeClass, new AdapteePattern());
			this.adapter.addClass(adapterClass, new AdapterPattern());
			this.adapter.addClass(targetClass, new TargetPattern());
			this.model.addPatternDetected(this.adapter);
		}
	}

}
