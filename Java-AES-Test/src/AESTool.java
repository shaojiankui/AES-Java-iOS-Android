


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class AESTool {
    /**
     * AES加密字符串
     *
     * @param content
     *            需要被加密的字符串
     * @param password
     *            加密需要的密码
     * @return 密文
     */
    public static byte[] encrypt(String content, String password) {
        try {
        	  if (password == null) {
                  System.out.print("Key为空null");
                  return null;
              }
              // 判断Key是否为16位
              if (password.length() != 16) {
                  System.out.print("Key长度不是16位");
                  return null;
              }
              byte[] raw = password.getBytes("utf-8");
              SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
              Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
              cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
              byte[] result = cipher.doFinal(content.getBytes("utf-8"));
              
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            random.setSeed(password.getBytes());
//
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
//
//            kgen.init(128, random);// 利用用户密码作为随机数初始化出
//            // 128位的key生产者
//            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
//
//            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
//
//            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
//            // null。
//
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
//
//            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//
//            byte[] byteContent = content.getBytes("utf-8");
//
//            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
//
//            byte[] result = cipher.doFinal(byteContent);// 加密

            return result;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密AES加密过的字符串
     *
     * @param content
     *            AES加密过过的内容
     * @param password
     *            加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
        	 // 判断Key是否正确
            if (password == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (password.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = password.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
  
            try {
                byte[] original = cipher.doFinal(content);
                return original;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
            
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            random.setSeed(password.getBytes());
//
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
//
//            kgen.init(128, random);// 利用用户密码作为随机数初始化出
//
//            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
//            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
//          
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
//            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
//            byte[] result = cipher.doFinal(content);
//            return null; // 明文

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return null;
    }
    
    public static String decrypt(String content, String password) {
        byte[] decryptFrom = parseHexStr2Byte(content);
        byte[] decrypt = decrypt(decryptFrom, password);
        if (decrypt!=null&&decrypt.length!=0){
            return new String(decrypt);
        }
        return null;
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String password = "1234512345123456";
 
        String str = "12382929sadadasd";

        byte[] encryptResult = encrypt(str, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);
 

 
        byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
        byte[] decryptResult = decrypt(decryptFrom, password);
        System.out.println("解密后：" + new String(decryptResult));
    }
}
