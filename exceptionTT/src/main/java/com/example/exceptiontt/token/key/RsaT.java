package com.example.exceptiontt.token.key;

import java.util.Map;

public class RsaT {
    /**
     * RSA数据加密和解密
     *
     * @throws Exception
     */
    public static void encryptAndDecrypt() throws Exception {
        Map<String, String> keyMap = RsaUtil.generateKey();
        String publicKeyStr = keyMap.get("publicKeyStr");
        String privateKeyStr = keyMap.get("privateKeyStr");
        System.out.println("-----------------生成的公钥和私钥------------------------------");
        System.out.println("获取到的公钥：" + publicKeyStr);
        System.out.println("获取到的私钥：" + privateKeyStr);
        // 待加密数据
        String data = "tranSeq=1920542585&amount=100&payType=wechat";
        // 公钥加密
        System.out.println("-----------------加密和解密------------------------------");
        System.out.println("待加密的数据：" + data);
        String encrypt = RsaUtil.encryptByPublicKey(data, publicKeyStr);
        System.out.println("加密后数据：" + encrypt);
        // 私钥解密
        String decrypt = RsaUtil.decryptByPrivateKey(encrypt, privateKeyStr);
        System.out.println("解密后数据：" + decrypt);
    }

    public static void encryptAndDecryptRevise() throws Exception {
        Map<String, String> keyMap = RsaUtil.generateKey();
        String publicKeyStr = keyMap.get("publicKeyStr");
        String privateKeyStr = keyMap.get("privateKeyStr");
        System.out.println("-----------------生成的公钥和私钥------------------------------");
        System.out.println("获取到的公钥：" + publicKeyStr);
        System.out.println("获取到的私钥：" + privateKeyStr);
        // 待加密数据
        String data = "tranSeq=1920542585&amount=100&payType=wechat";
        // 私钥加密
        System.out.println("-----------------加密和解密------------------------------");
        System.out.println("待加密的数据：" + data);
        String encrypt = RsaUtil.encryptByPrivateKey(data, privateKeyStr);
        System.out.println("加密后数据：" + encrypt);
        // 公钥解密
        String decrypt = RsaUtil.decryptByPublicKey(encrypt, publicKeyStr);
        System.out.println("解密后数据：" + decrypt);
    }

    public static void main(String[] args) throws Exception{
//        encryptAndDecryptRevise();
        //私钥加密
        String  key = RsaUtil.encryptByPrivateKey("tranSeq=1920542585&amount=100&payType=wechat","MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDErp/AL9GiFluUdOF9ZV3BNYTuyT6yaMlQPYoNYtWFOIKjbTPwNWot9DEJxq0DrQVAvoagpxkLXiteLetP259xrMtjT/Y+RsqTZik1p2/hHr2y2mb6jfazVMyKONuEP9w1IGr4pAA0ZPsd41slXQnrylmw9DiFxLyMbClp7Zu38AsK1KKdTiyq58fVBr9aRXreAnTwT/96Uo5Lwscw2VHu1yPkJwqQmtcdJLILst1ZzqYPNTiUFFjk8Nccc5WJ47FTCHh7D7U0hM7vdK2s5UETOquBgF5+Vjo+uFOVkWx63zrPIGJQdEMW6ckRqEKOwRMMlG289NJsyYDzihPSpD7tAgMBAAECgf8sM5sIuD7+P3+ImWacUF7St84GIGddKJXzg5zHQzvKX4J0BNKFOoz3pMdgjTTda2ttdtuXg4/H/j/A2aIZtspz48fz8De8T8YmBOC1g4VL8HqPYh+gc+DKbLvPZaJ4a51m/dEMmJu3bvpV8G1HPKGXwp3dFdKNCmWZsehpkKd/amNtV/T8+F5QoEl0DsBbNRYnC+ugr46PtyHKgcEOeCRXOdq4abmuYBFQNSgHPLSL/aKFdKrlV893DJgwODGK647pgtYrU2UAzqyLe3zTJFs9vC3LEtqkYOIrA+g8o5c2rRJ5FonyLVI4V5oynaggixufjRBd+ziip69nUV20pSECgYEA608+rPKvLQISZClKQXI7lS7kLgdb/lXudBdixL6WLa9daiW5RHeH5pi6DNkBCwrmJBadKLF/R6O+8El9qq8zMnHQPp3Klxns3JK1UH3ZeTsfuEXQJ9mX4W5cQW1C5CQa0jcplTl5FmW3054rqYMOrPKUNqOPr38seCeOJCviuAUCgYEA1fni3CmcObf8iZVwO/2UiLe4zF01VXmAdfxvFeQRUBshpTfAVUCsO8Qn41UiPz8OQy2WCPsoBj5Ci5auNhKLj1kM2Rlb0sbu0MIYghVnOrf1MBEI/qgolBVVgcI0mjMrjeZ0OhoGcdJu93+Raroe0CZ+fhBLYXHfPe5hhaWFJ8kCgYEAglS7BznHsush2p5QBZ3KyJ9XPPNQjbd0wpItX4GcVqN53xAT5Is8F4niCrmq7T3VKInp9B8Tu09Ds31RAFfXyInnaLcm/bgbTDRp/rIl4RLRR9RLLbdEe1UP/iERWqFwxZxOCNvzaGdggPJrhpETcNFPLFA9hluu+sIV5Yz1Hy0CgYEAszvNu31fTzm+X8C9coLGmH7MXOL2edJ8uFfq9PtFRUR0umAoy0CRBL8aamI1faTj3YGh24QF0rT0KNjetIx0Om7tRCzprXTVNihfPxeOkLDmwIyEbEuPMfts1HRIe6HBKeuQD9sc6trJ+Kbyt+OPS+vyqMYdXlJ0HPxA1CIBl4ECgYAQPIYmbEQtXF5fS4dCfpIuMgmeHrr9xE8kx/gmJGrgRFeH5XUA14GLoz1JxESw0l4kifzCgTeewKjuc658xP+QpBM7TqB7adKsXWoCVf9fee/JqoHz9SebWORKQoxofOBimv81ABhnVeZV4XirjfvK2DeCclOl55GBMyYe3jm4ZA==");
       //加密后的数据
        System.out.println("加密后的数据" + key);
        //公钥解密
        String key2 = RsaUtil.decryptByPublicKey(key,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxK6fwC/RohZblHThfWVdwTWE7sk+smjJUD2KDWLVhTiCo20z8DVqLfQxCcatA60FQL6GoKcZC14rXi3rT9ufcazLY0/2PkbKk2YpNadv4R69stpm+o32s1TMijjbhD/cNSBq+KQANGT7HeNbJV0J68pZsPQ4hcS8jGwpae2bt/ALCtSinU4squfH1Qa/WkV63gJ08E//elKOS8LHMNlR7tcj5CcKkJrXHSSyC7LdWc6mDzU4lBRY5PDXHHOVieOxUwh4ew+1NITO73StrOVBEzqrgYBeflY6PrhTlZFset86zyBiUHRDFunJEahCjsETDJRtvPTSbMmA84oT0qQ+7QIDAQAB");
        //解密后的数据
        System.out.println("解密后的数据" + key2);


    }

}
