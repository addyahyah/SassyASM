package sassy.asm.app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.synth.SynthSeparatorUI;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.VisitorAdapter;

public class GraphvizParser extends VisitorAdapter {

	private IModel model;
	private OutputStream output;

	public GraphvizParser(IModel model) throws FileNotFoundException {
		this.model = model;

		this.output = new FileOutputStream("./files/text.dot");
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
	public void preVisit(IModel m) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph sassy_uml{node [shape = \"record\"] ");
		this.write(sb);
	}

	
	@Override
	public void visit(IClass c) {
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		this.write(sb);
	}
	
	@Override
	public void visit(IField f) {
		StringBuilder sb = new StringBuilder();
		sb.append(f.getAccessType());
		sb.append(" ");
		sb.append(f.getFieldName());
		sb.append(" : ");
		sb.append(f.getType());
		sb.append("\\l");
		this.write(sb);
	}

	@Override
	public void preVisit(IClass c) {
		StringBuilder sb = new StringBuilder();
		sb.append(c.getName());
		sb.append("[label = \"{");
		sb.append(c.getName());
		sb.append("|");
		this.write(sb);
	}

	@Override
	public void visit(IMethod m) {
		StringBuilder sb = new StringBuilder();
		sb.append(m.getAccess());
		sb.append(" ");
		sb.append(m.getName());
		sb.append("(");
		String args = Arrays.toString(m.getArgs().toArray());
		sb.append(args.substring(1, args.length() - 1));
		sb.append(") : ");
		sb.append(m.getReturnType());
		sb.append("\\l");
		this.write(sb);
	}

	@Override
	public void postVisit(IClass c) {
		StringBuilder sb = new StringBuilder();
		sb.append("}\"]");
		this.write(sb);
	}

	@Override
	public void postVisit(IModel m) {
		StringBuilder sb = new StringBuilder();
		String rel = "";
		String relType = "";
		if (m.getRelations() != null) {
			HashMap<ArrayList<String>, String> relations = m.getRelations();

//			for (int i = 0; i < relations.size(); i++) {
				// ArrayList<String> reltions = relations.values();
				Set<ArrayList<String>> keySet = relations.keySet();
				
				
				for (ArrayList<String> keys : keySet) {
					rel = keys.get(0) + "->" + keys.get(1);
					relType = relations.get(keys);
					
					if (relType.equals("use")) {
						sb.append("edge [arrowhead = \"vee\"] [style = \"dashed\"] ");
						sb.append(rel);
						sb.append("\n");
					} else if (relType.equals("interface")) {
						sb.append("edge [arrowhead = \"empty\"] [style = \"dashed\"] ");
						sb.append(rel);
						sb.append("\n");
					} else if (relType.equals("superClass")) {
						sb.append("edge [arrowhead = \"empty\"] [style = \"solid\"] ");
						sb.append(rel);
						sb.append("\n");
					} else if (relType.equals("assoc")) {
						sb.append("edge [arrowhead = \"vee\"] [style = \"solid\"] ");
						sb.append(rel);
						sb.append("\n");
					}
				}
				
			}
//		}
		sb.append("}");
		this.write(sb);
	}

}
