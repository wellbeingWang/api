package com.fanmila.util.common;

import com.fanmila.util.framework.MD5Utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * 压缩UUID操作工具类
 * @author caliph
 *
 */
public class UUIDShortUtils {
	private static byte[] getBytes (char[] chars) { 
		   Charset cs = Charset.forName ("UTF-8"); 
		   CharBuffer cb = CharBuffer.allocate (chars.length); 
		   cb.put (chars); 
		                 cb.flip (); 
		   ByteBuffer bb = cs.encode (cb); 
		  
		   return bb.array(); 

		 } 


	private static char[] getChars (byte[] bytes) { 
		      Charset cs = Charset.forName ("UTF-8"); 
		      ByteBuffer bb = ByteBuffer.allocate(bytes.length); 
		      bb.put (bytes); 
		      bb.flip (); 
		       CharBuffer cb = cs.decode (bb); 
		  
		   return cb.array(); 
		} 
	 public static String shorter(String s) {
		 if(s.length()!=32)
		 	s=uuidFromString(MD5Utils.md5(s)).toString();
		 else
			 s=uuidFromString(s).toString();
		 String res=new String(Base64.encodeBase64URLSafe(asBytes(s)));
		 return res.substring(0,res.length()-2);
	 }
	 public static String recover(String s) throws IOException { 
	        int len = s.length(); 
	        char[] chars = new char[len+2]; 
	        chars[len]=chars[len+1]='='; 
	        for(int i=0;i<len;i++){ 
	            chars[i]=s.charAt(i); 
	        } 
	        return toUUID(Base64.decodeBase64URLSaf(chars)).toString().replaceAll("-", "");
	    } 
	 private static byte[] asBytes(String id) { 
	        UUID uuid=UUID.fromString(id); 
	        long msb = uuid.getMostSignificantBits(); 
	        long lsb = uuid.getLeastSignificantBits(); 
	        byte[] buffer = new byte[16]; 

	        for (int i = 0; i < 8; i++) { 
	                buffer[i] = (byte) (msb >>> 8 * (7 - i)); 
	        } 
	        for (int i = 8; i < 16; i++) { 
	                buffer[i] = (byte) (lsb >>> 8 * (7 - i)); 
	        } 
	        return buffer; 

	    } 

	    private static UUID toUUID(byte[] byteArray) { 
	        long msb = 0; 
	        long lsb = 0; 
	        for (int i = 0; i < 8; i++) 
	                msb = (msb << 8) | (byteArray[i] & 0xff); 
	        for (int i = 8; i < 16; i++) 
	                lsb = (lsb << 8) | (byteArray[i] & 0xff); 
	        UUID result = new UUID(msb, lsb); 

	        return result; 
	    } 
	    
	    private static UUID uuidFromString(String uuid){ 
	    	return UUID.fromString(uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5")); 
	    }

	public static void main(String[] args) throws IOException {
	/*    	String s = "a1dfv0HnshHIias38bczCQ";
	    	System.out.println(shortm.recover(s));*/
		UUIDShortUtils shortm = new UUIDShortUtils();

		String uuidStr = "00000000000000000000000000000000";
		System.out.println(uuidStr.length());
		System.out.println(MD5Utils.md5(uuidStr));
		System.out.println(shortm.shorter(uuidStr)+" eee  "+ shortm.recover("XlQyVsSArFd9MPdvkSDrdA"));
		//String uuid =  uuidFromString(uuidStr).toString();
		//System.out.println(uuid + " " + shortm.shorter(uuid) + " " + shortm.recover(shortm.shorter(uuid)));
	}
}
