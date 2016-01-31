package sassy.asm.detector;

import java.util.ArrayList;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;

public class SingletonDetector implements IDetector{
	private IModel model;

	public SingletonDetector(IModel model) {
		this.model = model;
	}


	@Override
	public void detectPattern() {
		checkTypeAndIsPrivate();		
	}
	
	
	private void checkTypeAndIsPrivate() {
		for (IClass c : this.model.getClasses()) {
			for (IField f : c.getFields()) {
				if (f.getAccessType().equals("-")
						&& f.getType().equals(c.getName())) {
					checkPrivateConstructor(c);
				}
			}
		}
	}

	private void checkPrivateConstructor(IClass c) {
		for (IMethod m : c.getMethods()) {
			if (m.getAccess().equals("-") && m.getName().equals("<init>")) {
				checkHasGetInstance(c);
			}
		}
	}

	private void checkHasGetInstance(IClass c) {
		for (IMethod m : c.getMethods()) {
			if (m.getAccess().equals("+")
					&& m.getReturnType().equals(c.getName())) {
				ArrayList<IClass> cs = new ArrayList<>();
				cs.add(c);
				this.model.addPattern(cs, this);
			}
		}
	}


	@Override
	public String getColor() {
		return ",color=blue";
	}


	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "\\n\\<\\<Singleton\\>\\>";
	}


}
