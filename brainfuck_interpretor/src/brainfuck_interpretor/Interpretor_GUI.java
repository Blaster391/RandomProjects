package brainfuck_interpretor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Interpretor_GUI {

	public static void main(String[] args) {
		Interpretor_GUI gui = new Interpretor_GUI();
	}
	
	public Interpretor_GUI(){
		
		
		JFrame frame = new JFrame("Brainfuck Interpretor");
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.setBorder(new EmptyBorder(2, 3, 2, 3));
		
		JPanel inputPane = new JPanel();
		inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.PAGE_AXIS));
		
		JTextArea sourceCodeWindow = new JTextArea("Source Code Window", 22, 40);
		JScrollPane sourceCodePane = new JScrollPane( sourceCodeWindow );
		inputPane.add(sourceCodePane);
		
		JLabel spacer = new JLabel("     ");
		inputPane.add(spacer);
		
		JTextArea inputTextWindow = new JTextArea("Input Window", 10, 40);
		JScrollPane inputTextPane = new JScrollPane( inputTextWindow );
		inputPane.add(inputTextPane);
		
		pane.add(inputPane);
		
		JButton interpretButton = new JButton("INTERPRET");
		pane.add(interpretButton);
		
		JTextArea outputWindow = new JTextArea("Result");
		JScrollPane outputPane = new JScrollPane( outputWindow );
		pane.add(outputPane);
		
		
		interpretButton.addActionListener(new ActionListener(){	
			public void actionPerformed(ActionEvent e){
				Interpretor i = new Interpretor();
				//i.interpret(sourceCodeWindow.getText());
				outputWindow.setText(i.interpret(sourceCodeWindow.getText(),inputTextWindow.getText()));
			}
		}
		);
		
		frame.add(pane);
		frame.setVisible(true);
	}
	

}
