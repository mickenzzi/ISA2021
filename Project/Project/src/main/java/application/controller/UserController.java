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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.User;
import application.model.dto.UserDTO;
import application.service.UserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		List<User> users = userService.findAll();
		for (User u : users) {
				if (!u.getRole().equals("ADMIN") && !u.getUsername().equals("mickenzi") && u.isDeleted() == false && u.isEnabled()==true) {
					usersDTO.add(new UserDTO(u));
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

	@PutMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) throws Exception {
		User user = userService.findById(userDTO.getId());

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.updateUser(userDTO);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping(value = "/deleteUser/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> deleteUser(@RequestBody UserDTO userDTO) throws Exception {
		User user = userService.findById(userDTO.getId());
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.deleteUser(userDTO.getId());
		user = userService.findById(userDTO.getId());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	

	@GetMapping(value = "/enableUser/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> enableUser(@PathVariable("username") String username) throws Exception {
		User user = userService.findByUsername(username);	
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			userService.enableUser(user.getId());
			user = userService.findById(user.getId());
			System.out.println("The task /enableUser was successfully completed.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	@GetMapping(value = "/disableUser/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> disableUser(@PathVariable("username") String username) throws Exception {
		User user = userService.findByUsername(username);	
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			userService.disableUser(user.getId());
			user = userService.findById(user.getId());
			System.out.println("The task /disableUser was successfully completed.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
		if (userDTO.getFirstName().isEmpty() || userDTO.getLastName().isEmpty() || userDTO.getAddress().isEmpty()
				|| userDTO.getCity().isEmpty() || userDTO.getCountry().isEmpty() || userDTO.getPhone().isEmpty()
				|| userDTO.getEmail().isEmpty() || userDTO.getUsername().isEmpty() || userDTO.getPassword1().isEmpty()
				|| userDTO.getPassword2().isEmpty() || userDTO.getRole().isEmpty()) {
			System.out.println("Error-some field are empty");
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
				System.out.println("The task /createUser was successfully completed.");
				return new ResponseEntity<>(user, HttpStatus.CREATED);
				}
			} else {
				System.out.println("Error-passwords don't match or password are too low.");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

	}

}
