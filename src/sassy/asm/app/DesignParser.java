package sassy.asm.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import sassy.asm.patterndetector.PatternDetector;
import sassy.asm.ui.FetchClasses;
import sassy.asm.visitor.ClassDeclarationVisitor;
import sassy.asm.visitor.ClassFieldVisitor;
import sassy.asm.visitor.ClassMethodVisitor;
import sassy.asm.api.IClass;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.impl.Model;
import sassy.asm.impl.Classy;

public class DesignParser {
//	 public static String[] classes = { "java/util/Collections",
//	 "java/util/Random", "java/util/List", "java/util/ListIterator",
//	 "java/util/concurrent/atomic/AtomicLong" };
//	, "java/awt/Container", "javax/swing/JButton", "java/awt/Component"
//	public static String[] classes = { "javax/swing/JComponent"};
	public static String[] classes;
	public static List<String> classRead = new ArrayList<>();
//	 public static final String packageName1 = "problem";
//	public static final String packageName = "sassy";

	public static final String packageName1 = "problem";
	public static final String packageName2 = "problem";

	// public static final String packageName = "";
	public static final String className = "DesignParser";
	public static final String methodName = "main";
	public static int depth = 5;
	public static String outputDir;

	private static IModel model;

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args
	 *            : the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
//	 public static void main(String[] args) throws IOException {
	public DesignParser(String[] args) {
		ArrayList<String> cls = new ArrayList<String>();
		// args[0] is input-directory
		String[] classesFetched = FetchClasses.getClasses(args[0], new File(
				args[0]));
		cls.addAll(Arrays.asList(classesFetched));

		// args[1] is input-classes
		String[] inputClasses = args[1].split(",");
		cls.addAll(Arrays.asList(inputClasses));
		classes = cls.toArray(new String[cls.size()]);
		 outputDir = args[2];
//		 System.out.println(outputDir);
		model = new Model();
		// String classAndMethodName = args[0];
		// depth = Integer.parseInt(args[1]);
		// String methodName = classAndMethodName.substring(classAndMethodName
		// .lastIndexOf(".") + 1);
		// String classy = classAndMethodName.substring(0,
		// classAndMethodName.lastIndexOf(".")).replace(".", "/");

		// String path = "./files/Lab1-3Classes.txt";
		// String path = "./files/javaClasses.txt";
//		String path = "./files/CompositeTestLab7-2.txt";
		// String path = "./files/AbstractFactoryPizzaStore.txt";
//		 String path = "./files/SassyASM.txt";
		// String path = "./files/AdapterTestLab5-1.txt";
		// String path = "./files/ChocolateBoilerSingleton.txt";
		// String path="./files/DecoratorTestLab2-1.txt";
//		 ParseClass parser = new ParseClass(path);
//		 ArrayList<String> result = null;
//		try {
//			result = parser.parse();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		 classes = result.toArray(new String[result.size()]);
		for (String className : classes) {
			className = className.replace(".", "/");
			try {
				readClasses(model, className, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		PatternDetector pd = new PatternDetector(model);
		pd.detectPatterns();

		try {
			GraphvizParser parse = new GraphvizParser(model);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		outer: for (IClass cl : model.getClasses()) {
			if (cl.getName().equals(
					className.substring(className.lastIndexOf("/") + 1))) {
				for (IMethod m : cl.getMethods()) {
					if (m.getName().equals(methodName)) {
						try {
							SDEditor sd = new SDEditor(model, depth);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						break outer;
					}
				}
			}
		}
	}

	public static void readClasses(IModel model, String className,
			boolean drawable) throws IOException {
		className = className.replace(".", "/").replace("$", "");
		if (className.startsWith("java/lang")) {
			return;
		}
		if (!classRead.contains(className)) {
			classRead.add(className);
		} else {
			return; 
		}
		// || className.contains("swing")
		if(className.contains("TransferHandlerHasGetTransferHandler")){
			return;
		}
		
		IClass c = new Classy();
//		System.out.println(className);
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

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		c.setDrawable(drawable);
		model.addClass(c);
	}

	public static IModel getModel() {
		return model;
	}
}