package com.wxp.action;

import org.apache.struts2.ServletActionContext;

import com.wxp.domain.User;

public class Redirectaction {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String execute() {
		// Ϊ�����֣������ں�������_second�����ǣ��Ա���������Action2���յ�������
		user.setUsername(user.getUsername() + "_second");
		user.setPassword(user.getPassword() + "_second");
		System.out.println(user);
		System.out.println("Second User Hashcode = " + user.hashCode());
		// ������ʾ��Ϣ�������ж�redirect��redirectAction��chainת�������Ƿ�ʧ����
		ServletActionContext.getRequest().setAttribute("message", "ע��ɹ���");
		return "success";
	}
}
