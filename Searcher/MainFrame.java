import java.awt.Font;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;


class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private MainFrameListener mainFrameListener;
	private DefaultListModel<String> displayModel;
	private JList<String> displayList;
	
	MainFrame() {

		super("Tweets Searcher");
		setBounds(100, 100, 950, 512);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
					
		displayModel = new DefaultListModel<>();
		displayList = new JList<>(displayModel);
		JScrollPane displayPane = new JScrollPane();
		displayPane.setBounds(348, 0, 586, 481);
		displayPane.setViewportView(displayList);
		getContentPane().add(displayPane);
		
		JTextField textField = new JTextField();
		textField.setBounds(68, 29, 256, 23);
		add(textField);
										
		JTextField userField = new JTextField();
		userField.setBounds(68, 83, 256, 23);
		add(userField);
		
		JTextField locationField = new JTextField();
		locationField.setColumns(10);
		locationField.setBounds(68, 132, 256, 23);
		add(locationField);
				
		JLabel lblText = new JLabel("text :");
		lblText.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblText.setBounds(22, 33, 46, 14);
		add(lblText);
		
		JLabel lblUser = new JLabel("user :");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUser.setBounds(22, 87, 46, 14);
		add(lblUser);
		
		JLabel lblLocation = new JLabel("location :");
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLocation.setBounds(6, 136, 63, 14);
		add(lblLocation);
		
		JTextField maxDocsReturnedField = new JTextField("10");
		maxDocsReturnedField.setBounds(164, 177, 27, 20);
		add(maxDocsReturnedField);
		
		JLabel lblMaxDocumentsReturned = new JLabel("Max documents returned : ");
		lblMaxDocumentsReturned.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMaxDocumentsReturned.setBounds(6, 180, 153, 14);
		add(lblMaxDocumentsReturned);
		
		JCheckBox chckbxOrderByDate = new JCheckBox("Order by date");
		chckbxOrderByDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxOrderByDate.setBounds(6, 217, 117, 23);
		add(chckbxOrderByDate);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(10, 257, 89, 23);
		btnSearch.addActionListener(event -> {
			try {
				mainFrameListener.search(textField.getText(), userField.getText(), locationField.getText(),
									     Integer.parseInt(maxDocsReturnedField.getText()), chckbxOrderByDate.isSelected());
			} catch (ParseException | IOException | NumberFormatException | InvalidTokenOffsetsException e) {
				e.printStackTrace();
			}
		});
		add(btnSearch);		
	}
	
	void setListener(MainFrameListener mainFrameListener) {
		this.mainFrameListener = mainFrameListener;
	}
	
	void display(int numDocsFound) {
		displayModel.clear();
		displayModel.addElement("Documents found : " + numDocsFound);
		displayModel.addElement("\n");
	}
	
	void display(String user, String text, String location, String date) {
		displayModel.addElement("Date : " + date);
		displayModel.addElement("Location : " + location);
		displayModel.addElement("User : " + user);
		displayModel.addElement("Text : " + text);
		displayModel.addElement("\n");
	}
}