package jsonconverter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ExcelToJson {

	private JFrame frmExcelToJson;
	private JTextField textField;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcelToJson window = new ExcelToJson();
					window.frmExcelToJson.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ExcelToJson() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExcelToJson = new JFrame();
		frmExcelToJson.getContentPane().setBackground(Color.WHITE);
		frmExcelToJson.setResizable(false);
		frmExcelToJson.setTitle("Excel to Json Converter");
		frmExcelToJson.setBounds(100, 100, 544, 317);
		frmExcelToJson.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExcelToJson.getContentPane().setLayout(null);
		
		JButton btnConvert = new JButton("Convert");
		btnConvert.setBounds(353, 200, 133, 47);
		btnConvert.setEnabled(false);
		frmExcelToJson.getContentPane().add(btnConvert);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(397, 125, 89, 23);
		frmExcelToJson.getContentPane().add(btnBrowse);
		
		textField = new JTextField();
		textField.setBounds(27, 126, 356, 20);
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		frmExcelToJson.getContentPane().add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(27, 29, 331, 63);
		textArea.setEditable(false);
		textArea.setBackground(Color.WHITE);
		frmExcelToJson.getContentPane().add(textArea);
		

		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File file = new File(textField.getText());
				JsonConverter converter = new JsonConverter();
				
				String json = converter.generateJson(file);
				
				if(converter.wasSuccessful()){
				JFileChooser fileChooser = new JFileChooser();
					if (fileChooser.showSaveDialog(frmExcelToJson) == JFileChooser.APPROVE_OPTION) {
					  // save to file
					  try {
						File savedFilePath = fileChooser.getSelectedFile();
						FileWriter fw = new FileWriter(savedFilePath+".json");
						fw.write(json);
						
						fw.close();
						
							textField.setText(null);
							textArea.setText("File " + file + " was saved successfully at " + savedFilePath + ".json" );
							

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else{
					textField.setText(null);
					textArea.setText("File not found, please try another file.");
				}
			  
//				System.out.println(converter.generateJson(file));
			}
		});
		
		btnBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frmExcelToJson);
				
				if(result == JFileChooser.APPROVE_OPTION){
					File selectedFile = fileChooser.getSelectedFile();
					textField.setText(selectedFile.getPath());
					btnConvert.setEnabled(true);
				}
			}
		});
	}
}
