package com.goushuang.lyz.tools;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeBySHA {
    public static String encodebySHA(String info){
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            byte[] srcBytes = info.getBytes();
            // 使用srcBytes更新摘要
            sha.update(srcBytes);
            // 完成哈希计算，得到result
            byte[] resultBytes = sha.digest();
            return new String(resultBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";


    }
}
