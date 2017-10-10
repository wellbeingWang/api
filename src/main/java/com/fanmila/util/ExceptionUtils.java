package com.fanmila.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionUtils {
	/**
	 * 未包含cause信息
	 */
	public static String getMessage(Exception ex) {
		StackTraceElement[] msgs = ex.getStackTrace();
		StringBuilder message = new StringBuilder(ex.toString());
		for (StackTraceElement msg : msgs) {
			message.append("\n\tat ").append(msg);
		}
		return message.toString();
	}

	/**
	 * 包含cause信息，性能相对较低
	 * 
	 * @author Larry Lang
	 * @date Oct 11, 2013
	 * 
	 * @param ex
	 * @return
	 */
	public static String getAllMessage(Exception ex) {
		PrintStream printStream = null;
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			printStream = new PrintStream(outputStream);
			ex.printStackTrace(printStream);
			return outputStream.toString();
		} finally {
			if (printStream != null) {
				printStream.close();
			}
		}
	}

	public static void main(String[] args) {

		{
			Exception exception = new Exception("asdfasdf", new Exception("11111111"));
			System.out.println(getAllMessage(exception));
			System.out.println(getMessage(exception));
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Exception exception = new Exception("asdfasdf");
		{
			System.out.println(getMessage(exception));
			long nanoTime = System.nanoTime();
			for (int i = 0; i < 10000; i++) {
				getMessage(exception);
			}
			System.out.println(System.nanoTime() - nanoTime);
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		{
			System.out.println(getAllMessage(exception));
			long nanoTime = System.nanoTime();
			for (int i = 0; i < 10000; i++) {
				getAllMessage(exception);
			}
			System.out.println(System.nanoTime() - nanoTime);
		}
	}
}
