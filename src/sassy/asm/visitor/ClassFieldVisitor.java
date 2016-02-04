package sassy.asm.visitor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.app.DesignParser;
import sassy.asm.impl.Fieldy;

public class ClassFieldVisitor extends ClassVisitor {
	private IClass c;
	private IModel model;
	private Set<String> primitives;

	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass c,
			IModel model) {
		super(api, decorated);
		this.c = c;
		this.model = model;
		primitives = new HashSet<String>();
		primitives.add(Boolean.class.toString());
		primitives.add(Character.class.toString());
		primitives.add(Byte.class.toString());
		primitives.add(Short.class.toString());
		primitives.add(Integer.class.toString());
		primitives.add(Long.class.toString());
		primitives.add(Float.class.toString());
		primitives.add(Double.class.toString());
		primitives.add(Void.class.toString());
	}

	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc,
				signature, value);
		String type = Type.getType(desc).getClassName();

		IField field = new Fieldy();
		addAccessType(access, field);
		field.setFieldName(name);

		field.setType(type);
		if (!field.getType().contains("$")
				|| !field.getFieldName().contains("$")) {
			c.addField(field);
			try {
				if (primitives.contains(type)) {
					DesignParser.readClasses(model, type, false);
				}
			} catch (IOException e) {
			}
		}

		if (signature != null) {
			if (signature.contains("<") || signature.contains(">")) {
				String bracket = signature.substring(
						signature.lastIndexOf("/") + 1).replace(";", "");

				this.model.addRelation(c.getName(), bracket, "assoc");
			} else {
				String bracket = signature.substring(0, signature.length() - 1);
				this.model.addRelation(c.getName(), bracket, "assoc");
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