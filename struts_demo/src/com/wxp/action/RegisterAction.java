package com.wxp.action;

import org.apache.struts2.ServletActionContext;

import com.wxp.domain.User;

public class RegisterAction {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String execute() {
		// Ϊ�����֣������ں�������_first�����ǣ��Ա���������Action1���յ�������
		user.setUsername(user.getUsername() + "_first");
		user.setPassword(user.getPassword() + "_first");
		System.out.println(user);
		System.out.println("First User Hashcode = " + user.hashCode());
		// ������ʾ��Ϣ�������ж�redirect��redirectAction��chainת�������Ƿ�ʧ����
		ServletActionContext.getRequest().setAttribute("message", "ע��ɹ���");
		return "success";
	}
}
