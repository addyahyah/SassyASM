package sassy.asm.api;

import java.util.ArrayList;

public interface IMethod {
	public String getName();

	public void setName(String name);

	public String getAccess();

	public void setAccess(String access);

	public String getReturnType();

	public void setReturnType(String returnType);

	public void addArg(String a);

	public ArrayList<String> getArgs();
}
