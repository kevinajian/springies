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
	private JPanel jp = new JPanel();
	private JLabel jl = new JLabel();
	private JTextField jt = new JTextField(20);
	private Springies mySpringies;
	public AssemblyLoaderDialog(Springies springies) {
		setTitle("Input");
		setVisible(true);
		setSize(400, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		jp.add(jt);
		jt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = jt.getText();
				hanldeUserSubmission(input);
			}
		});
		
		jp.add(jl);
		add(jp);
		mySpringies = springies;

	}
	
	private void hanldeUserSubmission(String filename){
		mySpringies.loadAssembly(filename);
	}
}
