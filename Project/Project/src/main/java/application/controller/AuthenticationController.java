package application.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import application.model.dto.JwtAuthenticationRequest;
import application.model.dto.UserTokenState;
import application.model.dto.UserLoginDTO;
import exception.ResourceConflictException;
import application.model.User;
import application.service.UserService;
import application.util.TokenUtils;


@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	/*
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<UserLoginDTO> login(
			@RequestBody JwtAuthenticationRequest authenticationRequest) {
		User user = userService.findByUsername(authenticationRequest.getUsername());
		if(user == null) {
			System.out.println("Username doesn't exist.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		else if(user.isDeleted()==false && user.isEnabled()==true){
			UserLoginDTO udto = new UserLoginDTO();
			udto.setRole(user.getRole());
			udto.setId(user.getId());
			return new ResponseEntity<>(udto,HttpStatus.OK);
		}
		else {
			System.out.println("User is not enabled.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}*/
	
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

		// Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
		// AuthenticationException
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		// Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
		// kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}
	


}
