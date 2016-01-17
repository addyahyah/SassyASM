package sassy.asm.visitor;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;

public interface IVisitor {
	void preVisit(IModel m);

	void preVisit(IClass c);

	void visit(IClass c);

	void postVisit(IModel m);

	void postVisit(IClass c);

	void visit(IMethod m);

	void visit(IField f);
}
