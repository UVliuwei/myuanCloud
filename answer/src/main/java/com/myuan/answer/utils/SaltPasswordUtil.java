package com.myuan.answer.utils;

import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;


public class SaltPasswordUtil {
	
	/**
	 * 重新加密密码
	 * @param password
	 * @return
	 */
	public final static String getNewPassword(String password) {
		
		String salt = password.substring(0,2);
		
		DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512  
		  
		HashRequest request = new HashRequest.Builder()  
		            .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes(password))  
		            .setSalt(ByteSource.Util.bytes(salt)).setIterations(2).build();  
		String newPassword = hashService.computeHash(request).toHex();   
		return newPassword;
	}
}
