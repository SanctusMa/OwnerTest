package king.chengwu.com.chengwuapp;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by shmyhui on 2017/3/24.
 */

public class Encypt {
    private static String RSA = "RSA";
    static String encode = "UTF-8";//保持平台兼容统一使用utf-8

    //私钥文件路径
    static String privateFile = "G:\\Desktop\\private_pkcs8_der.key";

    static BASE64Decoder decoder = new BASE64Decoder();
    static BASE64Encoder encoder = new BASE64Encoder();

    public void encryptMethod() throws Exception {
        String strPublicKey = "-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIKkHR3+" +
                "u1EBucMFZKDG0dyfyUY+aGj+okeBSD/KT88UunPAvfez+Rg3TKmOs3KFsEvR+4LtOA" +
                "LMk+SMAMAMll7e71KQGN1s9NIe3pvfX49L7/zxC23x18zYIKXA9mU3/Ph52CKLqXBRC/oqWxcSWayxnr8dewfSArxNcIpQRqSwIDAQAB-----END PUBLIC KEY-----";
        String pwd = "Myh@123456";
//        byte[] afterPwdByte = encryptData(pwd.getBytes(encode), loadPublicKey(strPublicKey));
//        String afterPwd = encoder.encode(afterPwdByte);
//        System.out.println("加密后:");
//        System.out.println(afterPwd);
        String afterPwd = encryptData(pwd, strPublicKey);
        System.out.println("afterPwd:");
        System.out.println(afterPwd);
        System.out.println();

        byte[] afteDecByte = RSADecrypt(decoder.decodeBuffer(afterPwd));
        String afterDec = new String(afteDecByte, encode);
        System.out.println("afterDec:");
        System.out.println(afterDec);
        System.out.println();
    }

    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param pwd          需加密数据的byte数据
     * @param publicKeyStr 公钥字符串
     * @return 加密后的byte型数据
     */
    public static String encryptData(String pwd, String publicKeyStr) {
        try {
            PublicKey publicKey = loadPublicKey(publicKeyStr);
            Cipher cipher = Cipher.getInstance(RSA);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            byte[] bytePwd = cipher.doFinal(pwd.getBytes("UTF-8"));
            return new BASE64Encoder().encode(bytePwd);
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data      需加密数据的byte数据
     * @param publicKey 公钥
     * @return 加密后的byte型数据
     */
    public static byte[] encryptData(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr.getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    //私钥解密
    public static byte[] RSADecrypt(byte[] encryptData) throws Exception {
        FileInputStream in = new FileInputStream(privateFile);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] tmpbuf = new byte[1024];
        int count = 0;
        while ((count = in.read(tmpbuf)) != -1) {
            bout.write(tmpbuf, 0, count);
        }
        in.close();
        //读取私钥
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bout.toByteArray());
        PrivateKey prk = keyFactory.generatePrivate(privateKeySpec);
//      System.out.println("私钥base64："+encoder.encode(prk.getPrivateExponent().toByteArray()));
        return doDecrypt(encryptData, prk, RSA);
    }

    /**
     * 执行解密操作
     *
     * @param data 待操作数据
     * @param key  Key
     * @param type 算法 RSA or DES
     * @return
     * @throws Exception
     */
    public static byte[] doDecrypt(byte[] data, Key key, String type) throws Exception {
        Cipher cipher = Cipher.getInstance(type);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static class Base64 {
        private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
        private static byte[] codes = new byte[256];

        static {
            int i;
            for (i = 0; i < 256; ++i) {
                codes[i] = -1;
            }

            for (i = 65; i <= 90; ++i) {
                codes[i] = (byte) (i - 65);
            }

            for (i = 97; i <= 122; ++i) {
                codes[i] = (byte) (26 + i - 97);
            }

            for (i = 48; i <= 57; ++i) {
                codes[i] = (byte) (52 + i - 48);
            }

            codes[43] = 62;
            codes[47] = 63;
        }

        public Base64() {
        }

        public static byte[] decode(byte[] data) {
            int len = (data.length + 3) / 4 * 3;
            if (data.length > 0 && data[data.length - 1] == 61) {
                --len;
            }

            if (data.length > 1 && data[data.length - 2] == 61) {
                --len;
            }

            byte[] out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;

            for (int ix = 0; ix < data.length; ++ix) {
                byte value = codes[data[ix] & 255];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) (accum >> shift & 255);
                    }
                }
            }

            if (index != out.length) {
                throw new RuntimeException("miscalculated data length!");
            } else {
                return out;
            }
        }

        public static char[] encode(byte[] data) {
            char[] out = new char[(data.length + 2) / 3 * 4];
            int i = 0;

            for (int index = 0; i < data.length; index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = 255 & data[i];
                val <<= 8;
                if (i + 1 < data.length) {
                    val |= 255 & data[i + 1];
                    trip = true;
                }

                val <<= 8;
                if (i + 2 < data.length) {
                    val |= 255 & data[i + 2];
                    quad = true;
                }

                out[index + 3] = alphabet[quad ? val & 63 : 64];
                val >>= 6;
                out[index + 2] = alphabet[trip ? val & 63 : 64];
                val >>= 6;
                out[index + 1] = alphabet[val & 63];
                val >>= 6;
                out[index + 0] = alphabet[val & 63];
                i += 3;
            }

            return out;
        }

        public static byte[] encodebyte(byte[] data) {
            byte[] out = new byte[(data.length + 2) / 3 * 4];
            int i = 0;

            for (int index = 0; i < data.length; index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = 255 & data[i];
                val <<= 8;
                if (i + 1 < data.length) {
                    val |= 255 & data[i + 1];
                    trip = true;
                }

                val <<= 8;
                if (i + 2 < data.length) {
                    val |= 255 & data[i + 2];
                    quad = true;
                }

                out[index + 3] = (byte) alphabet[quad ? val & 63 : 64];
                val >>= 6;
                out[index + 2] = (byte) alphabet[trip ? val & 63 : 64];
                val >>= 6;
                out[index + 1] = (byte) alphabet[val & 63];
                val >>= 6;
                out[index + 0] = (byte) alphabet[val & 63];
                i += 3;
            }

            return out;
        }
    }
}
