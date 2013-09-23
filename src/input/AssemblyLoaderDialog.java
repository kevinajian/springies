package input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import springies.Springies;

public class AssemblyLoaderDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel myPanel = new JPanel();
	private JLabel myLabel = new JLabel();
	private JTextField myTextField = new JTextField(20);
	private Springies mySpringies;
	
	// Creates graphical box for user to take in new assembly file
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
	
	private void handleUserSubmission(String filename){
		mySpringies.loadAssembly(filename);
	}
}
