package com.example.exceptiontt.jwt.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*jwt由三部分组成：
header.payload.Signature
header：token类型（jwt)和签名算法组成
payload：有效载荷，存放用户信息
Signature：密钥，对jwt进行加密，
*/
public class JwtUtils {
    private static final String jwtToken = "secretKey";//Signature,密钥

    public static String createToken(Long userId){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",userId);//payload,用户数据
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,jwtToken)//签发算法，密钥为jwtToken
                .setClaims(claims)//payload数据
                .setIssuedAt(new Date())//签发时间，生成不同的token
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 30));//有效时间30分钟
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String,Object> generateToken(String token){
        Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
        return (Map<String, Object>) parse.getBody();
    }

}
