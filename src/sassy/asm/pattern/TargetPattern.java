package sassy.asm.pattern;

public class TargetPattern implements IPattern{

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return ",style=filled,fillcolor=red";
	}

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "\\n\\<\\<target\\>\\>";
	}

}