package input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import springies.Springies;

/**
 * Creates dialog box to take user input of file name of assembly to be created
 * @author Kevin
 */
public class AssemblyLoaderDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel myPanel = new JPanel();
	private JLabel myLabel = new JLabel();
	private JTextField myTextField = new JTextField(20);
	private Springies mySpringies;
	
	/** 
	 * Creates graphical box for user to take in new assembly file
	 * @param springies - Springies engine that contains the simulation
	 */
	public AssemblyLoaderDialog(Springies springies) {
		setTitle("Input");
		setVisible(true);
		setSize(400, 200);

		myPanel.add(myTextField);
		myTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = myTextField.getText();
				handleUserSubmission(input);
			}
		});
		
		myPanel.add(myLabel);
		add(myPanel);
		mySpringies = springies;
	}
	
	/**
	 * Uses the user input to access the specified data file
	 * @param filename - String of data file to be accessed
	 */
	private void handleUserSubmission(String filename){
		mySpringies.loadAssembly(filename);
	}
}
