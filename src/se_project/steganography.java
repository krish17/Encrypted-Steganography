package se_project;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * This class starts the application. It shos two windows "Encrypt" and "Decrypt" 
 */
public class steganography {

	JFrame frmSteganography;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					steganography window = new steganography();
					window.frmSteganography.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public steganography() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSteganography = new JFrame();
		frmSteganography.setTitle("Steganography");
		frmSteganography.setBounds(100, 100, 450, 300);
		frmSteganography.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSteganography.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Encrypt");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmSteganography.dispose();
				Encryption encrypt = new Encryption();
				encrypt.setVisible(true);
			}
		});
		btnNewButton.setBounds(75, 79, 89, 86);
		frmSteganography.getContentPane().add(btnNewButton);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSteganography.dispose();
				Decryption decrypt = new Decryption();
				decrypt.setVisible(true);
			}
		});
		btnDecrypt.setBounds(280, 79, 89, 83);
		frmSteganography.getContentPane().add(btnDecrypt);
		
	}
	
}
