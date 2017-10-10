/*
 * LZString4Java By Rufus Huang 
 * https://github.com/rufushuang/lz-string4java
 * MIT License
 * 
 * Port from original JavaScript version by pieroxy 
 * https://github.com/pieroxy/lz-string
 */

package com.fanmila.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LZStringUtil {

	private static char[] keyStrBase64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
	private static char[] keyStrUriSafe = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-$".toCharArray();
	private static HashMap<char[], HashMap<Character, Integer>> baseReverseDic = new HashMap<char[], HashMap<Character, Integer>>();

	private static char getBaseValue(char[] alphabet, Character character) {
		HashMap<Character, Integer> map = baseReverseDic.get(alphabet);
		if (map == null) {
			map = new HashMap<Character, Integer>();
			baseReverseDic.put(alphabet, map);
			for (int i = 0; i < alphabet.length; i++) {
				map.put(alphabet[i], i);
			}
		}
		return (char) map.get(character).intValue();
	}
	
	public static String compressToBase64(String input) {
		if (input == null)
			return "";
		String res = LZStringUtil._compress(input, 6, new CompressFunctionWrapper() {
			@Override
			public Character doFunc(int a) {
				return keyStrBase64[a];
			}
		});
		switch (res.length() % 4) { // To produce valid Base64
		default: // When could this happen ?
		case 0:
			return res;
		case 1:
			return res + "===";
		case 2:
			return res + "==";
		case 3:
			return res + "=";
		}
	}
	
	public static String decompressFromBase64(String inputStr) {
		if (inputStr == null)
			return "";
		if (inputStr == "")
			return null;
		final char[] input = inputStr.toCharArray();
		// function(index) { return getBaseValue(keyStrBase64,
		// input.charAt(index)); }
		return LZStringUtil._decompress(input.length, 32, new DecompressFunctionWrapper() {
			@Override
			public Character doFunc(int index) {
				return getBaseValue(keyStrBase64, input[index]);
			}
		});
	}	

	public static String compressToUTF16(String input) {
		if (input == null)
			return "";
		return LZStringUtil._compress(input, 15, new CompressFunctionWrapper() {
			@Override
			public Character doFunc(int a) {
				return fc(a + 32);
			}
		}) + " ";
	}

	public static String decompressFromUTF16(String compressedStr) {
		if (compressedStr == null)
			return "";
		if (compressedStr == "")
			return null;
		final char[] compressed = compressedStr.toCharArray();
		return LZStringUtil._decompress(compressed.length, 16384, new DecompressFunctionWrapper() {
			@Override
			public Character doFunc(int index) {
				return (char) (compressed[index] - 32);
			}
		});
	}
	
	//TODO: java has no Uint8Array type, what can we do?
	
	public static String compressToEncodedURIComponent(String input) {
		if (input == null)
			return "";
		return LZStringUtil._compress(input, 6, new CompressFunctionWrapper() {
			@Override
			public Character doFunc(int a) {
				return keyStrUriSafe[a];
			}
		}) + " ";
	}

	public static String decompressFromEncodedURIComponent(String inputStr) {
	    if (inputStr == null) return "";
	    if (inputStr == "") return null;
	    inputStr = inputStr.replace(' ', '+');
	    final char[] input = inputStr.toCharArray();
		return LZStringUtil._decompress(input.length, 32, new DecompressFunctionWrapper() {
			@Override
			public Character doFunc(int index) {
				return getBaseValue(keyStrUriSafe, input[index]);
			}
		});
	}
	
	protected static class Data {
		int val;
		int position;
		StringBuffer string = new StringBuffer();
	}
	
	private static abstract class CompressFunctionWrapper {
		public abstract Character doFunc(int i);
	}

	public static String compress(String uncompressed) {
		return LZStringUtil._compress(uncompressed, 16, new CompressFunctionWrapper() {
			@Override
			public Character doFunc(int a) {
				return fc(a);
			}
		});
	}
	private static String _compress(String uncompressedStr, int bitsPerChar, CompressFunctionWrapper getCharFromInt) {
	    if (uncompressedStr == null) return "";
		int i, value;
		HashMap<String, Integer> context_dictionary = new HashMap<String, Integer>();
		HashSet<String> context_dictionaryToCreate = new HashSet<String>();
		String context_c = "";
		String context_wc = "";
		String context_w = "";
		double context_enlargeIn = 2d; // Compensate for the first entry which should not count
		int context_dictSize = 3;
		int context_numBits = 2;
		ArrayList<Character> context_data = new ArrayList<Character>(uncompressedStr.length() / 3);
		int context_data_val = 0;
		int context_data_position = 0;
		int ii;
		
		char[] uncompressed = uncompressedStr.toCharArray();
		for (ii = 0; ii < uncompressed.length; ii += 1) {
			context_c = String.valueOf(uncompressed[ii]);
			if (!context_dictionary.containsKey(context_c)) {
				context_dictionary.put(context_c, context_dictSize++);
				context_dictionaryToCreate.add(context_c);
			}

			context_wc = context_w + context_c;
			if (context_dictionary.containsKey(context_wc)) {
				context_w = context_wc;
			} else {
				if (context_dictionaryToCreate.contains(context_w)) {
					if (context_w.charAt(0) < 256) {
						for (i = 0; i < context_numBits; i++) {
							context_data_val = (context_data_val << 1);
							if (context_data_position == bitsPerChar - 1) {
								context_data_position = 0;
								context_data.add(getCharFromInt.doFunc(context_data_val));
								context_data_val = 0;
							} else {
								context_data_position++;
							}
						}
						value = context_w.charAt(0);
						for (i = 0; i < 8; i++) {
							context_data_val = (context_data_val << 1) | (value & 1);
							if (context_data_position == bitsPerChar - 1) {
								context_data_position = 0;
								context_data.add(getCharFromInt.doFunc(context_data_val));
								context_data_val = 0;
							} else {
								context_data_position++;
							}
							value = value >> 1;
						}
					} else {
						value = 1;
						for (i = 0; i < context_numBits; i++) {
							context_data_val = (context_data_val << 1) | value;
							if (context_data_position == bitsPerChar - 1) {
								context_data_position = 0;
								context_data.add(getCharFromInt.doFunc(context_data_val));
								context_data_val = 0;
							} else {
								context_data_position++;
							}
							value = 0;
						}
						value = context_w.charAt(0);
						for (i = 0; i < 16; i++) {
							context_data_val = (context_data_val << 1) | (value & 1);
							if (context_data_position == bitsPerChar - 1) {
								context_data_position = 0;
								context_data.add(getCharFromInt.doFunc(context_data_val));
								context_data_val = 0;
							} else {
								context_data_position++;
							}
							value = value >> 1;
						}
					}
					context_enlargeIn--;
					if (context_enlargeIn == 0) {
						context_enlargeIn = Math.pow(2, context_numBits);
						context_numBits++;
					}
					context_dictionaryToCreate.remove(context_w);
				} else {
					value = context_dictionary.get(context_w);
					for (i = 0; i < context_numBits; i++) {
						context_data_val = (context_data_val << 1) | (value & 1);
						if (context_data_position == bitsPerChar - 1) {
							context_data_position = 0;
							context_data.add(getCharFromInt.doFunc(context_data_val));
							context_data_val = 0;
						} else {
							context_data_position++;
						}
						value = value >> 1;
					}

				}
				context_enlargeIn--;
				if (context_enlargeIn == 0) {
					context_enlargeIn = Math.pow(2, context_numBits);
					context_numBits++;
				}
				// Add wc to the dictionary.
				context_dictionary.put(context_wc, context_dictSize++);
				context_w = context_c;
			}
		}
		
		// Output the code for w.
		if (!context_w.isEmpty()) {
			if (context_dictionaryToCreate.contains(context_w)) {
				if (context_w.charAt(0) < 256) {
					for (i = 0; i < context_numBits; i++) {
						context_data_val = (context_data_val << 1);
						if (context_data_position == bitsPerChar - 1) {
							context_data_position = 0;
							context_data.add(getCharFromInt.doFunc(context_data_val));
							context_data_val = 0;
						} else {
							context_data_position++;
						}
					}
					value = context_w.charAt(0);
					for (i = 0; i < 8; i++) {
						context_data_val = (context_data_val << 1) | (value & 1);
						if (context_data_position == bitsPerChar - 1) {
							context_data_position = 0;
							context_data.add(getCharFromInt.doFunc(context_data_val));
							context_data_val = 0;
						} else {
							context_data_position++;
						}
						value = value >> 1;
					}
				} else {
					value = 1;
					for (i = 0; i < context_numBits; i++) {
						context_data_val = (context_data_val << 1) | value;
						if (context_data_position == bitsPerChar - 1) {
							context_data_position = 0;
							context_data.add(getCharFromInt.doFunc(context_data_val));
							context_data_val = 0;
						} else {
							context_data_position++;
						}
						value = 0;
					}
					value = context_w.charAt(0);
					for (i = 0; i < 16; i++) {
						context_data_val = (context_data_val << 1) | (value & 1);
						if (context_data_position == bitsPerChar - 1) {
							context_data_position = 0;
							context_data.add(getCharFromInt.doFunc(context_data_val));
							context_data_val = 0;
						} else {
							context_data_position++;
						}
						value = value >> 1;
					}
				}
				context_enlargeIn--;
				if (context_enlargeIn == 0) {
					context_enlargeIn = Math.pow(2, context_numBits);
					context_numBits++;
				}
				context_dictionaryToCreate.remove(context_w);
			} else {
				value = context_dictionary.get(context_w);
				for (i = 0; i < context_numBits; i++) {
					context_data_val = (context_data_val << 1) | (value & 1);
					if (context_data_position == bitsPerChar - 1) {
						context_data_position = 0;
						context_data.add(getCharFromInt.doFunc(context_data_val));
						context_data_val = 0;
					} else {
						context_data_position++;
					}
					value = value >> 1;
				}

			}
			context_enlargeIn--;
			if (context_enlargeIn == 0) {
				context_enlargeIn = Math.pow(2, context_numBits);
				context_numBits++;
			}
		}

		// Mark the end of the stream
		value = 2;
		for (i = 0; i < context_numBits; i++) {
			context_data_val = (context_data_val << 1) | (value & 1);
			if (context_data_position == bitsPerChar - 1) {
				context_data_position = 0;
				context_data.add(getCharFromInt.doFunc(context_data_val));
				context_data_val = 0;
			} else {
				context_data_position++;
			}
			value = value >> 1;
		}

		// Flush the last char
		while (true) {
			context_data_val = (context_data_val << 1);
			if (context_data_position == bitsPerChar - 1) {
				context_data.add(getCharFromInt.doFunc(context_data_val));
				break;
			}
			else
				context_data_position++;
		}
		StringBuffer sb = new StringBuffer(context_data.size());
		for (char c : context_data)
			sb.append(c);
		return sb.toString();
	}
	
	private static abstract class DecompressFunctionWrapper {
		public abstract Character doFunc(int i);
	}
	protected static class DecData {
		public char val;
		public int position;
		public int index;		
	}

	public static String f(int i) {
		return String.valueOf((char) i);
	}
	public static Character fc(int i) {
		return Character.valueOf((char) i);
	}

	public static String decompress(final String compressed) {
		if (compressed == null)
			return "";
		if (compressed == "")
			return null;
		return LZStringUtil._decompress(compressed.length(), 32768, new DecompressFunctionWrapper() {
			char[] compChars = compressed.toCharArray();

			@Override
			public Character doFunc(int i) {
				return compChars[i];
			}
		});
	}
	private static String _decompress(int length, int resetValue, DecompressFunctionWrapper getNextValue) {
		ArrayList<String> dictionary = new ArrayList<String>();
		// TODO: is next an unused variable in original lz-string?
		@SuppressWarnings("unused")
		int next;
		double enlargeIn = 4d;
		int dictSize = 4;
		int numBits = 3;
		String entry = "";
		ArrayList<String> result = new ArrayList<String>();
		String w;
		int bits, resb; int maxpower, power;
		String c = null;
		DecData data = new DecData();
		data.val = getNextValue.doFunc(0);
		data.position = resetValue;
		data.index = 1;
		
		for (int i = 0; i < 3; i += 1) {
			dictionary.add(i, f(i));
		}
		
		bits = 0;
		maxpower = (int) Math.pow(2, 2);
		power = 1;
		while (power != maxpower) {
			resb = data.val & data.position;
			data.position >>= 1;
			if (data.position == 0) {
				data.position = resetValue;
				data.val = getNextValue.doFunc(data.index++);
			}
			bits |= (resb > 0 ? 1 : 0) * power;
			power <<= 1;
		}
		
	    switch (next = bits) {
	      case 0:
	          bits = 0;
	          maxpower = (int) Math.pow(2,8);
	          power=1;
	          while (power != maxpower) {
	            resb = data.val & data.position;
	            data.position >>= 1;
	            if (data.position == 0) {
	              data.position = resetValue;
	              data.val = getNextValue.doFunc(data.index++);
	            }
	            bits |= (resb>0 ? 1 : 0) * power;
	            power <<= 1;
	          }
	        c = f(bits);
	        break;
	      case 1:
	          bits = 0;
	          maxpower = (int) Math.pow(2,16);
	          power=1;
	          while (power!=maxpower) {
	            resb = data.val & data.position;
	            data.position >>= 1;
	            if (data.position == 0) {
	              data.position = resetValue;
	              data.val = getNextValue.doFunc(data.index++);
	            }
	            bits |= (resb>0 ? 1 : 0) * power;
	            power <<= 1;
	          }
	        c = f(bits);
	        break;
	      case 2:
	        return "";
	    }
	    dictionary.add(3, c);
	    w = c;
		result.add(w);
	    while (true) {
	        if (data.index > length) {
	          return "";
	        }

	        bits = 0;
	        maxpower = (int) Math.pow(2,numBits);
	        power=1;
	        while (power!=maxpower) {
	          resb = data.val & data.position;
	          data.position >>= 1;
	          if (data.position == 0) {
	            data.position = resetValue;
	            data.val = getNextValue.doFunc(data.index++);
	          }
	          bits |= (resb>0 ? 1 : 0) * power;
	          power <<= 1;
	        }
	        // TODO: very strange here, c above is as char/string, here further is a int, rename "c" in the switch as "cc"
	        int cc;
	        switch (cc = bits) {
	          case 0:
	            bits = 0;
	            maxpower = (int) Math.pow(2,8);
	            power=1;
	            while (power!=maxpower) {
	              resb = data.val & data.position;
	              data.position >>= 1;
	              if (data.position == 0) {
	                data.position = resetValue;
	                data.val = getNextValue.doFunc(data.index++);
	              }
	              bits |= (resb>0 ? 1 : 0) * power;
	              power <<= 1;
	            }

	            dictionary.add(dictSize++, f(bits));
	            cc = dictSize-1;
	            enlargeIn--;
	            break;
	          case 1:
	            bits = 0;
	            maxpower = (int) Math.pow(2,16);
	            power=1;
	            while (power!=maxpower) {
	              resb = data.val & data.position;
	              data.position >>= 1;
	              if (data.position == 0) {
	                data.position = resetValue;
	                data.val = getNextValue.doFunc(data.index++);
	              }
	              bits |= (resb>0 ? 1 : 0) * power;
	              power <<= 1;
	            }
	            dictionary.add(dictSize++, f(bits));
	            cc = dictSize-1;
	            enlargeIn--;
	            break;
	          case 2:
	        	StringBuffer sb = new StringBuffer(result.size());
	        	for (String s : result)
	        		sb.append(s);
	            return sb.toString();
	        }

	        if (enlargeIn == 0) {
	          enlargeIn = Math.pow(2, numBits);
	          numBits++;
	        }

	        if (cc < dictionary.size() && dictionary.get(cc) != null) {
	        	entry = dictionary.get(cc);
	        } else {
	          if (cc == dictSize) {
	            entry = w + w.charAt(0);
	          } else {
	            return null;
	          }
	        }
	        result.add(entry);

	        // Add w+entry[0] to the dictionary.
	        dictionary.add(dictSize++, w + entry.charAt(0));
	        enlargeIn--;

	        w = entry;

	        if (enlargeIn == 0) {
	          enlargeIn = Math.pow(2, numBits);
	          numBits++;
	        }

	      }
		
	}
	
	public static void main(String[] args) {
		String input;
//		input = "hello";
		input = "{\"v\":\"1\", \"d\":\"taobao.com\"}";
	
		System.out.println(decompressFromEncodedURIComponent("N4IghgJgDiBcIEYBMBmEAacBLCdEZAgFMBnAFzwAsyypYB6egIzCzIHsA7AcxIgGsAdCxwBXQQGN2AW2asOPelk7EAHoKiUoAfgBOAXgCSnMkV0AzMBKIAhACoBBCWSxcAZNP0R2Ad04Abdkg3Ml0wThJ9MAA2ABYAVmIieIkkJAAGBDAEAA4kCCZYnPimCDdRTiwAR1EifRQIAE4EaMgEN0gAfTIATyg6pA6ITul2Yn1BknZRXWt9Nyh2SMHoJf12qH8wMnN2XU9wiF12HDcJfywiE17+qKgsDqZzLe55sCYANzMSV055sJc7H0sQysQA7GDUmAkOYICgkBIwcV4o0kEQiEw3CRRExOhJKOFOER-PNsbj8YTiZ1OGBpHUhiRZvomGQFlsdnsDipjqcwFAoJ1+EQevocukUM0EI03N4-HiZJsiKZ1gswGFpJEmAB1Rr8ABaWp8jQAikhKP4ALIAZQQABE7LaAKI+ABSnBsPSYSAASggAJoob2w6QANR6qX8HyYACt0qoXdJ-GQDfxGgB5W1+2IWgBeFtUFqdqjTdn4PQtdgk8QTCEofpzCEttvYxbsLb1zaQ9fuJd8ADksHqenqABp9qMACRD6TAOtEAAU7M6LdHKLE+zmswn4v4IBOIB8JBrGhAu9IwABxRo5iAAMUalAkE5s5iY0lvybsKH8ogA0rEEHiAAZaRXWka8RxQPV-AkTg+ygL1YkaPsAGFYnSIDo2NaQIAvJNRz7dI-S1BAfEXR10j7OwLRQEt+GLTN0hXC1pGIwjZ0aURIO9FILwAVUaC1BzrQMEKQJC9SvFA31UD5iKDJg8JvFC9X4JgGhxK9KD1e9H2fV930-b92CqaN2A+MEAigX9OA1F1sL9TgXUoN9jUaK0rxxFBDGkDp7k6L5dB+LgJnScoSDMTosCgdYWkENJonipDBGiHIzguK4aD6Oo+QeXLBWFWL0nSNJMQiiRdCVUVGnMMEwFiJhGiIRpGneaJokaEpzDyGciAleIwWiJ4UCGbpsomNxAm4KKIC8eqmpQMEIEsRacgQCQmuIdJomatEq0ahB4TcAhpBwPB4laMFFoQBAiAa8VT1ReJ4neFhGgIG4iDwWUiVwTAKl+M74AQAgZn8KgaDoRhTHIc4sAkfgSCQYR4mkSQZGYEgJEEbxtEgGLkBG07ZousArrBG67qYB6kCel6mDejocBVURmY2NVaX0Gw-VvVQnDQidFkMHxDD9R0SGNCcSBsJAbBzBwc24Z0+z9Gx0iwK1RAcY5jQtMB2BQpAch6Wd0itaItagbgbGiCdo1tbhbRyBwXR8FDnatEdjW4JAHG4C8-RdX8mAcR1DAcEMHEMX8+ONK1bQcW1DDAY0UF-R3RGiRP9cdUQ7CtUOXRKwxvZsEgHFvbgUJIME+xIHJ2AcaQ7B8Bw-QcaMHEoABqBwPSIUQJxQtNjSrHxUbbAvh8k1QUIcC0eliF3VG7xppD1bvpFYhxu6wBwHE4URbTdWzYm7-P2DBR0gP8JBf0ofgD5dFDpFEFD2DIUQtXBFh4liR0-SGEMLeX8jdoy6C1FrUBUAXRVD4t6fglASCxBIFqXcogbCGCICOGwBd4hsBQhOfeTAoBAT9BeVmZAQyiCqOwY07Bojd2jI7EgfYECxGMP4f8v49TRD4n2eIE40zRD1PEEgTAh7QiTk+Ho-gLwOAQJwY0TAUIWl0HxGwPg+w2GzD3L2KEbDsB6MaKoSALwp2YYYW0YAHA9AvIYHMF5dCOmkOkHwxpYhEO7iOHwKjfzGiwCheclArTNxyHw9IfF+DSDTFgC0-gKJEH4BaQwfZ2AAFo7BiywI6ec0YwAoWrn2W8WN94W38M7BwZAJAjmiBAcO3o+I5DdhaCuj8LRQF7g4LAPSEAV3vPvKAEBbQ9EMFAHwLcXZ2GdhRPiLlOBYQcBeKAt4gKqAvPOPUrcbBannNwWIWh2DekoA4EgHwfDRGNA4HI-hdC6D9PEEMVyPghj4hIEMtTozRhsKoOwHCqhVFUFaPiQ5u4fHltCb0HxVBVAgewGwaZ-EfCtNwWoQjdDegtPIj0Hl-DejQfvBAVRu5ZxsL+NMnARZWhDI-Bwv4sDcD7LQxoPhHQSHWtGWITQcxxNELeJgR8kD+FvLeaIIZx56gNvvWIoh-CGDBNwJZQEoB+hQnxEMHwei-m9D4C8LluCItvC6C8FpFVgDeeYaQEg+78DAI6MAYAbAfAvLIqAWocyUR8WmCRaZ-BVFbj4dgsQ8koEoNEd+GFuBVDBN3fgSB-Tdz7PVTp852DmF4ZyrIv5fxVF-FaF0Hwq56koJy7g0RTLGhyBeHwIYkDRAQRebut52BEF-FrFCVRohWiqJwLUSA9RIGjN3TglB0lkF-HEKAFoiDPjBPOGwjRxm3hsNIJeEBfzd00RefgxogJBQcOkS+h8bCCMuHqWII4qjcByMaIgV6iA+BQBaLMNgRyiG4CZChNgEDHGTj4W0E4o3RDsDUUQxoxkoVSSe38JBu6Syjjqv0E4+LcCgPwPs3B+DRniI6fZNhCGxC1FaR0fYkLcG8d3SgtokCOkdPmeWJB26Wn4C6F0ugRXxBjlAeI5hyO2liCgaM6SUI7gcIQnoCBlW3lSSLQwsQJC2igNW4DpCSCNGjEgNM-B4FWhQo6TgeoSATitIrFA85yE+CgDYOZxpvQjj4tIbg0gXTcDfLEIC0w+K3itOhxeSd0khhsBOWI-gK4PryUgQwIZ5w5igB8fw5gsAfBdHQhwAACEAABfIAA"));
		System.out.println(decompressFromBase64(compressToBase64(input)));
		System.out.println(decompressFromUTF16(compressToUTF16(input)));
		System.out.println(decompressFromEncodedURIComponent(compressToEncodedURIComponent(input)));
		System.out.println(compressToEncodedURIComponent(input));
	}
}
