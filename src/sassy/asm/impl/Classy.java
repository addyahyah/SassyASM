package sassy.asm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class Classy implements IClass, ITraverser {
	private String name;
	private HashMap<String, IMethod> methods;
	private ArrayList<IField> fields;
	private boolean isInterface;
	private boolean isDrawable;

	public Classy() {
		this.methods = new HashMap<String, IMethod>();
		this.fields = new ArrayList<IField>();
		this.name = "";
		this.isInterface = false;
		this.isDrawable = false;
	}

	public boolean isDrawable() {
		return isDrawable;
	}

	public void setDrawable(boolean isDrawable) {
		this.isDrawable = isDrawable;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name.substring(name.lastIndexOf("/")+1);
	}

	public Collection<IMethod> getMethods() {
		return this.methods.values();
	}

	public HashMap<String, IMethod> getMethodsMap() {
		return this.methods;
	}

	public void addMethod(IMethod method) {
		this.methods.put(method.getName(), method);
	}

	public ArrayList<IField> getFields() {
		return fields;
	}

	public void addField(IField field) {
		this.fields.add(field);
	}

	@Override
	public void accept(IVisitor v) {
		if (this.isDrawable && !this.getName().contains("Object")) {
			v.preVisit(this);

			for (IField f : this.getFields()) {
				ITraverser t = (ITraverser) f;
				t.accept(v);
			}
			v.visit(this);
			for (IMethod m : this.getMethods()) {
				ITraverser t = (ITraverser) m;
				t.accept(v);
			}
			v.postVisit(this);
		}
	}
}
