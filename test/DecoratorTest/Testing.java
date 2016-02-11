package DecoratorTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import sassy.asm.patterndetector.PatternDetector;
import sassy.asm.visitor.ClassDeclarationVisitor;
import sassy.asm.visitor.ClassFieldVisitor;
import sassy.asm.visitor.ClassMethodVisitor;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;
import sassy.asm.api.IClass;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.impl.Model;
import sassy.asm.impl.Classy;

public class Testing {


	
	public void simpleDecoratorTest(){
		 String[] classes = { "java/util/Collections",
			"java/util/Random", "java/util/List", "java/util/ListIterator",
			"java/util/concurrent/atomic/AtomicLong" };
		 IModel m =build(classes);
	}
	
	
	
	private IModel build(String[] classes){
		IModel model = new Model();
		for (String className : classes) {
			try {
				readClasses(model, className, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		PatternDetector pd = new PatternDetector(model);
		pd.detectPatterns();
		return model;
	}

	public static void readClasses(IModel model, String className, boolean drawable)
			throws IOException {
		className = className.replace(".", "/").replace("$", "");
		if(className.startsWith("java/lang")){
			return;
		}
//		if (!classRead.contains(className)) {
//			classRead.add(className);
//		} else {
//			return;
//		}
//		System.out.println(className + "!!!!!!!!!!!!!!!!!"+ drawable);
		IClass c = new Classy();
		// ASM's ClassReader does the heavy lifting of parsing the
		// compiled
		// Java class
		ClassReader reader = new ClassReader(className);
		// make class declaration visitor to get superclass and
		// interfaces
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c,
				model);
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
		c.setDrawable(drawable);
		model.addClass(c);
	}
}
