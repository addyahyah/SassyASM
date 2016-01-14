package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.impl.Field;

public class ClassFieldVisitor extends ClassVisitor {
	private IClass c;

	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass c) {
		super(api, decorated);
		this.c = c;
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
			String bracket = signature.substring(signature.lastIndexOf("<")+1, signature.lastIndexOf(">")-1);
			c.addAssociation(bracket);
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