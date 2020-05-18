/** 
 * @file RSATool.java 
 * @date 2016��8��5�� 
 * @version 3.4.1 
 * 
 * Copyright (c) 2013 Sihua Tech, Inc. All Rights Reserved. 
 */

package pers.wxp.encrypt;

/** 
 *  
 * 
 * @author chengjian.he 
 * @version  3.4, 2016��8��5�� ����10:51:35  
 * @since   Yeexun 3.4 
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class RSATool {

	public static void makekeyfile(String pubkeyfile, String privatekeyfile)
			throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		// KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// ��ʼ����Կ������������Կ��СΪ1024λ
		keyPairGen.initialize(1024);
		// ����һ����Կ�ԣ�������keyPair��
		KeyPair keyPair = keyPairGen.generateKeyPair();

		// �õ�˽Կ
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		// �õ���Կ
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// ����˽Կ
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(privatekeyfile));
		oos.writeObject(privateKey);
		oos.flush();
		oos.close();

		oos = new ObjectOutputStream(new FileOutputStream(pubkeyfile));
		oos.writeObject(publicKey);
		oos.flush();
		oos.close();

		System.out.println("make file ok!");
	}

	/**
	 * 
	 * @param k
	 * @param data
	 * @param encrypt
	 *            1 ���� 0����
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws Exception
	 */
	public static byte[] handleData(Key k, byte[] data, int encrypt) throws Exception {

		if (k != null) {

			Cipher cipher = Cipher.getInstance("RSA");

			if (encrypt == 1) {
				cipher.init(Cipher.ENCRYPT_MODE, k);
				byte[] resultBytes = cipher.doFinal(data);
				return resultBytes;
			} else if (encrypt == 0) {
				cipher.init(Cipher.DECRYPT_MODE, k);
				byte[] resultBytes = cipher.doFinal(data);
				return resultBytes;
			} else {
				System.out.println("��������Ϊ: 1 ���� 0����");
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		String pubfile = "d:/temp/pub.key";
		String prifile = "d:/temp/pri.key";

		makekeyfile(pubfile, prifile);

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pubfile));
		RSAPublicKey pubkey = (RSAPublicKey) ois.readObject();
		ois.close();

		ois = new ObjectInputStream(new FileInputStream(prifile));
		RSAPrivateKey prikey = (RSAPrivateKey) ois.readObject();
		ois.close();

		// ʹ�ù�Կ����
		String msg = "~O(��_��)O����~";
		String enc = "UTF-8";

		// ʹ�ù�Կ����˽Կ����
		System.out.println("ԭ��: " + msg);
		byte[] result = handleData(pubkey, msg.getBytes(enc), 1);
		System.out.println("����: " + new String(result, enc));
		byte[] deresult = handleData(prikey, result, 0);
		System.out.println("����: " + new String(deresult, enc));

		msg = "����";
		// ʹ��˽Կ���ܹ�Կ����
		System.out.println("ԭ��: " + msg);
		byte[] result2 = handleData(prikey, msg.getBytes(enc), 1);
		System.out.println("����: " + new String(result2, enc));
		byte[] deresult2 = handleData(pubkey, result2, 0);
		System.out.println("����: " + new String(deresult2, enc));

	}
}
