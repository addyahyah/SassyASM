package sassy.asm.pattern;


public class AdapterPattern implements IPattern{

	@Override
	public String getColor() {
		return ",style=filled,fillcolor=red";
	}

	@Override
	public String getPattern() {
		return "\\n\\<\\<adapter\\>\\>";
	}
	
}
