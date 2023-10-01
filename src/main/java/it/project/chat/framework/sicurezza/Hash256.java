package it.project.chat.framework.sicurezza;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import it.project.chat.framework.data.BusinessException;

public class Hash256 {
	
	public Hash256() {
		// TODO Auto-generated constructor stub
	}

	public String getSHA256SecurePassword(String passwordToHash, String salt) throws BusinessException{
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(salt.getBytes(StandardCharsets.UTF_8));
	        byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	    	throw new BusinessException();
	    }
	}
	
}
