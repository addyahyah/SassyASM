package sassy.asm.pattern;

public class DecoratorComponentPattern implements IPattern{

	@Override
	public String getColor() {
		return ",style=filled,fillcolor=green";

	}

	@Override
	public String getPattern() {
		return "\\n\\<\\<component\\>\\>";

	}

}
