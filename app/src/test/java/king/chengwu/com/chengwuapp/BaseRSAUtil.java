package king.chengwu.com.chengwuapp;

import android.util.Log;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import Decoder.BASE64Encoder;

/**
 * @author by TRC
 * @date 2017年8月22日
 */
public final class BaseRSAUtil {
    private static BASE64Encoder encoder = new BASE64Encoder();
    private static String RSA = "RSA/None/PKCS1Padding";

    @Test
    public void main() throws Exception{
        String path = "G:\\Desktop\\Pub_Key1494811415972.der";
        String pwd = getRSAPwd(RSAEncrypt("m111111".getBytes("UTF-8"), path));
        System.out.println(pwd);

        String privatePath = "G:\\Desktop\\private_der.key";
        String afterPwd = new String(decryptData(pwd.getBytes("UTF-8"), loadPrivateKey(privatePath)));
        System.out.println(afterPwd);
    }

    /**
     * 随机生成RSA密钥对(默认密钥长度为1024)
     *
     * @return
     */
    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(1024);
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048<br>
     *                  一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    //公钥加密
    public static byte[] RSAEncrypt(byte[] plainText, String publicFile) throws Exception {
        //读取公钥
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        FileInputStream bais = new FileInputStream(publicFile);
        Certificate cert = certificatefactory.generateCertificate(bais);
        bais.close();
        PublicKey puk = cert.getPublicKey();
        return doEncrypt(plainText, puk, RSA);
    }

    public static String getRSAPwd(byte[] aa) {
        return encoder.encode(aa);
    }

    /**
     * 执行加密操作
     *
     * @param data 待操作数据
     * @param key  Key
     * @param type 算法 RSA or DES
     * @return
     * @throws Exception
     */
    public static byte[] doEncrypt(byte[] data, Key key, String type) throws Exception {
        Cipher cipher = Cipher.getInstance(type);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
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
            Log.e("e", e.toString());
            return null;
        }
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
            Log.e("e", e.toString());
            return null;
        }
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过encryptedData()加密返回的byte数据
     * @param privateKey    私钥
     * @return
     */
    public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 使用N、e值还原公钥
     *
     * @param modulus
     * @param publicExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String modulus, String publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 使用N、d值还原私钥
     *
     * @param modulus
     * @param privateExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String modulus, String privateExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) {
        try {
            byte[] buffer = Base64.decode(publicKeyStr.getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            Log.e("e", "无此算法");
            return null;
        } catch (InvalidKeySpecException e) {
            Log.e("e", "公钥非法");
            return null;
        } catch (NullPointerException e) {
            Log.e("e", "公钥数据为空");
            return null;
        } catch (Exception e1) {
            Log.e("e", e1.toString());
            return null;
        }
    }

    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr.getBytes());
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in) throws Exception {
        try {
            return loadPublicKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @return 是否成功
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
        try {
            return loadPrivateKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }

        return sb.toString();
    }

    public static String encrypt(String base64EncodedPublicKeyStr, String content) {
        try {
            byte[] bytes = encryptData(content.getBytes(), getPublicKey(Base64.decode(base64EncodedPublicKeyStr.getBytes())));
            return new String(Base64.encodebyte(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String base64EncodedPrivateKeyStr, String content) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] data = Base64.decode(content.getBytes());
        return new String(decryptData(data, getPrivateKey(Base64.decode(base64EncodedPrivateKeyStr.getBytes()))));
    }

    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    public static void printPublicKeyInfo(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("----------RSAPublicKey----------");
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }

    public static void printPrivateKeyInfo(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        System.out.println("----------RSAPrivateKey ----------");
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());

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
