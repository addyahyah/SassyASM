package sassy.asm.api;

public interface IVisitor {
	void visitClasses(StringBuilder sb);

	void visitImplementsArrows(StringBuilder sb);

	void visitExtendsArrows(StringBuilder sb);

	void visitUsesArrows(StringBuilder sb);

	void visitAssociationArrows(StringBuilder sb);

	void visitMethods(IClass c, StringBuilder sb);

	void visitFields(IClass c, StringBuilder sb);
}
