package sassy.asm.visitor;

import org.objectweb.asm.ClassVisitor;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;

public class ClassDeclarationVisitor extends ClassVisitor {
	private IClass c;
	private IModel model;

	public ClassDeclarationVisitor(int api, IClass c, IModel model) {
		super(api);
		this.c = c;
		this.model = model;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		c.setName(name);
		if (!superName.startsWith("java"))
			model.addRelation(c.getName(), superName, "superClass");
		for (String s : interfaces) {
			model.addRelation(c.getName(), s, "interface");
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}
}