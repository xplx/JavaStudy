package com.daoben.rfid.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.daoben.rfid.mapper.AssetDeviceInfoMapper;
import com.daoben.rfid.model.AssetDeviceInfo;
import com.daoben.rfid.utils.AcquireTimeStamp;

/**
 * @author wxp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml" })
public class AssetDeviceInfoTest {

	@Resource
	private AssetDeviceInfoMapper aInfoMapper;

	@Resource
	private AcquireTimeStamp ats;

	@Test
	public void selectAllDeviceInfoT() throws Exception {

		System.out.println(aInfoMapper.selectAllDeviceInfo());
	}
	
	@Test
	public void selectByDeviceHandle() throws Exception {

		System.out.println(aInfoMapper.selectByDeviceHandle("192.168.0.217"));
	}
	
	@Test
	public void selectByDeviceRfidIp() throws Exception {

		System.out.println(aInfoMapper.selectByRfidIp("192.168.0.217"));
	}


	@Test
	public void updateByDeviceStateT() throws Exception {

		AssetDeviceInfo aInfo = new AssetDeviceInfo();// 不能将模型注入
		aInfo.setRfid_Ip("192.168.0.2");
		aInfo.setRegister_Time(ats.getTimeStamp());
		aInfo.setDevice_Handle(444);
		System.out.println(aInfoMapper.updatePartDeviceState(aInfo));
	}

	@Test
	public void updatePartDeviceStateT() throws Exception {
		AssetDeviceInfo aInfo = new AssetDeviceInfo();// 不能将模型注入
		aInfo.setRfid_Ip("192.168.0.217");
		aInfo.setDevice_State(0);
		aInfo.setRegister_Time(ats.getTimeStamp());
		System.out.println(aInfoMapper.updatePartDeviceState(aInfo));
	}
}