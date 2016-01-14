package problem.asm;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

import sassy.asm.api.IClass;

public class ClassDeclarationVisitor extends ClassVisitor {
	private IClass c;

	public ClassDeclarationVisitor(int api, IClass c) {
		super(api);
		this.c = c;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		c.setName(name);
		if(!superName.startsWith("java"))
			c.setSuperClass(superName);
		c.setInterfaces(Arrays.asList(interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}
}