package sassy.asm.app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import sassy.asm.visitor.ITraverser;
import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.visitor.IVisitor;
import sassy.asm.visitor.VisitorAdapter;

public class SDEditor extends VisitorAdapter {
	private IModel model;
	private OutputStream output;
	private int depth;
	private int OriginalDepth;
	private Set<String> classes;
	private List<String> methodStrings;

	public SDEditor(IModel model, int depth) throws FileNotFoundException {
		this.model = model;
		this.output = new FileOutputStream("./files/text.sd");
		this.depth = depth;
		this.OriginalDepth = depth;
		this.methodStrings = new ArrayList<String>();
		this.classes = new HashSet<String>();
	}

	void write(StringBuilder sb) {
		try {
			System.out.println(sb);
			this.output.write(sb.toString().getBytes());
		} catch (IOException e) {
			new RuntimeException();
		}
	}

	@Override
	public void preVisit(IClass c) {

	}

	@Override
	public void preVisit(IModel m) {

	}

	@Override
	public void visit(IClass c) {

	}

	@Override
	public void visit(IField f) {

	}

	@Override
	public void preVisit(IMethod m) {
		this.depth--;
		if (this.depth <= 0) {
			return;
		}
		Queue<List<String>> stack = m.getStack();
		String originalOwner = "";
		if (stack.size() != 0) {
			originalOwner = stack.peek().get(0);
			if (!this.classes.contains(originalOwner) && !this.classes.contains("/"+originalOwner)) {
				this.classes.add(originalOwner);
			}
		}
	}

	@Override
	public void visit(IMethod m) {
		if (this.depth <= 0) {
			return;
		}
		Queue<List<String>> stack = m.getStack();
//		System.out.println(stack.size());
		for (List<String> list : stack) {
			String className = list.get(1);
//			System.out.println("Class name:  " + className);
			for (IClass c : this.model.getClasses()) {
//				System.out.println("C name : " + c.getName() +  " Class Name: " + className);
				if (c.getName().equals(className)) {
					if (list.get(2).contains("<init>")
							&& !this.classes.contains(className)) {
						if (this.classes.contains("/" + className)) {
							continue;
						}
						this.classes.add("/" + className);
					} else if (!this.classes.contains("/" + className)) {
						this.classes.add(className);
					}
//					System.out.println("Method name : " +list.get(2));
					IMethod method = c.getMethodsMap().get(list.get(2));
					String params = "[]";

					if (method.getArgs() != null) {
						params = Arrays.toString(method.getArgs().toArray());
					}
					String s = String.format("%s:%s=%s.%s(%s)", list.get(0)
							.toLowerCase(), method.getReturnType(), className
							.toLowerCase(), (method.getName()
							.contains("<init>")) ? "new" : method.getName(),
							params.substring(1, params.length() - 1));
					// Issue, needs a tissue
					this.methodStrings.add(s + "\n");

					ITraverser t = (ITraverser) method;
					t.accept((IVisitor) this);
					break;
				}
			}
		}
//		System.out.println(Arrays.toString(this.classes.toArray()));
	}

	@Override
	public void postVisit(IMethod m) {
		this.depth++;
		if (this.depth == this.OriginalDepth) {
			StringBuilder sb = new StringBuilder();
			for (String c : this.classes) {
				String s = String.format("%s:%s[a]\n", c.toLowerCase(),
						(c.startsWith("/") ? c.substring(1) : c));
				sb.append(s);
			}
			sb.append("\n");
			for (String ms : this.methodStrings) {
				sb.append(ms);
			}
			this.write(sb);
		}
	}

	@Override
	public void postVisit(IClass c) {

	}

	@Override
	public void postVisit(IModel m) {

	}

}
