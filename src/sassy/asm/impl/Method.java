package sassy.asm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import sassy.asm.api.IMethod;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class Method implements IMethod, ITraverser {
	private String name;
	private String access;
	private String returnType;
	private ArrayList<String> args;
	public Queue<List<String>> stack;
	
	public Method() {
		this.name = "";
		this.access = "";
		this.returnType = "";
		this.args = new ArrayList<String>();
		this.stack=new LinkedList<>();
	}

	public Queue<List<String>> getStack() {
		return stack;
	}

	public void addToStack(String originalOwner, String className, String methodName) {
		ArrayList<String> methodList = new ArrayList<>();
		methodList.add(originalOwner);
		methodList.add(className);
		methodList.add(methodName);
		this.stack.add(methodList);
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
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}
}
