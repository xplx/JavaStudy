package pers.wxp.gather;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class ListDemo {

	/**
	 * ��Ҫ�� foreach ѭ�������Ԫ�ص� remove/add ������ remove Ԫ����ʹ�� Iterator��ʽ��
	 */
	@Test
	public void removeDemo() {
		List<String> a = new ArrayList<String>();
		a.add("1");
		a.add("2");

//		for (String temp : a) {
//			if ("2".equals(temp)) {// ����ʹ��equalsʱ��ʹ����֪��ֵȥ���δ֪��ֵ����ó��ִ���
//				a.remove(temp);
//			}
//		}
//		System.out.println(a);
		
		Iterator<String> it = a.iterator();
		while (it.hasNext()) {
			String temp = it.next();
			if (temp == "2") {
				it.remove();
			}
		}
		System.out.println(a);
	}

}
