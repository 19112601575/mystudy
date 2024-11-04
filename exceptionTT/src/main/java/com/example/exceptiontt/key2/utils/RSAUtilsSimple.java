package com.example.exceptiontt.key2.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtilsSimple {
    private static final String RSA_KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 4096;

    public static Map<String, String> generateKey() {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //控制密钥大小
        keyPairGenerator.initialize(KEY_SIZE);

        //获取密钥对
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        //密钥编码
        byte[] privateKeyEncoded = privateKey.getEncoded();
        byte[] publicKeyEncoded = publicKey.getEncoded();

        String privateKeySt = Base64.getEncoder().encodeToString(privateKeyEncoded);
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKeyEncoded);

        Map<String, String> map = new HashMap<>();
        map.put("privateKeyStr", privateKeySt);
        map.put("publicKeyStr", publicKeyStr);

        return map;
    }

    //私钥加密
    public static String encryptByPrivateKey(String data, String privateKeyStr) throws Exception {

        byte[] bytes = Base64.getDecoder().decode(privateKeyStr);
        PrivateKey privateKey = KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(bytes));
        Cipher cipher = Cipher.getInstance(RSA_KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] doFinal = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(doFinal);

    }

    //公钥解密
    public static String decryptByPublicKey(String data, String publicKeyStr) throws Exception {

        byte[] bytes = Base64.getDecoder().decode(publicKeyStr);
        RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(bytes));
        Cipher cipher = Cipher.getInstance(RSA_KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(doFinal);

    }

    public static void main(String[] args) throws Exception {

        Map<String, String> map = generateKey();
        System.out.println("私钥"+map.get("privateKeyStr"));
        System.out.println("公钥"+map.get("publicKeyStr"));

        String pri = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNseXxSYZ6WKL3xWxaF2HJ7kUhbYT1/RN2z7rUn2jTB0SCux27VUm+jhw42BDDPwtVcg0pBGZm1xi2Sq1VxlRJ8iFzapzVWS2HkJKtserJoShu8SbPwFsLeCSJaUZzjABiDshd7nFD0DrZzSzkBFBaC4oLNiWVAGE8nhJlg9uEmhCB6sR34CYu/64YuJakz9ZB0t2fLMCHCx1hBHR9rbX01sS5hcznNM5G/ZWxEq8Daa/zVvgAPkh08B25mfCh5GVK+iiIJwT0W9PoTLL7GM/W3uwKtlRGoCtgaFaJf+Jya0sWPG57Y5znodMWy5BEIXqwvmBzOy0tD1h6d0Thz/99AgMBAAECggEAEi7UDSScRzL8psME5oK9n8YdxO5i85YoW0TI+uQB9aKnHgeZByMotXVTFdBURlnewHZEThEseuH7X9kxaSJsSpjxxffWbGJnP1BEIloEgR9XKvVzP5G1nUErnx6NVeKPYgX3nGYuzr737l8v9YrALO3yJwIpzHciAz9dqP1EvD1AIi+k7Cs2zonFsfh6HGV3+98pKC6zx5WtVgvIHayO44+pSVXf/nxQa3vEiqVCmr5jaZV9jII4Z3jLyvuMNiiH6uApjZXXTe/uwD9LbGpUwjj6iJ+rKRxOgau3djilpTuyGRQhPmOnbQFV2tIeB0qYQGROD1dpBV/DLdgAW7fEWQKBgQC7zMwNsfJt65XauYbtznZOSlR8+W83rLB8becrS6AFK9dnl1YYN68MgGRXWoDKrGw6ZZHL4+mdTfNbI+kIOh2kaMiua4AtDZk2Djou7//hc33KYTyMS0JX1aJx6QdgKUKgRsieotBGX9vVytyU/evfHWtAGsbbkyDX2X5L9surNwKBgQDBJtm0CgyOYrAihge19fWOA5LscpsETwQ2S+d2kU9c45XVR7bCAeI0uSNwrFsP3mrqoG7PY/5QJW3GjHUWYO9rPlzupMwyUJe8eA3mITRKmros/26V+czC3Ja80DKAR8dXMtzCUJp3ckJ2cWAhpYdC9xcGLYzXoo2BAY2S8+bM6wKBgAxm8fLVlZT8weC379k0kUuKTRoTvuyGUwFkoKeTTvYIdyk4LU9cHgtNT1MVfvvjdyJEIuS8IGlrBG84Z+NLMznwUZBzOPvAsiWHuv/uiY9x+RSsZzs8nRz/1sJ/CSWok+XRxt3zEp4TJolv5DFSs4kAgFyzZ/6IMNsQWe7aVYjxAoGASOrGww4MIXCmN9JLdOkOs3cr7zFwzhaaa3kKP5upz1JnjSXOm70kTW0bMP0QP5Ri3oKEcqy4JD+NCqiZx0H/AIiBguIZ6GC+d7sG3Drqx8NTx4sCj4HHSEUIbu1WM1FlssiTyJOvTKqI5rf0Z59ayfAH9CAcDIfkTbBtZu3G0tMCgYA/AfoFHxh9HsjcOqvJtlw5zkexxidSN0WNA7upzsWkAfPVZUKjNTOoZGABIi64akKNe5r1PPJfJZ6jwOUkSwXIMC8HwLeMAIvmq8iUIUlV2GE5AAw/5xrgLRH+fSUr1SwOx/bA8EJJxeWvQ/aoQtXDZaUr6ECICJ2h15rcg4qvYg==";
        System.out.println(encryptByPrivateKey("你好啊", pri));

        String pub = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjbHl8UmGelii98VsWhdhye5FIW2E9f0Tds+61J9o0wdEgrsdu1VJvo4cONgQwz8LVXINKQRmZtcYtkqtVcZUSfIhc2qc1Vkth5CSrbHqyaEobvEmz8BbC3gkiWlGc4wAYg7IXe5xQ9A62c0s5ARQWguKCzYllQBhPJ4SZYPbhJoQgerEd+AmLv+uGLiWpM/WQdLdnyzAhwsdYQR0fa219NbEuYXM5zTORv2VsRKvA2mv81b4AD5IdPAduZnwoeRlSvooiCcE9FvT6Eyy+xjP1t7sCrZURqArYGhWiX/icmtLFjxue2Oc56HTFsuQRCF6sL5gczstLQ9YendE4c//fQIDAQAB";
       String data = "JLX3H98hiKkFzhlLSU6uGFO8PKQRkGRnRAi2G7hVYFOdcvyXYW9/oZK3dt12FBZ1K0kK1VLWO49gLtFbi86KkB/wEbN4L4Z3k2Zni3NyPwntCzm5GXTUpceEWapEKdn9ilEzNbIzE9+d2/HprDhOFjUl30vZ5RI0mzPWrX0KQSpk5EmALDuV8xiU5G4xNINwZzyoAZfEx9NZiCbo7E7d406hYa5kZsBU3h8jwyxYAM0CoW0ixDvNsUGnsM1Vty8mB/RXTX1rM72qivt2Ez86a9ChnC/RzQXhaxwCdXiTe5raURU0RWPkJUIAbHxuR1xvfWxraJzMMgG/IF+qRAxaVQ==";
        String str = decryptByPublicKey(data,pub);
        System.out.println(str);
    }
}
