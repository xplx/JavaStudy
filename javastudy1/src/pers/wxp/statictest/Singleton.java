package pers.wxp.statictest;

/**
 * @author ���� E-mail:
 * @date ����ʱ�䣺2017��6��12�� ����1:36:09
 * @Description: TODO(���õ���ģʽ)
 * @version 1.0
 * @parameter
 */
public class Singleton {

	/**
	 * ����Ψһʵ��������
	 */
	final private static Singleton instance = new Singleton();

	/**
	 * @date 2017��6��12�� ����1:38:45
	 * @Description: TODO(����ģʽ��������һ��˽�л��Ĺ��캯��)
	 */
	private void Singleton() {

	}

	/**
	 * @date 2017��6��12�� ����1:51:29
	 * @Description: TODO(��ȡʵ��������)
	 * @return
	 */
	public static Singleton getInstance() {
		return instance;
	}

	public void printf() {
		System.out.println("hello word");
	}
}

/**
 * @author wuxiaopeng
 * @date 2017��6��12�� ����1:52:07
 * @Description: TODO(ʵ��������ģʽ����)
 * 
 */
class DemoTest {
	public static void main(String[] args) {
		while (true) {
			Singleton singleton = null;
			singleton = Singleton.getInstance();
			singleton.printf();
		}
	}
}
