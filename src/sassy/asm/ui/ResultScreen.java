package sassy.asm.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import sassy.asm.api.IClass;
import sassy.asm.api.IModel;
import sassy.asm.app.DesignParser;
import sassy.asm.pattern.DecoratorComponentPattern;
import sassy.asm.pattern.DecoratorPattern;
import sassy.asm.pattern.IPattern;
import sassy.asm.pattern.IPatternsFactory;
import sassy.asm.pattern.SingletonPattern;

public class ResultScreen {

	private JFrame frame;
	private static String phases;
	private static IModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResultScreen window = new ResultScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		phases = args[4];
		model = DesignParser.getModel();
	}

	/**
	 * Create the application.
	 */
	public ResultScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Design Parser - Result");
		frame.setBounds(200, 200, 1050, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String[] phaseArr = phases.split(", ");
		ArrayList<String> patterns = new ArrayList<>();
//		HashMap<String, CheckBoxNode> patternTo= new HashMap<>();
		// Get all the patterns we need to detect
		for (String s : phaseArr) {
			if (s.contains("Detection")) {
				String pattern = s.substring(0, s.indexOf("-"));
				patterns.add(pattern);
			}
		}

		ArrayList<IPatternsFactory> patternsDetected = model
				.getPatternDetected();
		ArrayList<String> classesDecorator = new ArrayList<String>();
		ArrayList<String> classesSingleton = new ArrayList<>();
		for (IPatternsFactory p : patternsDetected) {
			HashMap<IClass, IPattern> pList = p.getPatternList();
			for (IClass c : pList.keySet()) {
				if (pList.get(c) instanceof DecoratorPattern
						|| pList.get(c) instanceof DecoratorComponentPattern) {
					if (!classesDecorator.contains(c.getName())) {
						classesDecorator.add(c.getName());
					}
				} else if (pList.get(c) instanceof SingletonPattern) {
					if (!classesSingleton.contains(c.getName())) {
						classesSingleton.add(c.getName());
					}
				}
			}
		}
		CheckBoxNode decoratorOptions[] = new CheckBoxNode[classesDecorator
				.size()];
		CheckBoxNode singletonOptions[] = new CheckBoxNode[classesSingleton
				.size()];
		for (int i = 0; i < decoratorOptions.length; i++) {
			decoratorOptions[i] = new CheckBoxNode(classesDecorator.get(i),
					false);
			
			if(decoratorOptions[i].isSelected()){
				String className = decoratorOptions[i].getText();
				
			}
		}

		for (int i = 0; i < singletonOptions.length; i++) {
			singletonOptions[i] = new CheckBoxNode(classesSingleton.get(i),
					false);
		}
		// CheckBoxNode accessibilityOptions[] = {
		// new CheckBoxNode(
		// "Move system caret with focus/selection changes", false),
		// new CheckBoxNode("Always expand alt text for images", true) };

		// for checkboxes
		ArrayList<Object> rootNodes = new ArrayList<>();
//		for (String s : patterns) {
			Vector<?> decoratorVector = new NamedVector(patterns.get(0), decoratorOptions);
			Vector<?> singletonVector = new NamedVector(patterns.get(1), singletonOptions);

			rootNodes.add(decoratorVector);
			rootNodes.add(singletonVector);
//		}

		// CheckBoxNode browsingOptions[] = {
		// new CheckBoxNode("Notify when downloads complete", true),
		// new CheckBoxNode("Disable script debugging", true),
		// new CheckBoxNode("Use AutoComplete", true),
		// new CheckBoxNode("Browse in a new process", false) };
		// Vector<?> accessVector = new NamedVector("lol",
		// accessibilityOptions);
		// Vector<?> browseVector = new NamedVector("Browsing",
		// browsingOptions);

		// Object rootNodes[] = { accessVector, browseVector };

		Vector<?> rootVector = new NamedVector("Root",
				rootNodes.toArray(new Object[rootNodes.size()]));
		JTree tree = new JTree(rootVector);

		CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);

		tree.setCellEditor(new CheckBoxNodeEditor(tree));
		tree.setEditable(true);

		JScrollPane scrollPane = new JScrollPane(tree);

		scrollPane.setPreferredSize(new Dimension(
				(int) (frame.getWidth() * 0.3), frame.getHeight()));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		frame.getContentPane().add(scrollPane, BorderLayout.WEST);

		JScrollPane scrollPane_1 = new JScrollPane();

		Icon pic = new ImageProxy(
				"C:/EclipseWorkspaces/CSSE374/SassyASM/files/output.png");
		scrollPane_1 = new JScrollPane(new JLabel(pic));

		scrollPane_1.setPreferredSize(new Dimension(
				(int) (frame.getWidth() * 0.7), frame.getHeight()));
		scrollPane_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.getContentPane().add(scrollPane_1, BorderLayout.EAST);

		JMenuBar menuBar = new JMenuBar();
		// The File menu should offer a way to basically restart and load a
		// different configuration, as well as export the current graph
		JMenu fileMenu = new JMenu("File");
		// TODO: need to add action listeners
		JMenuItem restart = new JMenuItem("Restart");
		JMenuItem export = new JMenuItem("Export");
		fileMenu.add(restart);
		fileMenu.add(export);
		menuBar.add(fileMenu);
		// The Help menu should have options to get instructions on how to use
		// your program and “About” information about the program.
		JMenu helpMenu = new JMenu("Help");
		// TODO: need to add action listeners
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);
		menuBar.add(helpMenu);

		frame.setJMenuBar(menuBar);
	}

}
