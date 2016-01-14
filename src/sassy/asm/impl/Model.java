package sassy.asm.impl;

import java.util.ArrayList;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;

public class Model implements IModel {
	private ArrayList<IClass> classes;

	public Model() {
		this.classes = new ArrayList<>();
	}

	public void addClass(IClass c) {
		this.classes.add(c);
	}

	public ArrayList<IClass> getClasses() {
		return this.classes;
	}
}
