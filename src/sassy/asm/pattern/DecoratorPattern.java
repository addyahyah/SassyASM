package sassy.asm.pattern;

public class DecoratorPattern implements IPattern {

	@Override
	public String getColor() {
		return ",style=filled,fillcolor=green";
	}

	@Override
	public String getPattern() {
		return "\\n\\<\\<decorator\\>\\>";

	}

}
