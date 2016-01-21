package sassy.asm.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import sassy.asm.api.IClass;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.impl.Method;

public class ClassMethodVisitor extends ClassVisitor {
	private IClass c;
	private IModel model;

	public ClassMethodVisitor(int api) {
		super(api);
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, IClass c,
			IModel model) {
		super(api, decorated);
		this.c = c;
		this.model = model;

	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc,
				signature, exceptions);
		IMethod method = new Method();

		method.setName(name);
		addAccessLevel(access, method);
		addReturnType(desc, method);
		addArguments(desc, method);
		c.addMethod(method);

		VariableMethodVisitor vv = new VariableMethodVisitor(Opcodes.ASM5,
				toDecorate, c, this.model, method);

		return vv;

	}

	void addAccessLevel(int access, IMethod method) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "#";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "-";
		} else {
			level = "";
		}
		method.setAccess(level);
	}

	void addReturnType(String desc, IMethod method) {
		Type type = Type.getReturnType(desc);
		String returnType = type.getClassName();
		method.setReturnType(returnType);
		String typeString = type.toString();
		// V -> void, Z -> boolean
		if (!typeString.equals("V") && !typeString.equals("Z")) {
			String s = type.toString().substring(0,
					type.toString().length() - 1);
			// System.out.println(s);
			this.model.addRelation(c.getName(), s, "use");
		}
	}

	void addArguments(String desc, IMethod method) {
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String s = args[i].toString().substring(0,
					args[i].toString().length() - 1);
			this.model.addRelation(c.getName(), s, "use");
			String arg = args[i].getClassName();
			method.addArg(arg);
		}
	}
} // end class