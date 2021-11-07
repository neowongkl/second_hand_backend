package com.easygoing.backend.services.core.config.security.util

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SignatureException
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Component
class JwtUtil {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var securityUtil: SecurityUtil

    //base64 key
    //TheEncodeBase64MustBeGreaterThan256Bit!!!
    private var SECRET_KEY = "VGhlRW5jb2RlQmFzZTY0TXVzdEJlR3JlYXRlclRoYW4yNTZCaXQhISE="

    private var expiryInMinutes : Long = 60

    @PostConstruct
    fun postConstruct(){
        SECRET_KEY = securityUtil.jwt.secretKey
        expiryInMinutes = securityUtil.jwt.expiryInMinutes
    }

    fun extractUserName(token: String): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun extractExpiration(token: String): LocalDateTime {
        return extractClaim(token) { obj: Claims -> obj.expiration }.let { _date->
            Timestamp(_date.time).toLocalDateTime()
        }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims)->T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        val key = getSecretKey()
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build().parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).isBefore(LocalDateTime.now())
    }

    fun generateToken(userDetails: UserDetails): String? {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, userDetails.username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        val issueDate = LocalDateTime.now()
        val expirationDate = issueDate.plusMinutes(expiryInMinutes)
        val key = getSecretKey()
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Timestamp.valueOf(issueDate))
            .setExpiration(Timestamp.valueOf(expirationDate))
            .signWith(key)
            .compact()
    }

    private fun getSecretKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun validateToken(token: String): Boolean {
        val key = getSecretKey()
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        }catch (e: SignatureException) {
            logger.error("Invalid JWT signature");
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token");
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token");
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token");
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty.");
        }
        return false
    }
}