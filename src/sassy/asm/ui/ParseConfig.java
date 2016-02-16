package sassy.asm.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import sassy.asm.app.DesignParser;

public class ParseConfig {
	private static final String[] propertyNames = { "Input-Folder",
			"Input-Classes", "Output-Directory", "Dot-Path", "Phases",
			"Adapter-MethodDelegation", "Decorator-MethodDelegation",
			"Singleton-RequireGetInstance" };
	private File file;
	private Properties props;
	private ArrayList<String> properties;

	public ParseConfig(File file) {
		this.file = file;
	}

	public String[] parse() {
		try {
			FileInputStream fileInput = new FileInputStream(this.file);
			props = new Properties();
			props.load(fileInput);
			this.properties = new ArrayList<>();
			for (String s : ParseConfig.propertyNames) {
				this.properties.add(props.getProperty(s));
			}

			fileInput.close();

		} catch (IOException ioe) {
			for (StackTraceElement ste : ioe.getStackTrace())
				System.err.println(ste.toString());
		}
		String[] args = this.properties.toArray(new String[this.properties
				.size()]);

		DesignParser parser = new DesignParser(args);
		
		return args;
	}
}
