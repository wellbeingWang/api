package com.fanmila.handlers.cps.selfUnion.jd;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.KeySpec;

@SuppressWarnings("restriction")
public class JdCpsDES {
	private static final String ABCDEFGH = "abcdefgh";
	private static final String PUBKEY = "f29fc95a661703f291b3ec132c428c48";
	private static JdCpsDES jdCpsdes=new JdCpsDES();
	private Cipher cipher;
	private BASE64Encoder base64Encoder ;
	
	public static void main(String[] args) {
		String servPubKey =PUBKEY;// "ed077be8aa7abfcba58c5986df97a422"; // 京东提供
		String text = "thi_    is Test { ha{vcxzvcx中共三大范辛苦vzjcxzmf倒萨疯狂举动举出现在巨亏策略选择据可查询jo颇为如额外齐聚出现在了}hh =fds=af=ds=;d<>f,mcxvcxzj4&*(%^%#$^%*&()_+!@#$%^&*()_423ja flkdjaslk; "
				+ "vjcxlkzj}";
		String codedtext = null;
		try {
			codedtext = JdCpsDES.getInstance().encrypt(text); // 此处字符串不变
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" coded text  -->\n" + codedtext); // this is a byte

	}

	
	
	private JdCpsDES(){
		try {
			 base64Encoder = new BASE64Encoder();
			inintCiper();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static JdCpsDES getInstance(){
		if(null == jdCpsdes){
			jdCpsdes=new JdCpsDES();
		}
		
		return jdCpsdes ;
	}
	/**
	 * 初始化加密器
	 * @throws InvalidAlgorithmParameterException 
	 * @throws Exception
	 */
	private void inintCiper() throws  Exception{
		cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		byte[] arrayBytes = PUBKEY.getBytes("UTF8");
		KeySpec ks = new DESedeKeySpec(arrayBytes);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
		SecretKey key = skf.generateSecret(ks);
		IvParameterSpec ips = new IvParameterSpec(ABCDEFGH.getBytes("UTF8"));
		cipher.init(Cipher.ENCRYPT_MODE, key, ips);
	}
	/**
	 * 加密
	 * @param unencryptedString
	 * @return
	 * @throws Exception
	 */
	public  String encrypt(String unencryptedString)throws Exception {
		try {
			byte[] plainText = unencryptedString.getBytes("UTF8");
			byte[] encryptedText = cipher.doFinal(plainText);
			String encode = base64Encoder.encode(encryptedText);
			return encode;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
