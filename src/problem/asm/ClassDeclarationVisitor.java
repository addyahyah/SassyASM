package problem.asm;

import java.util.Arrays;

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
		if(!superName.startsWith("java"))
			c.setSuperClass(superName);
		c.setInterfaces(Arrays.asList(interfaces));
//		this.model.addClass(c);
		super.visit(version, access, name, signature, superName, interfaces);
	}
}