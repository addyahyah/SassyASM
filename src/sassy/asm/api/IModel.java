package sassy.asm.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import sassy.asm.visitor.ITraverser;

public interface IModel extends ITraverser{
	public void addClass(IClass c);
	public ArrayList<IClass> getClasses();
	public void addRelation(String name, String s, String string);
	public HashMap<ArrayList<String>, String> getRelations();
}
