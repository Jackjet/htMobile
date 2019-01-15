/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.kwchina.core.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Encpy
{

    public Encpy()
    {
    }

    public static void initEncrypt(String strKey)
    {
        int k = 0;
        key = strKey;
        keyCode = hd5(key);
        keyBase = new int[64];
        keyBaseAt = new int[128];
        keyCrc = getCRC(keyCode);
        keyByte = new int[keyCode.length()];
        for(int i = 0; i < keyByte.length; i++)
        {
            char c = keyCode.charAt(i);
            keyByte[i] = c;
        }

        for(int i = 0; i < keyLS.length; i += 2)
        {
            for(int j = keyLS[i]; j <= keyLS[i + 1];)
            {
                keyBase[k] = j;
                keyBaseAt[j] = k;
                j++;
                k++;
            }

        }

    }

    public static void initEncrypt()
    {
        if(key == null)
            initEncrypt("dc53406Ea33eAD90ftp://localhost:22");
    }

    public static String getEncString(String strMing)
    {
        String strMi;
        try
        {
            strMing = strMing.trim();
            if(strMing.length() > 0 && strMing.charAt(0) == ' ')
            {
                strMing = strMing.substring(1);
                return getDesString(strMing);
            }
        }
        catch(Exception _ex)
        {
            return "";
        }
        initEncrypt();
        strMi = (new StringBuilder(String.valueOf(getEncCode(strMing)))).append(keyCrc).toString();
        strMi = (new StringBuilder(String.valueOf(getCRC(strMi)))).append(strMi).toString();
        return toLS(strMi);
    }

    public static String getDesString(String strMi)
    {
        try
        {
            initEncrypt();
            return getDesCode(DesCheck(strMi));
        }
        catch(Exception _ex)
        {
            return "";
        }
    }

    private static String getEncCode(String str)
    {
        StringBuffer wordByte = new StringBuffer();
        int i = 0;
        for(int k = 0; i < str.length(); k++)
        {
            if(k >= keyByte.length)
                k = 0;
            int w = str.charAt(i);
            String ff;
            if(w > 65280)
            {
                w = Math.abs(w - 65536);
                ff = "FF";
            } else
            if(w < 0 && w > -256)
            {
                w = Math.abs(w);
                ff = "FF";
            } else
            if(w <= -256)
            {
                w += 65536;
                ff = "FF";
            } else
            if(w > 256)
                ff = "FF";
            else
                ff = "";
            wordByte.append((new StringBuilder(String.valueOf(ff))).append(toHex(w ^ keyByte[k], ff)).toString());
            i++;
        }

        return wordByte.toString();
    }

    private static String DesCheck(String strMi)
    {
        String strMiA = fromLS(strMi);
        strMi = strMiA.replaceAll(".{1}(.+)", "$1");
        if(!strMiA.toUpperCase().startsWith(getCRC(strMi).toUpperCase()))
            return "";
        initEncrypt();
        if(!strMi.toUpperCase().endsWith(keyCrc.toUpperCase()))
            return "";
        else
            return strMi.replaceAll("(.+).{1}", "$1");
    }

    private static String getDesCode(String str)
    {
        byte b[] = str.getBytes();
        if(b.length % 2 != 0)
            throw new IllegalArgumentException("clong");
        int th = 0;
        int k = -1;
        boolean ih = false;
        boolean ph = false;
        StringBuffer buf = new StringBuffer();
        for(int n = 0; n < b.length; n += 2)
        {
            int m = Integer.parseInt(new String(b, n, 2), 16);
            if(!ih && m == 255)
            {
                ih = true;
                th = 0;
                ph = true;
            } else
            if(ih)
            {
                if(th == 0 && ph)
                {
                    th += m * 256;
                    ph = false;
                } else
                {
                    th += m;
                    if(++k >= keyByte.length)
                        k = 0;
                    th ^= keyByte[k];
                    if(th < 256)
                        th = 0 - th;
                    char cc = (char)th;
                    buf.append(cc);
                    ih = false;
                    th = 0;
                }
            } else
            {
                if(++k >= keyByte.length)
                    k = 0;
                char cc = (char)(m ^ keyByte[k]);
                buf.append(cc);
            }
        }

        return buf.toString();
    }

    private static String toHex(int inter, String ff)
    {
        String str = "";
        str = Integer.toHexString(inter & 65535);
        if(str.length() == 1)
            str = (new StringBuilder("0")).append(str).toString();
        if(ff.equals("FF") && str.length() == 2)
            str = (new StringBuilder("00")).append(str).toString();
        return str.toUpperCase();
    }

    private static String toHex3(int inter)
    {
        String str = "";
        str = Integer.toHexString(inter & 65535);
        if(str.length() == 1)
            str = (new StringBuilder("00")).append(str).toString();
        else
        if(str.length() == 2)
            str = (new StringBuilder("0")).append(str).toString();
        return str.toUpperCase();
    }

    private static String getCRC(String str)
    {
        double CRC = 0.0D;
        for(int i = 0; i < str.length(); i++)
        {
            char s = str.charAt(i);
            CRC += s * (i + 1);
        }

        return Integer.toHexString((int)(CRC % 16D)).toUpperCase();
    }

    private static String toLS(String hex)
    {
        int l = hex.length();
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < l; i += 3)
        {
            int iHex = Integer.parseInt(hex.substring(i, i + 3 <= l ? i + 3 : l), 16);
            buf.append((new StringBuilder(String.valueOf((char)keyBase[iHex / 64]))).append((char)keyBase[iHex % 64]).toString());
        }

        String str = buf.toString();
        if(l % 3 > 0)
            str = (new StringBuilder(String.valueOf(str))).append((char)keyBase[(keyBaseAt[str.charAt(2)] + l % 3) % 64]).toString();
        return replaceChar(str);
    }

    private static String fromLS(String lsx)
    {
        lsx = replaceCharAt(lsx);
        int l = lsx.length();
        int m = 0;
        if(l % 2 > 0)
        {
            m = ((keyBaseAt[lsx.replaceAll("^.+(.)$", "$1").charAt(0)] + 64) - keyBaseAt[lsx.charAt(2)]) % 64;
            lsx = lsx.replaceAll("(.+).{1}", "$1");
        }
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < (m != 0 ? l - 1 : l); i += 2)
        {
            int iLS = keyBaseAt[lsx.charAt(i)] * 64 + keyBaseAt[lsx.charAt(i + 1)];
            buf.append(toHex3(iLS).substring(m <= 0 || i != l - 3 ? 0 : 3 - m));
        }

        return buf.toString();
    }

    private static String hd5(String strIn)
    {
        return DigestUtils.md5Hex(strIn.replaceAll("[^\\x00-\\xff]", "x")).toUpperCase();
    }

    private static String replaceChar(String str)
    {
        return str.replace("0", "0F").replace("_", "01").replace("@", "02");
    }

    private static String replaceCharAt(String str)
    {
        return str.replace("02", "@").replace("01", "_").replace("0F", "0");
    }

    public static void getKey(String strKey)
    {
        initEncrypt(strKey);
    }

    public static String md5(String strIn)
    {
        return DigestUtils.md5Hex(strIn);
    }

    private static String key = null;
    private static String keyCrc;
    private static String keyCode = null;
    private static int keyBase[] = null;
    private static int keyBaseAt[] = null;
    private static int keyByte[] = null;
    private static int keyLS[] = {
        48, 57, 65, 90, 97, 122, 64, 64, 95, 95
    };

}


/*
	DECOMPILATION REPORT

	Decompiled from: G:\WorkSpace\vjsp.app\WebContent\WEB-INF\lib\commons-webs-1.3.jar
	Total time: 228 ms
	Jad reported messages/errors:
Couldn't resolve all exception handlers in method getEncString
	Exit status: 0
	Caught exceptions:
*/