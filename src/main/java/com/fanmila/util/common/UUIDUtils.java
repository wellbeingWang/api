package com.fanmila.util.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class UUIDUtils {

	/**
	 * uuid不一定是16进制数，所以自己实现一个更open点的
	 * 
	 * @param str
	 *            uuid截取部分
	 * @return
	 */
	private static Integer getNumericValue(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		int num = 0;
		int count = 0;
		for (char c : str.toCharArray()) {
			int i = Character.getNumericValue(c);
			num = num + (i << (4 * count++));
		}
		return num;
	}

	/**
	 * <pre>
	 * uuid的后四位转为数字后取模
	 * 转换失败时返回随机数
	 * </pre>
	 * 
	 * @author Larry Lang
	 * @date Apr 10, 2013
	 * 
	 * @param uuid
	 * @return
	 */
	public static int moduuid(String uuid, int start, int end, int modBase) {
		Integer mod = null;
		if (modBase == 1) {
			return 0;
		}

		try {
			Integer num = getNumericValue(StringUtils.substring(uuid, start, end));
			if (num != null) {
				mod = (num % modBase);
			}
		} catch (Exception e) {
		}

		if (mod == null) {
			mod = RandomUtils.nextInt(modBase);
		}

		return mod;
	}
	
	/**
	 * MD5 加密
	 * 
	 * @return
	 */
	public static String md5(String input) throws NoSuchAlgorithmException {
        String result = input;
        if(input != null) {
            MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
            md.update(input.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            if ((result.length() % 2) != 0) {
                result = "0" + result;
            }
        }
        return result;
    }
}
