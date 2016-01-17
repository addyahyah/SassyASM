package sassy.asm.impl;

import java.util.ArrayList;

import sassy.asm.api.IMethod;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class Method implements IMethod, ITraverser {
	private String name;
	private String access;
	private String returnType;
	private ArrayList<String> args;
	
	public Method() {
		this.name = "";
		this.access = "";
		this.returnType = "";
		this.args = new ArrayList<String>();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType.substring(returnType.lastIndexOf(".")+1);
	}

	public void addArg(String a) {
		this.args.add(a.substring(a.lastIndexOf(".")+1));
	}

	public ArrayList<String> getArgs() {
		return this.args;
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}
}
