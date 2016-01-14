package problem.asm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;

public class GraphvizParser {
	private IModel model;
	private OutputStream output;

	public GraphvizParser(IModel model) throws FileNotFoundException {
		this.model = model;
		this.output = new FileOutputStream("./files/text.dot");
	}

	public void parse() throws IOException {
		StringBuilder sb = new StringBuilder();
		this.addClasses(sb);
		this.output.write(sb.toString().getBytes());
	}

	private void addClasses(StringBuilder sb) {
		sb.append("digraph sassy_uml{node [shape = \"record\"] ");
		for (IClass c : this.model.getClasses()) {
			sb.append(c.getName());
			sb.append("[label = \"{");
			sb.append(c.getName());
			sb.append("|");
			this.addFields(c, sb);
			sb.append("|");
			this.addMethods(c, sb);
			sb.append("}\"]");
		}
		// arrows here

		this.addExtendsArrows(sb);
		this.addImplementsArrows(sb);
		this.addAssociationArrows(sb);
		this.addUsesArrows(sb);
		sb.append("}");

	}

	private void addImplementsArrows(StringBuilder sb) {
		sb.append("edge [arrowhead = \"empty\"] edge [style = \"dashed\"]");
		for (IClass c : this.model.getClasses()) {
			if (c.getInterfaces() != null) {
				for (String s : c.getInterfaces()) {
					sb.append(c.getName());
					sb.append(" -> ");
					sb.append(s);
					sb.append("\n");
				}
			}
		}
	}

	private void addExtendsArrows(StringBuilder sb) {
		sb.append("edge [arrowhead = \"empty\"] edge [style = \"solid\"]");

		for (IClass c : this.model.getClasses()) {
			if (c.getSuperClass() != "") {
				sb.append(c.getName());
				sb.append(" -> ");
				sb.append(c.getSuperClass());
				sb.append("\n");
			}
		}
	}

	private void addUsesArrows(StringBuilder sb) {
		sb.append("edge [arrowhead = \"vee\"] edge [style = \"dashed\"]");
		for (IClass c : this.model.getClasses()) {
			if (c.getUses() != null) {
				for (String s : c.getUses()) {
					if (!c.getAssociations().contains(s)) {
						sb.append(c.getName());
						sb.append(" -> ");
						sb.append(s);
						sb.append("\n");
					}
				}

			}
		}
	}

	private void addAssociationArrows(StringBuilder sb) {
		sb.append("edge [arrowhead = \"vee\"] edge [style = \"solid\"]");
		for (IClass c : this.model.getClasses()) {
			if (c.getAssociations() != null) {
				for (String s : c.getAssociations()) {
					sb.append(c.getName());
					sb.append(" -> ");
					sb.append(s);
					sb.append("\n");
				}
			}
		}
	}

	private void addMethods(IClass c, StringBuilder sb) {
		for (IMethod m : c.getMethods()) {
			sb.append(m.getAccess());
			sb.append(" ");
			sb.append(m.getName());
			sb.append("(");
			String args = Arrays.toString(m.getArgs().toArray());
			sb.append(args.substring(1, args.length() - 1));

			sb.append(") : ");
			sb.append(m.getReturnType());
			sb.append("\\l");
		}
	}

	private void addFields(IClass c, StringBuilder sb) {
		for (IField f : c.getFields()) {
			sb.append(f.getAccessType());
			sb.append(" ");
			sb.append(f.getFieldName());
			sb.append(" : ");
			sb.append(f.getType());
			sb.append("\\l");
		}
	}

}
