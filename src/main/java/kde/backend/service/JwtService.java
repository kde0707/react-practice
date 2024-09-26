package kde.backend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
	static final long EXPIRATION_TIME = 86400000; //1 day ms
	static final String TOKEN_PREFIX = "Bearer ";
	static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String generateToken(String username) {
		String token = Jwts.builder()
						.setSubject(username)
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(key)
						.compact();
		return token;
	}
	public String getAuthUser(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if(token != null) {
			String user = Jwts.parserBuilder()
							.setSigningKey(key)
							.build()
							.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
							.getBody()
							.getSubject();

			if(user != null) return user;
		}

		return null;
	}
}