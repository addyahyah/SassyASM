package problem.asm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import sassy.asm.api.IModel;
import sassy.asm.impl.VisitorAdapter;

public class SDEditor extends VisitorAdapter {
	private IModel model;
	private OutputStream output;

	public SDEditor(IModel model) throws FileNotFoundException {
		this.model = model;
		this.output = new FileOutputStream("./files/text.dot");
	}
	
	public void parse() throws IOException{
		StringBuilder sb = new StringBuilder();
		
//		this.visitClasses(sb);
//		this.visitExtendsArrows(sb);
//		this.visitImplementsArrows(sb);
//		this.visitAssociationArrows(sb);
//		this.visitUsesArrows(sb);
//		sb.append("}");
//		this.output.write(sb.toString().getBytes());
	}

}
