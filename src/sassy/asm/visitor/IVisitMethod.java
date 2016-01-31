package sassy.asm.visitor;

@FunctionalInterface
public interface IVisitMethod {
	public void execute(ITraverser t);
}
