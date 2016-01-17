package sassy.asm.visitor;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import sassy.asm.api.IClass;
import sassy.asm.impl.Class;
import sassy.asm.api.IModel;

public class VariableMethodVisitor extends MethodVisitor {
	private IClass c;
	private IModel model;
	private int callDepth = 5;

	public VariableMethodVisitor(int arg0, IClass c) {
		super(arg0);
		this.c = c;
	}

	public VariableMethodVisitor(int arg0, MethodVisitor arg1, IClass c,
			IModel model) {
		super(arg0, arg1);
		this.c = c;
		this.model = model;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		// desc -> params and return type
		// boolean itf -> if the method's owner class is an interface.
		// name -> name of the method that is invoked
		if(name.contains("<init>")){
			this.model.addRelation(owner, name, "use");
		}
//		if (owner.contains("util") && !owner.contains("$")) {
//			String invokedMethodOwnerClass = owner.replaceAll("/", ".");
//			String invokedMethodName = name;
//		}
	}

//	@Override
//	public void visitTypeInsn(int opcode, String type) {
//		if (!type.contains("java")) {
//			this.model.addRelation(this, type, "use");
//		}
//	}

}
