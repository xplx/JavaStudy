package pers.wxp.Serial;

import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import gnu.io.*;

public class ContinueRead extends Thread implements SerialPortEventListener { // SerialPortEventListener
    // ������,�ҵ�����Ƕ�������һ���̼߳�����������
    static CommPortIdentifier portId; // ����ͨ�Ź�����
    static Enumeration<?> portList; // ��Ч�����ϵĶ˿ڵ�ö��
    InputStream inputStream; // �Ӵ�������������
    static OutputStream outputStream;// �򴮿��������
    static SerialPort serialPort; // ���ڵ�����
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(); // ��������������Ŷ���������

    @Override
    /**
     * SerialPort EventListene �ķ���,���������˿����Ƿ���������
     */
    public void serialEvent(SerialPortEvent event) {//

        switch (event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:// ���п�������ʱ��ȡ����
            byte[] readBuffer = new byte[20];
            try {
                int numBytes = -1;
                while (inputStream.available() > 0) {
                    numBytes = inputStream.read(readBuffer);

                    if (numBytes > 0) {
                        msgQueue.add(new Date() + "��ʵ�յ�������Ϊ��-----"
                                + new String(readBuffer));
                        readBuffer = new byte[20];// ���¹��컺����󣬷����п��ܻ�Ӱ����������յ�����
                    } else {
                        msgQueue.add("��------û�ж�������");
                    }
                }
            } catch (IOException e) {
            }
            break;
        }
    }

    /**
     * 
     * ͨ�������COM4���ڣ����ü������Լ���صĲ���
     * 
     * @return ����1 ��ʾ�˿ڴ򿪳ɹ������� 0��ʾ�˿ڴ�ʧ��
     */
    public int startComPort() {
        // ͨ������ͨ�Ź������õ�ǰ�����ϵĴ����б�
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {

            // ��ȡ��Ӧ���ڶ���
            portId = (CommPortIdentifier) portList.nextElement();

            System.out.println("�豸���ͣ�--->" + portId.getPortType());
            System.out.println("�豸���ƣ�---->" + portId.getName());
            // �ж϶˿������Ƿ�Ϊ����
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // �ж����COM4���ڴ��ڣ��ʹ򿪸ô���
                if (portId.getName().equals("COM4")) {
                    try {                        
                        serialPort = (SerialPort) portId.open("COM_4", 2000);// �򿪴�������ΪCOM_4(��������),�ӳ�Ϊ2����
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                        return 0;
                    }                
                    try {
                        inputStream = serialPort.getInputStream();// ���õ�ǰ���ڵ����������
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // ����ǰ�������һ��������
                    try {
                        serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // ���ü�������Ч��������������ʱ֪ͨ
                    serialPort.notifyOnDataAvailable(true);

                    // ���ô��ڵ�һЩ��д����
                    try {
                        // �����ʡ�����λ��ֹͣλ����żУ��λ
                        serialPort.setSerialPortParams(9600,
                                SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                        return 0;
                    }

                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println("--------------�������߳�������--------------");
            while (true) {
                // ������������д������ݾͽ������
                if (msgQueue.size() > 0) {
                    System.out.println(msgQueue.take());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ContinueRead cRead = new ContinueRead();
        int i = cRead.startComPort();
        int warn = 1;
        if (i == 1) {            
         // cRead.start();// �����߳��������յ�������
            try {
				 String st = "����----���";
				 System.out.println("�����ֽ�����" + st.getBytes("gbk").length);
                 outputStream.write(st.getBytes("gbk"), 0,st.getBytes("gbk").length);//���ͺ���
                 outputStream.write(warn);
             //   cRead.stop(); //ֹͣ�߳�              
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	System.out.println("���ڴ�ʧ��");
            return;
        }
    }
}
