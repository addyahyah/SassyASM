package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import sassy.asm.api.IClass;
import sassy.asm.impl.Model;

public class TestM4 {
	
	private HashMap<String, Boolean> expectedValues;
	
	@Before
	public void initialize(){
		expectedValues = new HashMap<String, Boolean>();		
		expectedValues.put("Runtime", true);
		expectedValues.put("Desktop",  true);
		expectedValues.put("Calendar", false);
		expectedValues.put("FilterInputStream", false);
	}
	
	@Test
	public void testLazySingletonDetection() throws IOException{
		String[] classes = {"test/LazySingletonTestClass", "test/NotASingleton"};
		Model model = new Model();
		MockDesignParser mdp = new MockDesignParser();
		mdp.visit(model, classes);
		
		for(IClass c : model.getClasses()){
			if(c.getName().equals("LazySingletonTestClass")){
				System.out.println("class is: "+ c.getName());
				assertTrue(c.isSingleton());
			}
			else{
				assertFalse(c.isSingleton());
			}
		}
	}
	
	@Test
	public void testEagerSingletonDetection() throws IOException {
		String[] classes = {"test/NotASingleton", "test/EagerSingletonTestClass"};
		Model model = new Model();
		MockDesignParser mdp = new MockDesignParser();
		mdp.visit(model, classes);
		
		for(IClass c : model.getClasses()){
			if(c.getName().equals("EagerSingletonTestClass")){
				assertTrue(c.isSingleton());
			}
			else{
				assertFalse(c.isSingleton());
			}
		}
	}
	
	@Test
	public void testJavaBuiltInSingleton() throws IOException {
		String[] classes = {"java/lang/Runtime", "java/io/FilterInputStream", "java/util/Calendar"};
		Model model = new Model();
		MockDesignParser mdp = new MockDesignParser();
		mdp.visit(model, classes);
		for(IClass c: model.getClasses()){
			System.out.println("Class is: " + c.getName());
			assertEquals(expectedValues.get(c.getName()), c.isSingleton());
		}
		
	}
	
	@Test 
	public void testEdgeCase() throws IOException {
		String[] classes = {"test/EdgeCaseNotSingleton", "test/LazySingletonTestClass", "test/EagerSingletonTestClass"};
		Model model = new Model();
		MockDesignParser mdp = new MockDesignParser();
		mdp.visit(model, classes);
		for(IClass c: model.getClasses()) {
			if(c.getName().equals("EdgeCaseNotSingleton")){
				assertFalse(c.isSingleton());
			}
			else{
				assertTrue(c.isSingleton());
			}
		}
	}

}
