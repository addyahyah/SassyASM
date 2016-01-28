package sassy.asm.visitor;

import org.objectweb.asm.MethodVisitor;

import sassy.asm.api.IClass;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.app.DesignParser;

public class VariableMethodVisitor extends MethodVisitor {
	private IClass c;
	private IModel model;
	private IMethod method;

	public VariableMethodVisitor(int arg0, IClass c) {
		super(arg0);
		this.c = c;
	}

	public VariableMethodVisitor(int arg0, MethodVisitor arg1, IClass c,
			IModel model, IMethod method) {
		super(arg0, arg1);
		this.c = c;
		this.model = model;
		this.method = method;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		// desc -> params and return type
		// boolean itf -> if the method's owner class is an interface.
		// name -> name of the method that is invoked
		if(name.contains("<init>") || name.contains("<clinit>")){
			this.model.addRelation(owner, name, "use");
		}
		if(owner.startsWith(DesignParser.packageName)) {
			owner = owner.substring(owner.lastIndexOf("/")+1);
			
			this.method.addToStack(this.c.getName(), owner, name);
//			System.out.println("method name: " + this.method.getName() + "++++++++++class name: " + this.c.getName());
//			System.out.println("_____________method owner class: " + owner + " name " + name);
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
