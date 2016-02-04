package sassy.asm.pattern;

public class SingletonPattern implements IPattern {

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return ",color=blue";
	}

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "\\n\\<\\<Singleton\\>\\>";
	}
}
