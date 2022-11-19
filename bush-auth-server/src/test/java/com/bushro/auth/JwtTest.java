package com.bushro.auth;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * <p> Jwt测试 </p>
 *
 * @author luo.qiang
 * @description
 * @date 2022/11/17 22:43
 */
public class JwtTest {

    /**
     * jwt.jks 证书生成命令
     * keytool -genkeypair -alias bushro -keyalg RSA -keypass 123456 -keystore jwt.jks -storepass 123456
     */


    @Test
    public void testCreateJwt() throws Exception {
        // 1、创建秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                // 秘钥位置
                new ClassPathResource("jwt.jks"),
                // 秘钥库密码
                "123456".toCharArray()
        );
        // 2、基于工厂拿到私钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("bushro", "123456".toCharArray());

        // 转化为rsa私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("私钥 " + getPrivateKeyStr(rsaPrivateKey));

        RSAPublicKey aPublic = (RSAPublicKey) keyPair.getPublic();
        System.out.println("公钥 " + getPublicKeyStr(aPublic));


        // 3、生成jwt
        Map<String, String> map = Maps.newHashMap();
        map.put("username", "lq");
        map.put("password", "123456");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(rsaPrivateKey));
        String jwtEncoded = jwt.getEncoded();
        System.out.println("jwtEncoded:" + jwtEncoded);
        String claims = jwt.getClaims();
        System.out.println("claims:" + claims);
    }

    /**
     * 获取公钥字符串
     *
     * @return 当前的公钥字符串
     */
    public String getPublicKeyStr(RSAPublicKey publicKey) {
        KeyFactory keyFactory = null;
        String publicKeyStr = null;
        try {
            //将公钥对象转换为字符串
            keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = keyFactory.getKeySpec(publicKey, X509EncodedKeySpec.class);
            byte[] buffer = keySpec.getEncoded();
            publicKeyStr = Base64.getEncoder().encodeToString(buffer);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("无此算法");
        } catch (InvalidKeySpecException e) {
            System.err.println("公钥非法");
        }
        return publicKeyStr;
    }

    /**
     * 获取私钥字符串
     *
     * @return 当前的私钥字符串
     */
    public String getPrivateKeyStr(RSAPrivateKey rsaPrivateKey) {
        KeyFactory keyFactory = null;
        String privateKeyStr = null;
        try {
            //将私钥对象转换为字符串
            keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = keyFactory.getKeySpec(rsaPrivateKey, PKCS8EncodedKeySpec.class);
            byte[] buffer = keySpec.getEncoded();
            privateKeyStr = Base64.getEncoder().encodeToString(buffer);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("无此算法");
        } catch (InvalidKeySpecException e) {
            System.err.println("私钥非法");
        }
        return privateKeyStr;
    }

    @Test
    public void testParseJwt() throws Exception {
        // 基于公钥去解析jwt
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoibHEifQ.pgtpIfM4EhJSnNWHZU1yhRuz49mguOoDnnzH514d_YNInMjt4DyiVyepLfkXpa6v4hz69quIIu-Lc2W8rI030H6E36h30o5L-Z2bbwuD_qlZwytrJH7QRc1SLOdLlwFAuv8WJaGvOhkrmk6rLdiJ0yANtNFc8cWssw3MYtAq7UCYhH686LEe0KjRNwhALcx0wMFwz3oqtyrvW45_3Ws3rhhdOMhpoUYJ1_R4__56ULua2BxL07stwlpMO16490Mhwqjlw43tFzx3Tz5Fj0UY85sex9xRPVL2-8xEMDBQsuR2k8KjLIv196oe9FGDszrnnTWWqBQwXmkECCTkToRVXQ";
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwi9W9Si+IM5meOp2DIDQ\n" +
                "4turKuw4xBV0kGw2EW6D6r4QU9tXZnTeC9AjqXdsSHqUK1QuS+Pdh8ZRVzE6wBgU\n" +
                "2kikgSq3C5VNo8TUtmKS1Z+fMWrZueQpcJ8agcKTZVtnm2Q5xR8ZUrRwZ4RxB2+Z\n" +
                "riE6zsBSuwqSgjWV6te/sivRFGnxijbLlUJEqbw/0nypnFRDUSMo07HCmkW+34Ky\n" +
                "BQTdCTUFFuTq5sgDk6srFNmQdfdr570kTS8t9NTp1TktqE5mg9EdsvkrIOTpG5BX\n" +
                "5AwRwUkF+7lUzffYl7+9k5OAKCt7OIGVf71Twak4b5M8OLf5eQvUFMPH0CMgJKTd\n" +
                "vwIDAQAB" +
                "-----END PUBLIC KEY-----";
        // 解析令牌
        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        // 获取负载
        String claims = token.getClaims();
        System.out.println(claims);
    }

}
