package sassy.asm.pattern;

public class CompositeCompositePattern implements IPattern{

	@Override
	public String getColor() {
		return ",style=filled,fillcolor=yellow";

	}

	@Override
	public String getPattern() {
		return "\\n\\<\\<Composite\\>\\>";
	}

}
