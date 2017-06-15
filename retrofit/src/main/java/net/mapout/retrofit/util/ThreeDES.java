package net.mapout.retrofit.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * @author denny
 */
public class ThreeDES {

    public static final byte[] keyBytes = {0x77, 0x29, 0x74, 0x74, 0x31, 0x45, 0x33, 0x33,
			 0x28, 0x35, 0x19, 0x12, 0x12, 0x12, 0x35, 0x76,
			 0x11, 0x22, 0x4B, 0x5D, 0x5D, 0x10, 0x40, 0x38};

    public static String encryptThreeDESECB(String src,String key) throws Exception {
      DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
      SecretKey securekey = keyFactory.generateSecret(dks);

      Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, securekey);
      byte[] b=cipher.doFinal(src.getBytes("UTF-8"));

      return BASE64Util.encode(b);
    }

    public static String encryptThreeDESECB(String src){
        try {
            return encryptThreeDESECB(src, new String(keyBytes));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("encrypt code error");
    }
    }

    public static String decryptThreeDESECB(String src) throws Exception {
    	return decryptThreeDESECB(src, new String(keyBytes));
    }

    public static String decryptThreeDESECB(String src,String key) throws Exception {
      //BASE64Decoder decoder = new BASE64Decoder();
      byte[] bytesrc = BASE64Util.decode(src);
      DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
      SecretKey securekey = keyFactory.generateSecret(dks);

      Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, securekey);
      byte[] retByte = cipher.doFinal(bytesrc);

      return new String(retByte, "UTF-8");
    }

}
