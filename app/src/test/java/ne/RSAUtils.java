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
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class RSAUtils {

    static BASE64Decoder decoder = new BASE64Decoder();
    static BASE64Encoder encoder = new BASE64Encoder();

    static String DES = "DES";
    static String RSA = "RSA";

    static String encode = "UTF-8";//保持平台兼容统一使用utf-8

    static String filePath = RSAUtils.class.getResource("/").getPath();
    //
//    static String filePath = "D:/";
    //私钥文件路径
//    static String privateFile = filePath + "/keys/private_pkcs8_der.key";
    static String privateFile = "G:\\Desktop\\private_pkcs8_der.key";
    //公钥文件路径
    static String publicFile = filePath + "/keys/rsa_public_key.der";

    //des 加密
    private static byte[] encryptByteDES(byte[] byteD, String strKey) throws Exception {
        return doEncrypt(byteD, getKey(strKey), DES);
    }

    //des 解密
    private static byte[] decryptByteDES(byte[] byteD, String strKey) throws Exception {
        return doDecrypt(byteD, getKey(strKey), DES);
    }

    public static SecretKey getKey(String strKey) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey sk = keyFactory.generateSecret(desKeySpec);
        return sk;
    }

    //客户端加密
    static HashMap<String, String> DESAndRSAEncrypt(byte[] dataToEncypt, String pwd) throws Exception {

        byte[] encryptData = encryptByteDES(dataToEncypt, pwd);
        String dataBase64 = encoder.encode(encryptData);

        byte[] encryptKey = RSAEncrypt(pwd.getBytes(encode));
        String keyBase64 = encoder.encode(encryptKey);

        HashMap<String, String> data = new HashMap<>();
        data.put("data", dataBase64);
        data.put("key", keyBase64);
        return data;
    }

    //服务端解密
    static String DESAndRSADecrypt(HashMap<String, String> data) throws Exception {
        String dataBase64 = data.get("data");
        String keyBase64 = data.get("key");

        byte[] encryptedData = decoder.decodeBuffer(dataBase64);
        byte[] encryptedKey = decoder.decodeBuffer(keyBase64);

        byte[] decryptedKey = RSADecrypt(encryptedKey);
        String pwd = new String(decryptedKey, encode);
        System.out.println("DES密码：" + pwd);

        byte[] decryptedData = decryptByteDES(encryptedData, pwd);
        String textDecrypt = new String(decryptedData, encode);
        return textDecrypt;
    }

    //公钥加密
    public static byte[] RSAEncrypt(byte[] plainText) throws Exception {
        //读取公钥
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        FileInputStream bais = new FileInputStream(publicFile);
        Certificate cert = certificatefactory.generateCertificate(bais);
        bais.close();
        PublicKey puk = cert.getPublicKey();
//		System.out.println("公钥base64："+encoder.encode(puk.getEncoded()));
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
//		System.out.println("私钥base64："+encoder.encode(prk.getPrivateExponent().toByteArray()));
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


    public static String RSAEncrypt(String publicKeyString, String text) throws Exception {
        // 解码
        byte[] buffer = decoder.decodeBuffer(publicKeyString);
        // 转换为publicKey 选择加密模式为RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        // 加密编码返回
        byte[] bytes = cipher.doFinal(text.getBytes("UTF-8"));
        String encryptResult = encoder.encode(bytes);
        return encryptResult;
    }

    //pkcs8_der.key文件为私钥 只能保存在服务端
    //public_key.der为公钥文件，保存在客户端
    @Test
    public void main() throws Exception {
//
//        byte[] bytes = RSAEncrypt(text.getBytes(encode));
//        String enc = encoder.encode(bytes);
//        System.out.println(enc);

        String text = "123456a";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIKkHR3+u1EBucMFZKDG0dyfyUY+aGj+okeBSD/KT88UunPAvfez+Rg3TKmOs3KFsEvR+4LtOALMk+SMAMAMll7e71KQGN1s9NIe3pvfX49L7/zxC23x18zYIKXA9mU3/Ph52CKLqXBRC/oqWxcSWayxnr8dewfSArxNcIpQRqSwIDAQAB";

        String s1 = RSAEncrypt(publicKey, text);
        System.out.println("密文:" + s1);
        byte[] bytes1 = decoder.decodeBuffer(s1);
        byte[] bytes2 = RSADecrypt(bytes1);
        String s = new String(bytes2);
        System.out.println("明文" + s);

//        generateKeyPair();

    }
}

