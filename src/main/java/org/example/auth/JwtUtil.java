package org.example.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;
//token generation

//responsibilities are :-generate JWT,validate JWT,Extract username from JWT
public class JwtUtil {

    private static final String SECRET = "SECRET";
    private static final String issuer = "ems-app";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public static final String generateJWTToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()*60*60*1000))
                .sign(algorithm);
    }
    public static DecodedJWT validateJWTToken(String token) {
        JWTVerifier jwtVerifier= JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        return jwtVerifier.verify(token);
    }
    public static String getUsernameFromJWT(String token) {
        return validateJWTToken(token).getSubject();
    }

    public static void main(String[] args) {
        String token = generateJWTToken("admin");
        System.out.println(token);

        String tokenNew ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImVtcy1hcHAiLCJpYXQiOjE3NDk3OTExMzQsImV4cCI6NjI5OTI0ODA4NDQ3MDAwMH0.PhZyilggoh92fu1Gca6lOGcDpX33EbTEbqHtSMYbcjc"
                ;

    }
    /*
    JWT Token format :-
    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0
    .KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
    header
    payload (Base 64 encryption) (not hashed)
    signature (hashed part)
     */

}
