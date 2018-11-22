package ne;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by shmyhui on 2017/3/28.
 */

public class Encrypt {
    static BASE64Decoder decoder = new BASE64Decoder();
    static BASE64Encoder encoder = new BASE64Encoder();

    static String RSA = "RSA/None/PKCS1Padding";

    static String encode = "UTF-8";//保持平台兼容统一使用utf-8

    //私钥文件路径
    static String privateFile = "G:\\Desktop\\private_der1.key";
    //公钥文件路径
    static String publicFile = "G:\\Desktop\\public_key1.der";

    //pkcs8_der.key文件为私钥 只能保存在服务端
    //public_key.der为公钥文件，保存在客户端
    @Test
    public void main() throws Exception {
//        EnAndDescryptUtil.generateKeyPair();

        String pwd = "111111";
//
        byte[] afterPwdByte = RSAEncrypt(pwd.getBytes(encode));
        String afterPwd = encoder.encode(afterPwdByte);
        System.out.println("RSA加密后密码:");
        System.out.println(afterPwd);
        System.out.println();


        afterPwd = "OG8ueX/B87U/az8vU4XkJayyBtvmFXbt3k+U5IVxosrQlggQVMcxBsPSLwbSmnE4XC3XT8RlvyHN" +
                "Oh5sUKzDqfM1vmOE1f1u2+Q0cWpIXkO3sYsTa97IiZIKdm4Au3cES2gTUO9dVm2g4W4evoVh5VYP" +
                "pswnLhLlCJgiAsjX6S4=";
        byte[] encryptedKey = decoder.decodeBuffer(afterPwd);
        byte[] decryptedKey = RSADecrypt(encryptedKey);
        String afterPwd1 = new String(decryptedKey, encode);
        System.out.println("RSA解密后密码:");
        System.out.println(afterPwd1);
        System.out.println();
    }

    //公钥加密
    public static byte[] RSAEncrypt(byte[] plainText) throws Exception {
        //读取公钥
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        FileInputStream bais = new FileInputStream(publicFile);
        Certificate cert = certificatefactory.generateCertificate(bais);
        bais.close();
        PublicKey puk = cert.getPublicKey();
//      System.out.println("公钥base64："+encoder.encode(puk.getEncoded()));
        return doEncrypt(plainText, puk, RSA);
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

    public static void generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
        kpg.initialize(1024); // 指定密钥的长度，初始化密钥对生成器
        KeyPair kp = kpg.generateKeyPair(); // 生成密钥对
        RSAPublicKey puk = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey prk = (RSAPrivateKey) kp.getPrivate();
        BigInteger e = puk.getPublicExponent();
        BigInteger n = puk.getModulus();
        BigInteger d = prk.getPrivateExponent();

        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println("public key:\n" + encoder.encode(n.toByteArray()));
        System.out.println("private key:\n" + encoder.encode(d.toByteArray()));
    }
}
