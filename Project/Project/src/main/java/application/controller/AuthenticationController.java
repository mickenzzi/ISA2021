package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.User;
import application.model.dto.JwtAuthenticationRequest;
import application.model.dto.UserLoginDTO;
import application.service.UserService;


@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
	
	@Autowired
	private UserService userService;
	
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
	}

}
