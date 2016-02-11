package sassy.asm.visitor;

import java.io.IOException;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.app.DesignParser;

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
		
		if(superName==null){
			return;
		}

		model.addRelation(c.getName(), superName, "superClass");
		if (!superName.equals("java/lang/Object") && !superName.startsWith(DesignParser.packageName)) {
			try {
				DesignParser.readClasses(model, superName, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for (String s : interfaces) {

			model.addRelation(c.getName(), s, "interface");
			if (!s.equals("java/lang/Object") && !s.startsWith(DesignParser.packageName)) {
				try {
					DesignParser.readClasses(model, s, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			c.setInterface(true);
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}
}