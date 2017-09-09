package pers.wxp.encrypt;

/** 
 * @file AES.java 
 * @date 2016��8��5�� 
 * @version 3.4.1 
 * 
 * Copyright (c) 2013 Sihua Tech, Inc. All Rights Reserved. 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
/** 
 *  
 * 
 * @author chengjian.he 
 * @version  3.4, 2016��8��5�� ����11:35:13  
 * @since   Yeexun 3.4 
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

public class AES {

	private Key key;

	/**
	 * ����AES�Գ���Կ
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		SecureRandom random = new SecureRandom();
		keygen.init(random);
		this.key = keygen.generateKey();
	}

	/**
	 * ����
	 * 
	 * @param in
	 * @param out
	 * @throws InvalidKeyException
	 * @throws ShortBufferException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 */
	public void encrypt(InputStream in, OutputStream out)
			throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, IOException {
		this.crypt(in, out, Cipher.ENCRYPT_MODE);
	}

	/**
	 * ����
	 * 
	 * @param in
	 * @param out
	 * @throws InvalidKeyException
	 * @throws ShortBufferException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 */
	public void decrypt(InputStream in, OutputStream out)
			throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, IOException {
		this.crypt(in, out, Cipher.DECRYPT_MODE);
	}

	/**
	 * ʵ�ʵļ��ܽ��ܹ���
	 * 
	 * @param in
	 * @param out
	 * @param mode
	 * @throws IOException
	 * @throws ShortBufferException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public void crypt(InputStream in, OutputStream out, int mode)
			throws IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(mode, this.key);

		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];

		int inLength = 0;
		boolean more = true;
		while (more) {
			inLength = in.read(inBytes);
			if (inLength == blockSize) {
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			} else {
				more = false;
			}
		}
		if (inLength > 0)
			outBytes = cipher.doFinal(inBytes, 0, inLength);
		else
			outBytes = cipher.doFinal();
		out.write(outBytes);
		out.flush();
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}

	public static void main(String[] args) throws Exception {
		AES aes = new AES();
		aes.generateKey();
		File file = new File("D:/aa.jpg");
		FileInputStream in = new FileInputStream(file);
		File file1 = new File("D:/temp/pub.key");
		FileOutputStream out = new FileOutputStream(file1);
		aes.encrypt(in, out);
		aes.decrypt(in, out);
	}

}