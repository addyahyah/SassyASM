package sassy.asm.api;

import java.util.ArrayList;
import java.util.List;

public interface IClass {
	public String getName();

	public void setName(String name);

	public ArrayList<IMethod> getMethods();

	public void addMethod(IMethod method);

	public ArrayList<IField> getFields();

	public void addField(IField field);

	public void setSuperClass(String superClass);

	public List<String> getInterfaces();

	public void setInterfaces(List<String> interfaces);

	public String getSuperClass();
	
	public List<String> getAssociations();

	public void addAssociation(String assoc);

	public List<String> getUses();

	public void addUse(String use);

}
