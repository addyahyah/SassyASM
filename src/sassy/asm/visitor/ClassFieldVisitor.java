package sassy.asm.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.impl.Field;

public class ClassFieldVisitor extends ClassVisitor {
	private IClass c;
	private IModel model;

	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass c,
			IModel model) {
		super(api, decorated);
		this.c = c;
		this.model = model;
	}

	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc,
				signature, value);
		String type = Type.getType(desc).getClassName();

		IField field = new Field();
		addAccessType(access, field);
		field.setFieldName(name);
		field.setType(type);
		c.addField(field);
		
		
		if (signature != null) {
			if (signature.contains("<") || signature.contains(">")) {
				String bracket = signature.substring(
						signature.lastIndexOf("<") + 1,
						signature.lastIndexOf(">") - 1);
				this.model.addRelation(c.getName(), bracket, "assoc");
				// System.out.println("ASSOC :  " + c.getName() + " : " +
				// bracket);

			} else {
				String bracket = signature.substring(0, signature.length() - 1);
				this.model.addRelation(c.getName(), bracket, "assoc");
				// System.out.println("ASSOC : " + c.getName() + " : " +
				// bracket);

			}
		} else {
			if (desc.contains("/") && !desc.contains("java")) {
				String bracket = desc.substring(desc.lastIndexOf("/"),
						desc.length() - 1);
				this.model.addRelation(c.getName(), bracket, "assoc");
			}
		}
		return toDecorate;
	};

	public void addAccessType(int access, IField field) {
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
		field.setAccessType(level);
	}
}