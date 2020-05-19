package pers.wxp.throwdemo;

import org.junit.Test;

/**
 * @author ���� E-mail:
 * @date ����ʱ�䣺2017��6��13�� ����9:11:08
 * @Description: TODO(������һ�仰��������������)
 * @version 1.0
 * @parameter
 */
public class ThrowDemo extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Test
	public void ThrowDemo1() {
		try {
			throw new Exception("���������˲�");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @date 2017��6��13�� ����9:22:32
	 * @Description: TODO(throws Exception �����׳��쳣)
	 * @Description: TODO(throws Exception ���ô��׳�����)
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public float div(int a, int b) throws Exception {
		try {
			System.out.println("=======���㿪ʼ========");
			float c = a / b;
			return c;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {// finally��return֮ǰִ��
			System.out.println("=======�������========");
		}
	}

	@Test
	public void divDoIt() {
		try {
			System.out.println(div(10, 5));
			System.out.println(div(10, 0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @date 2017��6��13�� ����9:35:49
	 * @Description: TODO(RuntimeException����ѡ���Ƿ����쳣��Exception���봦���쳣)
	 */
	@Test
	public void ThrowDemo2() {
		String str1 = "123";
		int num = Integer.parseInt(str1);
		System.out.println(num * num);

	}

	/**
	 * @date 2017��6��13�� ����9:41:46
	 * @Description: TODO(���������ö���) ѡ��˵���Run ---> Run Configurations...---> ѡ��
	 *               Arguments ѡ� �� VM arguments �ı��������룺 -ea ע�⣺�м�û�пո�������� -da
	 *               ��ʾ��ֹ���ԡ� Ȼ��رոô��ڣ�Ȼ�󱣴�Ϳ����˶��ԡ�
	 */

	@Test
	public void ThrowDemo3() {
		boolean isOpen = false;
		assert isOpen = true; // ��������˶��ԣ��ὫisOpen��ֵ��Ϊtrue
		System.out.println(isOpen);// ��ӡ�Ƿ����˶���
		int x = 10;
		assert x == 30 : "x�Ƿ����30";//
		System.out.println(x);
	}

	/**
	 * @date 2017��6��13�� ����9:56:08
	 * @Description: TODO(�����Զ����쳣)
	 */
	public ThrowDemo(String arg) {
		super(arg);
	}

	@Test
	public void ThrowDemoDoIt() throws Exception {
		throw new ThrowDemo("�׳��쳣");
	}
}
