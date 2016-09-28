package se_project;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;

/*
 * This class handles the encryption window of the gui. It takes the message, key and image as inputs
 * and writes the image to the disk
 */
public class Encryption extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	JLabel lblNewLabel_1 = new JLabel("                      Image");
	JButton btnInputImage = new JButton("Input Image");
	Image img = null;
	String message = null;
	String key = null;
	File file = null;
	JFrame frame = new JFrame();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Encryption frame = new Encryption();
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
	public Encryption() {
		setBackground(Color.RED);
		setTitle("Encryption");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(32, 11, 100, 14);
		contentPane.add(lblMessage);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setDragEnabled(true);
		textArea.setLineWrap(true);
		textArea.setEditable(true);
		//textArea.setBounds(150, 136, 119, 59);
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setBounds(15, 35, 220, 180);
	    contentPane.add(scrollPane);
	   
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setSize(700,400);
	    setVisible(true);
		
		JLabel lblKey = new JLabel("Input Key");
		lblKey.setBounds(67, 293, 111, 30);
		contentPane.add(lblKey);
		
		textField = new JTextField();
		textField.setBounds(32, 245, 166, 45);
		contentPane.add(textField);
		textField.setColumns(10);
		lblNewLabel_1.setBackground(Color.RED);
		
		lblNewLabel_1.setBounds(290, 28, 178, 199);
		contentPane.add(lblNewLabel_1);
		
		btnInputImage.setToolTipText("");
		btnInputImage.setBounds(331, 267, 103, 23);
		contentPane.add(btnInputImage);
		
		btnInputImage.addActionListener(new ActionListener() {

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
	              lblNewLabel_1.setIcon(ResizeImage(path));
	          }
	          
	           //if the user click on save in Jfilechooser


	          else if(result == JFileChooser.CANCEL_OPTION){
	              System.out.println("No File Select");
	          }
	        }
	    });
		
		   getContentPane().setLayout(null);
		
		JButton btnHideMessage = new JButton("Hide message");
		btnHideMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   
				   Embed embed = new Embed();
				   message = textArea.getText();
				   key = textField.getText();
				   if(message.length() == 0 || key.length() == 0 )
				   {
					   if(message.length() == 0)
							   JOptionPane.showMessageDialog(frame,"Invalid message.");
					   if(key.length() == 0)
					    	   JOptionPane.showMessageDialog(frame,"Invalid key.");
				   }
				   else
				   {
					   embed.embedMsg(file,message, key);
					   JOptionPane.showMessageDialog(frame,"Succesfully hidden the message.");
					   //embed.decode();
				   }
				
			}
		});
		btnHideMessage.setBounds(503, 89, 158, 158);
		contentPane.add(btnHideMessage);

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
        Image newImg = img.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
}

