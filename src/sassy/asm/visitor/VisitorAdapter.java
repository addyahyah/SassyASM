package sassy.asm.visitor;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;

public abstract class VisitorAdapter implements IVisitor {

	@Override
	public void preVisit(IMethod m) {		
	}

	@Override
	public void postVisit(IMethod m) {		
	}

	@Override
	public void preVisit(IClass c) {		
	}

	@Override
	public void preVisit(IModel m) {
	}

	@Override
	public void visit(IClass c) {
	}

	@Override
	public void visit(IField f) {
	}

	@Override
	public void visit(IMethod m) {
	}

	@Override
	public void postVisit(IClass c) {
	}

	@Override
	public void postVisit(IModel m) {
	}

}
