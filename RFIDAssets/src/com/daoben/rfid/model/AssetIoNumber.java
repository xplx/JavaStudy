package com.daoben.rfid.model;

import java.sql.Timestamp;

public class AssetIoNumber {

	private String everyday_Time;
	private int io_Sum_Count;
	private int warn_Sum_Count;
	private int differ_Sum_Count;

	public AssetIoNumber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssetIoNumber(String everyday_Time, int io_Sum_Count, int warn_Sum_Count, int differ_Sum_Count) {
		super();
		this.everyday_Time = everyday_Time;
		this.io_Sum_Count = io_Sum_Count;
		this.warn_Sum_Count = warn_Sum_Count;
		this.differ_Sum_Count = differ_Sum_Count;
	}

	public String getEveryday_Time() {
		return everyday_Time;
	}

	public void setEveryday_Time(String everyday_Time) {
		this.everyday_Time = everyday_Time;
	}

	public int getIo_Sum_Count() {
		return io_Sum_Count;
	}

	public void setIo_Sum_Count(int io_Sum_Count) {
		this.io_Sum_Count = io_Sum_Count;
	}

	public int getWarn_Sum_Count() {
		return warn_Sum_Count;
	}

	public void setWarn_Sum_Count(int warn_Sum_Count) {
		this.warn_Sum_Count = warn_Sum_Count;
	}

	public int getDiffer_Sum_Count() {
		return differ_Sum_Count;
	}

	public void setDiffer_Sum_Count(int differ_Sum_Count) {
		this.differ_Sum_Count = differ_Sum_Count;
	}

	@Override
	public String toString() {
		return "AssetIoNumber [everyday_Time=" + everyday_Time + ", io_Sum_Count=" + io_Sum_Count + ", warn_Sum_Count="
				+ warn_Sum_Count + ", differ_Sum_Count=" + differ_Sum_Count + "]";
	}
}
