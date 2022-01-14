package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.Adventure;
import application.model.User;
import application.model.dto.UserDTO;
import application.service.UserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/getAllUsers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getAllUsers(@PathVariable("id") Long id) {
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		List<User> users = userService.findAll();
		User user = new User();
		user = userService.findById(id);
		if(user.getUsername().equals("mickenzi")) {
			for(User u: users) {
				if(!u.getUsername().equals("mickenzi") && u.isEnabled()==true) {
					usersDTO.add(new UserDTO(u));
				}
			}
		}
		else {
			for(User u: users) {
				if(!u.getUsername().equals("mickenzi") && u.isEnabled()==true && !u.getRole().equals("ADMIN")) {
					usersDTO.add(new UserDTO(u));
				}
			}
		}
		System.out.println("The task /getAllUsers was successfully completed.");
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/getUserById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/getUserByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable("username") String username) {
		User user = userService.findByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) throws Exception {
		User user = userService.findById(userDTO.getId());
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!userDTO.getPassword1().equals(userDTO.getPassword2())) {
			System.out.println("Error-Passwords don't match.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.updateUser(userDTO);
		if(user.getPassword().equals(userDTO.getPassword1())) {
			System.out.println("Old password is the same as new password.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping(value = "/deleteUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Adventure> deleteUser(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		userService.deleteUser(id);
		System.out.println("The task /deleteUser was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@GetMapping(value = "/enableUser/{username}/{idRequest}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> enableUser(@PathVariable("username") String username, @PathVariable("idRequest") Long idRequest) throws Exception {
		User user = userService.findByUsername(username);	
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			userService.enableUser(user.getId(), idRequest);
			user = userService.findById(user.getId());
			System.out.println("The task /enableUser was successfully completed.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/approveDeleteRequest/{username}/{idRequest}/{text}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> approveDeleteRequest(@PathVariable("username") String username, @PathVariable("idRequest") Long idRequest, @PathVariable("text") String text) throws Exception {
		User user = userService.findByUsername(username);	
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			userService.approveDeleteRequest(user.getId(), idRequest, text);
			user = userService.findById(user.getId());
			System.out.println("The task /approveDeleteRequest was successfully completed.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/disableUser/{username}/{idRequest}/{text}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> disableUser(@PathVariable("username") String username, @PathVariable("idRequest") Long idRequest, @PathVariable("text") String text) throws Exception {
		User user = userService.findByUsername(username);	
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			userService.disableUser(user.getId(), idRequest,text);
			user = userService.findById(user.getId());
			System.out.println("The task /disableUser was successfully completed.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/rejectDeleteRequest/{username}/{idRequest}/{text}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> rejectDeleteRequest(@PathVariable("username") String username, @PathVariable("idRequest") Long idRequest, @PathVariable("text") String text) throws Exception {
		User user = userService.findByUsername(username);	
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			userService.rejectDeleteRequest(user.getId(), idRequest, text);
			user = userService.findById(user.getId());
			System.out.println("The task /rejectDeleteRequest was successfully completed.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
		if (userDTO.getFirstName().isEmpty() || userDTO.getLastName().isEmpty() || userDTO.getAddress().isEmpty()
				|| userDTO.getCity().isEmpty() || userDTO.getCountry().isEmpty() || userDTO.getPhone().isEmpty()
				|| userDTO.getEmail().isEmpty() || userDTO.getUsername().isEmpty() || userDTO.getPassword1().isEmpty()
				|| userDTO.getPassword2().isEmpty() || userDTO.getRole().isEmpty()) {
			System.out.println("Error-some fields are empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			if (userDTO.getPassword1().equals(userDTO.getPassword2()) && userDTO.getPassword1().length()>=3) {
				User existUser = this.userService.findByUsername(userDTO.getUsername());
				if (existUser != null) {
					System.out.println("Username already exists");
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				else {
				User user = userService.createUser(userDTO);
				if(user == null) {
					System.out.println("Mail is not valid or active.");
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				else {
					System.out.println("The task /createUser was successfully completed.");
					return new ResponseEntity<>(user, HttpStatus.CREATED);
				}
				}
			} else {
				System.out.println("Error-passwords don't match or password are too low.");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

	}

}
