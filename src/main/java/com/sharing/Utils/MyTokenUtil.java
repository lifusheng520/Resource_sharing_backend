package com.sharing.Utils;

/**
 * @author 李福生
 * @date 2022-3-5
 * @time 下午 04:52
 */

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * token工具类
 */
public class MyTokenUtil {
    /**
     * token过期时间30分钟
     */
    private static final long EXPIRE_TIME = 1000 * 60 * 30l;

    /**
     * 秘钥
     */
    private static RSAKey rsaKey;

    /**
     * 公钥
     */
    private static RSAKey publicRsaKey;

    static {
        try {
            rsaKey = new RSAKeyGenerator(2048).generate();
            publicRsaKey = rsaKey.toPublicJWK();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
    }

    public static String build(int id, String username) {
        try {
            // 根据服务端（签名方）持有的秘钥生成秘钥
            RSASSASigner signer = new RSASSASigner(rsaKey);

            // 建立载体
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("lfs")
                    .issuer("baigei.com.lfs")
                    .expirationTime(new Date(System.currentTimeMillis() + MyTokenUtil.EXPIRE_TIME))
                    .claim("id", id)
                    .claim("username", username)
                    .build();

            // 建立签名
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
            signedJWT.sign(signer);

            // 生成token
            String token = signedJWT.serialize();
            return token;
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Map<String, String> parse(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);

            Map<String, String> res = new HashMap<>();

            //获取载体中的数据
            Object id = jwt.getJWTClaimsSet().getClaim("id");
            Object username = jwt.getJWTClaimsSet().getClaim("username");

            if (!Objects.isNull(id))
                res.put("id", id.toString());
            if (!Objects.isNull(username))
                res.put("username", username.toString());

            //校验超时
            if (new Date().after(jwt.getJWTClaimsSet().getExpirationTime()))
                res.put("expired", "true");
            else
                res.put("expired", "false");

            return res;
        } catch (ParseException e) {
        }
        return null;
    }

}
