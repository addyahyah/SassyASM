package sassy.asm.pattern;

import java.util.HashMap;
import java.util.List;

import sassy.asm.api.IClass;

public interface IPatterns {
	public HashMap<IClass, IPattern> getPatternList();
	public void addClass(IClass c, IPattern p);
}
