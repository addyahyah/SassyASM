package problem.asm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseClass {
	private String path;
	private ArrayList<String> classes;

	public ParseClass(String path) {
		this.path = path;
		this.classes = new ArrayList<String>();
	}

	public ArrayList<String> parse() throws IOException {
		FileReader input = new FileReader(this.path);
		BufferedReader buffer = new BufferedReader(input);
		String line = null;
		while((line=buffer.readLine())!=null){
			this.classes.add(line);
		}

		return this.classes;
	}
}
