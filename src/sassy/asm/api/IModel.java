package sassy.asm.api;

import java.util.ArrayList;

public interface IModel {
	public void addClass(IClass c);
	public ArrayList<IClass> getClasses();
}
