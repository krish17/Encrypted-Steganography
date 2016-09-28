package se_project;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;

/*
 * This class handles the decryption window of the gui. this class is used
 * to input image and key and show the output message
 */
public class Decryption extends JFrame{
	private JPanel contentPane;
	private JTextField textField;
	JLabel lblImage = new JLabel("                    Image");
	JButton btnUploadImage = new JButton("Upload Image");
	Image img = null;
	File file =null;
	String key = null;
	String message = null;
	JFrame frame = new JFrame();
	Embed embed = new Embed();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Decryption frame = new Decryption();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Decryption() {
		setTitle("Decryption");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 493, 311);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUploadStegoimage = new JLabel("Upload Stegoimage");
		lblUploadStegoimage.setBounds(41, 11, 112, 14);
		contentPane.add(lblUploadStegoimage);
		
		JLabel lblInputKey = new JLabel("Input key");
		lblInputKey.setBounds(53, 236, 122, 25);
		contentPane.add(lblInputKey);
		
		textField = new JTextField();
		textField.setBounds(21, 205, 196, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setDragEnabled(true);
		textArea.setLineWrap(true);
		textArea.setEditable(true);
		//textArea.setBounds(150, 136, 119, 59);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(275, 30, 150, 150);
		contentPane.add(scrollPane);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				key = textField.getText();
				message = embed.decode(key,file);
				textArea.setText(message);
				   if(message.length() == 0 || key.length() == 0 )
				   {
					   if(message.length() == 0)
							   JOptionPane.showMessageDialog(frame,"Invalid message.");
					   if(key.length() == 0)
					    	   JOptionPane.showMessageDialog(frame,"Invalid key.");
					   
				   }
				JFrame frame = new JFrame();
				   JOptionPane.showMessageDialog(frame,"Succesfully disclosed the message.");
				
			}
		});
		
		btnDecrypt.setBounds(275, 193, 150, 28);
		contentPane.add(btnDecrypt);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(317, 11, 89, 14);
		contentPane.add(lblMessage);

		lblImage.setBounds(20, 36, 169, 115);
		contentPane.add(lblImage);
				
		btnUploadImage.setBounds(53, 162, 112, 23);
		contentPane.add(btnUploadImage);
		
		btnUploadImage.addActionListener(new ActionListener() {
			//used to navigate the menu to fetch the image
	        public void actionPerformed(ActionEvent e) {
	        
	          JFileChooser file = new JFileChooser();
	          file.setCurrentDirectory(new File(System.getProperty("user.home")));
	          //filter the files
	          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
	          file.addChoosableFileFilter(filter);
	          int result = file.showSaveDialog(null);
	           //if the user click on save in Jfilechooser
	          if(result == JFileChooser.APPROVE_OPTION){
	              File selectedFile = file.getSelectedFile();
	              String path = selectedFile.getAbsolutePath();
	              lblImage.setIcon(ResizeImage(path));
	          }
	          
	           //if the user click on save in Jfilechooser

	          else if(result == JFileChooser.CANCEL_OPTION){
	              System.out.println("No File Select");
	          }
	        }
	    });
		
		  getContentPane().setLayout(null);
		  

	}
	
	public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        img = MyImage.getImage();
        file = new File(ImagePath);
        try {
			ImageIO.read(file);
			if(!file.exists() || file.length() == 0)
				   JOptionPane.showMessageDialog(frame,"Invalid file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
        Image newImg = img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
}
