package test;

public class EagerSingletonTestClass {
	
	private static EagerSingletonTestClass uniqueInstance = new EagerSingletonTestClass();
	
	private EagerSingletonTestClass() {
		
	}
	
	public static EagerSingletonTestClass getInstance(){
		return uniqueInstance;
	}

}
