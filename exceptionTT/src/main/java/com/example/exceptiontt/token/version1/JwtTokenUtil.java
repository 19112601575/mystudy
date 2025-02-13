package com.example.exceptiontt.token.version1;

import com.example.exceptiontt.exception.Resp;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//用于生成token的类
public class JwtTokenUtil {

    private static final String SECRET_KEY = "abcdefgabcdefghijklmnopqrstuvwxyz"; // 密钥
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;//加密方式

    //ttMillis是token持续时间
    public static String createToken(String id, long ttlMillis) {
        // 签名密钥
        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        // 设置JWT的签发时间和过期时间
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ttlMillis);
        // 使用指定的密钥和算法生成JWT
        /*
            iss：Issuer，发行者
            sub：Subject，主题
            aud：Audience，观众
            exp：Expiration time，过期时间
            nbf：Not before
            iat：Issued at，发行时间
            jti：JWT ID
         */
        return Jwts.builder().setSubject(id)//设置id
                .setIssuedAt(now) // 设置签发时间
                .setExpiration(expiration) // 设置过期时间
                .signWith(secretKeySpec, signatureAlgorithm) // 设置签名密钥和签名算法
                .compact(); // 生成JWT字符串
    }


    //验证token如果正确返回用户id
    public static Resp checkToken(String token) {
        try {
            // 解析token
            Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))) // 设置密钥
                    .parseClaimsJws(token) // 解析token
                    .getBody(); // 获取负载

            // 验证负载中的信息
            String subject = claims.getSubject(); // 获取用户ID或其他信息
            Date expiration = claims.getExpiration(); // 获取过期时间
            System.out.println(expiration.toString());

            // 验证token是否过期
            if (expiration.before(new Date())) {
                throw new TokenException("token失效");
            }
            return Resp.success(subject);

        } catch (ExpiredJwtException e) {
            // 当token过期时，会捕获到ExpiredJwtException异常
            return Resp.error("Token已过期");
        } catch (UnsupportedJwtException e) {
            // 当token不受支持时，会捕获到UnsupportedJwtException异常
            return Resp.error("Token不受支持");
        } catch (MalformedJwtException e) {
            // 当token格式错误时，会捕获到MalformedJwtException异常
            return Resp.error("Token格式错误");
        } catch (SignatureException e) {
            // 当token签名错误时，会捕获到SignatureException异常
            return Resp.error("Token签名错误");
        } catch (IllegalArgumentException e) {
            // 当token为空或非法时，会捕获到IllegalArgumentException异常
            return Resp.error("Token为空或非法");
        } catch (TokenException e) {
            // 处理TokenException
            return Resp.error("Token验证失败: " + e.getMessage());
        } catch (Exception e) {
            // 处理其他异常
            return Resp.error("发生未知错误: " + e.getMessage());
        }
    }
}
