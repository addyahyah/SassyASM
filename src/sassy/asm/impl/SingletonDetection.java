package sassy.asm.impl;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;

public class SingletonDetection {
	private IModel model;

	public SingletonDetection(IModel model) {
		this.model = model;
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
				c.setSingleton(true);
			}
		}
	}
}
