package se_project;

/*
 * This class handles the encryption
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Encrypt {
	JFrame frame = new JFrame();
	
	/*
	 * This method does encryption using AES algorithm. This method takes message and key
	 * as strings and returns the encrypted message in a byte array 
	 */
	public byte[] doEncryption(String message,String key)
	{
		Cipher cipher = null;
		Key secretKey = null;
		secretKey = new SecretKeySpec(key.getBytes(),"AES");
			try {
				cipher = Cipher.getInstance("AES/CBC/NoPadding");
			} catch (NoSuchAlgorithmException e) {
				JOptionPane.showMessageDialog(frame,"Problem with algorithm","error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.out.println("problem with algorithm");
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//System.out.println("problem with padding");
			}
			 try {
		    	   byte[] ivByte = new byte[cipher.getBlockSize()];
		    	   IvParameterSpec ivParamsSpec = new IvParameterSpec(ivByte);
					try {
						cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivParamsSpec);
					} catch (InvalidAlgorithmParameterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InvalidKeyException e) {
					JOptionPane.showMessageDialog(frame,"Invalid Key","error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					//System.out.println("problem with key");
				}
		       byte[] encryptedMessage = null;
		       
				try {
					encryptedMessage = cipher.doFinal(message.getBytes());
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Encrypted message: " +new String(encryptedMessage));
				
				return (encryptedMessage);
	}
	/*
	 * This method does decryption and takes message and key as parameters and returns byte array 
	 * decrypted message array
	 */
	
	public byte[] doDecryption(byte[] message, String key)
	{
		//byte [] message1 = message.getBytes();
		//String key = "1234567812345678";
		Key secretKey = null;
		if(key.length()==0)//checks for key length of zero
			JOptionPane.showMessageDialog(frame,"Invalid Key","error",JOptionPane.ERROR_MESSAGE);
		secretKey = new SecretKeySpec(key.getBytes(),"AES");
		byte[] decrypted = null;
 	   	Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(frame,"Problem with algorithm","error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			//System.out.println("problem with algorithm");
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("problem with padding");
		}
	       try {
	    	   byte[] ivByte = new byte[cipher.getBlockSize()];
	    	   IvParameterSpec ivParamsSpec = new IvParameterSpec(ivByte);
				try {
					cipher.init(Cipher.DECRYPT_MODE, secretKey,ivParamsSpec);
				} catch (InvalidAlgorithmParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				decrypted = cipher.doFinal(message);
				//return(cipher.doFinal(message1);
			} catch (InvalidKeyException e) {
				JOptionPane.showMessageDialog(frame,"Invalid Key","error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
	       	catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       return (decrypted);
	}
}
