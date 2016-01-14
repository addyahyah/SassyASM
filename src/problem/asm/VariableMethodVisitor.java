package problem.asm;

import org.objectweb.asm.MethodVisitor;

import sassy.asm.api.IClass;

public class VariableMethodVisitor extends MethodVisitor {
	private IClass c;

	public VariableMethodVisitor(int arg0, IClass c) {
		super(arg0);
		this.c = c;
	}

	public VariableMethodVisitor(int arg0, MethodVisitor arg1, IClass c) {
		super(arg0, arg1);
		this.c = c;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		// desc -> params and return type
		// boolean itf -> if the method's owner class is an interface.
		if (!(name.contains("<init>") || name.contains("<clinit>"))) {
				c.addUse(owner);
		}
	}

//	@Override
//	public void visitTypeInsn(int opcode, String type) {
//		if (!type.contains("java")) {
//			System.out.println(type);
//			c.addUse(type);
//		}
//	}

}
