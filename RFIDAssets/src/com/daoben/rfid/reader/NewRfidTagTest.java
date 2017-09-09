package com.daoben.rfid.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.daoben.rfid.service.AssetWarnService;
import com.daoben.rfid.service.IoLibraryService;
import com.daoben.rfid.service.ReadeIoTimeService;
import com.daoben.rfid.utils.ToStringSixteen;

@Service
public class NewRfidTagTest { // 创建类MyTcp
	@Resource
	private ToStringSixteen toStringSixteen;

	@Resource
	private ReaderFunction readerFunction;

	@Resource
	private AssetWarnService asw;

	@Resource
	private IoLibraryService ioLibraryService;

	@Resource
	private ReadeIoTimeService readeIoTimeService;

	@Resource
	private ConnReadermplNew connReadermplNew;

	static private Set<String> setTagId = new HashSet<String>();// 读写器读到数据

	static private Set<String> setTagIdTwo = new HashSet<String>();// 获取读写器二次数据

	static private List<String> listTagDiffer = new ArrayList<String>();// 差异数据记录

	static private int countflag = 0;

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 读取RFID标签休眠时间调整
	 */
	public void delay_ms(int time) {
		try {
			Thread.sleep(time);// s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 进出库判断RFID 215读写器心跳包63
	 */
	public void getRfidInOut(int serverPort) {
		// Create a server socket to accept client connection request
		ServerSocket servSocket = null;
		Socket clientSocket = null;
		InputStream in = null;
		int recvMsgSize = 0;
		byte[] receivBuf = new byte[20];
		try {
			servSocket = new ServerSocket(serverPort);// 创建服务套接字
			while (true) {
				log.info("等待客户端连接");
				clientSocket = servSocket.accept();// 等待客户机连接，创建新的socket和客户端连接
				SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);
				in = clientSocket.getInputStream();
				// OutputStream out = clientSocket.getOutputStream();
				while ((recvMsgSize = in.read(receivBuf)) != -1) {
					// String recString = new String(receivBuf, "ISO-8859-1");
					// System.out.println("显示字符串"+recString);// 获取收集数据
					String recString = toStringSixteen.ByteToStriong(receivBuf);// 接收16进制数据
					String rfidheart = recString.substring(8, 10);// 获取读写器心跳包时间
					if (rfidheart.equals("63")) {
						//log.warn("215心跳包" + rfidheart);
						clientSocket.setSoTimeout(60000);// 设置超时时就断开连接
					}
					if (recvMsgSize == 20) {
						String firstFlag = recString.substring(0, 4);// 获取TCP首标志
						String finalFlag = recString.substring(36, 40);// 获取TCP尾标志
						String Flag = firstFlag.concat(finalFlag); // 整合首尾标志
						if (Flag.equals("7f7f7f7f")) {
							/******** 获取标签ID号 ********/
							String Htag_id = recString.substring(20, 22);// 标签id高字节
							String Ltag_id = recString.substring(18, 20);// 标签id低字节
							String Hexttag_id = Htag_id.concat(Ltag_id); // 整合标签号
							int tag_id = Integer.parseInt(Hexttag_id, 16);// 获取标签ID号
							// log.info("8082标签Tag_Id：" + tag_id);
							setTagId.add(tag_id + "");// list获取标签id
						}
					}
					// out.write(receivBuf, 0, recvMsgSize);//回写数据
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.warn("8082读写器连接异常，从新连接");
		} finally {
			try {
				// 关闭资源
				if (servSocket != null) {
					servSocket.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 进出库判断216RFID 216读写器心跳包7f7f0001
	 */
	public void getRfidInOutA(int serverPort) {
		// Create a server socket to accept client connection request
		ServerSocket servSocket = null;
		Socket clientSocket = null;
		InputStream in = null;
		int recvMsgSize = 0;
		byte[] receivBuf = new byte[20];
		try {
			servSocket = new ServerSocket(serverPort);// 创建服务套接字
			while (true) {
				log.info("等待客户端连接");
				clientSocket = servSocket.accept();// 等待客户机连接，创建新的socket和客户端连接
				SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);
				in = clientSocket.getInputStream();
				// OutputStream out = clientSocket.getOutputStream();
				while ((recvMsgSize = in.read(receivBuf)) != -1) {
					// String recString = new String(receivBuf, "ISO-8859-1");
					// System.out.println("显示字符串"+recString);// 获取收集数据
					String recString = toStringSixteen.ByteToStriong(receivBuf);// 接收16进制数据
					String rfidheart = recString.substring(0, 8);// 获取读写器心跳包时间
					if (rfidheart.equals("7f7f0001")) {// 216读写器
						clientSocket.setSoTimeout(60000);// 设置超时时就断开连接
					}
					if (recvMsgSize == 20) {
						String firstFlag = recString.substring(0, 4);// 获取TCP首标志
						String finalFlag = recString.substring(36, 40);// 获取TCP尾标志
						String Flag = firstFlag.concat(finalFlag); // 整合首尾标志
						if (Flag.equals("7f7f7f7f")) {
							/******** 获取标签ID号 ********/
							String Htag_id = recString.substring(20, 22);// 标签id高字节
							String Ltag_id = recString.substring(18, 20);// 标签id低字节
							String Hexttag_id = Htag_id.concat(Ltag_id); // 整合标签号
							int tag_id = Integer.parseInt(Hexttag_id, 16);// 获取标签ID号
							// log.info("8083标签Tag_Id：" + tag_id);
							setTagId.add(tag_id + "");// list获取标签id
						}
					}
					// out.write(receivBuf, 0, recvMsgSize);//回写数据
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.warn("8083读写器连接异常，从新连接");
		} finally {
			try {
				// 关闭资源
				if (servSocket != null) {
					servSocket.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 进出库判断216RFID 216读写器心跳包7f7f0001
	 */
	public void getRfidInOutB(int serverPort) {
		// Create a server socket to accept client connection request
		ServerSocket servSocket = null;
		Socket clientSocket = null;
		InputStream in = null;
		int recvMsgSize = 0;
		byte[] receivBuf = new byte[20];
		try {
			servSocket = new ServerSocket(serverPort);// 创建服务套接字
			while (true) {
				log.info("等待客户端连接");
				clientSocket = servSocket.accept();// 等待客户机连接，创建新的socket和客户端连接
				SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);
				in = clientSocket.getInputStream();
				// OutputStream out = clientSocket.getOutputStream();
				while ((recvMsgSize = in.read(receivBuf)) != -1) {
					// String recString = new String(receivBuf, "ISO-8859-1");
					// System.out.println("显示字符串"+recString);// 获取收集数据
					String recString = toStringSixteen.ByteToStriong(receivBuf);// 接收16进制数据
					String rfidheart = recString.substring(0, 8);// 获取读写器心跳包时间
					if (rfidheart.equals("7f7f0001")) {// 216读写器
						clientSocket.setSoTimeout(60000);// 设置超时时就断开连接
					}
					if (recvMsgSize == 20) {
						String firstFlag = recString.substring(0, 4);// 获取TCP首标志
						String finalFlag = recString.substring(36, 40);// 获取TCP尾标志
						String Flag = firstFlag.concat(finalFlag); // 整合首尾标志
						if (Flag.equals("7f7f7f7f")) {
							/******** 获取标签ID号 ********/
							String Htag_id = recString.substring(20, 22);// 标签id高字节
							String Ltag_id = recString.substring(18, 20);// 标签id低字节
							String Hexttag_id = Htag_id.concat(Ltag_id); // 整合标签号
							int tag_id = Integer.parseInt(Hexttag_id, 16);// 获取标签ID号
							// log.info("8083标签Tag_Id：" + tag_id);
							setTagId.add(tag_id + "");// list获取标签id
						}
					}
					// out.write(receivBuf, 0, recvMsgSize);//回写数据
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.warn("8083读写器连接异常，从新连接");
		} finally {
			try {
				// 关闭资源
				if (servSocket != null) {
					servSocket.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateRfidWarnInfo() {
		while (true) {
			delay_ms(4000);
			int counttag = setTagId.size();
			log.warn("读写器读读完标签：" + counttag);
			setTagId.clear();
			if (counttag >= 70) {
				log.warn("读写器读读完标签：" + counttag);
				setTagId.clear();

			}
		}
	}

	public List<String> getDiffrent(Set<String> set1, Set<String> set2) {
		for (String str : set1) {
			if (!set2.contains(str)) {
				listTagDiffer.add(str);// 读写器读到数据多出的数据
			}
		}
		countflag++;
		if (countflag == 2) {
			setTagIdTwo.clear();// 将第二次清空
			setTagIdTwo.addAll(setTagId);// 将第一次的值复制给第二次
			setTagId.clear();// 将第一次清空
			countflag = 0;
		}
		return listTagDiffer;
	}
}
