package gustavolessa.ticketing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class Notes extends JFrame {

	public Notes() {

		super();
		setTitle("ReadMe - General Notes");
		setSize(400,600);
		this.setLayout(new BorderLayout());		
		
		JTextArea textArea = new JTextArea(390, 590);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea){
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(400, 600);
			}
		};

		try (InputStream txt = Notes.class.getClass().getResourceAsStream("/ReadMe.txt")) {
			Reader test = new InputStreamReader(txt);
			textArea.read(test, "File");
		} catch (IOException exp) {
			exp.printStackTrace();
		}

		this.add(scrollPane, BorderLayout.CENTER);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		revalidate();
		repaint();

	}
}
