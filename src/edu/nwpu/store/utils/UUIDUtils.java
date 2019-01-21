package edu.nwpu.store.utils;

import java.util.UUID;

public class UUIDUtils {
	/**
	 * 随机生成id
	 * @return
	 */
	public static String getId(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	
	public static String getUUID64(){
		return getId()+getId();
	}
	
	/**
	 * 生成随机码
	 * @return
	 */
	public static String getCode(){
		return getUUID64();
	}
	/*
	public static void main(String[] args) {
		System.out.println(getId());
		System.out.println(getUUID64());
		System.out.println(UUID.randomUUID().toString());
	}
	*/
}
