package sassy.asm.app;

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

public class DesignParser {
	public static String[] classes = { "java/util/Collections",
			"java/util/Random", "java/util/List", "java/util/ListIterator",
			"java/util/concurrent/atomic/AtomicLong" };
	public static List<String> classRead = new ArrayList<>();
	public static final String packageName = "problem";
//	public static final String packageName = "sassy";

	public static final String className = "DesignParser";
	public static final String methodName = "main";
	public static int depth = 5;

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args
	 *            : the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		IModel model = new Model();
		String classAndMethodName = args[0];
		depth = Integer.parseInt(args[1]);
		String methodName = classAndMethodName.substring(classAndMethodName
				.lastIndexOf(".") + 1);
		String classy = classAndMethodName.substring(0,
				classAndMethodName.lastIndexOf(".")).replace(".", "/");

//		 String path = "./files/Lab1-3Classes.txt";
		// String path = "./files/javaClasses.txt";

//		 String path = "./files/AbstractFactoryPizzaStore.txt";
//		String path = "./files/SassyASM.txt";
//		 String path = "./files/AdapterTestLab5-1.txt";
//		 String path = "./files/ChocolateBoilerSingleton.txt";
		String path="./files/DecoratorTestLab2-1.txt";
		ParseClass parser = new ParseClass(path);
		ArrayList<String> result = parser.parse();
		classes = result.toArray(new String[result.size()]);
		for (String className : classes) {
			readClasses(model, className, true);
		}
		
		PatternDetector pd = new PatternDetector(model);
		pd.detectPatterns();
		
		
		GraphvizParser parse = new GraphvizParser(model);
		outer: for (IClass cl : model.getClasses()) {
			if (cl.getName().equals(
					className.substring(className.lastIndexOf("/") + 1))) {
				for (IMethod m : cl.getMethods()) {
					if (m.getName().equals(methodName)) {
						SDEditor sd = new SDEditor(model, depth);
						break outer;
					}
				}
			}
		}
	}

	public static void readClasses(IModel model, String className, boolean drawable)
			throws IOException {
		className = className.replace(".", "/").replace("$", "");
		if(className.startsWith("java/lang")){
			return;
		}
		if (!classRead.contains(className)) {
			classRead.add(className);
		} else {
			return;
		}
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