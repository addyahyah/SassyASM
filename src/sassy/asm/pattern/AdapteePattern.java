package sassy.asm.pattern;

public class AdapteePattern implements IPattern{

	@Override
	public String getColor() {
		return ",style=filled,fillcolor=red";
	}

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "\\n\\<\\<adaptee\\>\\>";
	}

}
