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

import problem.car.api.ICar;
import sassy.asm.api.IClass;
import sassy.asm.api.IField;
import sassy.asm.api.IMethod;
import sassy.asm.api.IModel;
import sassy.asm.detector.IDetector;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.VisitType;
import sassy.asm.visitor.Visitor;

public class GraphvizParser {

	private IModel model;
	private OutputStream output;
	private final Visitor visitor;

	public GraphvizParser(IModel model) throws FileNotFoundException {
		this.model = model;
		this.output = new FileOutputStream("./files/text.dot");
		this.visitor = new Visitor();

		this.setUpPrevisitModel();
		this.setUpPrevisitClass();
		this.setUpVisitClass();
		this.setUpVisitField();
		this.setUpVisitMethod();
		this.setUpPostvisitClass();
		this.setUpPostvisitModel();
		ITraverser t = (ITraverser) model;
		t.accept(this.visitor);

	}

	void write(String s) {

		try {
			// System.out.println(sb);
			this.output.write(s.getBytes());
		} catch (IOException e) {
			new RuntimeException();
		}
	}

	private void setUpPrevisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, IModel.class,
				(ITraverser t) -> {
					String s = "digraph sassy_uml{node [shape = \"record\"] ";
					this.write(s);
				});
	}

	public void setUpVisitClass() {
		this.visitor.addVisit(VisitType.Visit, IClass.class,
				(ITraverser t) -> {
					this.write("|");
				});
	}

	public void setUpVisitField() {
		this.visitor.addVisit(
				VisitType.Visit,
				IField.class,
				(ITraverser t) -> {
					IField f = (IField) t;
					String line = String.format("%s %s : %s\\l",
							f.getAccessType(), f.getFieldName(), f.getType());
					this.write(line);
				});
	}

	public void setUpPrevisitClass() {
		this.visitor
				.addVisit(
						VisitType.PreVisit,
						IClass.class,
						(ITraverser t) -> {
							IClass c = (IClass) t;
							String interfaceString = "\\<\\<interface\\>\\>\\n";
							String patterns = "";
							for (IDetector detector : this.model.getPatterns()
									.values()) {
								patterns += detector.getPattern();
							}
							String line = String.format(
									"%s[label = \"{%s%s%s|", c.getName(),
									(c.isInterface()) ? interfaceString : "",
									c.getName(), patterns);
							this.write(line);
						});
	}

	public void setUpVisitMethod() {
		this.visitor.addVisit(
				VisitType.Visit,
				IMethod.class,
				(ITraverser t) -> {
					IMethod m = (IMethod) t;
					if (!m.getName().contains("<init>")
							&& !m.getName().contains("<clinit>")) {
						String args = Arrays.toString(m.getArgs().toArray());
						String line = String.format("%s %s(%s) : %s\\l",
								m.getAccess(), m.getName(),
								args.substring(1, args.length() - 1),
								m.getReturnType());
						this.write(line);
					}
				});
	}

	public void setUpPostvisitClass() {
		this.visitor.addVisit(VisitType.PostVisit, IClass.class,
				(ITraverser t) -> {
					IClass c = (IClass) t;		
				String patterns = "";
				for (IDetector detector : this.model.getPatterns().values()) {
					patterns += detector.getColor();
				}
				String line = String.format("}\"%s]", patterns);
				this.write(line);
			});
	}

	public void setUpPostvisitModel() {
		this.visitor
				.addVisit(
						VisitType.PostVisit,
						IModel.class,
						(ITraverser t) -> {
							IModel m = (IModel) t;
							StringBuilder sb = new StringBuilder();
							String rel = "";
							String relType = "";
							if (m.getRelations() != null) {
								HashMap<ArrayList<String>, String> relations = m
										.getRelations();

								Set<ArrayList<String>> keySet = relations
										.keySet();

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
							// }
							sb.append("}");
							this.write(sb.toString());
						});
	}

}
