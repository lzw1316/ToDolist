package com.example.todolist.common.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    /**
     * @param secretKey 密钥
     * @param ttlMillis 过期时间
     * @param claims    具体信息
     * @return
     */

    //创建jwt令牌
    public static String createJwt(String secretKey, long ttlMillis, Map<String, Object> claims) {
        //指定256签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long ttlTime = System.currentTimeMillis() + ttlMillis;
        //生成jwt令牌的时间
        Date time = new Date(ttlTime);


        ZoneId cstZone = ZoneId.of("Asia/Shanghai");

        // 获取当前时间
        LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(ttlMillis); // 过期时间为1天后

        // 转换为带有时区信息的Instant
        Instant instant = expirationTime.atZone(cstZone).toInstant();

        // 创建带有时区信息的Date对象
        Date expirationDate = Date.from(instant);




        //设置jwt令牌
        JwtBuilder jwtBuilder = Jwts.builder()
                //设置声明
                .setClaims(claims)
                //设置签名算法和签名算法的密钥
                .signWith(signatureAlgorithm, secretKey)
                //设置过期时间
                .setExpiration(expirationDate);

        return jwtBuilder.compact();
    }

    /**
     * @param token 加密后的token
     * @return
     */
    //解密jwt
    public static Claims praseJwt(String secretKey, String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
