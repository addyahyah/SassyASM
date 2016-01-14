package sassy.asm.impl;

import java.util.ArrayList;
import java.util.List;


import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;


public class Class implements IClass {
	private String name;
	private ArrayList<IMethod> methods;
	private ArrayList<IField> fields;
	private String superClass;
	private List<String> interfaces;
	private List<String> associations;
	private List<String> uses;

	public Class() {
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.name = "";
		this.superClass = "";
		this.interfaces = new ArrayList<>();
		this.associations = new ArrayList<>();
		this.uses = new ArrayList<>();
	}

	public List<String> getAssociations() {
		return associations;
	}

	public void addAssociation(String assoc) {
		if (assoc.contains(";")) {
			String[] map = assoc.split(";");
			for (String s : map) {
				addAssocHelper(s);
			}
		} else {
			addAssocHelper(assoc);
		}
	}

	public void addAssocHelper(String assoc) {
		if (!assoc.contains("java")) {
			assoc = assoc.substring(assoc.lastIndexOf("/") + 1);
			if (!this.associations.contains(assoc)) {
				this.associations.add(assoc);
			}
		}
	}

	public List<String> getUses() {
		return uses;
	}

	public void addUse(String use) {
		if (!use.contains("java")) {
			use = use.substring(use.lastIndexOf("/")+1);
			if (!this.uses.contains(use) && !use.equals(this.getName())) {
				this.uses.add(use);
			}
		}
	}

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass.substring(superClass.lastIndexOf("/") + 1);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name.substring(name.lastIndexOf("/") + 1);
	}

	public ArrayList<IMethod> getMethods() {
		return methods;
	}

	public void addMethod(IMethod method) {
		this.methods.add(method);
	}

	public ArrayList<IField> getFields() {
		return fields;
	}

	public void addField(IField field) {
		this.fields.add(field);
	}

	public List<String> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<String> interfaces) {
		List<String> newList = new ArrayList<>();
		for (int i = 0; i < interfaces.size(); i++) {
			newList.add(interfaces.get(i).substring(
					interfaces.get(i).lastIndexOf("/") + 1));
		}
		this.interfaces = newList;
	}
}
