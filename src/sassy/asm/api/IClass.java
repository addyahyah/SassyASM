package sassy.asm.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface IClass extends IModelPart {
	public String getName();

	public void setName(String name);

	public Collection<IMethod> getMethods();
	public HashMap<String, IMethod> getMethodsMap();

	public void addMethod(IMethod method);

	public ArrayList<IField> getFields();

	public void addField(IField field);

	public boolean isInterface();

	public void setInterface(boolean isInterface);
}
