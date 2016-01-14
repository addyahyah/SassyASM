package sassy.asm.impl;

import sassy.asm.api.IClass;
import sassy.asm.api.IVisitor;

public class VisitorAdapter implements IVisitor {

	@Override
	public void visitClasses(StringBuilder sb) {
	}

	@Override
	public void visitImplementsArrows(StringBuilder sb) {
	}

	@Override
	public void visitExtendsArrows(StringBuilder sb) {
	}

	@Override
	public void visitUsesArrows(StringBuilder sb) {
	}

	@Override
	public void visitAssociationArrows(StringBuilder sb) {
	}

	@Override
	public void visitMethods(IClass c, StringBuilder sb) {
	}

	@Override
	public void visitFields(IClass c, StringBuilder sb) {
	}

}
