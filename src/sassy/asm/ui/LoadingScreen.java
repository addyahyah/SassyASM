package sassy.asm.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class LoadingScreen implements ActionListener, PropertyChangeListener {

	private JFrame frame;
	private JButton btnLoad;
	private JButton btnAnalyze;
	private JFileChooser fc;
	private File file;
	private JProgressBar progressBar;
	private Task task;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadingScreen window = new LoadingScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoadingScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

//		fc = new JFileChooser();
		file = new File("C:/EclipseWorkspaces/CSSE374/SassyASM/files/config.properties");

		btnLoad = new JButton("Load Config");
		btnLoad.addActionListener(this);
		panel.add(btnLoad);

		btnAnalyze = new JButton("Analyze");
		btnAnalyze.addActionListener(this);

		panel.add(btnAnalyze);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);

		progressBar = new JProgressBar();

		panel_1.add(progressBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLoad) {
			int returnVal = fc.showOpenDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
//				file = fc.getSelectedFile();
//				file = new File("C:/EclipseWorkspaces/CSSE374/SassyASM/files/config.properties");
				JLabel label = new JLabel("Opening: " + file.getName() + ".\n");
				frame.add(label);
				frame.repaint();
			} else {
				System.out.println("Open command cancelled by user.\n");
			}

		} else if (e.getSource() == btnAnalyze) {
			task = new Task();
			task.addPropertyChangeListener(this);
			task.execute();
		}
	}
	 /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
//            System.out.println(String.format(
//                    "Completed %d%% of task.\n", task.getProgress()));
        } 
    }
	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			//TODO: need to fix this later
			Random random = new Random();
			int progress = 0;
			// Initialize progress property.
			setProgress(0);
			while (progress < 100) {
				// Sleep for up to one second.
				try {
					Thread.sleep(random.nextInt(100));
				} catch (InterruptedException ignore) {
				}
				// Make random progress.
				progress += random.nextInt(10);
				setProgress(Math.min(progress, 100));
			}
			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			frame.dispose();
			ParseConfig fileParser = new ParseConfig(file);
			String[] args = fileParser.parse();
			ResultScreen.main(args);
		}
	}

}
