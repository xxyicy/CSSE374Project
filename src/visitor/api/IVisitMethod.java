package visitor.api;


@FunctionalInterface
public interface IVisitMethod {
	public void execute(ITraverser t);
}
