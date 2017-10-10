package com.fanmila.util;

/**
 * 使用DES加密与解密,可对byte[],String类型进行加密与解密 密文可使用String,byte[]存储. 方法: void
 * getKey(String strKey)从strKey的字条生成一个Key String getEncString(String
 * strMing)对strMing进行加密,返回String密文 String getDesString(String
 * strMi)对strMin进行解密,返回String明文 byte[] getEncCode(byte[] byteS)byte[]型的加密 byte[]
 * getDesCode(byte[] byteD)byte[]型的解密
 */
public class Encrypt {
	private static EncryptionDecryption enc =null;
	static{
		try {
			enc =new EncryptionDecryption("fanqianbb");
		} catch (Exception e) {
			// TODOAuto-generated catch block
				e.printStackTrace();
				
		}
	}

	
	public static void main(String[] args) {
		String strKey = "12jdjooaeg;lkjsaodngaodikf;lkdsa";

		char[] strKeyChar = strKey.toCharArray();
		for (char sk : strKeyChar) {

			System.out.println((int)sk);
		}
		
		/*try {

			System.out.println( encString("A10007884") );
			System.out.println( desString("7824f6d57b7a62fba2cde53122850fff1d529b38fa1c152a") );
			

		} catch (Exception e) {
				e.printStackTrace();
				
		}*/
	}
	
	// 加密String明文输入,String密文输出
	public static String encString(String strMing) {
		
		try {
			return enc.encrypt(strMing);
		} catch (Exception e) {
			// TODOAuto-generated catch block
				e.printStackTrace();
				
		}
		return strMing;
	}

	// 解密:以String密文输入,String明文输出
	public static String desString(String strMi) throws Exception {
		return enc.decrypt(strMi);
	}

}