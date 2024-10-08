package kde.backend.controller;

import kde.backend.domain.AccountCredentials;
import kde.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@Autowired
	private JwtService jwtService;
	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials accountCredentials)   {
		UsernamePasswordAuthenticationToken upat
						= new UsernamePasswordAuthenticationToken(accountCredentials.getUsername(), accountCredentials.getPassword());
		Authentication auth = authenticationManager.authenticate(upat);

		String jwts = jwtService.generateToken(auth.getName());

		return ResponseEntity.ok()
						.header(HttpHeaders.AUTHORIZATION, "Bearer "+ jwts)
						.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
						.build();
	}
}
