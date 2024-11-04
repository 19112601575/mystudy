package com.example.exceptiontt.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtils {
    public static String createJWT(String id, String subject, long ttlMillis, SecretKey key){
        //获取签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("user")
                .setIssuedAt(now)
                .signWith(key,signatureAlgorithm);
        if(ttlMillis >= 0){
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(expDate);
        }
        return builder.compact();

    }


    public static Claims parseJWT(String jwt, SecretKey keySpec){
        return Jwts.parser()
                .setSigningKey(keySpec)
                .parseClaimsJws(jwt)
                .getBody();
    }

//    public static ResultCode validateJWT(String jwt){
//        ResultCode checkResult = new ResultCode();
//        Claims claims = null;
//        try {
//            claims = parseJWT(jwt);
//            checkResult.setSuccess(true);
//            checkResult.setClaims(claims);
//        } catch (ExpiredJwtException e) {
//            checkResult.setErrCode("解析失败");
//            checkResult.setSuccess(false);
//        }
//        return checkResult;
//    }
    public static void main(String[] args) {
        //生成密匙
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jwt = JWTUtils.createJWT("1","test",10000,key);
        System.out.println(jwt);
        System.out.println(JWTUtils.parseJWT(jwt,key));
    }
}
