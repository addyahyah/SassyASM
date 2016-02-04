package sassy.asm.detector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.pattern.AdapteePattern;
import sassy.asm.pattern.Pattern;
import sassy.asm.pattern.AdapterPattern;
import sassy.asm.pattern.TargetPattern;

public class AdapterDetector implements IDetector {
	private IModel model;
	private Pattern adapter;

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
					//need to check inheritCandidate() exists in the assoc candidate as the second element
					for (ArrayList<String> assoc : assocCandidates) {
						if(assoc.get(1).equals(target)){
							System.out.println(adapter + " " + adaptee +  " " + target);
							this.addToCandidates(adapter,adaptee,target);
							break;
						}
					}
				}
			}
		}
	}

	private void addToCandidates(String adapter, String adaptee, String target) {
		IClass adapterClass = null;
		IClass adapteeClass = null;
		IClass targetClass = null;
		this.adapter = new Pattern();
		for (IClass c : this.model.getClasses()) {
			if (c.getName().equals(adaptee)) {
				adapteeClass = c;
				adapteeClass.setDrawable(true);
				this.adapter.addClass(adapteeClass, new AdapteePattern());
			} else if (c.getName().equals(target)) {
				targetClass = c;
				targetClass.setDrawable(true);
				this.adapter.addClass(targetClass, new TargetPattern());
			} else if (c.getName().equals(adapter)) {
				adapterClass = c;
				adapterClass.setDrawable(true);
				this.adapter.addClass(adapterClass, new AdapterPattern());
			}

		}
		this.model.addPatternDetected(this.adapter);
	}

}
