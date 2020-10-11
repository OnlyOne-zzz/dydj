package com.bestfeng.dydj.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @author zh
 * @ClassName: WinXinPayUtil
 * @Description: 微信支付工具类
 * @date 2018年6月15日
 */
public class SmallEncryptedDateUtil {

    /**
     * @param encryptedData
     * @param sessionkey
     * @param iv
     * @return
     * @throws Exception
     */
    public static String encryptedData(String encryptedData, String sessionkey, String iv) throws Exception {
        // 被加密的数据
        byte[] dataByte = Base64Utils.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64Utils.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64Utils.decode(iv);
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int    groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp   = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher              cipher     = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec       spec       = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
        byte[] resultByte = cipher.doFinal(dataByte);
        String result     = "";
        if (null != resultByte && resultByte.length > 0) {
            result = new String(resultByte, "UTF-8");
        }
        return result;
    }
}
