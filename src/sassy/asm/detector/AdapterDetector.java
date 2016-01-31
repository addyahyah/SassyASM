package sassy.asm.detector;

import java.util.ArrayList;
import java.util.HashMap;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;

public class AdapterDetector implements IDetector {
	private IModel model;

	public AdapterDetector(IModel model) {
		this.model = model;
	} 

	@Override
	public void detectPattern() {
		HashMap<ArrayList<String>, String> relations = model.getRelations();
		ArrayList<ArrayList<String>> assocCandidates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> inheritCandidates = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> key : relations.keySet()) {
			if (relations.get(key).equals("assoc")) {
				assocCandidates.add(key);
			} else if (relations.get(key).equals("interface")
					|| relations.get(key).equals("superClass")) {
				inheritCandidates.add(key);
			}
		}

		for (IClass c : this.model.getClasses()) {
			for (ArrayList<String> assocCandidate : assocCandidates) {
				if (c.getName().equals(assocCandidate.get(0))) {
					for (ArrayList<String> inheritCandidate : inheritCandidates) {
						if (c.getName().equals(inheritCandidate.get(0))) {
							this.addToCandidates(c, assocCandidate.get(1), inheritCandidate.get(1));
						}
					}
				}
			}
		}
	}

	private void addToCandidates(IClass adapter, String adaptee, String target) {
		ArrayList<IClass> l = new ArrayList<>();
		l.add(adapter);
		for(IClass c : this.model.getClasses()) {
			if(c.getName().equals(adaptee)) {
				l.add(c);
			} else if(c.getName().equals(target)) {
				l.add(c);
			}
		}
		this.model.addPattern(l, this);
	}
	

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return ",style=filled,fillcolor=red";
	}

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "\\<\\<Adapter\\>\\>";
	}



}
