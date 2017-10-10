package com.fanmila.util;

/**
 * Created by wellbein on 2017/5/4.
 */
public class AlloydingEncrypt {
    private static String cltJsStrKey = "helloworld";

    public static String cryptStr(String strContent, String strKey) {
        String strCrptText = "";
        int nTemp = 0;
        int nTemp2;
        int nSeed;
        int nPos = 0;
        char[] strKeyChar = strKey.toCharArray();
        for (char sk : strKeyChar) {
            nTemp += (int)sk;
        }
        nTemp = (int)(nTemp * 0.1 /6);
        nSeed = nTemp;
        char[] strContentChar = strContent.toCharArray();
        for (char sc : strContentChar) {
            nTemp = (int)sc;

            nPos++;
            if (nPos == 6)
                nPos =0;
            nTemp2 = 0;
            if (nPos == 0)
                nTemp2 = nTemp - (nSeed - 2);
            else if (nPos == 1)
                nTemp2 = nTemp + (nSeed - 5);
            else if (nPos == 2)
                nTemp2 = nTemp - (nSeed - 4);
            else if (nPos == 3)
                nTemp2 = nTemp + (nSeed - 2);
            else if (nPos == 4)
                nTemp2 = nTemp - (nSeed - 3);
            else if (nPos == 5)
                nTemp2 = nTemp + (nSeed - 5);
            nTemp2 += nPos;
            strCrptText += (char)nTemp2;
        }
        return strCrptText;
    }

    public static String deCryptStr(String strContent, String strKey) {
        String strDeCrptText = "";
        int nTemp = 0;
        int nTemp2;
        int nPos = 0;
        int nSeed;
        char[] strKeyChar = strKey.toCharArray();
        for (char sk : strKeyChar) {
            nTemp += (int)sk;
        }
        nTemp = (int)(nTemp * 0.1 /6);
        nSeed = nTemp;
        char[] strContentChar = strContent.toCharArray();
        for (char sc : strContentChar)  {
            nTemp = (int)sc;
            nPos++;
            if (nPos == 6)  nPos = 0;
            nTemp2 = 0;

            if (nPos == 0)
                nTemp2 = nTemp + (nSeed - 2);
            else if (nPos == 1)
                nTemp2 = nTemp - (nSeed - 5);
            else if (nPos == 2)
                nTemp2 = nTemp + (nSeed - 4);
            else if (nPos == 3)
                nTemp2 = nTemp - (nSeed - 2);
            else if (nPos == 4)
                nTemp2 = nTemp + (nSeed - 3);
            else if (nPos == 5)
                nTemp2 = nTemp - (nSeed - 5);
            nTemp2 -= nPos;
            strDeCrptText += (char)nTemp2;
        }
        return strDeCrptText;
    }

    public static String cryptCltJsStr(String strContent){
        return cryptStr(strContent, cltJsStrKey);
    }
    public static void main(String[] args) {

        String a = "{lilaichen]g}";
        String key = "helloworld";
        String b = cryptStr(a, key);
        System.out.println(cryptStr(a, key));
        System.out.println(deCryptStr("\u0089`|asYq\\xcoW\u008B", key));
    }


}
