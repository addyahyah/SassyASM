package sassy.asm.detector;


import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.pattern.IPatterns;
import sassy.asm.pattern.Pattern;
import sassy.asm.pattern.SingletonPattern;

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
		
		IPatterns p = new Pattern();
		for (IMethod m : c.getMethods()) {
			if (m.getAccess().equals("+")
					&& m.getReturnType().equals(c.getName())) {
				p.addClass(c, new SingletonPattern());
			}
		}
		this.model.addPatternDetected(p);
	}

}
