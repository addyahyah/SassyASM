package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import sassy.asm.api.IClass;
import sassy.asm.impl.Model;
import sassy.asm.pattern.IPattern;
import sassy.asm.pattern.IPatterns;

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
	
//	@Test
//	public void testLazySingletonDetection() throws IOException{
//		String[] classes = {"test/LazySingletonTestClass", "test/NotASingleton"};
//		Model model = new Model();
//		MockDesignParser mdp = new MockDesignParser();
//		mdp.visit(model, classes);
//		
//		for(IClass c : model.getClasses()){
//			if(c.getName().equals("LazySingletonTestClass")){
//				System.out.println("class is: "+ c.getName());
//				assertTrue(c.isSingleton());
//			}
//			else{
//				assertFalse(c.isSingleton());
//			}
//		}
//	}
	
	@Test
	public void testLazySingletonDetection() throws IOException{
		String[] classes = {"test/LazySingletonTestClass", "test/NotaSingleton"};
		Model model = new Model();
		MockDesignParser mdp = new MockDesignParser();
		mdp.visit(model, classes);
		
		for (IPatterns p : model.getPatternDetected()) {
			HashMap<IClass, IPattern> pList = p.getPatternList();
			
			for(IClass c: model.getClasses()){
				if(c.getName().equals("LazySingletonTestClass")){
					assertTrue(pList.get(c).getPattern().equals("Singleton"));
				}
				else{
					assertFalse(pList.get(c).getPattern().equals("Singleton"));
				}
			}

		}
		
		

	}
	
	@Test
	public void testEagerSingletonDetection() throws IOException {
		String[] classes = {"test/NotASingleton", "test/EagerSingletonTestClass"};
		Model model = new Model();
		MockDesignParser mdp = new MockDesignParser();
		mdp.visit(model, classes);
		
		
		for (IPatterns p : model.getPatternDetected()) {
			HashMap<IClass, IPattern> pList = p.getPatternList();
			
			for(IClass c: model.getClasses()){
				if(c.getName().equals("EagerSingletonTestClass")){
					assertTrue(pList.get(c).getPattern().equals("Singleton"));
				}
				else{
					assertFalse(pList.get(c).getPattern().equals("Singleton"));
				}
			}

		}
		
//		for(IClass c : model.getClasses()){
//			if(c.getName().equals("EagerSingletonTestClass")){
//				assertTrue(c.isSingleton());
//			}
//			else{
//				assertFalse(c.isSingleton());
//			}
//		}
	}
	
	@Test
	public void testJavaBuiltInSingleton() throws IOException {
		String[] classes = {"java/lang/Runtime", "java/io/FilterInputStream", "java/util/Calendar"};
		Model model = new Model();
		MockDesignParser mdp = new MockDesignParser();
		mdp.visit(model, classes);
		
		for (IPatterns p : model.getPatternDetected()) {
			HashMap<IClass, IPattern> pList = p.getPatternList();
			
			for(IClass c: model.getClasses()){
				assertEquals(expectedValues.get(c.getName()), pList.get(c).getPattern().equals("Singleton"));
			}

		}
//		for(IClass c: model.getClasses()){
//			System.out.println("Class is: " + c.getName());
//			assertEquals(expectedValues.get(c.getName()), c.isSingleton());
//		}
		
	}
	
	@Test 
	public void testEdgeCase() throws IOException {
		String[] classes = {"test/EdgeCaseNotSingleton", "test/LazySingletonTestClass", "test/EagerSingletonTestClass"};
		Model model = new Model();
		MockDesignParser mdp = new MockDesignParser();
		mdp.visit(model, classes);
		
		for (IPatterns p : model.getPatternDetected()) {
			HashMap<IClass, IPattern> pList = p.getPatternList();
			
			for(IClass c: model.getClasses()){
				if(c.getName().equals("EdgeCaseNotSingleton")){
					assertTrue(pList.get(c).getPattern().equals("Singleton"));
				}
				else{
					assertFalse(pList.get(c).getPattern().equals("Singleton"));
				}
			}

		}
//		for(IClass c: model.getClasses()) {
//			if(c.getName().equals("EdgeCaseNotSingleton")){
//				assertFalse(c.isSingleton());
//			}
//			else{
//				assertTrue(c.isSingleton());
//			}
//		}
	}

}
