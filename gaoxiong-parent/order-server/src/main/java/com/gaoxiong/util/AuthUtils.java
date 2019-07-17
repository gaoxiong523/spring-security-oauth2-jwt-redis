package com.gaoxiong.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaoxiong
 * @ClassName AuthUtils
 * @Description TODO
 * @date 2019/7/17 11:21
 */
public class AuthUtils {
    public static String getReqUser(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer");
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("SigningKey".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
        String localUser = (String) claims.get("userinfo");
        // 拿到当前用户
        return localUser;
    }


}
