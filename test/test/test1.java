package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import sassy.asm.app.DesignParser;

public class test1 {
	private TestDriver td;
	private String sd;
	
	@Before
	public void setUp() throws IOException, URISyntaxException{
		String[] array = { "sassy.asm.app.DesignParser.main(String[])" };
		td = new TestDriver(array);
		td.runDriver();
		
	      java.net.URL url = DesignParser.class.getResource("files/text.sd");
	        java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());
	        sd = new String(java.nio.file.Files.readAllBytes(resPath), "UTF8"); 
	}

	@Test
	public void testModNew() throws IOException {
		// String expected = "";
		//String[] array = { "sassy.asm.app.DesignParser.main(String[])" };
		// String[] array = {"java/util/Collections", "java/util/Random",
		// "java/util/List", "java/util/ListIterator",
		// "java/util/concurrent/atomic/AtomicLong"};
		//TestDriver td = new TestDriver(array);
		//System.out.println(1);
		//System.out.println(Arrays.toString(array));
		//td.runDriver();
		
		assertTrue(sd.contains("designparser:void=model.new()"));
		
	}
	
	@Test
	public void testNewIModel() {
		assertTrue(sd.contains("designparser:void=graphvizparser.new(IModel)"));
	}
	
	@Test
	public void testNewVisitorAdapter()  {
		assertTrue(sd.contains("graphvizparser:void=visitoradapter.new()"));
	}
	
	@Test
	public void testNewString()  {
		assertTrue(sd.contains("designparser:void=parseclass.new(String)"));
	}
	
	@Test
	public void testGetModelClasses() {
		assertTrue(sd.contains("designparser:ArrayList=imodel.getClasses()"));
	}
	
	@Test
	public void testGetIClassName() {
		assertTrue(sd.contains("designparser:String=iclass.getName()"));
	}
	
	@Test
	public void testGetIClassMethods() {
		assertTrue(sd.contains("designparser:Collection=iclass.getMethods()"));
	}
	
	@Test
	public void testGetIMethodName() {
		assertTrue(sd.contains("designparser:String=imethod.getName()"));
	}
	
	@Test
	public void testClassFieldVisitor(){
		assertTrue(sd.contains("/classfieldvisitor:ClassFieldVisitor[a]"));
	}
	
	@Test
	public void testClassMethodVisiotr(){
		assertTrue(sd.contains("/classmethodvisitor:ClassMethodVisitor[a]"));
	}
	
	@Test
	public void testJUNIT() throws IOException {
		assertEquals(1,0);
	}

}
