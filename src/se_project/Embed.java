/*
 * This class acts as a driver class. It takes the input from the two GUI classes
 * "Encryption" and "Decryption"  and process it.
 * Reference:http://www.dreamincode.net/forums/topic/27950-steganography/
 * https://fivedots.coe.psu.ac.th/~ad/jg/javaArt6/stego.pdf
 * http://www.developer.com/java/ent/article.php/3530866/Steganography-101-using-Java.htm
 */
package se_project;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Embed{
	JFrame frame = new JFrame();
	/*
	 * This method takes image as a file, message and key as a string as inputs. This 
	 * method embeds the message into the image. Before that it encrypts the message 
	 * with the help of "encrypt" class. This method returns none
	 */
	public void embedMsg(File file, String myMessage, String key)
	{
		BufferedImage myImage = null;
		
		try {
			if(file == null)
				JOptionPane.showMessageDialog(frame,"Invalid Image","error",JOptionPane.ERROR_MESSAGE);
			myImage = ImageIO.read(file);
			if(myImage == null)
				JOptionPane.showMessageDialog(frame,"Invalid Image","error",JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame,e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		int msgLngth = myMessage.length();
		while(msgLngth%16!=0)//padding to make the message multiple of 16
		{
			sb.append(" ");
			msgLngth++;
		}
		String padding  = sb.toString();
		myMessage = myMessage + padding;
		Encrypt encrypt = new Encrypt();//message encyrption
		byte [] myMessageAr = encrypt.doEncryption(myMessage,key);
		
		WritableRaster raster = myImage.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		byte [] imageArray = buffer.getData();
		
		int msgLength = myMessage.length();
		System.out.println(msgLength + 32+"   "+ imageArray.length);
		
		
		byte byte3 = (byte)((msgLength & 0xFF000000) >>> 24); //0
		byte byte2 = (byte)((msgLength & 0x00FF0000) >>> 16); //0
		byte byte1 = (byte)((msgLength & 0x0000FF00) >>> 8 ); //0
		byte byte0 = (byte)((msgLength & 0x000000FF)	   );
		byte length[] = {byte3,byte2,byte1,byte0};
		
		imageArray = embedMessage(imageArray, length,0);
		imageArray = embedMessage(imageArray, myMessageAr, 32);
		
		//file.delete();
		try {
			ImageIO.write(myImage,"png",new File((System.getProperty("user.home")), "Desktop\\output.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame,"Cannot Generate the Image.","error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}
	/*
	 * This method takes the key as a string and embedded image as a file
	 */
	String decode(String key, File file)
	{
		
		BufferedImage decodeImage = null;
		try {
			if(file == null)
				JOptionPane.showMessageDialog(frame,"Invalid Image","error",JOptionPane.ERROR_MESSAGE);
			decodeImage = ImageIO.read(file);
			if(decodeImage == null)
				JOptionPane.showMessageDialog(frame,"Invalid Image","error",JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame,"Cannot Generate the Image.","error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		WritableRaster raster = decodeImage.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		byte [] message = buffer.getData();
		
		int length = 0;
		int offset = 32;
		//setting offset
		for(int i=0; i<32; ++i) 
		{
			length = (length << 1) | (message[i] & 1);
		}
		
		byte[] result = new byte[length];
		
		
		for(int b=0; b<result.length; ++b )
		{
			//loop through each bit within a byte of text
			for(int i=0; i<8; ++i, ++offset)
			{
				//assign bit: [(new byte value) << 1] OR [(text byte) AND 1]
				result[b] = (byte)((result[b] << 1) | (message[offset] & 1));
			}
		}
		
		Encrypt decrypt = new Encrypt();
		result = decrypt.doDecryption(result,key);
		String finalMessage = new String(result);
		
		return finalMessage;
	}
	/*
	 * This method embeds the message into the image. It takes image byte array message byte array
	 * and offset integer as parameters
	 */
	//adding text to the image "imageArray"
	private byte[] embedMessage(byte[] image, byte[] addition, int offset)
	{
		if(addition.length + offset > image.length)
		{
			JOptionPane.showMessageDialog(frame,"Message is too lengthy to fit in the Image","error",JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException("File not long enough!");
		}
		for(int i=0; i<addition.length; ++i)
		{
			int add = addition[i];
			for(int bit=7; bit>=0; --bit, ++offset) 
			{
				int b = (add >>> bit) & 1;
				image[offset] = (byte)((image[offset] & 0xFE) | b );
			}
		}
		return image;
	}

}


