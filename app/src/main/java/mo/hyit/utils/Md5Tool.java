package mo.hyit.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Tool {
    public static String getStringMd5(String origin){
        if(TextUtils.isEmpty(origin)){
            return "";
        }else{
            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("md5");
                byte[]  bytes = md5.digest(origin.getBytes());
                StringBuilder result = new StringBuilder();
                for (byte b : bytes) {
                    String temp = Integer.toHexString(b & 0xff);
                    if (temp.length() == 1) {
                        temp = "0" + temp;
                    }
                    result.append(temp);
                }
                return result.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
