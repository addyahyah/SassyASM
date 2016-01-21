package problem.asm;

import java.io.IOException;
import java.util.ArrayList;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.impl.Model;
import sassy.asm.impl.Class;

public class DesignParser {
	public static String[] classes = { "problem.asm.GraphvizParser" };

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
	 GraphvizParser parse = new GraphvizParser(model);
	 // String path = "./files/Lab1-3Classes.txt";
	 // String path = "./files/AbstractFactoryPizzaStore.txt";
	 String path = "./files/SassyASM.txt";
	 ParseClass parser = new ParseClass(path);
	 ArrayList<String> result = parser.parse();
	 classes = result.toArray(new String[result.size()]);
	 for (String className : classes) {
	 IClass c = new Class();
	
	 // ASM's ClassReader does the heavy lifting of parsing the compiled
	 // Java class
	 ClassReader reader = new ClassReader(className);
	 // make class declaration visitor to get superclass and interfaces
	 ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
	 c, model);
	 // DECORATE declaration visitor with field visitor
	 ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
	 decVisitor, c, model);
	 // DECORATE field visitor with method visitor
	 ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
	 fieldVisitor, c, model);
	
	 // TODO: add more DECORATORS here in later milestones to accomplish
	 // specific tasks
	 // Tell the Reader to use our (heavily decorated) ClassVisitor to
	 // visit the class
	 reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
	 model.addClass(c);
	 }
	
	 parse.parse();
	 }

//	public static void main(String[] args) throws IOException {
//		IModel model = new Model();
//		SDEditor sd = new SDEditor(model);
//		IClass c = new Class();
//		ClassReader reader = new ClassReader("java.util.Collections");
//		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c,
//				model);
//		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
//				decVisitor, c, model);
//		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
//				fieldVisitor, c, model);
//		// TODO: ... More visitors / decorators
//		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
//
//		sd.parse();
//	}
}