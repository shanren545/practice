package shanren.crypt;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.codec.digest.DigestUtils;

public class Rsa {
    RSAPublicKey publicKey;
    RSAPrivateKey privateKey;


    public static void main(String[] args) throws Exception {
        new Rsa().test();
    }

    public void test() throws Exception {
        KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
        g.initialize(1024);
        KeyPair keyPair = g.generateKeyPair();

        // 公钥
        publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String content = "txw";
        System.out.println("原文：" + content);
        byte[] contentData = content.getBytes();
        byte[] encryptedData = encryptByPrivateKey(contentData, privateKey);
        System.out.println("密文：" + Base64.getEncoder().encodeToString(encryptedData));
        byte[] decryptedData = decryptByPublicKey(encryptedData, publicKey);
        System.out.println("公钥解密数据：" + new String(decryptedData));

        encryptedData = encryptByPublicKey(contentData, publicKey);
        System.out.println("密文：" + Base64.getEncoder().encodeToString(encryptedData));
        decryptedData = decryptByPrivateKey(encryptedData, privateKey);
        System.out.println("密文：" + Base64.getEncoder().encodeToString(encryptedData));
        decryptedData = decryptByPrivateKey(encryptedData, privateKey);
        System.out.println("私钥解密数据：" + new String(decryptedData));

        // byte[] hash = DigestUtils.sha256(contentData);
        // byte[] hash = encryptSHA(contentData);
        byte[] diySign = encryptByPrivateKey(encryptSHA(contentData), privateKey);
        String diySignStr = Base64.getEncoder().encodeToString(diySign);
        System.out.println("DIY签名：" + diySignStr);
        // boolean diyRt = verify(contentData, publicKey, diySignStr);
        // System.out.println("DIY签名验证结果：" + diyRt);

        String sign = sign(contentData, privateKey);
        System.out.println("签          名：" + sign);
        sign = sign(contentData, privateKey);
        System.out.println("签          名：" + sign);

        boolean rt = verify(contentData, publicKey, sign);
        System.out.println("签名验证结果：" + rt);
    }

    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(data);
        return sha.digest();
    }


    public static byte[] encryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
        // //解密密钥
        // byte[] keyBytes = decryptBASE64(key);
        // //取私钥
        // PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // 对数据加密
        System.out.println("RSAPrivateKey.getAlgorithm:" + privateKey.getAlgorithm());
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        cipher.update(data);
        return cipher.doFinal();
    }

    public static byte[] decryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
        // 对私钥解密
        // byte[] keyBytes = decryptBASE64(key);
        //
        // PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    public static byte[] encryptByPublicKey(byte[] data, RSAPublicKey publicKey) throws Exception {
        // // 对公钥解密
        // byte[] keyBytes = decryptBASE64(key);
        // // 取公钥
        // X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        // KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        // 对数据解密
        System.out.println("RSAPublicKey.getAlgorithm:" + publicKey.getAlgorithm());
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static byte[] decryptByPublicKey(byte[] data, RSAPublicKey publicKey) throws Exception {
        // 对私钥解密
        // byte[] keyBytes = decryptBASE64(key);
        // X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        // KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //
        // 对数据解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }


    public static String sign(byte[] data, RSAPrivateKey privateKey) throws Exception {
        // 解密私钥
        // byte[] keyBytes = decryptBASE64(privateKey);
        // // 构造PKCS8EncodedKeySpec对象
        // PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // // 指定加密算法
        // KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // // 取私钥匙对象
        // PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);

        final byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    public static boolean verify(byte[] data, RSAPublicKey publicKey, String sign) throws Exception {
        // 解密公钥
        // byte[] keyBytes = decryptBASE64(publicKey);
        // // 构造X509EncodedKeySpec对象
        // X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        // // 指定加密算法
        // KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // // 取公钥匙对象
        // PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(Base64.getDecoder().decode(sign));
    }


    // public static Map<String,Object> initKey()throws Exception{
    // KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
    // keyPairGenerator.initialize(1024);
    // KeyPair keyPair = keyPairGenerator.generateKeyPair();
    //
    // //公钥
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    // //私钥
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    //
    // Map<String,Object> keyMap = new HashMap<String, Object>(2);
    // keyMap.put(PUBLIC_KEY, publicKey);
    // keyMap.put(PRIVATE_KEY, privateKey);
    //
    // return keyMap;
    // }
    // public static String getPublicKey(Map<String, Object> keyMap)throws Exception{
    // Key key = (Key) keyMap.get(PUBLIC_KEY);
    // return encryptBASE64(key.getEncoded());
    // }
    //
    // /**
    // * 取得私钥，并转化为String类型
    // * @param keyMap
    // * @return
    // * @throws Exception
    // */
    // public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
    // Key key = (Key) keyMap.get(PRIVATE_KEY);
    // return encryptBASE64(key.getEncoded());
    // }

}
