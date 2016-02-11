package test;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import sassy.asm.api.IClass;
import sassy.asm.api.IMethod;
import sassy.asm.app.DesignParser;
import sassy.asm.app.GraphvizParser;
import sassy.asm.app.ParseClass;
import sassy.asm.app.SDEditor;
import sassy.asm.impl.Classy;
import sassy.asm.impl.Model;
import sassy.asm.visitor.ClassDeclarationVisitor;
import sassy.asm.visitor.ClassFieldVisitor;
import sassy.asm.visitor.ClassMethodVisitor;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class MockDesignParser {
	
	public void visit(Model model, String[] classes) throws IOException{
		GraphvizParser parse = new GraphvizParser(model);

	
		for (String className : classes) {
			// if (className.contains("java/util/")) {
			System.out.println(className);

			IClass c = new Classy();

			// ASM's ClassReader does the heavy lifting of parsing the
			// compiled
			// Java class
			ClassReader reader = new ClassReader(className);
			// make class declaration visitor to get superclass and
			// interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
					c, model);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					decVisitor, c, model);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
					fieldVisitor, c, model);

			// TODO: add more DECORATORS here in later milestones to
			// accomplish
			// specific tasks
			// Tell the Reader to use our (heavily decorated) ClassVisitor
			// to
			// visit the class
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			model.addClass(c);
			// }
		}
		
		//old design
//		SingletonDetection singleton = new SingletonDetection(model);
//		ITraverser t = (ITraverser) model;
//		t.accept(parse);
//		
		

	}

}
