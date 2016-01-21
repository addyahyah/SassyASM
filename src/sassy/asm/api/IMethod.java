package sassy.asm.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public interface IMethod extends IModelPart{
	public String getName();

	public void setName(String name);

	public String getAccess();

	public void setAccess(String access);

	public String getReturnType();

	public void setReturnType(String returnType);

	public void addArg(String a);

	public ArrayList<String> getArgs();
	
	public void addToStack(String originalOwner, String className, String methodName);
	
	public Queue<List<String>> getStack();

}
