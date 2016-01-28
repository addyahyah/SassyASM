package test;

public class LazySingletonTestClass {
	private static LazySingletonTestClass instance;

	private LazySingletonTestClass() {
	}

	public static LazySingletonTestClass getInstance() {
		if (instance == null) {
			instance = new LazySingletonTestClass();
		}
		return instance;
	}
}
