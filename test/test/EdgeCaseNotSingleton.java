package test;

public class EdgeCaseNotSingleton {
	
	
	private static EdgeCaseNotSingleton instance;

	public EdgeCaseNotSingleton() {
	}

	public static EdgeCaseNotSingleton getInstance() {
		if (instance == null) {
			instance = new EdgeCaseNotSingleton();
		}
		return instance;
	}
}