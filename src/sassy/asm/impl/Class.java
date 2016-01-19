package sassy.asm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModelPart;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class Class implements IClass, ITraverser {
	private String name;
	private HashMap<String, IMethod> methods;
	private ArrayList<IField> fields;

	public Class() {
		this.methods = new HashMap<String,IMethod>();
		this.fields = new ArrayList<IField>();
		this.name = "";

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name.substring(name.lastIndexOf("/") + 1);
	}

	public Collection<IMethod> getMethods() {
		return this.methods.values();
	}
	
	public HashMap<String, IMethod> getMethodsMap(){
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
		v.preVisit(this);
		for(IField f : this.getFields()){
			ITraverser t = (ITraverser) f;
			t.accept(v);
		}
		v.visit(this);
		for(IMethod m : this.getMethods()){
			ITraverser t = (ITraverser) m;
			t.accept(v);
		}
		v.postVisit(this);
	}
}
